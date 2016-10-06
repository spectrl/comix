package com.spectrl.comix.collection.data.repository;

import com.spectrl.comix.api.MarvelService;
import com.spectrl.comix.collection.data.model.Comics;

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

    public Observable<Comics> getComics(int limit) {
        return marvelService.getComics(limit)
                .map(marvelApiResponse -> Comics.builder()
                        .comicList(marvelApiResponse.data().results())
                        .build());
    }
}
