package com.spectrl.comix.collection.data.repository;

import com.spectrl.comix.api.MarvelService;
import com.spectrl.comix.collection.data.BudgetPredicate;
import com.spectrl.comix.collection.data.model.Comic;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Kavi @ SPECTRL Ltd. on 22/09/2016.
 */

public class ComicStore implements ComicsRepository {
    private static final int DEFAULT_LIMIT = 100;

    // This could be split into separate interfaces per feature for larger applications
    private final MarvelService marvelService;

    public ComicStore(MarvelService marvelService) {
        this.marvelService = marvelService;
    }

    @Override
    public Observable<List<Comic>> fetchComics(int limit) {
        return marvelService.getComics(limit)
                .map(marvelApiResponse -> marvelApiResponse.data().results());
    }

    @Override
    public Observable<List<Comic>> comicsInBudget(BigDecimal budget) {
        final BigDecimal[] remainingBudget = {budget};
        return fetchComics(DEFAULT_LIMIT)
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
