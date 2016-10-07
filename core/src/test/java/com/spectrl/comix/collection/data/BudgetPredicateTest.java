package com.spectrl.comix.collection.data;

import com.spectrl.comix.collection.data.model.Comic;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by Kavi @ SPECTRL Ltd. on 07/10/2016.
 */
public class BudgetPredicateTest {

    private static final float PRICE = 3.00f;
    private static final BigDecimal HIGH_BUDGET = new BigDecimal("5.00");
    private static final BigDecimal LOW_BUDGET = new BigDecimal("2.00");
    private static final BigDecimal EQUAL_BUDGET = new BigDecimal(String.valueOf(PRICE));

    private static final Comic COMIC = Comic.create(5, "TITLE1", "DESCRIPTION", 100, Comic.Image.create("thumb", "ext"),
            Collections.singletonList(Comic.Price.create("digital", PRICE)),
            Collections.singletonList(Comic.Image.create("PATH", "png")),
            Comic.Creators.create(Collections.singletonList(
                    Comic.Creators.CreatorSummary.create("uri", "Kavi", "creator"))));

    @Test
    public void returnTrueIfPriceUnderBudget() {
        BudgetPredicate budgetPredicate = new BudgetPredicate(HIGH_BUDGET);
        assertThat(budgetPredicate.test(COMIC)).isTrue();
    }

    @Test
    public void returnTrueIfPriceEqualsBudget() {
        BudgetPredicate budgetPredicate = new BudgetPredicate(EQUAL_BUDGET);
        assertThat(budgetPredicate.test(COMIC)).isTrue();
    }

    @Test
    public void returnFalseIfPriceOverBudget() {
        BudgetPredicate budgetPredicate = new BudgetPredicate(LOW_BUDGET);
        assertThat(budgetPredicate.test(COMIC)).isFalse();
    }
}