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
import retrofit2.Response;
import timber.log.Timber;

public class MovieBoundaryCallback extends PagedList.BoundaryCallback<MovieDTO> {
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
        loadPage(1);
    }

    @Override
    public void onItemAtFrontLoaded(@NonNull MovieDTO itemAtFront) {
        int previousPage = itemAtFront.getPage() - 1;
        if (previousPage > 0)
            loadPage(previousPage);
        else if (isFirstLoad) {
            isFirstLoad = false;
            loadGenres();
            loadPage(1);
        }
    }

    @Override
    public void onItemAtEndLoaded(@NonNull MovieDTO itemAtEnd) {
        int nextPage = itemAtEnd.getPage() + 1;
        if (nextPage <= maxPages)
            loadPage(nextPage);
    }

    private void loadPage(int page) {
        if (isRunning)
            return;

        isRunning = true;
        executors.networkIO().execute(() -> {
            try {
                Response<MovieResult> result = tmdbService.getMovies(page).execute();
                if (!result.isSuccessful()) {
                    Timber.e("%s%s", result.code(), result.message());
                    return;
                }

                MovieResult movieResult = result.body();
                if (maxPages == -1)
                    maxPages = movieResult.getTotalPages();

                List<MovieDTO> movies = new ArrayList();
                for (Movie movie : movieResult.getResults()) {
                    List<Genre> genres = database.getGenreListDirect(movie.getGenreIds());

                    String genresFormatted =  genres.stream()
                            .map(Genre::getName)
                            .collect(Collectors.joining(" | "));

                    MovieDTO dto = new MovieDTO(movie.getId(), movie.getTitle(), movie.getOverview(),
                            movie.getPopularity(), movie.getVoteAverage(), movie.getPosterPath(),
                            movie.getReleaseDate(), movieResult.getPage(), genresFormatted);

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
        executors.networkIO().execute(() -> {
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
        });
    }
}
