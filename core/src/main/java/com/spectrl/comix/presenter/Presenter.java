package com.spectrl.comix.presenter;

import com.spectrl.comix.view.View;

/**
 * Created by Kavi @ SPECTRL Ltd. on 07/07/16.
 */
public interface Presenter<V extends View> {
    void takeView(V view);
    void dropView(V view);
    void enter();
    void exit();
}
