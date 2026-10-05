package com.webservice.week04.dto;

public record MovieResponse(
        Long id,
        String title,
        String director,
        String genre,
        int year,
        int price
) {}