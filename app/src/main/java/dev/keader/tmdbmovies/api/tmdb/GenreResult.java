package dev.keader.tmdbmovies.api.tmdb;

import java.util.List;

public class GenreResult {
    private List<Genre> genres;

    public GenreResult(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Genre> getGenres() {
        return genres;
    }
}
