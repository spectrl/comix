package com.spectrl.comix.api;

import com.squareup.moshi.Moshi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.*;

/**
 * Created by Kavi @ SPECTRL Ltd. on 27/09/2016.
 */

@Module
public class ApiModule {
    private static final String MARVEL_API_URL = "http://gateway.marvel.com/";

    private final boolean isDebug;

    public ApiModule(boolean isDebug) {
        this.isDebug = isDebug;
    }

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
    OkHttpClient provideOkHttpClient(MarvelAuthorizationInterceptor marvelAuthorizationInterceptor) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(isDebug ? Level.BASIC : Level.NONE);
        return new OkHttpClient.Builder()
                .addInterceptor(marvelAuthorizationInterceptor)
                .addInterceptor(logging)
                .build();
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

    @Provides @Singleton
    MarvelService provideMarvelService(Retrofit retrofit) {
        return retrofit.create(MarvelService.class);
    }
}
