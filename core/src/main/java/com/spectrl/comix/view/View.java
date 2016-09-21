package com.spectrl.comix.view;

/**
 * Created by Kavi @ SPECTRL Ltd. on 07/07/16.
 */
public interface View<IL extends View.InteractionListener> {

    void attach(IL interactionListener);

    void detach(IL interactionListener);

    interface InteractionListener {}
}
