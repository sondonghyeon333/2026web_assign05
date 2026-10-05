package com.webservice.week04.controller;

import com.webservice.week04.dto.*;
import com.webservice.week04.service.MovieService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/movies") // 컨트롤러 안의 주소들은  /api/movies가 붙음
public class MovieController {

    private final MovieService movieService;

    // 서비스 가져다 쓰려고 생성자 주입하기
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    // 영화 등록 POST
    @PostMapping
    public ResponseEntity<MovieResponse> create(@RequestBody MovieRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.create(request));
    }

    // 전체 영화 목록 조회 GET
    @GetMapping
    public List<MovieResponse> findAll() {
        return movieService.findAll();
    }

    // 특정 영화 1개만 조회 GET 요청 뒤에 /id
    @GetMapping("/{id}")
    public MovieResponse findById(@PathVariable Long id) {
        return movieService.findById(id);
    }

    // 영화 정보 수정 PUT 뒤에 /id
    @PutMapping("/{id}")
    public MovieResponse update(@PathVariable Long id, @RequestBody MovieRequest request) {
        return movieService.update(id, request);
    }

    // 영화 삭제 DELETE 요청 뒤에 /id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        movieService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Step 5 확장 기능 현재 저장된 영화가 총 몇 개인지 알려줌
    @GetMapping("/count")
    public long getCount() {
        return movieService.getMovieCount();
    }
}