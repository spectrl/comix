package com.spectrl.comix.collection.data.repository.source;

import com.spectrl.comix.collection.data.model.Comic;

import java.util.List;

import rx.Observable;

/**
 * Created by Kavi @ SPECTRL Ltd. on 01/10/2016.
 */

public interface ComicService {

    Observable<List<Comic>> getComics();
}
