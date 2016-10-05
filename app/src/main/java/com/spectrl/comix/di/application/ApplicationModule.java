package com.spectrl.comix.di.application;

import android.app.Application;

import com.spectrl.comix.BuildConfig;
import com.spectrl.comix.api.MarvelAuthorizationInterceptor;
import com.spectrl.comix.di.MainThread;
import com.spectrl.comix.util.Connection;
import com.spectrl.comix.util.Connectivity;

import java.io.File;

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
    Connection provideConnection() {
        return new Connectivity(application);
    }
}
