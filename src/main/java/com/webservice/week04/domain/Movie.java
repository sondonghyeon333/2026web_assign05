package com.webservice.week04.domain;

public class Movie {
    private Long id;
    private String title;     // 영화 제목
    private String director;  // 감독
    private String genre;     // 장르
    private int year;         // 개봉 연도
    private int price;        // 가격

    // 기본 생성자
    public Movie() {}

    // 전체 필드를 받는 생성자
    public Movie(Long id, String title, String director, String genre, int year, int price) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.year = year;
        this.price = price;
    }

    // Getter / Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
}