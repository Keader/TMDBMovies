package dev.keader.tmdbmovies.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import dev.keader.tmdbmovies.api.tmdb.Company;
import dev.keader.tmdbmovies.api.tmdb.Genre;
import dev.keader.tmdbmovies.database.model.MovieCompany;
import dev.keader.tmdbmovies.database.model.MovieDTO;
import dev.keader.tmdbmovies.database.model.MovieGenre;
import dev.keader.tmdbmovies.database.model.MovieWithRelations;

public abstract class TMDBDao {

    // Inserts / Updates
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertOrUpdateMovie(MovieDTO movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertOrUpdateGenre(Genre genre);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertOrUpdateCompany(Company company);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertOrUpdateMovieGenre(MovieGenre movieGenre);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertOrUpdateMovieCompany(MovieCompany movieCompany);

    @Transaction
    void insertOrUpateMovieWithRelations(MovieWithRelations movieWithRelations) {
        int movieId = movieWithRelations.getMovie().getId();
        insertOrUpdateMovie(movieWithRelations.getMovie());
        for (Genre genre: movieWithRelations.getGenres()) {
            MovieGenre movieGenre = new MovieGenre(movieId, genre.getId());
            insertOrUpdateGenre(genre);
            insertOrUpdateMovieGenre(movieGenre);
        }
        for (Company company : movieWithRelations.getCompanies()) {
            MovieCompany movieCompany = new MovieCompany(movieId, company.getId());
            insertOrUpdateCompany(company);
            insertOrUpdateMovieCompany(movieCompany);
        }
    }

    // Update
    @Query("UPDATE Movie SET " +
            "voteAverage = :voteAverage, originalTitle = :originalTitle, released = :releasedInt," +
            " releaseDate = :releaseDate, voteCount = :voteCount, backdropPath = :backdropPath " +
            "WHERE id = :movieId")
    abstract void updateMovieDetail(int movieId, double voteAverage, String originalTitle,
                                    int releasedInt, String releaseDate, int voteCount,
                                    String backdropPath);

    // Selects
    @Transaction
    @Query("SELECT * FROM Movie WHERE id = :movieId")
    abstract LiveData<MovieWithRelations> getMovieWithRelations(int movieId);

    @Transaction
    @Query("SELECT * FROM Movie WHERE id = :movieId")
    abstract MovieWithRelations getMovieWithRelationsDirect(int movieId);

    // Do not have detail part
    @Query("SELECT * FROM Movie WHERE id = :movieId")
    abstract LiveData<MovieDTO> getMovieSimple(int movieId);

    // Do not have detail part
    @Query("SELECT * FROM Movie WHERE id = :movieId")
    abstract MovieDTO getMovieSimpleDirect(int movieId);

    @Query("SELECT * FROM Genre WHERE id = :genreId")
    abstract Genre getGenreDirect(int genreId);

    @Query("SELECT * FROM Genre WHERE id = :genreId")
    abstract LiveData<Genre> getGenre(int genreId);

    @Query("SELECT * FROM Company WHERE id = :companyId")
    abstract Company getCompanyDirect(int companyId);

    @Query("SELECT * FROM Genre WHERE id = :companyId")
    abstract LiveData<Company> getCompany(int companyId);

    // Deletes (clean)
    @Query("DELETE FROM Movie")
    abstract void cleanMovies();

    @Query("DELETE FROM Genre")
    abstract void cleanGenres();

    @Query("DELETE FROM Company")
    abstract void cleanCompanies();

    @Query("DELETE FROM MovieCompany")
    abstract void cleanMovieCompany();

    @Query("DELETE FROM MovieGenre")
    abstract void cleanMovieGenres();

    @Transaction
    void cleanDatabase() {
        cleanMovies();
        cleanGenres();
        cleanCompanies();
        cleanMovieCompany();
        cleanMovieGenres();
    }
}
