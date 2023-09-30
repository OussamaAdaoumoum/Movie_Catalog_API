package com.app.ratings_data_service.models;

public class Rating {
    public String movieID;
    public int rating;

    public Rating(String movieID, int rating) {
        this.movieID = movieID;
        this.rating = rating;
    }

    public String getMovieID() {
        return movieID;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
