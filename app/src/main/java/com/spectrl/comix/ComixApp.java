package com.spectrl.comix;

import android.app.Application;
import android.support.annotation.NonNull;

import com.spectrl.comix.api.ApiModule;
import com.spectrl.comix.di.application.ApplicationComponent;
import com.spectrl.comix.di.application.ApplicationModule;
import com.spectrl.comix.di.application.DaggerApplicationComponent;
import com.spectrl.comix.di.application.Injector;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Kavi @ SPECTRL Ltd. on 22/09/2016.
 */

public class ComixApp extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        buildComponents();
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        if (Injector.matchesService(name)) {
            return applicationComponent;
        }
        return super.getSystemService(name);
    }

    private void buildComponents() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .apiModule(new ApiModule(BuildConfig.DEBUG))
                .build();
    }
}
