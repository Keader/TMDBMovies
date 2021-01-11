package dev.keader.tmdbmovies.api;

import dev.keader.tmdbmovies.api.tmdb.MovieDetail;
import dev.keader.tmdbmovies.api.tmdb.MovieResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDBService {
    @GET("movie/popular")
    Call<MovieResult> getMovies(@Query("page") int page);

    @GET("movie/{movie_id}")
    Call<MovieDetail> getMovieDetail(@Path("movie_id") long movieId, @Query("external_source") String source);
}

