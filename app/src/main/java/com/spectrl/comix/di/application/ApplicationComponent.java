package com.spectrl.comix.di.application;

import com.spectrl.comix.api.ApiModule;
import com.spectrl.comix.di.DataModule;
import com.spectrl.comix.di.activity.ActivityComponent;
import com.spectrl.comix.di.activity.ActivityModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = { ApplicationModule.class, ApiModule.class, DataModule.class})
public interface ApplicationComponent {
    // Subcomponents
    ActivityComponent plus(ActivityModule activityModule);
}
