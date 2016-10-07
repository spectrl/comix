package com.spectrl.comix.comic.di;

import com.spectrl.comix.collection.data.repository.ComicsRepository;
import com.spectrl.comix.comic.presenter.ComicPresenter;
import com.spectrl.comix.di.ActivityScope;
import com.spectrl.comix.di.MainThread;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

/**
 * Created by Kavi @ SPECTRL Ltd. on 07/10/2016.
 */
@Module
public class ComicModule {

    private final int id;

    public ComicModule(int id) {
        this.id = id;
    }

    @Provides @ActivityScope
    ComicPresenter provideComicPresenter(ComicsRepository comicsRepository, @MainThread Scheduler mainThread) {
        return new ComicPresenter(id, comicsRepository, mainThread);
    }
}
