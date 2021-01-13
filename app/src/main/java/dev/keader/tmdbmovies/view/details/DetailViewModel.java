package dev.keader.tmdbmovies.view.details;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import dev.keader.tmdbmovies.database.model.MovieWithRelations;
import dev.keader.tmdbmovies.repository.TMDBRepository;

public class DetailViewModel extends ViewModel {
    private final TMDBRepository repository;

    @ViewModelInject
    public DetailViewModel(TMDBRepository repository) {
        this.repository = repository;
    }

    public LiveData<MovieWithRelations> getMovies(int movieId) {
        return repository.getMovieDetail(movieId);
    }
}
