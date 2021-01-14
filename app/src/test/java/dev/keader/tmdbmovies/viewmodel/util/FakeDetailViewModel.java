package dev.keader.tmdbmovies.viewmodel.util;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.Arrays;
import dev.keader.tmdbmovies.api.tmdb.Company;
import dev.keader.tmdbmovies.api.tmdb.Genre;
import dev.keader.tmdbmovies.database.model.MovieDTO;
import dev.keader.tmdbmovies.database.model.MovieWithRelations;

public class FakeDetailViewModel {
    public static int VALID_ID = 99;

    public LiveData<MovieWithRelations> getMovies(int movieId) {
        if (movieId == VALID_ID) {
            MovieDTO dto = new MovieDTO(1, "Thor");
            Genre genre = new Genre(1, "Acao");
            Company company = new Company(1, "Marvel");
            MovieWithRelations mWR = new MovieWithRelations();
            mWR.setMovie(dto);
            mWR.setCompanies(Arrays.asList(company));
            mWR.setGenres(Arrays.asList(genre));
            return new MutableLiveData<>(mWR);
        }
        return new MutableLiveData<>(null);
    }
}
