package dev.keader.tmdbmovies.view.home;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import dev.keader.tmdbmovies.database.model.MovieDTO;
import dev.keader.tmdbmovies.database.model.MovieWithRelations;
import dev.keader.tmdbmovies.repository.TMDBRepository;

public class HomeViewModel extends ViewModel {
    private final TMDBRepository repository;
    private LiveData<PagedList<MovieWithRelations>> moviePagedList;

    @ViewModelInject
    public HomeViewModel(TMDBRepository rep) {
        repository = rep;
        moviePagedList = repository.loadMovies();
    }

    public LiveData<PagedList<MovieWithRelations>> getMoviePagedList() {
        return moviePagedList;
    }
}
