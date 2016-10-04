package com.spectrl.comix.collection.data.repository;

import com.spectrl.comix.api.MarvelService;
import com.spectrl.comix.api.data.model.MarvelApiResponse;
import com.spectrl.comix.collection.data.model.Comic;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Mockito.when;

/**
 * Created by Kavi @ SPECTRL Ltd. on 04/10/2016.
 */
public class ComicStoreTest {
    private static final int COMIC_LIMIT = 3;
    private static final List<Comic> COMICS = Arrays.asList(
            Comic.create(0, "TITLE1", 1, 100,
                    Collections.singletonList(Comic.Price.create("digital", 5.00f)),
                    Collections.singletonList(Comic.Image.create("PATH", "png"))),
            Comic.create(1, "TITLE2", 3, 100,
                    Collections.singletonList(Comic.Price.create("digital", 5.00f)),
                    Collections.singletonList(Comic.Image.create("PATH", "png"))),
            Comic.create(2, "TITLE3", 5, 100,
                    Collections.singletonList(Comic.Price.create("digital", 5.00f)),
                    Collections.singletonList(Comic.Image.create("PATH", "png"))));
    private static final MarvelApiResponse API_RESPONSE = MarvelApiResponse.create(
            200, "test", MarvelApiResponse.Data.create(COMIC_LIMIT, COMICS));

    @Mock
    private MarvelService marvelService;

    private ComicStore comicStore;

    @Before
    public void setUp() throws Exception {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        comicStore = new ComicStore(marvelService);

        when(marvelService.getComics(COMIC_LIMIT)).thenReturn(Observable.just(API_RESPONSE));
    }

    @Test
    public void requestFromServiceAndMapToComics() {
        Observable<List<Comic>> comicsObservable = comicStore.fetchComics(COMIC_LIMIT);
        TestSubscriber<List<Comic>> testSubscriber = new TestSubscriber<>();
        comicsObservable.subscribe(testSubscriber);

        testSubscriber.assertReceivedOnNext(Collections.singletonList(COMICS));
    }
}