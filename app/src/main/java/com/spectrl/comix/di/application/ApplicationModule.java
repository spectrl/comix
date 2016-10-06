package com.spectrl.comix.di.application;

import android.app.Application;
import android.content.SharedPreferences;

import com.spectrl.comix.BuildConfig;
import com.spectrl.comix.api.MarvelAuthorizationInterceptor;
import com.spectrl.comix.collection.data.model.Comic;
import com.spectrl.comix.collection.data.repository.DiskCache;
import com.spectrl.comix.collection.data.repository.SharedPrefsDiskCache;
import com.spectrl.comix.di.MainThread;
import com.spectrl.comix.util.Connectivity;
import com.spectrl.comix.util.Network;
import com.squareup.moshi.Moshi;

import java.io.File;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

/**
 * A module for Android-specific dependencies which require a {@link Context} or
 * {@link Application} to create.
 */
@Module
public class ApplicationModule {
    private static final String CACHE_PREFS_FILE = "cache";

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    /**
     * Expose the application to the graph.
     */
    @Provides
    @Singleton
    Application application() {
        return application;
    }

    @Provides
    @Singleton @MainThread
    Scheduler provideMainThreadScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Provides @Singleton
    MarvelAuthorizationInterceptor provideMarvelAuthorizationInterceptor() {
        return new MarvelAuthorizationInterceptor(BuildConfig.MARVEL_PUBLIC_KEY, BuildConfig.MARVEL_PRIVATE_KEY);
    }

    @Provides @Singleton
    File provideCacheDirectory() {
        return application.getCacheDir();
    }

    @Provides @Singleton
    Connectivity provideConnection() {
        return new Network(application);
    }

    @Provides @Singleton
    DiskCache<String, List<Comic>> provideComicsDiskCache(SharedPreferences sharedPreferences, Moshi moshi) {
        return new SharedPrefsDiskCache(sharedPreferences, moshi);
    }

    @Provides @Singleton
    SharedPreferences provideCacheSharedPrefs() {
        return application.getSharedPreferences(CACHE_PREFS_FILE, 0);
    }
}
