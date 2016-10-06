package com.spectrl.comix.di.activity;

import android.app.Activity;

import com.spectrl.comix.AndroidNavigator;
import com.spectrl.comix.view.Navigator;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    /**
     * Expose the activity to the graph.
     */
    @Provides
    @ActivityScope
    Activity activity() {
        return activity;
    }

    @Provides
    @ActivityScope
    Navigator provideAndroidNavigator() {
        return new AndroidNavigator(activity);
    }
}
