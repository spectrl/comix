package com.spectrl.comix.api;

import com.squareup.moshi.Moshi;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level;

/**
 * Created by Kavi @ SPECTRL Ltd. on 27/09/2016.
 */

@Module
public class ApiModule {
    private static final String MARVEL_API_URL = "http://gateway.marvel.com/";
    private static final String HTTP_CACHE_DIR = "http-cache";
    private static final int CACHE_SIZE = 10 * 1024 * 1024; // 10 MiB
    private static final String CACHE_CONTROL = "Cache-Control";

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
    Cache provideCache(File cacheDirectory) {
        return new Cache(new File(cacheDirectory, HTTP_CACHE_DIR), CACHE_SIZE);
    }

    @Provides @Singleton
    Interceptor provideCacheControlInterceptor() {
        return chain -> {
            Response originalResponse = chain.proceed(chain.request());
            CacheControl cacheControl = new CacheControl.Builder()
                    .maxAge(60, TimeUnit.SECONDS)
                    .build();
            return originalResponse.newBuilder()
                    .header(CACHE_CONTROL, cacheControl.toString())
                    .build();
        };
    }

    @Provides @Singleton
    OkHttpClient provideOkHttpClient(MarvelAuthorizationInterceptor marvelAuthorizationInterceptor, Interceptor cacheControl, Cache cache) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(isDebug ? Level.HEADERS : Level.NONE);
        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(marvelAuthorizationInterceptor)
                .addInterceptor(cacheControl)
                .cache(cache)
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
