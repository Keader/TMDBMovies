package dev.keader.tmdbmovies.repository;


import java.io.IOException;

import javax.inject.Inject;

import dev.keader.tmdbmovies.AppExecutors;
import dev.keader.tmdbmovies.Constants;
import dev.keader.tmdbmovies.api.TMDBService;
import dev.keader.tmdbmovies.api.tmdb.MovieDetail;
import dev.keader.tmdbmovies.api.tmdb.MovieResult;
import retrofit2.Response;
import timber.log.Timber;

public class TMDBRepository {
    private final TMDBService tmdbService;
    private final AppExecutors executors;

    @Inject
    public TMDBRepository(TMDBService tmdbService, AppExecutors executors) {
        this.tmdbService = tmdbService;
        this.executors = executors;
    }

    public String loadMovies(int page) {
        executors.networkIO().execute(() -> {
            try {
                Response<MovieResult> result = tmdbService.getMovies(page).execute();
                if (!result.isSuccessful())
                    throw new IOException("Network Error Code: " + result.code());
                // TODO: WAITING IMPLEMENT DB
            } catch (IOException e) {
                Timber.e(e);
            }
        });
        return "Finalizado";
    }

    public String loadMovieDetail(long movieId) {
        executors.networkIO().execute(() -> {
            try {
                Response<MovieDetail> result = tmdbService.getMovieDetail(movieId, Constants.TMDB_EXTERNAL_SOURCE).execute();
                if (!result.isSuccessful())
                    throw new IOException("Network Error Code: " + result.code());
                // TODO: WAITING IMPLEMENT DB
            } catch (IOException e) {
                Timber.e(e);
            }
        });
        return "Finalizado";
    }
}
