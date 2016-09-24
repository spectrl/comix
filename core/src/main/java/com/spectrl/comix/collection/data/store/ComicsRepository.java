package com.spectrl.comix.collection.data.store;

import com.spectrl.comix.collection.data.model.Comic;

import java.util.List;

/**
 * Created by Kavi @ SPECTRL Ltd. on 22/09/2016.
 */

public interface ComicsRepository {

    // TODO: 22/09/2016 Rx-ify (make async)
    List<Comic> fetchComics();
}
