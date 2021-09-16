package com.example.mymovieapp;

/* Movie class to save relevant information about a movie */
public class Movie {
    String name, image, releaseDate, description, voteRating;

    public Movie(String name, String image, String releaseDate, String description, String voteRating) {
        this.name = name;
        this.image = image;
        this.releaseDate = releaseDate;
        this.description = description;
        this.voteRating = voteRating;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getVoteRating() {
        return voteRating;
    }
}
