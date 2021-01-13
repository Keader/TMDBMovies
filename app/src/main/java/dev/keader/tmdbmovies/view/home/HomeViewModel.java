package dev.keader.tmdbmovies.view.home;


import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import dev.keader.tmdbmovies.database.model.MovieDTO;
import dev.keader.tmdbmovies.repository.TMDBRepository;

public class HomeViewModel extends ViewModel {
    private final TMDBRepository repository;
    private LiveData<PagedList<MovieDTO>> moviePagedList;

    @ViewModelInject
    public HomeViewModel(TMDBRepository repository) {
        this.repository = repository;
        moviePagedList = repository.loadMovies();
    }

    public TMDBRepository getRepository() {
        return repository;
    }

    public LiveData<PagedList<MovieDTO>> getMoviePagedList() {
        return moviePagedList;
    }
}
