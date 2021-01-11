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

        // TODO: Initialize Timber Firebase Here
        Timber.plant(new Timber.DebugTree());

        // Force Light theme (fix bugs with XIAOMI devices)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
}
