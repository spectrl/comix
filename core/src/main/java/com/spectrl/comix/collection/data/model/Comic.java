package com.spectrl.comix.collection.data.model;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.List;

/**
 * Created by Kavi @ SPECTRL Ltd. on 22/09/2016.
 */

@AutoValue
public abstract class Comic {

    abstract int id();
    abstract String title();
    abstract double issueNumber();
    abstract int pageCount();
    abstract List<Price> prices();
    abstract List<Image> images();

    public static Comic create(int id, String title, double issueNumber, int pageCount, List<Price> prices, List<Image> images) {
        return new AutoValue_Comic(id, title, issueNumber, pageCount, prices, images);
    }

    public static JsonAdapter<Comic> jsonAdapter(Moshi moshi) {
        return new AutoValue_Comic.MoshiJsonAdapter(moshi);
    }

    @AutoValue
    public static abstract class Price {
        abstract String type();
        abstract float price();

        public static Price create(String type, float price) {
            return new AutoValue_Comic_Price(type, price);
        }

        public static JsonAdapter<Price> jsonAdapter(Moshi moshi) {
            return new AutoValue_Comic_Price.MoshiJsonAdapter(moshi);
        }
    }

    @AutoValue
    public static abstract class Image {
        abstract String path();
        abstract String extension();

        public static Image create(String path, String extension) {
            return new AutoValue_Comic_Image(path, extension);
        }

        public static JsonAdapter<Image> jsonAdapter(Moshi moshi) {
            return new AutoValue_Comic_Image.MoshiJsonAdapter(moshi);
        }
    }
}
