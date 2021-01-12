package dev.keader.tmdbmovies.database.model;

import androidx.room.Entity;
import androidx.room.Index;

@Entity(primaryKeys = {"movieId", "genreId"}, indices = {@Index("genreId")})
public class MovieGenre {
    private int movieId;
    private int genreId;

    public MovieGenre(int movieId, int genreId) {
        this.movieId = movieId;
        this.genreId = genreId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }
}
