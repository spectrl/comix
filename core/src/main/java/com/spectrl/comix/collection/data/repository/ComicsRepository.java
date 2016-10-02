package com.spectrl.comix.collection.data.repository;

import com.spectrl.comix.collection.data.model.Comic;

import java.util.List;

import rx.Observable;

/**
 * Created by Kavi @ SPECTRL Ltd. on 22/09/2016.
 */

public interface ComicsRepository {

    Observable<List<Comic>> fetchComics(int limit);
}
