package com.spectrl.comix.collection.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.spectrl.comix.R;
import com.spectrl.comix.collection.data.model.Comic;

import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.spectrl.comix.collection.view.CollectionContract.CollectionInteractionListener;

/**
 * Created by Kavi @ SPECTRL Ltd. on 24/09/2016.
 */

public class CollectionView extends FrameLayout implements CollectionContract.CollectionView {

    @BindView(R.id.swipe_layout) SwipeRefreshLayout swipeLayout;
    @BindView(R.id.comics_recyclerview) RecyclerView comicsRecyclerView;

    @BindInt(R.integer.num_columns) int columns;

    private Unbinder unbinder;

    private CollectionInteractionListener interactionListener;

    private final ComicCollectionAdapter collectionAdapter;

    public CollectionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        collectionAdapter = new ComicCollectionAdapter();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View.inflate(getContext(), R.layout.collection_view, this);
        unbinder = ButterKnife.bind(this);

        comicsRecyclerView.setAdapter(collectionAdapter);
        comicsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), columns));
    }

    @Override
    protected void onDetachedFromWindow() {
        unbinder.unbind();
        super.onDetachedFromWindow();
    }

    @Override
    public void displayComics(List<Comic> comics) {
        collectionAdapter.update(comics);
    }

    @Override
    public void setProgressIndicator(boolean active) {
        swipeLayout.setRefreshing(active);
    }

    @Override
    public void attach(final CollectionInteractionListener interactionListener) {
        this.interactionListener = interactionListener;
        swipeLayout.setOnRefreshListener(interactionListener::refreshComics);
        collectionAdapter.setInteractionListener(interactionListener);
    }

    @Override
    public void detach(CollectionInteractionListener interactionListener) {
        this.interactionListener = null;
        collectionAdapter.setInteractionListener(null);
    }
}
