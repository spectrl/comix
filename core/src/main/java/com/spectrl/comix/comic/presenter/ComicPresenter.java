package com.spectrl.comix.comic.presenter;

import com.spectrl.comix.collection.data.model.Comic;
import com.spectrl.comix.collection.data.repository.ComicsRepository;
import com.spectrl.comix.di.MainThread;
import com.spectrl.comix.presenter.BasePresenter;

import rx.Scheduler;
import rx.Subscription;
import rx.schedulers.Schedulers;

import static com.spectrl.comix.comic.view.ComicContract.ComicInteractionListener;
import static com.spectrl.comix.comic.view.ComicContract.ComicView;

/**
 * Created by Kavi @ SPECTRL Ltd. on 07/10/2016.
 */

public class ComicPresenter extends BasePresenter<ComicView> implements ComicInteractionListener {

    private Subscription comicSubscription;

    private final int id;
    private final ComicsRepository comicsRepository;
    private final Scheduler mainThread;

    public ComicPresenter(int id, ComicsRepository comicsRepository, @MainThread Scheduler mainThread) {
        this.id = id;
        this.comicsRepository = comicsRepository;
        this.mainThread = mainThread;
    }

    @Override
    public void enter() {
        comicSubscription = comicsRepository.browseComic(id)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread)
                .subscribe(this::populateView);
    }

    @Override
    public void exit() {
        comicSubscription.unsubscribe();
    }

    private void populateView(Comic comic) {
        if (!hasView()) { return; }
        getView().setCover(comic.thumbnail().imageUrl());
        getView().setTitle(comic.title());
        getView().setPageCount(String.valueOf(comic.pageCount()));
        getView().setPrice(String.valueOf(comic.lowestPrice()));
        getView().setDescription(comic.description());
    }
}
