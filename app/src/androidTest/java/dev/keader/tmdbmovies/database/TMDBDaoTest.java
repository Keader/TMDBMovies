package dev.keader.tmdbmovies.database;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static com.google.common.truth.Truth.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import dev.keader.tmdbmovies.api.tmdb.Company;
import dev.keader.tmdbmovies.api.tmdb.Genre;
import dev.keader.tmdbmovies.database.dao.TMDBDao;
import dev.keader.tmdbmovies.database.model.MovieCompany;
import dev.keader.tmdbmovies.database.model.MovieDTO;
import dev.keader.tmdbmovies.database.model.MovieGenre;

@RunWith(AndroidJUnit4.class)
public class TMDBDaoTest {
    private Context context = ApplicationProvider.getApplicationContext();
    private TMDBDatabase database;
    private TMDBDao dao;

    @Before
    public void setupDatabase() {
        database = Room.inMemoryDatabaseBuilder(
                context,
                TMDBDatabase.class)
                .allowMainThreadQueries()
                .build();
        dao = database.tmdbDao();
    }

    @After
    public void closeDb() {
        database.close();
    }

    @Test
    public void insertUpdateGenreTest() {
        dao.cleanDatabase();
        assertThat(dao.getGenreDirect(10)).isNull();
        Genre genre = new Genre(10, "Comedia");
        dao.insertOrUpdateGenre(genre);
        assertThat(dao.getGenreDirect(10)).isNotNull();
        genre.setName("Humor");
        dao.insertOrUpdateGenre(genre);
        assertThat(dao.getGenreDirect(10).getName()).isEqualTo("Humor");
    }

    @Test
    public void insertUpdateCompanyTest() {
        dao.cleanDatabase();
        assertThat(dao.getCompanyDirect(20)).isNull();
        Company company = new Company(20,"Warner");;
        dao.insertOrUpdateCompany(company);
        assertThat(dao.getCompanyDirect(20)).isNotNull();
        company.setName("Disney");
        dao.insertOrUpdateCompany(company);
        assertThat(dao.getCompanyDirect(20).getName()).isEqualTo("Disney");
    }

    @Test
    public void insertUpdateMovieTest() {
        dao.cleanDatabase();
        assertThat(dao.getMovieSimpleDirect(3025)).isNull();
        MovieDTO dto = new MovieDTO(3025, "Pinguins");
        dao.insertOrUpdateMovie(dto);
        assertThat(dao.getMovieSimpleDirect(3025)).isNotNull();
        dto.setTitle("Madagascar");
        dao.insertOrUpdateMovie(dto);
        assertThat(dao.getMovieSimpleDirect(3025).getTitle()).isEqualTo("Madagascar");
    }

    @Test
    public void checkRelationsTest() {
        dao.cleanDatabase();
        Genre genre = new Genre(10, "Comedia");
        dao.insertOrUpdateGenre(genre);
        MovieDTO dto = new MovieDTO(3025, "Pinguins");
        Company company = new Company(20,"Warner");;
        dao.insertOrUpdateCompany(company);
        dao.insertOrUpdateMovie(dto);
        MovieGenre movieGenre = new MovieGenre(3025, 10);
        MovieCompany movieCompany = new MovieCompany(3025, 20);
        dao.insertOrUpdateMovieCompany(movieCompany);
        dao.insertOrUpdateMovieGenre(movieGenre);
        assertThat(dao.getMovieWithRelationsDirect(3025)).isNotNull();
        assertThat(dao.getMovieWithRelationsDirect(3025).getCompanies().size()).isEqualTo(1);
        assertThat(dao.getMovieWithRelationsDirect(3025).getGenres().size()).isEqualTo(1);
        dao.cleanMovieGenres();
        assertThat(dao.getMovieWithRelationsDirect(3025).getGenres().size()).isEqualTo(0);
        dao.cleanMovieCompany();
        assertThat(dao.getMovieWithRelationsDirect(3025).getCompanies().size()).isEqualTo(0);
        dao.cleanMovies();
        assertThat(dao.getMovieWithRelationsDirect(3025)).isNull();
    }

}
