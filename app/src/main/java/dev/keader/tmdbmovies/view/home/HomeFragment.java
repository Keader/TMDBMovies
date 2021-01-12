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
import androidx.room.Room;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import dagger.hilt.android.AndroidEntryPoint;
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
import timber.log.Timber;

@AndroidEntryPoint
public class HomeFragment extends Fragment {
    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        /*
        TMDBDatabase db = Room.inMemoryDatabaseBuilder(
                getActivity().getApplicationContext(),
                TMDBDatabase.class)
                .allowMainThreadQueries()
                .build();
        TMDBDao dao = db.tmdbDao();

        Genre genre = new Genre(10, "Comedia");
        Company company = new Company(20,"Warner");
        MovieDTO dto = new MovieDTO(12345, "Pinguins", "De ferias na praia", 10.5, "pinguin.png");
        MovieCompany movieCompany = new MovieCompany(12345, 20);
        MovieGenre movieGenre = new MovieGenre(12345, 10);
        dao.insertOrUpdateGenre(genre);
        dao.insertOrUpdateCompany(company);
        dao.insertOrUpdateMovieCompany(movieCompany);
        dao.insertOrUpdateMovieGenre(movieGenre);
        dao.insertOrUpdateMovie(dto);
        MovieWithRelations movieWithRelations = dao.getMovieWithRelationsDirect(12345);
        Timber.d(movieWithRelations.toString());*/

        return binding.getRoot();
    }
}
