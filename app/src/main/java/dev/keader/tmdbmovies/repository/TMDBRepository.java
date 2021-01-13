package dev.keader.tmdbmovies.repository;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dev.keader.tmdbmovies.AppExecutors;
import dev.keader.tmdbmovies.Constants;
import dev.keader.tmdbmovies.api.TMDBService;
import dev.keader.tmdbmovies.api.tmdb.Company;
import dev.keader.tmdbmovies.api.tmdb.MovieDetail;
import dev.keader.tmdbmovies.api.tmdb.MovieResult;
import dev.keader.tmdbmovies.database.dao.TMDBDao;
import dev.keader.tmdbmovies.database.model.MovieCompany;
import dev.keader.tmdbmovies.database.model.MovieDTO;
import dev.keader.tmdbmovies.database.model.MovieWithRelations;
import dev.keader.tmdbmovies.view.adapters.paging.MovieBoundaryCallback;
import retrofit2.Response;
import timber.log.Timber;

public class TMDBRepository {
    private final TMDBService tmdbService;
    private final AppExecutors executors;
    private final TMDBDao database;

    @Inject
    public TMDBRepository(TMDBService tmdbService, AppExecutors executors, TMDBDao database) {
        this.tmdbService = tmdbService;
        this.executors = executors;
        this.database = database;
    }

    public LiveData<PagedList<MovieWithRelations>> loadMovies() {
        DataSource.Factory<Integer, MovieWithRelations> dataSourceFactory = database.getMoviePaged();
        return new LivePagedListBuilder(dataSourceFactory, 20)
                .setBoundaryCallback(new MovieBoundaryCallback(tmdbService, executors, database))
                .build();
    }

    public LiveData<MovieWithRelations> getMovieDetail(int movieId) {
        executors.networkIO().execute(()-> {
            try {
                Response<MovieDetail> result = tmdbService.getMovieDetail(movieId, Constants.TMDB_EXTERNAL_SOURCE).execute();
                if (!result.isSuccessful()) {
                    Timber.e("%s%s", result.code(), result.message());
                    return;
                }

                MovieDetail movieDetail = result.body();
                int released = movieDetail.isReleased() ? 1 : 0;
                // Update Movie Detail
                database.updateMovieDetail(movieId, movieDetail.getVoteAverage(), movieDetail.getOriginalTitle(),
                        released, movieDetail.getVoteCount(), movieDetail.getBackdropPath());
                // Insert Companies
                database.insertOrUpdateCompanies(movieDetail.getProductionCompanies());
                List<MovieCompany> movieCompanyList = new ArrayList();
                for (Company company : movieDetail.getProductionCompanies())
                    movieCompanyList.add(new MovieCompany(movieId, company.getId()));
                // Insert Relation <movieId, companieId>
                database.insertOrUpdateMovieCompanies(movieCompanyList);

            } catch (IOException e) {
                Timber.e(e);
            }
        });

        return database.getMovieWithRelations(movieId);
    }
}
