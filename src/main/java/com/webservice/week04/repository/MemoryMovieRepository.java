package com.webservice.week04.repository;

import com.webservice.week04.domain.Movie;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class MemoryMovieRepository implements MovieRepository {

    // 자바 Map에 영화 데이터를 저장
    private final Map<Long, Movie> store = new LinkedHashMap<>();

    // 영화 등록할 때 id를 1씩 차례대로 늘려주려고 만든 번호표 발행기 같은 거
    private long sequence = 0L;

    // 영화 등록: id 하나씩 더해서 부여하고 맵에 집어넣기
    @Override
    public Movie save(Movie movie) {
        movie.setId(++sequence); // id 자동 생성!
        store.put(movie.getId(), movie); // 메모리 맵에 저장 완료
        return movie;
    }

    // 전체 조회: 맵에 저장된 영화들 전부 다 꺼내서 리스트로  보여주기
    @Override
    public List<Movie> findAll() {
        return new ArrayList<>(store.values());
    }

    // 단건 조회: id로 딱 한 개만 찾기
    @Override
    public Optional<Movie> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    // 수정: 기존에 있던 id 자리에 새로운 영화 데이터를 덮어씌우기
    @Override
    public Movie update(Movie movie) {
        store.put(movie.getId(), movie);
        return movie;
    }

    //  삭제: id에 해당하는 영화를 메모리 맵에서 지우기
    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }

    // Step 5 확장 기능 : 지금 메모리에 영화가 총 몇 개 들어있는지 개수 세서 알려주기
    @Override
    public long count() {
        return store.size();
    }
}