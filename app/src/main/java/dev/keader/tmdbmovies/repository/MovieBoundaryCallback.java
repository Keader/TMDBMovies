package dev.keader.tmdbmovies.repository;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dev.keader.tmdbmovies.AppExecutors;
import dev.keader.tmdbmovies.api.TMDBService;
import dev.keader.tmdbmovies.api.tmdb.Genre;
import dev.keader.tmdbmovies.api.tmdb.GenreResult;
import dev.keader.tmdbmovies.api.tmdb.Movie;
import dev.keader.tmdbmovies.api.tmdb.MovieResult;
import dev.keader.tmdbmovies.database.TMDBDatabase;
import dev.keader.tmdbmovies.database.dao.TMDBDao;
import dev.keader.tmdbmovies.database.model.MovieDTO;
import dev.keader.tmdbmovies.database.model.MovieGenre;
import dev.keader.tmdbmovies.database.model.MovieWithRelations;
import retrofit2.Response;
import timber.log.Timber;

public class MovieBoundaryCallback extends PagedList.BoundaryCallback<MovieWithRelations> {
    private final TMDBService tmdbService;
    private final AppExecutors executors;
    private final TMDBDao tmdbDao;
    private final TMDBDatabase database;
    private boolean isRunning;
    private int maxPages;
    private boolean isFirstLoad;
    private final OnLoadError errorHandler;

    public MovieBoundaryCallback(TMDBService tmdbService, AppExecutors executors, TMDBDao tmdbDao, TMDBDatabase database, OnLoadError errorHandler) {
        this.tmdbService = tmdbService;
        this.executors = executors;
        this.tmdbDao = tmdbDao;
        this.database = database;
        this.errorHandler = errorHandler;
        isRunning = false;
        isFirstLoad = true;
        maxPages = -1;
    }

    public interface OnLoadError {
        void onLoadError(int code);
    }

    @Override
    public void onZeroItemsLoaded() {
        if (isFirstLoad) {
            loadPage(1, true);
            isFirstLoad = false;
        }
    }

    @Override
    public void onItemAtFrontLoaded(@NonNull MovieWithRelations itemAtFront) {
        int page = (itemAtFront.getMovie().getIndex() / 20) + 1;
        int previousPage = page - 1;
        if (previousPage > 0)
            loadPage(previousPage, isFirstLoad);
        else if (isFirstLoad) {
            loadPage(1, true);
            isFirstLoad = false;
        }
    }

    @Override
    public void onItemAtEndLoaded(@NonNull MovieWithRelations itemAtEnd) {
        int page = (itemAtEnd.getMovie().getIndex() / 20) + 1;
        int nextPage = page + 1;
        if (nextPage <= maxPages)
            loadPage(nextPage, isFirstLoad);
    }

    // PS: firstLoad is "cached" here, because can have a racing condition
    private void loadPage(int page, boolean firstLoad) {
        if (isRunning)
            return;

        isRunning = true;
        executors.networkIO().execute(() -> {
            try {
                Response<MovieResult> result = tmdbService.getMovies(page).execute();
                if (!result.isSuccessful()) {
                    Timber.e("%s %s", result.code(), result.message());
                    errorHandler.onLoadError(result.code());
                    return;
                }

                MovieResult movieResult = result.body();
                if (maxPages == -1)
                    maxPages = movieResult.getTotalPages();

                List<MovieDTO> movies = new ArrayList<>();
                List<MovieGenre> movieGenreList = new ArrayList<>();
                for (int i = 0; i < movieResult.getResults().size(); i++) {
                    Movie movie = movieResult.getResults().get(i);
                    int index = (movieResult.getPage() - 1) * 20 + i;

                    MovieDTO dto = new MovieDTO(movie.getId(), movie.getTitle(), movie.getOverview(),
                            movie.getPopularity(), index, movie.getVoteAverage(),
                            movie.getPosterPath(), movie.getReleaseDate());

                    movies.add(dto);

                    // Add MovieGenre
                    for (int genreId: movie.getGenreIds())
                        movieGenreList.add(new MovieGenre(movie.getId(), genreId));
                }

                database.runInTransaction(() -> {
                    if (firstLoad) {
                        tmdbDao.cleanDatabase();
                        List<Genre> genres = loadGenres();
                        tmdbDao.insertOrUpdateGenres(genres);
                    }
                    tmdbDao.insertOrUpdateMovieGenres(movieGenreList);
                    tmdbDao.insertOrUpdateMovies(movies);
                });

            } catch (IOException e) {
                errorHandler.onLoadError(0);
                Timber.e(e);
            } finally {
                isRunning = false;
            }
        });
    }

    private List<Genre> loadGenres() {
        try {
            Response<GenreResult> result = tmdbService.getGenres().execute();
            if (!result.isSuccessful()) {
                Timber.e("%s %s", result.code(), result.message());
                errorHandler.onLoadError(result.code());
                return new ArrayList<>();
            }
            return result.body().getGenres();
        } catch (IOException e) {
            errorHandler.onLoadError(0);
            Timber.e(e);
            return new ArrayList<>();
        }
    }
}
