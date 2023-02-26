package com.rikesh.cinemaghar.Model;

public class Movie {
    private String title;
    private String description;
    private String image;
    private String videoId;
    private String backDrop_image;
    private String release_date;
    private double average_vote ;

    public Movie() {

    }

    public Movie(String title, String description, String image, String backDrop_image, String release_date, double average_vote, String videoId) {
        this.title = title;
        this.videoId = videoId;
        this.description = description;
        this.image = image;
        this.backDrop_image = backDrop_image;
        this.release_date = release_date;
        this.average_vote = average_vote;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBackDrop_image() {
        return backDrop_image;
    }

    public void setBackDrop_image(String backDrop_image) {
        this.backDrop_image = backDrop_image;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public double getAverage_vote() {
        return average_vote;
    }

    public void setAverage_vote(double average_vote) {
        this.average_vote = average_vote;
    }


}
