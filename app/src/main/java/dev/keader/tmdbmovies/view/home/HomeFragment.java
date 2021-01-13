package dev.keader.tmdbmovies.view.home;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.room.Room;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import dagger.hilt.android.AndroidEntryPoint;
import dev.keader.tmdbmovies.NavGraphDirections;
import dev.keader.tmdbmovies.R;
import dev.keader.tmdbmovies.api.tmdb.Company;
import dev.keader.tmdbmovies.api.tmdb.Genre;
import dev.keader.tmdbmovies.database.TMDBDatabase;
import dev.keader.tmdbmovies.database.dao.TMDBDao;
import dev.keader.tmdbmovies.database.model.MovieCompany;
import dev.keader.tmdbmovies.database.model.MovieDTO;
import dev.keader.tmdbmovies.database.model.MovieGenre;
import dev.keader.tmdbmovies.database.model.MovieWithRelations;
import dev.keader.tmdbmovies.databinding.FragmentHomeBinding;
import dev.keader.tmdbmovies.view.adapters.MovieAdapter;
import timber.log.Timber;

import static androidx.core.content.ContextCompat.getSystemService;

@AndroidEntryPoint
public class HomeFragment extends Fragment {
    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        binding.setLifecycleOwner(this);

        MovieAdapter adapter = new MovieAdapter(viewModel);
        binding.recyclerViewMovies.setAdapter(adapter);

        viewModel.getMoviePagedList().observe(getViewLifecycleOwner(), list -> {
            if (list.isEmpty())
                showConnectionAnimation();
            else {
                adapter.submitList(list);
                hideConnectionAnimation();
            }
        });

        viewModel.getMovieClick().observe(getViewLifecycleOwner(), movieWithRelations -> {
            if (movieWithRelations != null) {
                NavGraphDirections.ActionGlobalDetailFragment action = HomeFragmentDirections.actionGlobalDetailFragment(
                        movieWithRelations.getMovie().getId());
                Navigation.findNavController(getView()).navigate(action);
                viewModel.finishOnClickEvent();
            }
        });

        return binding.getRoot();
    }

    private void showConnectionAnimation() {
        binding.animConnection.setVisibility(View.VISIBLE);
        binding.recyclerViewMovies.setVisibility(View.GONE);
    }

    private void hideConnectionAnimation() {
        if (binding.animConnection.getVisibility() == View.GONE)
            return;

        binding.animConnection.setVisibility(View.GONE);
        binding.recyclerViewMovies.setVisibility(View.VISIBLE);
    }
}
