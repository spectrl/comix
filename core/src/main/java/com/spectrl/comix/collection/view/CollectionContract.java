package com.spectrl.comix.collection.view;

import com.spectrl.comix.collection.data.model.Comic;
import com.spectrl.comix.collection.data.model.Comics;
import com.spectrl.comix.collection.view.model.Budget;
import com.spectrl.comix.view.View;

/**
 * Created by Kavi @ SPECTRL Ltd. on 07/07/2016.
 */

public interface CollectionContract {

    interface CollectionView extends View<CollectionInteractionListener> {

        void displayComics(Comics comics);

        void setProgressIndicator(boolean active);

        void setRefreshEnabled(boolean enabled);

        void showPageCount(int count);

        void showBudgetInfo(boolean active);

        void setBudgetComicCount(int count);

        void setBudgetComicPrice(String price);

        boolean isShowingBudgetInfo();

        // TODO: 22/09/2016 Error state
    }

    interface CollectionInteractionListener extends View.InteractionListener {

        void refreshComics(boolean forceUpdate);

        void onComicChosen(Comic comic);

        void onPageCount();

        void onBudget(Budget budget);
    }
}
