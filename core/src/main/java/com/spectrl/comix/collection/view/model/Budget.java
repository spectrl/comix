package com.spectrl.comix.collection.view.model;

import com.google.auto.value.AutoValue;

import java.math.BigDecimal;

/**
 * Created by Kavi @ SPECTRL Ltd. on 05/10/2016.
 */

@AutoValue
public abstract class Budget {

    public enum Action {
        OPEN,
        CLOSE,
        UPDATE,
        CLEAR
    }

    public abstract Action action();
    public abstract BigDecimal amount();

    public static Budget create(Action action, BigDecimal amount) {
        return new AutoValue_Budget(action, amount);
    }

    public static Budget create(Action action) {
        return create(action, BigDecimal.valueOf(-1));
    }
}
