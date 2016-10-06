package com.spectrl.comix.collection.view;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.spectrl.comix.R;
import com.spectrl.comix.collection.data.model.Comics;

import butterknife.BindDimen;
import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.spectrl.comix.collection.view.CollectionContract.CollectionInteractionListener;

/**
 * Created by Kavi @ SPECTRL Ltd. on 24/09/2016.
 */

public class CollectionView extends FrameLayout implements CollectionContract.CollectionView {

    @BindView(R.id.collection_view_container) ViewGroup container;
    @BindView(R.id.budget_info) View budgetInfo;
    @BindView(R.id.budget_comic_count) TextView budgetComicCount;
    @BindView(R.id.budget_comic_price) TextView budgetComicPrice;
    @BindView(R.id.swipe_layout) SwipeRefreshLayout swipeLayout;
    @BindView(R.id.comics_recyclerview) RecyclerView comicsRecyclerView;

    @BindInt(R.integer.num_columns) int columns;
    @BindDimen(R.dimen.grid_spacing) int gridPadding;

    private Unbinder unbinder;
    private boolean isShowingBudgetInfo;

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
        comicsRecyclerView.addItemDecoration(new ItemOffsetDecoration(getContext(), R.dimen.grid_spacing));
    }

    @Override
    protected void onDetachedFromWindow() {
        unbinder.unbind();
        super.onDetachedFromWindow();
    }

    @Override
    public void displayComics(Comics comics) {
        collectionAdapter.update(comics);
    }

    @Override
    public void setProgressIndicator(boolean active) {
        swipeLayout.setRefreshing(active);
    }

    @Override
    public void showPageCount(int count) {
        Snackbar.make(swipeLayout,
                String.format(getContext().getString(R.string.page_count_message),
                        count),
                Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showBudgetInfo(boolean active) {
        animateView(active);
        isShowingBudgetInfo = active;
    }

    @Override
    public boolean isShowingBudgetInfo() {
        return isShowingBudgetInfo;
    }

    @Override
    public void setBudgetComicCount(int count) {
        budgetComicCount.setText(getContext().getString(R.string.comics_in_budget, count));
    }

    @Override
    public void setBudgetComicPrice(String price) {
        budgetComicPrice.setText(getContext().getString(R.string.budget_total_price, price));
    }

    @Override
    public void setRefreshEnabled(boolean enabled) {
        swipeLayout.setEnabled(enabled);
    }

    @Override
    public void attach(final CollectionInteractionListener interactionListener) {
        this.interactionListener = interactionListener;
        swipeLayout.setOnRefreshListener(() -> interactionListener.refreshComics(true));
        collectionAdapter.setInteractionListener(interactionListener);
    }

    @Override
    public void detach(CollectionInteractionListener interactionListener) {
        this.interactionListener = null;
        collectionAdapter.setInteractionListener(null);
    }

    private void animateView(boolean expand) {
        int distance = budgetInfo.getMeasuredHeight();
        ViewCompat.animate(container)
                .yBy(distance * ((expand) ? 1 : -1))
                .withEndAction(() -> setRecyclerViewBottomPadding(expand ? distance : gridPadding * 2))
                .start();
    }

    private void setRecyclerViewBottomPadding(int bottomPadding) {
        comicsRecyclerView.setPadding(
                comicsRecyclerView.getPaddingLeft(),
                comicsRecyclerView.getPaddingTop(),
                comicsRecyclerView.getPaddingRight(),
                bottomPadding);
    }
}
