package com.spectrl.comix.comic.view;

import com.spectrl.comix.view.View;

import java.util.List;

/**
 * Created by Kavi @ SPECTRL Ltd. on 07/07/2016.
 */

public interface ComicContract {

    interface ComicView extends View<ComicInteractionListener> {

        void setCover(String coverUrl);

        void setTitle(String title);

        void setPageCount(String pageCount);

        void setPrice(String price);

        void setDescription(String description);

        void setCreators(List<String> creators);
    }

    interface ComicInteractionListener extends View.InteractionListener {

        void onBookmark();
    }
}
