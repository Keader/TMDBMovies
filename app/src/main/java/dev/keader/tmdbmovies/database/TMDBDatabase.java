package dev.keader.tmdbmovies.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import dev.keader.tmdbmovies.api.tmdb.Company;
import dev.keader.tmdbmovies.api.tmdb.Genre;
import dev.keader.tmdbmovies.database.dao.TMDBDao;
import dev.keader.tmdbmovies.database.model.MovieCompany;
import dev.keader.tmdbmovies.database.model.MovieDTO;
import dev.keader.tmdbmovies.database.model.MovieGenre;

@Database(entities = { Company.class, Genre.class, MovieDTO.class,
        MovieGenre.class, MovieCompany.class }, version = 1, exportSchema = false)
public abstract class TMDBDatabase extends RoomDatabase {
    public abstract TMDBDao tmdbDao();
}
