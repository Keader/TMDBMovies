package dev.keader.tmdbmovies.view.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import dagger.hilt.android.AndroidEntryPoint;
import dev.keader.tmdbmovies.MainActivity;
import dev.keader.tmdbmovies.R;
import dev.keader.tmdbmovies.databinding.FragmentDetailBinding;

@AndroidEntryPoint
public class DetailFragment extends Fragment {
    private DetailViewModel viewModel;
    private FragmentDetailBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        int movieId = DetailFragmentArgs.fromBundle(getArguments()).getMovieId();
        viewModel.getMovie(movieId).observe(getViewLifecycleOwner(), movieWithRelations -> {
            if (movieWithRelations != null) {
                binding.setMovieWithRelations(movieWithRelations);
                MainActivity activity = (MainActivity) getActivity();
                activity.setAppBarTitle(movieWithRelations.getMovie().getTitle());
            }
        });

        return binding.getRoot();
    }
}
