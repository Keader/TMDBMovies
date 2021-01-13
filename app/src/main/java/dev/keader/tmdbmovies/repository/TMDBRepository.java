package dev.keader.tmdbmovies.repository;


import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.io.IOException;

import javax.inject.Inject;

import dev.keader.tmdbmovies.AppExecutors;
import dev.keader.tmdbmovies.Constants;
import dev.keader.tmdbmovies.api.TMDBService;
import dev.keader.tmdbmovies.api.tmdb.MovieDetail;
import dev.keader.tmdbmovies.api.tmdb.MovieResult;
import dev.keader.tmdbmovies.database.dao.TMDBDao;
import dev.keader.tmdbmovies.database.model.MovieDTO;
import dev.keader.tmdbmovies.database.model.MovieWithRelations;
import dev.keader.tmdbmovies.view.adapters.paging.MovieBoundaryCallback;
import retrofit2.Response;
import timber.log.Timber;

public class TMDBRepository {
    private final TMDBService tmdbService;
    private final AppExecutors executors;
    private final TMDBDao database;

    @Inject
    public TMDBRepository(TMDBService tmdbService, AppExecutors executors, TMDBDao database) {
        this.tmdbService = tmdbService;
        this.executors = executors;
        this.database = database;
    }

    public LiveData<PagedList<MovieWithRelations>> loadMovies() {
        DataSource.Factory<Integer, MovieWithRelations> dataSourceFactory = database.getMoviePaged();
        LivePagedListBuilder builder = new LivePagedListBuilder(dataSourceFactory, 20);
        return builder
                .setBoundaryCallback(new MovieBoundaryCallback(tmdbService, executors, database))
                .build();
    }

    /*

    public String loadMovies(int page) {
        executors.networkIO().execute(() -> {
            try {
                Response<MovieResult> result = tmdbService.getMovies(page).execute();
                if (!result.isSuccessful())
                    throw new IOException("Network Error Code: " + result.code());


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

     */
}
