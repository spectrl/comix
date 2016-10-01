package com.spectrl.comix.collection.data.repository;

import com.spectrl.comix.collection.data.model.Comic;
import com.spectrl.comix.collection.data.repository.source.ComicService;

import java.util.List;

import rx.Observable;

/**
 * Created by Kavi @ SPECTRL Ltd. on 22/09/2016.
 */

public class ComicStore implements ComicsRepository {

    private final ComicService comicService;

    public ComicStore(ComicService comicService) {
        this.comicService = comicService;
    }

    @Override
    public Observable<List<Comic>> fetchComics() {
        return comicService.getComics();
    }
}
