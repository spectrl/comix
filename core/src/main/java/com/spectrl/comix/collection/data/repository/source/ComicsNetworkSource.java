package com.spectrl.comix.collection.data.repository.source;

import com.spectrl.comix.api.MarvelService;
import com.spectrl.comix.collection.data.model.Comic;

import java.util.List;

import rx.Observable;

/**
 * Created by Kavi @ SPECTRL Ltd. on 01/10/2016.
 */

public class ComicsNetworkSource implements ComicService {

    private final MarvelService marvelService;

    public ComicsNetworkSource(MarvelService marvelService) {
        this.marvelService = marvelService;
    }

    @Override
    public Observable<List<Comic>> getComics() {
        return marvelService.getComics(100);
    }
}
