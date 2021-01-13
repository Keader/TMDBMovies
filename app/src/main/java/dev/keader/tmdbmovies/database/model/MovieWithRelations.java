package dev.keader.tmdbmovies.database.model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;
import java.util.Objects;

import dev.keader.tmdbmovies.api.tmdb.Company;
import dev.keader.tmdbmovies.api.tmdb.Genre;

public class MovieWithRelations {
    @Embedded
    private MovieDTO movie;
    @Relation(
            entity =Genre.class,
            entityColumn = "id", parentColumn = "id",
            associateBy = @Junction(
                    value = MovieGenre.class,
                    parentColumn = "movieId",
                    entityColumn ="genreId"
            )
    )
    private List<Genre> genres;
    @Relation(
            entity = Company.class,
            entityColumn = "id", parentColumn = "id",
            associateBy = @Junction(
                    value = MovieCompany.class,
                    parentColumn = "movieId",
                    entityColumn ="companyId"
            )
    )
    private List<Company> companies;

    public MovieWithRelations() { }

    public MovieDTO getMovie() {
        return movie;
    }

    public void setMovie(MovieDTO movie) {
        this.movie = movie;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieWithRelations that = (MovieWithRelations) o;
        return movie.equals(that.movie) &&
                genres.equals(that.genres);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movie, genres);
    }
}
