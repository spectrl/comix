package com.spectrl.comix.collection.data.repository;

import com.spectrl.comix.api.MarvelService;
import com.spectrl.comix.collection.data.model.Comic;

import java.util.List;

import rx.Observable;

/**
 * Created by Kavi @ SPECTRL Ltd. on 22/09/2016.
 */

public class ComicStore implements ComicsRepository {

    // This could be split into separate interfaces per feature for larger applications
    private final MarvelService marvelService;

    public ComicStore(MarvelService marvelService) {
        this.marvelService = marvelService;
    }

    @Override
    public Observable<List<Comic>> fetchComics(int limit) {
        return marvelService.getComics(100);
    }
}
