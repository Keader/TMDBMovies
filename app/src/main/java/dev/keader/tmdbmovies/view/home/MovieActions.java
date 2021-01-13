package dev.keader.tmdbmovies.view.home;

import dev.keader.tmdbmovies.database.model.MovieWithRelations;

public interface MovieActions {
    void onMovieClick(MovieWithRelations movieWithRelations);
}
