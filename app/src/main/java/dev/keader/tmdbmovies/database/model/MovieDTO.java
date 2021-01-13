package dev.keader.tmdbmovies.database.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "Movie")
public class MovieDTO {
    @PrimaryKey
    private int id;
    private String title;
    private String overview;
    private double popularity;
    private String posterPath;
    private int index;
    private String releaseDate;
    private double voteAverage;
    // Additional data (detail)
    private String originalTitle;
    private int voteCount;
    private String backdropPath;
    private boolean released;

    public MovieDTO(int id, String title, String overview, double popularity, String posterPath, int index, String releaseDate, double voteAverage, String originalTitle, int voteCount, String backdropPath, boolean released) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.index = index;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.originalTitle = originalTitle;
        this.voteCount = voteCount;
        this.backdropPath = backdropPath;
        this.released = released;
    }

    @Ignore
    public MovieDTO(int id, String title, String overview, double popularity, int index, double voteAverage, String posterPath, String releaseDate) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.popularity = popularity;
        this.index = index;
        this.voteAverage = voteAverage;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
    }

    @Ignore
    public MovieDTO(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieDTO movieDTO = (MovieDTO) o;
        return id == movieDTO.id &&
                Double.compare(movieDTO.popularity, popularity) == 0 &&
                index == movieDTO.index &&
                Double.compare(movieDTO.voteAverage, voteAverage) == 0 &&
                voteCount == movieDTO.voteCount &&
                released == movieDTO.released &&
                Objects.equals(title, movieDTO.title) &&
                Objects.equals(overview, movieDTO.overview) &&
                Objects.equals(posterPath, movieDTO.posterPath) &&
                Objects.equals(releaseDate, movieDTO.releaseDate) &&
                Objects.equals(originalTitle, movieDTO.originalTitle) &&
                Objects.equals(backdropPath, movieDTO.backdropPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, overview, popularity, posterPath, index, voteAverage, originalTitle, released, releaseDate, voteCount, backdropPath);
    }
}
