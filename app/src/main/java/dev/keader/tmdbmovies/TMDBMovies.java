package dev.keader.tmdbmovies;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

import dagger.hilt.android.HiltAndroidApp;
import timber.log.Timber;

@HiltAndroidApp
public class TMDBMovies extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());

        // Force Light theme (fix bugs with XIAOMI devices)
        // i was using a xiaomi for test :)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
}
