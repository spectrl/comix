package com.spectrl.comix.collection.data;

import com.spectrl.comix.collection.data.model.Comic;
import com.spectrl.comix.util.Predicate;

import java.math.BigDecimal;

/**
 * Created by Kavi @ SPECTRL Ltd. on 05/10/2016.
 */

public class BudgetPredicate implements Predicate<Comic> {

    private final BigDecimal budget;

    public BudgetPredicate(BigDecimal budget) {
        this.budget = budget;
    }

    @Override
    public boolean test(Comic comic) {
        return BigDecimal.valueOf(comic.lowestPrice()).compareTo(budget) <= 0;
    }
}
