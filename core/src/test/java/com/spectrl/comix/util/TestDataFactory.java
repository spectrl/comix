package com.spectrl.comix.util;

import com.spectrl.comix.api.data.model.MarvelApiResponse;
import com.spectrl.comix.collection.data.model.Comic;
import com.spectrl.comix.collection.data.model.Comics;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.spectrl.comix.collection.data.model.Comics.*;

/**
 * Created by Kavi @ SPECTRL Ltd. on 07/10/2016.
 */

public class TestDataFactory {

    private static final Comic COMIC = Comic.create(5, "TITLE1", "DESCRIPTION", 100, Comic.Image.create("thumb", "ext"),
            Collections.singletonList(Comic.Price.create("digital", 5.00f)),
            Collections.singletonList(Comic.Image.create("PATH", "png")),
            Comic.Creators.create(Collections.singletonList(
                    Comic.Creators.CreatorSummary.create("uri", "Kavi", "creator"))));

    public static final int COMIC_LIMIT = 3;

    private static final List<Comic> COMIC_LIST = Arrays.asList(
            Comic.create(0, "TITLE1", "DESCRIPTION", 100, Comic.Image.create("thumb", "ext"),
                    Collections.singletonList(Comic.Price.create("digital", 5.00f)),
                    Collections.singletonList(Comic.Image.create("PATH", "png")),
                    Comic.Creators.create(Collections.singletonList(
                            Comic.Creators.CreatorSummary.create("uri", "Kavi", "creator")))),
            Comic.create(1, "TITLE2", "DESCRIPTION", 100, Comic.Image.create("thumb", "ext"),
                    Collections.singletonList(Comic.Price.create("digital", 5.00f)),
                    Collections.singletonList(Comic.Image.create("PATH", "png")),
                    Comic.Creators.create(Collections.singletonList(
                            Comic.Creators.CreatorSummary.create("uri", "Kavi", "creator")))),
            Comic.create(2, "TITLE3", "DESCRIPTION", 100, Comic.Image.create("thumb", "ext"),
                    Collections.singletonList(Comic.Price.create("digital", 5.00f)),
                    Collections.singletonList(Comic.Image.create("PATH", "png")),
                    Comic.Creators.create(Collections.singletonList(
                            Comic.Creators.CreatorSummary.create("uri", "Kavi", "creator")))));

    private static final MarvelApiResponse API_RESPONSE = MarvelApiResponse.create(
            200, "test", MarvelApiResponse.Data.create(COMIC_LIMIT, COMIC_LIST));

    public static final int PAGE_COUNT = 300;

    private static final Comics COMICS = builder()
            .comicList(COMIC_LIST)
            .timestamp(0)
            .source(Source.MEMORY)
            .build();

    public static Comic getComic() {
        return COMIC;
    }

    public static Comics getComics() {
        return COMICS;
    }

    public static MarvelApiResponse getApiResponse() {
        return API_RESPONSE;
    }
}
