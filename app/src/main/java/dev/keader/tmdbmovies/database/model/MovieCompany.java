package dev.keader.tmdbmovies.database.model;

import androidx.room.Entity;
import androidx.room.Index;


@Entity(primaryKeys = {"movieId", "companyId"}, indices = {@Index("companyId")})
public class MovieCompany {
    private int movieId;
    private int companyId;

    public MovieCompany(int movieId, int companyId) {
        this.movieId = movieId;
        this.companyId = companyId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}
