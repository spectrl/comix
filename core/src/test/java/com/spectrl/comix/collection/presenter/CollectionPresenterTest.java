package com.spectrl.comix.collection.presenter;

import com.spectrl.comix.collection.data.model.Comic;
import com.spectrl.comix.collection.data.repository.ComicsRepository;
import com.spectrl.comix.util.TestDataFactory;
import com.spectrl.comix.view.Navigator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.Observable;

import static com.google.common.truth.Truth.assertThat;
import static com.spectrl.comix.collection.view.CollectionContract.CollectionView;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Kavi @ SPECTRL Ltd. on 22/09/2016.
 */
public class CollectionPresenterTest {

    @Mock
    private CollectionView collectionView;
    @Mock
    private Navigator navigator;
    @Mock
    private ComicsRepository comicsRepository;

    private CollectionPresenter collectionPresenter;

    @Before
    public void setUp() throws Exception {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        collectionPresenter = new CollectionPresenter(comicsRepository, navigator);
        collectionPresenter.takeView(collectionView);

        // Stub method to return dummy data
        when(comicsRepository.fetchComics(TestDataFactory.COMIC_LIMIT)).thenReturn(Observable.just(TestDataFactory.getComics()));

        // FIXME: 07/10/2016 NEED TO MOCK SCHEDULERS IN ORDER TO RUN TESTS...
    }

    @After
    public void tearDown() throws Exception {
        collectionPresenter.dropView(collectionView);
    }

    @Test
    public void fetchComicsAndDisplay() {
        collectionPresenter.refreshComics(true);
        verify(comicsRepository).fetchComics(TestDataFactory.COMIC_LIMIT);
        verify(collectionView).displayComics(TestDataFactory.getComics());
    }

    @Test
    public void displayProgressOnForceUpdate() {
        collectionPresenter.refreshComics(true);

        verify(collectionView).setProgressIndicator(true);
        verify(comicsRepository).fetchComics(TestDataFactory.COMIC_LIMIT);
        verify(collectionView).setProgressIndicator(false);
    }

    @Test
    public void dontDisplayProgressWhenNotForceUpdate() {
        collectionPresenter.refreshComics(false);
        verify(collectionView, never()).setProgressIndicator(true);
    }

    @Test
    public void updatePageCount() {
        collectionPresenter.refreshComics(false);
        assertThat(collectionPresenter.totalPageCount).isEqualTo(TestDataFactory.PAGE_COUNT);
    }

    @Test
    public void unsubscribeSubscriptionsOnExit() {
        collectionPresenter.exit();
        // TODO: 07/10/2016
    }

    @Test
    public void openComicDetailsWhenChosen() {
        Comic comic = TestDataFactory.getComic();
        collectionPresenter.onComicChosen(comic);
        verify(navigator).openComic(comic.id());
    }
}