package dev.keader.tmdbmovies.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
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

@Dao
public abstract class TMDBDao {

    // Inserts / Updates
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertOrUpdateMovie(MovieDTO movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertOrUpdateGenre(Genre genre);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertOrUpdateCompany(Company company);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertOrUpdateMovieGenre(MovieGenre movieGenre);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertOrUpdateMovieCompany(MovieCompany movieCompany);

    @Transaction
    public void insertOrUpateMovieWithRelations(MovieWithRelations movieWithRelations) {
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
    public abstract void updateMovieDetail(int movieId, double voteAverage, String originalTitle,
                                    int releasedInt, String releaseDate, int voteCount,
                                    String backdropPath);

    // Selects
    @Transaction
    @Query("SELECT * FROM Movie WHERE id = :movieId")
    public abstract LiveData<MovieWithRelations> getMovieWithRelations(int movieId);

    @Transaction
    @Query("SELECT * FROM Movie WHERE id = :movieId")
    public abstract MovieWithRelations getMovieWithRelationsDirect(int movieId);

    // Do not have detail part
    @Query("SELECT * FROM Movie WHERE id = :movieId")
    public abstract LiveData<MovieDTO> getMovieSimple(int movieId);

    // Do not have detail part
    @Query("SELECT * FROM Movie WHERE id = :movieId")
    public abstract MovieDTO getMovieSimpleDirect(int movieId);

    @Query("SELECT * FROM Genre WHERE id = :genreId")
    public abstract Genre getGenreDirect(int genreId);

    @Query("SELECT * FROM Genre WHERE id = :genreId")
    public abstract LiveData<Genre> getGenre(int genreId);

    @Query("SELECT * FROM Company WHERE id = :companyId")
    public abstract Company getCompanyDirect(int companyId);

    @Query("SELECT * FROM Genre WHERE id = :companyId")
    public abstract LiveData<Company> getCompany(int companyId);

    // Deletes (clean)
    @Query("DELETE FROM Movie")
    public abstract void cleanMovies();

    @Query("DELETE FROM Genre")
    public abstract void cleanGenres();

    @Query("DELETE FROM Company")
    public abstract void cleanCompanies();

    @Query("DELETE FROM MovieCompany")
    public abstract void cleanMovieCompany();

    @Query("DELETE FROM MovieGenre")
    public abstract void cleanMovieGenres();

    @Transaction
    public void cleanDatabase() {
        cleanMovies();
        cleanGenres();
        cleanCompanies();
        cleanMovieCompany();
        cleanMovieGenres();
    }
}
