package dev.keader.tmdbmovies.view.home;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import dev.keader.tmdbmovies.database.model.MovieWithRelations;
import dev.keader.tmdbmovies.repository.TMDBRepository;

public class HomeViewModel extends ViewModel implements MovieActions {
    private final TMDBRepository repository;
    private TMDBRepository.MovieListObservable movieListObservable;
    private MutableLiveData<MovieWithRelations> movieClick;

    @ViewModelInject
    public HomeViewModel(TMDBRepository rep) {
        repository = rep;
        movieListObservable = repository.loadMovies();
        movieClick = new MutableLiveData<>();
    }

    @Override
    public void onMovieClick(MovieWithRelations movieWithRelations) {
        if (movieWithRelations != null)
            movieClick.setValue(movieWithRelations);
    }

    public void finishOnClickEvent() {
        movieClick.setValue(null);
    }

    public LiveData<MovieWithRelations> getMovieClick() {
        return movieClick;
    }

    public TMDBRepository.MovieListObservable getMovieListObservable() {
        return movieListObservable;
    }
}

