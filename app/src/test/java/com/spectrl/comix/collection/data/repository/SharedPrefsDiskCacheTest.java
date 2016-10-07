package com.spectrl.comix.collection.data.repository;

import android.content.SharedPreferences;

import com.spectrl.comix.api.JsonAdapterFactory;
import com.spectrl.comix.collection.data.model.Comics;
import com.squareup.moshi.Moshi;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import rx.Observable;
import rx.observers.TestSubscriber;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by Kavi @ SPECTRL Ltd. on 07/10/2016.
 */
public class SharedPrefsDiskCacheTest {
    private static final String DUMMY_KEY = "key";

    private static final String JSON = "{\n" +
            "  \"comicList\": [\n" +
            "    {\n" +
            "      \"id\": 42882,\n" +
            "      \"title\": \"Lorna the Jungle Girl (1954) #6\",\n" +
            "      \"pageCount\": 32,\n" +
            "      \"thumbnail\": {\n" +
            "        \"path\": \"http://i.annihil.us/u/prod/marvel/i/mg/9/40/50b4fc783d30f\",\n" +
            "        \"extension\": \"jpg\"\n" +
            "      },\n" +
            "      \"prices\": [\n" +
            "        {\n" +
            "          \"type\": \"printPrice\",\n" +
            "          \"price\": 0\n" +
            "        }\n" +
            "      ],\n" +
            "      \"images\": [\n" +
            "        {\n" +
            "          \"path\": \"http://i.annihil.us/u/prod/marvel/i/mg/9/40/50b4fc783d30f\",\n" +
            "          \"extension\": \"jpg\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"creators\": {\n" +
            "        \"items\": []\n" +
            "      }\n" +
            "    }\n" +
            "  ],\n" +
            "  \"timestamp\": 1475845110,\n" +
            "  \"source\": \"NETWORK\"\n" +
            "}";

    @Mock
    private SharedPreferences sharedPreferences;

    private SharedPrefsDiskCache diskCache;

    @Before
    public void setUp() throws Exception {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Can't mock final class
        Moshi moshi = new Moshi.Builder()
                .add(JsonAdapterFactory.create())
                .build();

        // Get a reference to the class under test
        diskCache = new SharedPrefsDiskCache(sharedPreferences, moshi);
    }

    @Test
    public void singleEmission() {
        when(sharedPreferences.getString(anyString(), anyString())).thenReturn(JSON);

        Observable<Comics> comicsObservable = diskCache.get(DUMMY_KEY);
        TestSubscriber<Comics> testSubscriber = new TestSubscriber<>();
        comicsObservable.subscribe(testSubscriber);

        testSubscriber.assertValueCount(1);
    }

    @Test
    public void emptyCacheOrInvalidKey() {
        when(sharedPreferences.getString(anyString(), anyString())).thenReturn("");

        Observable<Comics> comicsObservable = diskCache.get(DUMMY_KEY);
        TestSubscriber<Comics> testSubscriber = new TestSubscriber<>();
        comicsObservable.subscribe(testSubscriber);

        assertThat(testSubscriber.getOnNextEvents().get(0)).isNull();

        testSubscriber.assertNoErrors();
        testSubscriber.assertTerminalEvent();
    }

    @Test
    public void malformedJson() {
        when(sharedPreferences.getString(anyString(), anyString())).thenReturn("+invalid_json!@");

        Observable<Comics> comicsObservable = diskCache.get(DUMMY_KEY);
        TestSubscriber<Comics> testSubscriber = new TestSubscriber<>();
        comicsObservable.subscribe(testSubscriber);

        testSubscriber.assertError(IOException.class);
        testSubscriber.assertNoValues();
        testSubscriber.assertTerminalEvent();
    }

    @Test
    public void comicsMarkedWithDiskSourceMetadata() {
        when(sharedPreferences.getString(anyString(), anyString())).thenReturn(JSON);

        Observable<Comics> comicsObservable = diskCache.get(DUMMY_KEY);
        TestSubscriber<Comics> testSubscriber = new TestSubscriber<>();
        comicsObservable.subscribe(testSubscriber);

        Comics comics = testSubscriber.getOnNextEvents().get(0);
        assertThat(comics.source()).isEqualTo(Comics.Source.DISK);
    }
}