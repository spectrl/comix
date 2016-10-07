package com.spectrl.comix.comic.view;

import com.spectrl.comix.view.View;

/**
 * Created by Kavi @ SPECTRL Ltd. on 07/07/2016.
 */

public interface ComicContract {

    interface ComicView extends View<ComicInteractionListener> {

        void setCover(String coverUrl);

        void setTitle(String title);

        void setIssueNumber(String issueNumber);

        void setPageCount(String pageCount);

        void setPrice(String price);

        void setDescription(String description);
    }

    interface ComicInteractionListener extends View.InteractionListener {
        // No interaction
    }
}
