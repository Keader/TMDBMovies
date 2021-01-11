package dev.keader.tmdbmovies.view.home;


import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;

import dev.keader.tmdbmovies.repository.TMDBRepository;

public class HomeViewModel extends ViewModel {
    private final TMDBRepository repository;

    @ViewModelInject
    public HomeViewModel(TMDBRepository repository) {
        this.repository = repository;
    }
}
