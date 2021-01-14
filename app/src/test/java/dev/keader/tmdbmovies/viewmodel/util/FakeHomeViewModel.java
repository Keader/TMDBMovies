package dev.keader.tmdbmovies.viewmodel.util;

import androidx.lifecycle.MutableLiveData;
import java.util.List;
import dev.keader.tmdbmovies.database.model.MovieWithRelations;

public class FakeHomeViewModel {
    private MutableLiveData<List<MovieWithRelations>> moviePagedList;
    private MutableLiveData<MovieWithRelations> movieClick;

    public FakeHomeViewModel() {
        moviePagedList = new MutableLiveData<>();
        movieClick = new MutableLiveData<>();
    }

    public void setMoviePagedList(MutableLiveData<List<MovieWithRelations>> moviePagedList) {
        this.moviePagedList = moviePagedList;
    }

    public void onMovieClick(MovieWithRelations movieWithRelations) {
        if (movieWithRelations != null)
            movieClick.setValue(movieWithRelations);
    }

    public void finishOnClickEvent() {
        movieClick.setValue(null);
    }

    public MutableLiveData<MovieWithRelations> getMovieClick() {
        return movieClick;
    }

    public MutableLiveData<List<MovieWithRelations>> getMoviePagedList() {
        return moviePagedList;
    }
}
