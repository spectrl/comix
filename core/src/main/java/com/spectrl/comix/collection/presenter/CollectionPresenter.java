package com.spectrl.comix.collection.presenter;

import com.spectrl.comix.collection.data.model.Comic;
import com.spectrl.comix.collection.data.repository.ComicsRepository;
import com.spectrl.comix.di.MainThread;
import com.spectrl.comix.presenter.BasePresenter;

import javax.inject.Inject;

import rx.Scheduler;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.spectrl.comix.collection.view.CollectionContract.CollectionInteractionListener;
import static com.spectrl.comix.collection.view.CollectionContract.CollectionView;

/**
 * Created by Kavi @ SPECTRL Ltd. on 22/09/2016.
 */

public class CollectionPresenter extends BasePresenter<CollectionView> implements CollectionInteractionListener {

    private static final int COMIC_LIMIT = 100;
    
    private CompositeSubscription subscriptions = new CompositeSubscription();

    @Inject @MainThread
    Scheduler mainThread;

    private final ComicsRepository comicsRepository;

    @Inject
    public CollectionPresenter(ComicsRepository comicsRepository) {
        this.comicsRepository = comicsRepository;
    }

    @Override
    public void enter() {
        getView().attach(this);
        refreshComics();
    }

    @Override
    public void exit() {
        getView().detach(this);
    }

    @Override
    public void refreshComics() {
        subscriptions.add(comicsRepository.fetchComics(COMIC_LIMIT)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread)
                .doOnSubscribe(() -> {
                    if (!hasView()) { return; }
                    getView().setProgressIndicator(true);
                })
                .subscribe(comics -> {
                    if (!hasView()) { return; }
                    getView().setProgressIndicator(false);
                    getView().displayComics(comics);
                }, throwable -> {
                    if (!hasView()) { return; }
                    getView().setProgressIndicator(false);
                    // TODO: 02/10/2016 Display error
                }));
    }

    @Override
    public void onComicChosen(Comic comic) {
        // TODO: 22/09/2016 Open Comic details Activity using Navigator
    }

    @Override
    public void onSetBudget(float budget) {
        
    }

    @Override
    public void onPageCount() {
        if (!hasView()) { return; }
        getView().showPageCount();
    }
}
