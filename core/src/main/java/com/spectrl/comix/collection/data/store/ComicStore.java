package com.spectrl.comix.collection.data.store;

import com.spectrl.comix.collection.data.model.Comic;

import java.util.List;

/**
 * Created by Kavi @ SPECTRL Ltd. on 22/09/2016.
 */

public class ComicStore implements ComicsRepository {

    @Override
    public List<Comic> fetchComics() {
        // TODO: 22/09/2016 Get from API service using Retrofit
        return null;
    }
}
