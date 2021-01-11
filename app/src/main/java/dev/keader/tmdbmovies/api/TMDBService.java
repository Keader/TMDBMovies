package dev.keader.tmdbmovies.api;

import androidx.lifecycle.LiveData;
import dev.keader.tmdbmovies.api.tmdb.MovieResult;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDBService {
    @GET("movie/popular")
    LiveData<MovieResult> getMovies(@Query("page") int page);

    @GET("movie/{movie_id}")
    LiveData<MovieResult> getMovieDetail(@Path("movie_id") long movieId, @Query("external_source") String source);
}
