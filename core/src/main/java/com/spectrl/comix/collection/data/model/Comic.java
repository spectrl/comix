package com.spectrl.comix.collection.data.model;

import com.google.auto.value.AutoValue;

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

    public static Comic create(int id, String title, double issueNumber, int pageCount, Price[] prices, Image[] images) {
        return new AutoValue_Comic(id, title, issueNumber, pageCount, prices, images);
    }

    @AutoValue
    static abstract class Price {
        abstract String type();
        abstract float price();

        public Price create(String type, float price) {
            return new AutoValue_Comic_Price(type, price);
        }
    }

    @AutoValue
    static abstract class Image {
        abstract String path();
        abstract String extension();

        public Image create(String path, String extension) {
            return new AutoValue_Comic_Image(path, extension);
        }
    }
}
