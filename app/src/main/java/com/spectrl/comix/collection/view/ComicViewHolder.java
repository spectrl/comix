package com.spectrl.comix.collection.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.spectrl.comix.R;
import com.spectrl.comix.collection.data.model.Comic;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kavi @ SPECTRL Ltd. on 04/10/2016.
 */

class ComicViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.comic_book_view) ComicBookView comicBookView;

    ComicViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void bindTo(final Comic comic, final ComicSelectionListener listener) {
        comicBookView.setCover(comic.thumbnail().imageUrl());
        comicBookView.setTitle(comic.title());
        itemView.setOnClickListener(view -> listener.onComicSelected(comic));
    }

    interface ComicSelectionListener {
        void onComicSelected(Comic comic);
    }
}
