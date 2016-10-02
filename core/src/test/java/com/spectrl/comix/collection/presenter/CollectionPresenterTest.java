package com.spectrl.comix.collection.presenter;

import com.spectrl.comix.collection.data.model.Comic;
import com.spectrl.comix.collection.data.repository.ComicsRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static com.spectrl.comix.collection.view.CollectionContract.CollectionView;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Kavi @ SPECTRL Ltd. on 22/09/2016.
 */
public class CollectionPresenterTest {
    private static List<Comic> COMICS = new ArrayList<>(); // TODO: 24/09/2016 Fill in once model class created

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
        collectionPresenter = new CollectionPresenter(collectionView, comicsRepository);

        // Stub method to return dummy data
        when(comicsRepository.fetchComics()).thenReturn(COMICS);
    }

    @Test
    public void fetchComicsAndDisplay() {
        collectionPresenter.refreshComics();

        verify(collectionView).setProgressIndicator(true);
        verify(comicsRepository).fetchComics();
        verify(collectionView).setProgressIndicator(false);
        verify(collectionView).displayComics(COMICS);
    }

    @Test
    public void openComicDetailsWhenChosen() {
        // TODO: 22/09/2016 Requires Navigator
    }
}