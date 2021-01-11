package dev.keader.tmdbmovies.api.tmdb;

import java.util.List;

public class MovieDetail extends Movie {
    private String originalTitle;
    private boolean released;
    private String releaseDate;
    private int voteCount;
    private List<Genre> genres;
    private List<Company> productionCompanies;

    public MovieDetail(boolean adult, int id, String title, String overview, String popularity, String posterPath, double voteAverage, String originalTitle, boolean released, String releaseDate, int voteCount, List<Genre> genres, List<Company> productionCompanies) {
        super(adult, id, title, overview, popularity, posterPath, voteAverage);
        this.originalTitle = originalTitle;
        this.released = released;
        this.releaseDate = releaseDate;
        this.voteCount = voteCount;
        this.genres = genres;
        this.productionCompanies = productionCompanies;
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
}

