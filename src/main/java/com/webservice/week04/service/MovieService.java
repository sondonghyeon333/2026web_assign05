package com.webservice.week04.service;

import com.webservice.week04.domain.Movie;
import com.webservice.week04.dto.*;
import com.webservice.week04.repository.MovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class MovieService {

    private final MovieRepository repository;

    // 스프링이 레포지토리를 알아서 넣어줌
    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    // 영화 등록: 클라이언트가 보낸 Request를 받아서 새 영화 객체로 만든 다음 저장하기
    public MovieResponse create(MovieRequest r) {
        validateRequest(r); //잘못된 입력 처리
        Movie movie = new Movie(null, r.title(), r.director(), r.genre(), r.year(), r.price());
        return toResponse(repository.save(movie));
    }

    // 전체 조회: 저장된 영화들 싹 다 가져와서 Response 형태로 바꿔서 보여주기
    public List<MovieResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    // 단건 조회: id로 영화 하나 찾기
    public MovieResponse findById(Long id) {
        return toResponse(findMovie(id));
    }

    //  수정: id로 기존 영화 찾은 다음에 새로운 내용으로 바꾸기
    public MovieResponse update(Long id, MovieRequest r) {
        validateRequest(r); // 잘못된 입력처리
        Movie m = findMovie(id);
        m.setTitle(r.title());
        m.setDirector(r.director());
        m.setGenre(r.genre());
        m.setYear(r.year());
        m.setPrice(r.price());
        return toResponse(repository.update(m));
    }

    // 삭제: id에 해당하는 영화가 있는지 먼저 확인하고 지우기
    public void delete(Long id) {
        findMovie(id);
        repository.deleteById(id);
    }

    // Step 5 확장 기능 : 지금 저장된 영화가 총 몇 개인지 개수 세서 알려주기
    public long getMovieCount() {
        return repository.count();
    }
    // Step 5 확장 기능 : 잘못된 입력 처리 등록/수정 시 값이 올바른지 검사하기
    private void validateRequest(MovieRequest r) {
        // 제목, 감독, 장르가 비어있거나 공백인 경우 -> 400 Bad Request
        if (r.title() == null || r.title().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "영화 제목은 필수입니다.");
        }
        if (r.director() == null || r.director().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "감독명은 필수입니다.");
        }
        if (r.genre() == null || r.genre().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "장르는 필수입니다.");
        }

        // 가격이 음수(-)인 경우 -> 400 Bad Request
        if (r.price() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "가격은 음수일 수 없습니다.");
        }

        // 연도가 허용 범위를 벗어난 경우 : 1900년 이전 또는 미래 2100년 이후
        if (r.year() < 1900 || r.year() > 2100) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "유효하지 않은 개봉 연도입니다.");
        }
    }
    // id로 영화 찾기
    private Movie findMovie(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found: " + id)
        );
    }

    // Movie를 MovieResponse로
    private MovieResponse toResponse(Movie m) {
        return new MovieResponse(m.getId(), m.getTitle(), m.getDirector(), m.getGenre(), m.getYear(), m.getPrice());
    }
}