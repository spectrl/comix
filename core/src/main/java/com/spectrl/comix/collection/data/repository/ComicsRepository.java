package com.spectrl.comix.collection.data.repository;

import com.spectrl.comix.collection.data.model.Comics;

import java.math.BigDecimal;

import rx.Observable;

/**
 * Created by Kavi @ SPECTRL Ltd. on 22/09/2016.
 */

public interface ComicsRepository {

    Observable<Comics> fetchComics(int limit);

    Observable<Comics> comicsInBudget(BigDecimal budget);
}
