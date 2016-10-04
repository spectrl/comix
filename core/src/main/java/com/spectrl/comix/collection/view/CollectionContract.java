package com.spectrl.comix.collection.view;

import com.spectrl.comix.collection.data.model.Comic;
import com.spectrl.comix.view.View;

import java.util.List;

/**
 * Created by Kavi @ SPECTRL Ltd. on 07/07/2016.
 */

public interface CollectionContract {

    interface CollectionView extends View<CollectionInteractionListener> {

        void displayComics(List<Comic> comics);

        void setProgressIndicator(boolean active);

        void showPageCount();

        // TODO: 22/09/2016 Error state
    }

    interface CollectionInteractionListener extends View.InteractionListener {

        void refreshComics();

        void onComicChosen(Comic comic);

        void onSetBudget(float budget);

        void onPageCount();
    }
}
