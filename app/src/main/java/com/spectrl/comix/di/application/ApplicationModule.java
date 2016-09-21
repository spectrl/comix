package com.spectrl.comix.di.application;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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
}
