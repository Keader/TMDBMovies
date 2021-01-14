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
import dev.keader.tmdbmovies.database.TMDBDatabase;
import dev.keader.tmdbmovies.database.dao.TMDBDao;
import dev.keader.tmdbmovies.database.model.MovieCompany;
import dev.keader.tmdbmovies.database.model.MovieWithRelations;
import retrofit2.Response;
import timber.log.Timber;

public class TMDBRepository {
    private final TMDBService tmdbService;
    private final AppExecutors executors;
    private final TMDBDao tmdbDao;
    private final TMDBDatabase database;

    @Inject
    public TMDBRepository(TMDBService tmdbService, AppExecutors executors, TMDBDao tmdbDao, TMDBDatabase database) {
        this.tmdbService = tmdbService;
        this.executors = executors;
        this.tmdbDao = tmdbDao;
        this.database = database;
    }

    public MovieListObservable loadMovies() {
        DataSource.Factory<Integer, MovieWithRelations> dataSourceFactory = tmdbDao.getMoviePaged();
        MutableLiveData<Integer> error = new MutableLiveData<>();

        MovieBoundaryCallback movieBoundaryCallback = new MovieBoundaryCallback(tmdbService, executors, tmdbDao, database, code -> {
            error.postValue(code);
        });

        LiveData<PagedList<MovieWithRelations>> data = new LivePagedListBuilder<>(dataSourceFactory, 20)
                .setBoundaryCallback(movieBoundaryCallback)
                .build();

        return new MovieListObservable(data, error);
    }

    public LiveData<MovieWithRelations> getMovieDetail(int movieId) {
        executors.networkIO().execute(()-> {
            try {
                Response<MovieDetail> result = tmdbService.getMovieDetail(movieId, Constants.TMDB_EXTERNAL_SOURCE).execute();
                if (!result.isSuccessful()) {
                    Timber.e("%s %s", result.code(), result.message());
                    return;
                }

                MovieDetail movieDetail = result.body();
                int released = movieDetail.isReleased() ? 1 : 0;
                // Update Movie Detail
                tmdbDao.updateMovieDetail(movieId, movieDetail.getVoteAverage(), movieDetail.getOriginalTitle(),
                        released, movieDetail.getVoteCount(), movieDetail.getBackdropPath());
                // Insert Companies
                tmdbDao.insertOrUpdateCompanies(movieDetail.getProductionCompanies());
                List<MovieCompany> movieCompanyList = new ArrayList();
                for (Company company : movieDetail.getProductionCompanies())
                    movieCompanyList.add(new MovieCompany(movieId, company.getId()));
                // Insert Relation <movieId, companieId>
                tmdbDao.insertOrUpdateMovieCompanies(movieCompanyList);

            } catch (IOException e) {
                Timber.e(e);
            }
        });

        return tmdbDao.getMovieWithRelations(movieId);
    }

    public static class MovieListObservable {
        private final LiveData<PagedList<MovieWithRelations>> pagedListLiveData;
        private final MutableLiveData<Integer> error;

        public MovieListObservable(LiveData<PagedList<MovieWithRelations>> pagedListLiveData, MutableLiveData<Integer> error) {
            this.pagedListLiveData = pagedListLiveData;
            this.error = error;
        }

        public LiveData<PagedList<MovieWithRelations>> getPagedListLiveData() {
            return pagedListLiveData;
        }

        public MutableLiveData<Integer> getError() {
            return error;
        }
    }
}
