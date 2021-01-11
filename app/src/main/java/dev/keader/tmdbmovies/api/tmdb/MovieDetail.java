package dev.keader.tmdbmovies.api.tmdb;

import java.util.List;

public class MovieDetail {
    private String originalTitle;
    private boolean released;
    private String releaseDate;
    private int voteCount;
    private List<Genre> genres;
    private List<Company> productionCompanies;
    private boolean adult;
    private int id;
    private String title;
    private String overview;
    private String popularity;
    private String posterPath;
    private double voteAverage;

    public MovieDetail(String originalTitle, boolean released, String releaseDate, int voteCount, List<Genre> genres, List<Company> productionCompanies, boolean adult, int id, String title, String overview, String popularity, String posterPath, double voteAverage) {
        this.originalTitle = originalTitle;
        this.released = released;
        this.releaseDate = releaseDate;
        this.voteCount = voteCount;
        this.genres = genres;
        this.productionCompanies = productionCompanies;
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

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Company> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(List<Company> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    @Override
    public String toString() {
        return super.toString() + "MovieDetail{" +
                "originalTitle='" + originalTitle + '\'' +
                ", released=" + released +
                ", releaseDate='" + releaseDate + '\'' +
                ", voteCount=" + voteCount +
                ", genres=" + genres +
                ", productionCompanies=" + productionCompanies +
                '}';
    }
}

