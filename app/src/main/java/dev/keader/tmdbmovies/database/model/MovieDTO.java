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
    private int page;
    private String releaseDate;
    private double voteAverage;
    private String genresFormatted;
    // Additional data (detail)
    private String originalTitle;
    private int voteCount;
    private String backdropPath;
    private boolean released;

    public MovieDTO(int id, String title, String overview, double popularity, String posterPath, int page, double voteAverage, String originalTitle, boolean released, String releaseDate, String genresFormatted, int voteCount, String backdropPath) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.page = page;
        this.voteAverage = voteAverage;
        this.originalTitle = originalTitle;
        this.released = released;
        this.releaseDate = releaseDate;
        this.genresFormatted = genresFormatted;
        this.voteCount = voteCount;
        this.backdropPath = backdropPath;
    }

    @Ignore
    public MovieDTO(int id, String title, String overview, double popularity, double voteAverage, String posterPath, String releaseDate, int page, String genresFormatted) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.popularity = popularity;
        this.voteAverage = voteAverage;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.page = page;
        this.genresFormatted = genresFormatted;
    }

    public String getGenresFormatted() {
        return genresFormatted;
    }

    public void setGenresFormatted(String genresFormatted) {
        this.genresFormatted = genresFormatted;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
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
                page == movieDTO.page &&
                Double.compare(movieDTO.voteAverage, voteAverage) == 0 &&
                released == movieDTO.released &&
                title.equals(movieDTO.title) &&
                Objects.equals(overview, movieDTO.overview) &&
                Objects.equals(posterPath, movieDTO.posterPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, overview, popularity, posterPath, page, voteAverage, originalTitle, released, releaseDate, voteCount, backdropPath);
    }
}
