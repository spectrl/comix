package com.spectrl.comix.collection.presenter;

import com.spectrl.comix.collection.data.model.Comic;
import com.spectrl.comix.collection.data.repository.ComicsRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import rx.Observable;

import static com.spectrl.comix.collection.view.CollectionContract.CollectionView;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Kavi @ SPECTRL Ltd. on 22/09/2016.
 */
public class CollectionPresenterTest {
    private static final int COMIC_LIMIT = 3;
    private static final List<Comic> COMICS = Arrays.asList(
            Comic.create(0, "TITLE1", 1, 100,
                    Collections.singletonList(Comic.Price.create("digital", 5.00f)), Collections.singletonList(Comic.Image.create("PATH", "png"))),
            Comic.create(1, "TITLE2", 3, 100,
                    Collections.singletonList(Comic.Price.create("digital", 5.00f)), Collections.singletonList(Comic.Image.create("PATH", "png"))),
            Comic.create(2, "TITLE3", 5, 100,
                    Collections.singletonList(Comic.Price.create("digital", 5.00f)), Collections.singletonList(Comic.Image.create("PATH", "png"))));

    @Mock
    private CollectionView collectionView;

    @Mock
    private ComicsRepository comicsRepository;

    private CollectionPresenter collectionPresenter;

    @Before
    public void setUp() throws Exception {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        collectionPresenter = new CollectionPresenter(comicsRepository);
        collectionPresenter.takeView(collectionView);

        // Stub method to return dummy data
        when(comicsRepository.fetchComics(COMIC_LIMIT)).thenReturn(Observable.just(COMICS));
    }

    @After
    public void tearDown() throws Exception {
        collectionPresenter.dropView(collectionView);
    }

    @Test
    public void fetchComicsAndDisplay() {
        collectionPresenter.refreshComics();

        verify(collectionView).setProgressIndicator(true);
        verify(comicsRepository).fetchComics(COMIC_LIMIT);
        verify(collectionView).setProgressIndicator(false);
        verify(collectionView).displayComics(COMICS);
    }

    @Test
    public void openComicDetailsWhenChosen() {
        // TODO: 22/09/2016 Requires Navigator
    }
}