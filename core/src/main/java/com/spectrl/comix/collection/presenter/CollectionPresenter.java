package com.spectrl.comix.collection.presenter;

import com.spectrl.comix.collection.data.model.Comic;
import com.spectrl.comix.collection.data.repository.ComicsRepository;
import com.spectrl.comix.di.MainThread;
import com.spectrl.comix.presenter.BasePresenter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private final static Logger LOGGER = Logger.getLogger(CollectionPresenter.class.getName());

    private static final int COMIC_LIMIT = 100;
    
    private CompositeSubscription subscriptions = new CompositeSubscription();

    @Inject @MainThread
    Scheduler mainThread;

    private final ComicsRepository comicsRepository;

    private int totalPageCount;
    private BigDecimal budget = BigDecimal.valueOf(-1);

    @Inject
    public CollectionPresenter(ComicsRepository comicsRepository) {
        this.comicsRepository = comicsRepository;
    }

    @Override
    public void enter() {
        getView().attach(this);
        if (!haveBudget()) {
            refreshComics(true);
        }
    }

    @Override
    public void exit() {
        getView().detach(this);
    }

    @Override
    public void refreshComics(boolean forceUpdate) {
        subscriptions.add(comicsRepository.fetchComics(COMIC_LIMIT)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread)
                .doOnSubscribe(() -> {
                    if (!hasView() || !forceUpdate) { return; }
                    getView().setProgressIndicator(true);
                })
                .doOnNext(comics -> totalPageCount = countPages(comics))
                .doOnError(throwable -> totalPageCount = 0)
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
    public void onSetBudget(BigDecimal budget) {
        LOGGER.log(Level.INFO, String.format(Locale.ENGLISH, "Budget is %f", budget));
        this.budget = budget;

        // If we have no budget, load everything
        if (!haveBudget()) {
            refreshComics(false);
            return;
        }

        // Otherwise display comics in budget
        subscriptions.add(comicsRepository.comicsInBudget(budget)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread)
                .doOnNext(comics -> totalPageCount = countPages(comics))
                .doOnError(throwable -> totalPageCount = 0)
                .subscribe(comics -> {
                    if (!hasView()) { return; }
                    getView().displayComics(comics);
                }));
    }

    @Override
    public void onPageCount() {
        if (!hasView()) { return; }
        getView().showPageCount(totalPageCount);
    }

    private int countPages(List<Comic> comics) {
        int pages = 0;
        for (Comic comic : comics) {
            pages += comic.pageCount();
        }
        return pages;
    }

    private boolean haveBudget() {
        return budget.compareTo(BigDecimal.ZERO) >= 0;
    }
}
