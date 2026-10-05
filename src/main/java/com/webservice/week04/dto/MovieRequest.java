package com.webservice.week04.dto;

public record MovieRequest(
        String title,     // 영화 제목
        String director,  // 감독
        String genre,     // 장르
        int year,         // 개봉 연도
        int price         // 가격
) {}