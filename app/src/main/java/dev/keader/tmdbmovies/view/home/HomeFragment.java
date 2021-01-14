package dev.keader.tmdbmovies.view.home;


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
import com.google.android.material.snackbar.Snackbar;
import dagger.hilt.android.AndroidEntryPoint;
import dev.keader.tmdbmovies.MainActivity;
import dev.keader.tmdbmovies.NavGraphDirections;
import dev.keader.tmdbmovies.R;
import dev.keader.tmdbmovies.databinding.FragmentHomeBinding;
import dev.keader.tmdbmovies.repository.TMDBRepository;
import dev.keader.tmdbmovies.view.adapters.MovieAdapter;

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

        TMDBRepository.MovieListObservable movieListObservable = viewModel.getMovieListObservable();

        movieListObservable.getPagedListLiveData().observe(getViewLifecycleOwner(), list -> {
            if (list.isEmpty())
                showConnectionAnimation();
            else {
                adapter.submitList(list);
                hideConnectionAnimation();
            }
        });

        movieListObservable.getError().observe(getViewLifecycleOwner(), code -> {
            if (code != null) {
                if (code == 0)
                    showSnackMessage(getString(R.string.network_error), Snackbar.LENGTH_LONG);
                else
                    showSnackMessage(getString(R.string.network_error_format, code), Snackbar.LENGTH_SHORT);
                movieListObservable.getError().setValue(null);
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

    private void showSnackMessage(String text, int duration) {
        MainActivity activity = (MainActivity) getActivity();
        activity.getSnackBarInstance(text, duration).show();
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
