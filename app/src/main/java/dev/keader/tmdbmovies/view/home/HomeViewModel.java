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
    private LiveData<PagedList<MovieWithRelations>> moviePagedList;
    private MutableLiveData<MovieWithRelations> movieClick;

    @ViewModelInject
    public HomeViewModel(TMDBRepository rep) {
        repository = rep;
        moviePagedList = repository.loadMovies();
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

    public LiveData<PagedList<MovieWithRelations>> getMoviePagedList() {
        return moviePagedList;
    }
}

