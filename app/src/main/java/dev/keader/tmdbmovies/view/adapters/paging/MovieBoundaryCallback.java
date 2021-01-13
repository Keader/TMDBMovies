package dev.keader.tmdbmovies.view.adapters.paging;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dev.keader.tmdbmovies.AppExecutors;
import dev.keader.tmdbmovies.api.TMDBService;
import dev.keader.tmdbmovies.api.tmdb.Genre;
import dev.keader.tmdbmovies.api.tmdb.GenreResult;
import dev.keader.tmdbmovies.api.tmdb.Movie;
import dev.keader.tmdbmovies.api.tmdb.MovieResult;
import dev.keader.tmdbmovies.database.dao.TMDBDao;
import dev.keader.tmdbmovies.database.model.MovieDTO;
import dev.keader.tmdbmovies.database.model.MovieGenre;
import dev.keader.tmdbmovies.database.model.MovieWithRelations;
import retrofit2.Response;
import timber.log.Timber;

public class MovieBoundaryCallback extends PagedList.BoundaryCallback<MovieWithRelations> {
    private final TMDBService tmdbService;
    private final AppExecutors executors;
    private final TMDBDao database;
    private boolean isRunning;
    private int maxPages;
    private boolean isFirstLoad;

    public MovieBoundaryCallback(TMDBService tmdbService, AppExecutors executors, TMDBDao database) {
        this.tmdbService = tmdbService;
        this.executors = executors;
        this.database = database;
        isRunning = false;
        isFirstLoad = true;
        maxPages = -1;
    }

    @Override
    public void onZeroItemsLoaded() {
        loadPage(1, isFirstLoad);
        isFirstLoad = false;
    }

    @Override
    public void onItemAtFrontLoaded(@NonNull MovieWithRelations itemAtFront) {
        int page = (itemAtFront.getMovie().getIndex() / 20) + 1;
        int previousPage = page - 1;
        if (previousPage > 0)
            loadPage(previousPage, isFirstLoad);
        else if (isFirstLoad) {
            loadPage(1, isFirstLoad);
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

    // OBS: Check Genre is "cached" here, because can have a racing condition
    private void loadPage(int page, boolean loadGenre) {
        if (isRunning)
            return;

        isRunning = true;
        executors.networkIO().execute(() -> {
            try {
                if (loadGenre)
                    loadGenres();
                Response<MovieResult> result = tmdbService.getMovies(page).execute();
                if (!result.isSuccessful()) {
                    Timber.e("%s%s", result.code(), result.message());
                    return;
                }

                MovieResult movieResult = result.body();
                if (maxPages == -1)
                    maxPages = movieResult.getTotalPages();

                List<MovieDTO> movies = new ArrayList();
                for (int i = 0; i < movieResult.getResults().size(); i++) {
                    Movie movie = movieResult.getResults().get(i);
                    int index = (movieResult.getPage() - 1) * 20 + i;

                    MovieDTO dto = new MovieDTO(movie.getId(), movie.getTitle(), movie.getOverview(),
                            movie.getPopularity(), index, movie.getVoteAverage(),
                            movie.getPosterPath(), movie.getReleaseDate());

                    movies.add(dto);

                    // Add genreIds
                    List<MovieGenre> movieGenreList = new ArrayList();
                    for (int genreId: movie.getGenreIds())
                        movieGenreList.add(new MovieGenre(movie.getId(), genreId));
                    database.insertOrUpdateMovieGenres(movieGenreList);
                }

                database.insertOrUpdateMovies(movies);

            } catch (IOException e) {
                Timber.e(e);
            } finally {
                isRunning = false;
            }
        });
    }

    private void loadGenres() {
        try {
            Response<GenreResult> result = tmdbService.getGenres().execute();

            if (!result.isSuccessful()) {
                Timber.e("%s%s", result.code(), result.message());
                return;
            }
            database.insertOrUpdateGenres(result.body().getGenres());
        } catch (IOException e) {
            Timber.e(e);
        }
    }
}
