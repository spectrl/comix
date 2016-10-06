package com.spectrl.comix.collection.data.repository;

import com.spectrl.comix.collection.data.BudgetPredicate;
import com.spectrl.comix.collection.data.model.Comic;
import com.spectrl.comix.collection.data.model.Comics;
import com.spectrl.comix.util.Connectivity;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.spectrl.comix.collection.data.model.Comics.*;

/**
 * Created by Kavi @ SPECTRL Ltd. on 22/09/2016.
 */

public class ComicStore implements ComicsRepository {
    private final static Logger LOGGER = Logger.getLogger(ComicStore.class.getName());

    private static final int DEFAULT_LIMIT = 100;
    private static final String COMIC_CACHE_KEY = "key_comics_list";
    private static final int CACHE_MAX_AGE = 60;
    private static final long CACHE_MAX_STALE = TimeUnit.SECONDS.convert(24, TimeUnit.HOURS);

    private final RetrofitNetworkSource networkSource;
    private final DiskCache<String, Comics> diskCache;
    private final Connectivity connectivity;

    public ComicStore(RetrofitNetworkSource networkSource, DiskCache<String, Comics> diskCache, Connectivity connectivity) {
        this.networkSource = networkSource;
        this.diskCache = diskCache;
        this.connectivity = connectivity;
    }

    @Override
    public Observable<Comics> fetchComics(int limit) {
        return Observable.merge(
                networkSource.getComics(limit)
                        .doOnNext(comics -> diskCache.put(COMIC_CACHE_KEY, comics).subscribe())
                        .subscribeOn(Schedulers.io()),
                diskCache.get(COMIC_CACHE_KEY).subscribeOn(Schedulers.io()))
                .onErrorReturn(throwable -> {
                    LOGGER.log(Level.SEVERE, throwable.getMessage(), throwable);
                    return null;
                })
                .filter(comics -> comics != null) // e.g. Ignore empty cache or error
                .first(comics -> connectivity.connected()
                        ? comics.isFresh(CACHE_MAX_AGE)
                        : comics.isFresh(CACHE_MAX_STALE))
                .share();
    }

    @Override
    public Observable<Comics> comicsInBudget(BigDecimal budget) {
        final BigDecimal[] remainingBudget = {budget};
        return fetchComics(DEFAULT_LIMIT)
                .observeOn(Schedulers.computation())
                .map(Comics::sortedByPrice)
                .flatMap(new Func1<Comics, Observable<Comic>>() {
                    @Override
                    public Observable<Comic> call(Comics comics) {
                        return Observable.from(comics.comicList());
                    }
                })
                .filter(comic -> new BudgetPredicate(remainingBudget[0]).test(comic))
                .doOnNext(comic -> remainingBudget[0] =
                        remainingBudget[0].subtract(new BigDecimal(String.valueOf(comic.lowestPrice()))))
                .toList()
                .map(comics -> builder()
                        .comicList(comics)
                        .source(Source.MEMORY)
                        .build());
    }
}
