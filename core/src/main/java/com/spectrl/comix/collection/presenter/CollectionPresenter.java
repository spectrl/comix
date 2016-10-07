package com.spectrl.comix.collection.presenter;

import com.spectrl.comix.collection.data.model.Comic;
import com.spectrl.comix.collection.data.model.Comics;
import com.spectrl.comix.collection.data.repository.ComicsRepository;
import com.spectrl.comix.collection.view.model.Budget;
import com.spectrl.comix.di.ActivityScope;
import com.spectrl.comix.di.MainThread;
import com.spectrl.comix.presenter.BasePresenter;
import com.spectrl.comix.view.Navigator;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import rx.Scheduler;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.spectrl.comix.collection.view.CollectionContract.CollectionInteractionListener;
import static com.spectrl.comix.collection.view.CollectionContract.CollectionView;
import static com.spectrl.comix.collection.view.model.Budget.*;

/**
 * Created by Kavi @ SPECTRL Ltd. on 22/09/2016.
 */

@ActivityScope
public class CollectionPresenter extends BasePresenter<CollectionView> implements CollectionInteractionListener {
    private final static Logger LOGGER = Logger.getLogger(CollectionPresenter.class.getName());

    private static final int COMIC_LIMIT = 100;
    
    private CompositeSubscription subscriptions = new CompositeSubscription();

    @Inject @MainThread
    Scheduler mainThread;
    @Inject Navigator navigator;

    private final ComicsRepository comicsRepository;

    private int totalPageCount;
    private Action budgetAction = Action.CLOSE;

    @Inject
    public CollectionPresenter(ComicsRepository comicsRepository) {
        this.comicsRepository = comicsRepository;
    }

    @Override
    public void enter() {
        getView().attach(this);
        if (budgetAction == Action.CLOSE) {
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
                    LOGGER.log(Level.SEVERE, throwable.getMessage(), throwable);
                    if (!hasView()) { return; }
                    getView().setProgressIndicator(false);
                    // TODO: 02/10/2016 Display error
                }));
    }

    @Override
    public void onComicChosen(Comic comic) {
        navigator.openComic(comic.id());
    }

    @Override
    public void onBudget(Budget budget) {
        this.budgetAction = budget.action();
        switch (budget.action()) {
            case OPEN:
                if (!hasView()) { return; }
                getView().setRefreshEnabled(false);
                break;
            case CLOSE:
                refreshComics(false);
                if (!hasView()) { return; }
                getView().setRefreshEnabled(true);
                if (getView().isShowingBudgetInfo()) {
                    getView().showBudgetInfo(false);
                }
                break;
            case UPDATE:
                findComics(budget.amount());
                break;
            case CLEAR:
                findComics(BigDecimal.valueOf(Float.MAX_VALUE));
                break;
        }
    }

    private void findComics(BigDecimal budget) {
        LOGGER.log(Level.INFO, String.format(Locale.ENGLISH, "Budget is %f", budget));

        subscriptions.add(comicsRepository.comicsInBudget(budget)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread)
                .doOnNext(comics -> totalPageCount = countPages(comics))
                .doOnError(throwable -> totalPageCount = 0)
                .subscribe(comics -> {
                    if (!hasView()) { return; }
                    getView().displayComics(comics);
                    if (!getView().isShowingBudgetInfo()) {
                        getView().showBudgetInfo(true);
                    }
                    getView().setBudgetComicCount(comics.size());
                    getView().setBudgetComicPrice(totalPrice(comics));
                }));
    }

    @Override
    public void onPageCount() {
        if (!hasView()) { return; }
        getView().showPageCount(totalPageCount);
    }

    private int countPages(Comics comics) {
        int pages = 0;
        for (Comic comic : comics.comicList()) {
            pages += comic.pageCount();
        }
        return pages;
    }

    private String totalPrice(Comics comics) {
        BigDecimal total = BigDecimal.ZERO;
        for (Comic comic : comics.comicList()) {
            total = total.add(BigDecimal.valueOf(comic.lowestPrice()));
        }
        return total.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }
}
