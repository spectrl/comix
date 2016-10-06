package com.spectrl.comix.collection.data.model;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Created by Kavi @ SPECTRL Ltd. on 22/09/2016.
 */

@AutoValue
public abstract class Comic {

    public abstract int id();
    public abstract String title();
    public abstract double issueNumber();
    @Nullable public abstract String description();
    public abstract int pageCount();
    public abstract Image thumbnail();
    public abstract List<Price> prices();
    public abstract List<Image> images();
    public abstract Creators creators();

    public static Comic create(int id, String title, double issueNumber, String description,
                               int pageCount, Image thumbnail, List<Price> prices, List<Image> images, Creators creators) {
        return new AutoValue_Comic(id, title, issueNumber, description, pageCount, thumbnail, prices, images, creators);
    }

    public static JsonAdapter<Comic> jsonAdapter(Moshi moshi) {
        return new AutoValue_Comic.MoshiJsonAdapter(moshi);
    }

    public float lowestPrice() {
        List<Price> sortedList = new ArrayList<>(prices());
        Collections.sort(sortedList, Price.byPrice());
        return sortedList.get(0).price();
    }

    @AutoValue
    public static abstract class Price {
        public abstract String type();
        public abstract float price();

        public static Price create(String type, float price) {
            return new AutoValue_Comic_Price(type, price);
        }

        public static JsonAdapter<Price> jsonAdapter(Moshi moshi) {
            return new AutoValue_Comic_Price.MoshiJsonAdapter(moshi);
        }

        private static Comparator<? super Price> byPrice() {
            return new Comparator<Price>() {
                @Override
                public int compare(Price o1, Price o2) {
                    return Float.compare(o1.price(), (o2.price()));
                }
            };
        }
    }

    @AutoValue
    public static abstract class Image {
        public abstract String path();
        public abstract String extension();

        public String imageUrl() {
            return String.format("%s.%s", path(), extension());
        }

        public static Image create(String path, String extension) {
            return new AutoValue_Comic_Image(path, extension);
        }

        public static JsonAdapter<Image> jsonAdapter(Moshi moshi) {
            return new AutoValue_Comic_Image.MoshiJsonAdapter(moshi);
        }
    }

    @AutoValue
    public static abstract class Creators {
        public abstract List<CreatorSummary> items();

        public static Creators create(List<CreatorSummary> items) {
            return new AutoValue_Comic_Creators(items);
        }

        public static JsonAdapter<Creators> jsonAdapter(Moshi moshi) {
            return new AutoValue_Comic_Creators.MoshiJsonAdapter(moshi);
        }

        @AutoValue
        public static abstract class CreatorSummary {
            public abstract String resourceURI();
            public abstract String name();
            public abstract String role();

            public static CreatorSummary create(String resourceURI, String name, String role) {
                return new AutoValue_Comic_Creators_CreatorSummary(resourceURI, name, role);
            }

            public static JsonAdapter<CreatorSummary> jsonAdapter(Moshi moshi) {
                return new AutoValue_Comic_Creators_CreatorSummary.MoshiJsonAdapter(moshi);
            }
        }
    }
}
