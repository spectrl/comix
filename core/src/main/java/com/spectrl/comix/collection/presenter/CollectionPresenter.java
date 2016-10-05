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
    private boolean isShowingBudgetInfo;

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
    public void onBudget(boolean active) {
        if (!hasView()) { return; }
        getView().setRefreshEnabled(!active);
    }

    @Override
    public void onSetBudget(BigDecimal budget) {
        LOGGER.log(Level.INFO, String.format(Locale.ENGLISH, "Budget is %f", budget));
        this.budget = budget;

        // TODO: 05/10/2016 Refactor this into explicit isBudgetMode 
        // If we have no budget, load everything
        if (!haveBudget()) {
            if (isShowingBudgetInfo && hasView()) {
                getView().showBudgetInfo(false);
                isShowingBudgetInfo = false;
            }

            refreshComics(false);
            return;
        } else {
            if (!hasView()) { return; }
            getView().setRefreshEnabled(false);
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

                    if (!isShowingBudgetInfo) {
                        getView().showBudgetInfo(true);
                        isShowingBudgetInfo = true;
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

    private int countPages(List<Comic> comics) {
        int pages = 0;
        for (Comic comic : comics) {
            pages += comic.pageCount();
        }
        return pages;
    }

    private String totalPrice(List<Comic> comics) {
        BigDecimal total = BigDecimal.ZERO;
        for (Comic comic : comics) {
            total = total.add(BigDecimal.valueOf(comic.lowestPrice()));
        }
        return total.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    private boolean haveBudget() {
        return budget.compareTo(BigDecimal.ZERO) >= 0;
    }
}
