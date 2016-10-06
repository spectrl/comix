package com.spectrl.comix.di;

import com.spectrl.comix.collection.data.repository.ComicStore;
import com.spectrl.comix.collection.data.repository.ComicsRepository;
import com.spectrl.comix.collection.data.repository.source.RetrofitNetworkSource;

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
    ComicsRepository provideComicsRepository(RetrofitNetworkSource networkSource) {
        return new ComicStore(networkSource);
    }
}
