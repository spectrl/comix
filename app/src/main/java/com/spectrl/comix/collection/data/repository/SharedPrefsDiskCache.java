package com.spectrl.comix.collection.data.repository;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.spectrl.comix.collection.data.model.Comics;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import rx.Completable;
import rx.Observable;

import static com.spectrl.comix.collection.data.model.Comics.*;

/**
 * Created by Kavi @ SPECTRL Ltd. on 06/10/2016.
 */

public class SharedPrefsDiskCache implements DiskCache<String, Comics> {

    private final SharedPreferences sharedPrefs;
    private final JsonAdapter<Comics> jsonAdapter;

    public SharedPrefsDiskCache(SharedPreferences sharedPrefs, Moshi moshi) {
        this.sharedPrefs = sharedPrefs;

        jsonAdapter = moshi.adapter(Comics.class);
    }

    @Override
    public Completable put(String key, Comics comics) {
        String json = jsonAdapter.toJson(comics);
        return Completable.fromAction(() -> sharedPrefs.edit()
                .putString(key, json)
                .apply());
    }

    @Override
    public Observable<Comics> get(String key) {
        return Observable.fromCallable(() -> {
            String json = sharedPrefs.getString(key, "");
            return json.isEmpty() ? null : jsonAdapter.fromJson(json)
                    .toBuilder()
                    .source(Source.DISK)
                    .build();
        });
    }
}
