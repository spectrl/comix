package com.spectrl.comix.collection.data.repository;

import com.spectrl.comix.api.MarvelService;
import com.spectrl.comix.collection.data.model.Comics;
import com.spectrl.comix.util.TestDataFactory;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.Observable;
import rx.observers.TestSubscriber;

import static com.google.common.truth.Truth.assertThat;
import static com.spectrl.comix.util.TestDataFactory.COMIC_LIMIT;
import static org.mockito.Mockito.when;

/**
 * Created by Kavi @ SPECTRL Ltd. on 07/10/2016.
 */
public class RetrofitNetworkSourceTest {

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

        when(marvelService.getComics(COMIC_LIMIT)).thenReturn(Observable.just(TestDataFactory.getApiResponse()));
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