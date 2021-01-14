package dev.keader.tmdbmovies.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import dev.keader.tmdbmovies.api.tmdb.Company;
import dev.keader.tmdbmovies.api.tmdb.Genre;
import dev.keader.tmdbmovies.database.model.MovieDTO;
import dev.keader.tmdbmovies.database.model.MovieWithRelations;
import dev.keader.tmdbmovies.viewmodel.util.FakeHomeViewModel;
import dev.keader.tmdbmovies.viewmodel.util.LiveDataTestUtil;

import static com.google.common.truth.Truth.assertThat;

public class HomeViewModelTest {
    private FakeHomeViewModel viewModel;
    private MutableLiveData<List<MovieWithRelations>> liveDataList;
    private List<MovieWithRelations> movies;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule =
            new InstantTaskExecutorRule();

    @Before
    public void setupViewModel() {
        viewModel = new FakeHomeViewModel();
        MovieDTO dto = new MovieDTO(1, "Thor");
        MovieDTO dto2 = new MovieDTO(2, "Dr Estranho");
        Genre genre = new Genre(1, "Comedia");
        Genre genre2 = new Genre(2, "Acao");
        Genre genre3 = new Genre(3, "Terror");
        Company company = new Company(1, "Marvel");
        Company company2 = new Company(2, "Fox");

        MovieWithRelations mWR = new MovieWithRelations();
        mWR.setMovie(dto);
        mWR.setCompanies(Arrays.asList(company, company2));
        mWR.setGenres(Arrays.asList(genre, genre2, genre3));
        MovieWithRelations mWR2 = new MovieWithRelations();
        mWR2.setMovie(dto2);
        mWR2.setCompanies(Arrays.asList(company));
        mWR2.setGenres(Arrays.asList(genre, genre3));
        movies = Arrays.asList(mWR, mWR2);
        liveDataList = new MutableLiveData<>(movies);
    }

    @Test
    public void onMovieClickTest() throws InterruptedException {
        viewModel.onMovieClick(movies.get(0));
        assertThat(LiveDataTestUtil.getOrAwaitValue(viewModel.getMovieClick())).isNotNull();
        assertThat(LiveDataTestUtil.getOrAwaitValue(viewModel.getMovieClick())).isEqualTo(movies.get(0));
        viewModel.finishOnClickEvent();
        assertThat(LiveDataTestUtil.getOrAwaitValue(viewModel.getMovieClick())).isNull();
    }

    @Test
    public void onLoadDataTest() throws InterruptedException {
        viewModel.setMoviePagedList(liveDataList);
        assertThat(LiveDataTestUtil.getOrAwaitValue(viewModel.getMoviePagedList())).isNotNull();
        assertThat(LiveDataTestUtil.getOrAwaitValue(viewModel.getMoviePagedList()).size()).isEqualTo(2);
        List<MovieWithRelations> movies2 = new ArrayList<>(movies);
        movies2.add(movies.get(0));
        liveDataList.setValue(movies2);
        assertThat(LiveDataTestUtil.getOrAwaitValue(viewModel.getMoviePagedList()).size()).isEqualTo(3);
        assertThat(LiveDataTestUtil.getOrAwaitValue(viewModel.getMoviePagedList()).get(1)).isEqualTo(movies.get(1));
    }

}
