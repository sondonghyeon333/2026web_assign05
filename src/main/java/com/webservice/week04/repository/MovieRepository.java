package com.webservice.week04.repository;

import com.webservice.week04.domain.Movie;
import java.util.List;
import java.util.Optional;

public interface MovieRepository {
    //저장
    Movie save(Movie movie);
    // 모든 영화 리스트 조회
    List<Movie> findAll();
    // ID를 통해 영화 조회
    Optional<Movie> findById(Long id);
    //수정
    Movie update(Movie movie);
    //삭제
    void deleteById(Long id);

    // Step 5 확장 기능: 현재 저장된 데이터 개수 조회
    long count();
}
