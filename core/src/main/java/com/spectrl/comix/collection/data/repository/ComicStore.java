package com.spectrl.comix.collection.data.repository;

import com.spectrl.comix.collection.data.BudgetPredicate;
import com.spectrl.comix.collection.data.model.Comic;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Kavi @ SPECTRL Ltd. on 22/09/2016.
 */

public class ComicStore implements ComicsRepository {
    private final static Logger LOGGER = Logger.getLogger(ComicStore.class.getName());

    private static final int DEFAULT_LIMIT = 100;
    private static final String COMIC_CACHE_KEY = "key_comics_list";

    private final RetrofitNetworkSource networkSource;
    private final DiskCache<String, List<Comic>> diskCache;

    public ComicStore(RetrofitNetworkSource networkSource, DiskCache<String, List<Comic>> diskCache) {
        this.networkSource = networkSource;
        this.diskCache = diskCache;
    }

    @Override
    public Observable<List<Comic>> fetchComics(int limit) {
        return Observable.merge(
                networkSource.getComics(limit)
                        .doOnNext(comics -> diskCache.put(COMIC_CACHE_KEY, comics).subscribe())
                        .subscribeOn(Schedulers.io()),
                diskCache.get(COMIC_CACHE_KEY).subscribeOn(Schedulers.io()))
                .onErrorReturn(throwable -> {
                    LOGGER.log(Level.SEVERE, throwable.getMessage(), throwable);
                    return null;
                })
                .filter(comics -> comics != null); // e.g. Ignore empty cache or error
    }

    @Override
    public Observable<List<Comic>> comicsInBudget(BigDecimal budget) {
        final BigDecimal[] remainingBudget = {budget};
        return fetchComics(DEFAULT_LIMIT)
                .observeOn(Schedulers.computation())
                .map(comics -> {
                    Collections.sort(comics, Comic.byPrice());
                    return comics;
                })
                .flatMap(new Func1<List<Comic>, Observable<Comic>>() {
                    @Override
                    public Observable<Comic> call(List<Comic> comics) {
                        return Observable.from(comics);
                    }
                })
                .filter(comic -> new BudgetPredicate(remainingBudget[0]).test(comic))
                .doOnNext(comic -> remainingBudget[0] = remainingBudget[0].subtract(BigDecimal.valueOf(comic.lowestPrice())))
                .toList();
    }
}
