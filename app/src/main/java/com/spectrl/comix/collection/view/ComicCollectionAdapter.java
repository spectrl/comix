package com.spectrl.comix.collection.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.spectrl.comix.R;
import com.spectrl.comix.collection.data.model.Comic;

import java.util.Collections;
import java.util.List;

import static com.spectrl.comix.collection.view.CollectionContract.CollectionInteractionListener;

/**
 * Created by Kavi @ SPECTRL Ltd. on 04/10/2016.
 */

public class ComicCollectionAdapter extends RecyclerView.Adapter<ComicViewHolder> {

    private List<Comic> comics = Collections.emptyList();

    private CollectionInteractionListener interactionListener;

    public void update(List<Comic> comics){
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
        holder.bindTo(comics.get(position), selectionListener);
    }

    @Override
    public int getItemCount() {
        return comics.size();
    }

    @Override
    public long getItemId(int position) {
        return comics.get(position).id();
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
