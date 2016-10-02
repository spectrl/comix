package com.spectrl.comix.di;

import com.spectrl.comix.api.MarvelService;
import com.spectrl.comix.collection.data.repository.ComicStore;
import com.spectrl.comix.collection.data.repository.ComicsRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Kavi @ SPECTRL Ltd. on 02/10/2016.
 */

@Module
public class DataModule {

    @Provides
    @Singleton
    ComicsRepository provideComicsRepository(MarvelService marvelService) {
        return new ComicStore(marvelService);
    }
}
