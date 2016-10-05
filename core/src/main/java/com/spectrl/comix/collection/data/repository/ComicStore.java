package com.spectrl.comix.collection.data.repository;

import com.spectrl.comix.api.MarvelService;
import com.spectrl.comix.collection.data.model.Comic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;

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
        return fetchComics(DEFAULT_LIMIT)
                .map(comics -> {
                    Collections.sort(comics, Comic.byPrice());
                    List<Comic> withinBudget = new ArrayList<>();
                    BigDecimal budgetRemaining = budget;
                    for (Comic comic : comics) {
                        BigDecimal lowestPrice = new BigDecimal(String.valueOf(comic.lowestPrice()));
                        budgetRemaining = budgetRemaining.subtract(lowestPrice);
                        if (budgetRemaining.signum() == -1) {
                            break;
                        }
                        withinBudget.add(comic);
                    }
                    return withinBudget;
                });
    }
}
