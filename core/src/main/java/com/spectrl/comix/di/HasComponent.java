package com.spectrl.comix.di;

/**
 * Created by kavi on 15/06/15.
 */
public interface HasComponent<C> {
    C getComponent();
    void setComponent(C component);
}
