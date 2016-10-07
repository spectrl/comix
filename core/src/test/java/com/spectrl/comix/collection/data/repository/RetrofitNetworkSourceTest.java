package com.spectrl.comix.collection.data.repository;

import com.spectrl.comix.api.MarvelService;
import com.spectrl.comix.api.data.model.MarvelApiResponse;
import com.spectrl.comix.collection.data.model.Comic;
import com.spectrl.comix.collection.data.model.Comics;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by Kavi @ SPECTRL Ltd. on 07/10/2016.
 */
public class RetrofitNetworkSourceTest {
    private static final int COMIC_LIMIT = 3;

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
    @Mock
    private MarvelService marvelService;

    private RetrofitNetworkSource networkSource;

    @Before
    public void setUp() throws Exception {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        networkSource = new RetrofitNetworkSource(marvelService);

        when(marvelService.getComics(COMIC_LIMIT)).thenReturn(Observable.just(API_RESPONSE));
    }

    @Test
    public void wellFormedApiRequest() {
        Observable<Comics> comicsObservable = networkSource.getComics(COMIC_LIMIT);
        TestSubscriber<Comics> testSubscriber = new TestSubscriber<>();
        comicsObservable.subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void mapApiResponseToComics() {
        Observable comicsObservable = networkSource.getComics(COMIC_LIMIT);
        TestSubscriber testSubscriber = new TestSubscriber<>();
        comicsObservable.subscribe(testSubscriber);

        assertThat(testSubscriber.getOnNextEvents().get(0).getClass()).isAssignableTo(Comics.class);
    }

    @Test
    public void comicsMarkedWithNetworkSourceMetadata() {
        Observable<Comics> comicsObservable = networkSource.getComics(COMIC_LIMIT);
        TestSubscriber<Comics> testSubscriber = new TestSubscriber<>();
        comicsObservable.subscribe(testSubscriber);

        Comics comics = testSubscriber.getOnNextEvents().get(0);
        assertThat(comics.source()).isEqualTo(Comics.Source.NETWORK);
    }

    @Test
    public void timestampIsInThePast() {
        Observable<Comics> comicsObservable = networkSource.getComics(COMIC_LIMIT);
        TestSubscriber<Comics> testSubscriber = new TestSubscriber<>();
        comicsObservable.subscribe(testSubscriber);

        Comics comics = testSubscriber.getOnNextEvents().get(0);
        assertThat(comics.timestamp()).isAtMost(System.currentTimeMillis() / 1000L);
    }
}