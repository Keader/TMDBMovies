package dev.keader.tmdbmovies.repository;

import androidx.lifecycle.LiveData;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dev.keader.tmdbmovies.AppExecutors;
import dev.keader.tmdbmovies.api.TMDBService;
import dev.keader.tmdbmovies.api.tmdb.Movie;
import dev.keader.tmdbmovies.api.tmdb.MovieResult;
import retrofit2.Response;
import timber.log.Timber;

public class TMDBRepository {
    private final TMDBService tmdbService;
    private final AppExecutors executors;


    public TMDBRepository(TMDBService tmdbService, AppExecutors executors) {
        this.tmdbService = tmdbService;
        this.executors = executors;
    }

    public LiveData<List<Movie>> loadMovies(int page) {
        executors.networkIO().execute(() -> {
            try {
                Response<MovieResult> result = tmdbService.getMovies(page).execute();
                if (!result.isSuccessful())
                    throw new IOException("Network Error Code: " + result.code());

                Timber.d(result.body().toString());
            } catch (IOException e) {
                Timber.e(e);
            }
        });
        // TODO: Implement DB
        return null;
    }
}
