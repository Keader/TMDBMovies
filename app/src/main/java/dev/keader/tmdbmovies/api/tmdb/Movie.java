package dev.keader.tmdbmovies.api.tmdb;

public class Movie {
    private boolean adult;
    private int id;
    private String title;
    private String overview;
    private String popularity;
    private String posterPath;
    private double voteAverage;

    public Movie(boolean adult, int id, String title, String overview, String popularity, String posterPath, double voteAverage) {
        this.adult = adult;
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }
}
