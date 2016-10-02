package com.spectrl.comix.api;

import com.squareup.moshi.Moshi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by Kavi @ SPECTRL Ltd. on 27/09/2016.
 */

@Module
public class ApiModule {
    private static final String MARVEL_API_URL = "http://gateway.marvel.com/";

    @Provides @Singleton
    HttpUrl provideBaseUrl() {
        return HttpUrl.parse(MARVEL_API_URL);
    }

    @Provides @Singleton
    Moshi provideMoshi() {
        return new Moshi.Builder()
                .add(JsonAdapterFactory.create())
                .build();
    }

    @Provides @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient client = new OkHttpClient();
        // TODO: 28/09/2016 Configure OkHttpClient - timeout, cache etc.
        return client;
    }

    @Provides @Singleton
    Retrofit provideRetrofit(HttpUrl baseUrl, Moshi moshi, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }
}
