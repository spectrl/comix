package com.spectrl.comix.collection.presenter;

import com.spectrl.comix.collection.data.model.Comic;
import com.spectrl.comix.presenter.BasePresenter;

import static com.spectrl.comix.collection.view.CollectionContract.CollectionInteractionListener;
import static com.spectrl.comix.collection.view.CollectionContract.CollectionView;

/**
 * Created by Kavi @ SPECTRL Ltd. on 22/09/2016.
 */

public class CollectionPresenter extends BasePresenter<CollectionView> implements CollectionInteractionListener {

    public CollectionPresenter(CollectionView view) {
        super(view);
    }

    @Override
    public void enter() {

    }

    @Override
    public void exit() {

    }

    @Override
    public void onComicChosen(Comic comic) {
        // TODO: 22/09/2016 Open Comic details Activity using Navigator
    }

    private void fetchComics() {
        // TODO: 22/09/2016 Fetch from repo
    }
}
