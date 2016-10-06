package com.spectrl.comix.collection.data.model;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Kavi @ SPECTRL Ltd. on 06/10/2016.
 */

@AutoValue
public abstract class Comics {

    public enum Source {
        NETWORK,
        DISK,
        MEMORY
    }

    public abstract List<Comic> comicList();
    abstract long timestamp();
    abstract Source source();

    public static Builder builder() {
        return new AutoValue_Comics.Builder()
                .timestamp(System.currentTimeMillis() / 1000);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder comicList(List<Comic> comics);
        abstract Builder timestamp(long timestamp);
        public abstract Builder source(Source source);
        public abstract Comics build();
    }

    public abstract Builder toBuilder();

    public static JsonAdapter<Comics> jsonAdapter(Moshi moshi) {
        return new AutoValue_Comics.MoshiJsonAdapter(moshi);
    }

    public Comic getComicAt(int position) {
        return comicList().get(position);
    }

    public int size() {
        return comicList().size();
    }

    public Comics sortedByPrice() {
        List<Comic> sortedList = new ArrayList<>(comicList());
        Collections.sort(sortedList, Comics.byPrice());
        return toBuilder()
                .comicList(sortedList)
                .build();
    }

    public static Comparator<? super Comic> byPrice() {
        return new Comparator<Comic>() {
            @Override
            public int compare(Comic o1, Comic o2) {
                return Float.compare(o1.lowestPrice(), o2.lowestPrice());
            }
        };
    }
}
