package com.spectrl.comix.api.data.model;

import com.google.auto.value.AutoValue;
import com.spectrl.comix.collection.data.model.Comic;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.List;

/**
 * Created by Kavi @ SPECTRL Ltd. on 22/09/2016.
 */

@AutoValue
public abstract class MarvelApiResponse {

    public abstract int code();
    public abstract String status();
    public abstract Data data();

    public static MarvelApiResponse create(int code, String status, Data data) {
        return new AutoValue_MarvelApiResponse(code, status, data);
    }

    public static JsonAdapter<MarvelApiResponse> jsonAdapter(Moshi moshi) {
        return new AutoValue_MarvelApiResponse.MoshiJsonAdapter(moshi);
    }

    @AutoValue
    public static abstract class Data {
        public abstract int count();
        public abstract List<Comic> results();

        public static Data create(int count, List<Comic> results) {
            return new AutoValue_MarvelApiResponse_Data(count, results);
        }

        public static JsonAdapter<Data> jsonAdapter(Moshi moshi) {
            return new AutoValue_MarvelApiResponse_Data.MoshiJsonAdapter(moshi);
        }
    }
}
