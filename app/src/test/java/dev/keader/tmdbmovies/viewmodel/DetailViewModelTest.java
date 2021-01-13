package dev.keader.tmdbmovies.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import dev.keader.tmdbmovies.viewmodel.util.FakeDetailViewModel;
import dev.keader.tmdbmovies.viewmodel.util.LiveDataTestUtil;

import static com.google.common.truth.Truth.assertThat;

public class DetailViewModelTest {

    private FakeDetailViewModel viewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule =
            new InstantTaskExecutorRule();

    @Before
    public void setupViewModel() {
        viewModel = new FakeDetailViewModel();
    }

    @Test
    public void getMovieWithValidIdTest() throws InterruptedException {
        assertThat(LiveDataTestUtil.getOrAwaitValue(viewModel.getMovies(FakeDetailViewModel.VALID_ID))).isNotNull();
    }

    @Test
    public void getMovieWithInvalidIdTest() throws InterruptedException {
        assertThat(LiveDataTestUtil.getOrAwaitValue(viewModel.getMovies(47))).isNull();
    }
}
