package com.spectrl.comix.presenter;

import com.spectrl.comix.view.View;

/**
 * Created by Kavi @ SPECTRL Ltd. on 07/07/16.
 */
public abstract class BasePresenter<V extends View> implements Presenter<V> {
    private V view;

    public final void takeView(V view) {
        if (view == null) throw new NullPointerException("new view must not be null");

        if (this.view != view) {
            if (this.view != null) dropView(this.view);

            this.view = view;
        }
    }

    public void dropView(V view) {
        if (view == null) throw new NullPointerException("dropped view must not be null");
        if (view == this.view) {
            this.view = null;
        }
    }

    protected final V getView() {
        return view;
    }

    protected final boolean hasView() {
        return view != null;
    }
}
