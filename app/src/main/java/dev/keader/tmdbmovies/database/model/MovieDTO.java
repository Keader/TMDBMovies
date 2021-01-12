package dev.keader.tmdbmovies.database.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Movie")
public class MovieDTO {
    @PrimaryKey
    private int id;
    private String title;
    private String overview;
    private double popularity;
    private String posterPath;
    // Additional data (detail)
    private double voteAverage;
    private String originalTitle;
    private boolean released;
    private String releaseDate;
    private int voteCount;
    private String backdropPath;

    public MovieDTO(int id, String title, String overview, double popularity, String posterPath, double voteAverage, String originalTitle, boolean released, String releaseDate, int voteCount, String backdropPath) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.originalTitle = originalTitle;
        this.released = released;
        this.releaseDate = releaseDate;
        this.voteCount = voteCount;
        this.backdropPath = backdropPath;
    }

    @Ignore
    public MovieDTO(int id, String title, String overview, double popularity, String posterPath) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.popularity = popularity;
        this.posterPath = posterPath;
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

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
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

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public boolean isReleased() {
        return released;
    }

    public void setReleased(boolean released) {
        this.released = released;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }
}
