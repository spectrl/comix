package com.spectrl.comix.collection.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.spectrl.comix.R;
import com.spectrl.comix.collection.data.model.Comic;
import com.spectrl.comix.collection.data.model.Comics;

import java.util.Collections;

import static com.spectrl.comix.collection.data.model.Comics.*;
import static com.spectrl.comix.collection.view.CollectionContract.CollectionInteractionListener;

/**
 * Created by Kavi @ SPECTRL Ltd. on 04/10/2016.
 */

public class ComicCollectionAdapter extends RecyclerView.Adapter<ComicViewHolder> {

    private Comics comics = builder()
            .comicList(Collections.emptyList())
            .source(Source.MEMORY)
            .build();

    private CollectionInteractionListener interactionListener;

    // TODO: 06/10/2016 Use DiffUtil
    public void update(Comics comics){
        this.comics = comics;
        notifyDataSetChanged();
    }

    @Override
    public ComicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ComicViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comic, parent, false));
    }

    @Override
    public void onBindViewHolder(ComicViewHolder holder, int position) {
        holder.bindTo(comics.getComicAt(position), selectionListener);
    }

    @Override
    public int getItemCount() {
        return comics.size();
    }

    @Override
    public long getItemId(int position) {
        return comics.getComicAt(position).id();
    }

    public void setInteractionListener(CollectionInteractionListener interactionListener) {
        this.interactionListener = interactionListener;
    }

    private final ComicViewHolder.ComicSelectionListener selectionListener = new ComicViewHolder.ComicSelectionListener() {
        @Override
        public void onComicSelected(Comic comic) {
            interactionListener.onComicChosen(comic);
        }
    };
}
