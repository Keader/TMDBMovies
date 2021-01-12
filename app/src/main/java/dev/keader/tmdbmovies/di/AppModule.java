package dev.keader.tmdbmovies.di;

import android.content.Context;

import androidx.room.Room;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import dev.keader.tmdbmovies.Constants;
import dev.keader.tmdbmovies.TMDBMovies;
import dev.keader.tmdbmovies.api.TMDBService;
import dev.keader.tmdbmovies.database.TMDBDatabase;
import dev.keader.tmdbmovies.database.dao.TMDBDao;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {
    @Provides
    @Singleton
    Context provideContext(TMDBMovies application) {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .serializeNulls()
                .create();
    }

    @Provides
    @Singleton
    TMDBService provideTMDbService(OkHttpClient client, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(Constants.TMDB_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
                .create(TMDBService.class);
    }

    @Singleton
    @Provides
    OkHttpClient provideHttpClient(Interceptor interceptor) {
        return new OkHttpClient.Builder()
                .followRedirects(true)
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(interceptor)
                .build();
    }

    @Provides
    @Singleton
    Interceptor provideInterceptor() {
        return chain -> {
            Request oRequest = chain.request();
            String host = oRequest.url().host();
            Timber.d("Request to: " + oRequest.url());
            HttpUrl url = oRequest.url().newBuilder()
                    .addQueryParameter("api_key", Constants.TMDB_API_KEY)
                    .addQueryParameter("language", Locale.getDefault().toLanguageTag())
                    .build();

            Request.Builder requestBuilder = oRequest.newBuilder()
                    .url(url);

            Request request = requestBuilder.build();
            return chain.proceed(request);
        };
    }

    @Provides
    @Singleton
    TMDBDatabase provideDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(
                context.getApplicationContext(),
                TMDBDatabase.class,
                "tmdb_movies_database")
                .build();
    }

    @Provides
    @Singleton
    TMDBDao  provideTMDBDao(TMDBDatabase db) {
        return db.tmdbDao();
    }
}
