package com.example.mymovieapp;

public class Movie {
    String name;
    String image;

    public Movie(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }
}
