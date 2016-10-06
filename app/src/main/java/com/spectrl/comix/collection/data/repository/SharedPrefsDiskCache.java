package com.spectrl.comix.collection.data.repository;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.spectrl.comix.collection.data.model.Comic;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.lang.reflect.Type;
import java.util.List;

import rx.Completable;
import rx.Observable;

/**
 * Created by Kavi @ SPECTRL Ltd. on 06/10/2016.
 */

public class SharedPrefsDiskCache implements DiskCache<String, List<Comic>> {

    private final SharedPreferences sharedPrefs;
    private final JsonAdapter<List<Comic>> jsonAdapter;

    public SharedPrefsDiskCache(SharedPreferences sharedPrefs, Moshi moshi) {
        this.sharedPrefs = sharedPrefs;

        Type listOfComicsType = Types.newParameterizedType(List.class, Comic.class);
        jsonAdapter = moshi.adapter(listOfComicsType);
    }

    @Override
    public Completable put(String key, List<Comic> comics) {
        String json = jsonAdapter.toJson(comics);
        return Completable.fromAction(() -> sharedPrefs.edit()
                .putString(key, json)
                .apply());
    }

    @Override
    public Observable<List<Comic>> get(String key) {
        return Observable.fromCallable(() -> {
            String json = sharedPrefs.getString(key, "");
            return TextUtils.isEmpty(json) ? null : jsonAdapter.fromJson(json);
        });
    }
}
