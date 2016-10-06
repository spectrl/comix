package com.spectrl.comix.collection.data.repository;

import com.spectrl.comix.api.MarvelService;
import com.spectrl.comix.collection.data.model.Comic;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by Kavi @ SPECTRL Ltd. on 06/10/2016.
 */

@Singleton
public class RetrofitNetworkSource {

    private final MarvelService marvelService;

    @Inject
    public RetrofitNetworkSource(MarvelService marvelService) {
        this.marvelService = marvelService;
    }

    public Observable<List<Comic>> getComics(int limit) {
        return marvelService.getComics(limit)
                .map(marvelApiResponse -> marvelApiResponse.data().results());
    }
}
