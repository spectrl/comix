package com.spectrl.comix.collection.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Kavi @ SPECTRL Ltd. on 07/10/2016.
 */

/* See: https://github.com/googlesamples/android-XYZTouristAttractions/blob/master/Application/src/main/java/com/example/android/xyztouristattractions/ui/AttractionsRecyclerView.java */
public class ComixRecyclerView extends RecyclerView {

    private View emptyView;

    private AdapterDataObserver dataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            updateEmptyView();
        }
    };

    public ComixRecyclerView(Context context) {
        super(context);
    }

    public ComixRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ComixRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Designate a view as the empty view. When the backing adapter has no
     * data this view will be made visible and the recycler view hidden.
     *
     */
    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        if (getAdapter() != null) {
            getAdapter().unregisterAdapterDataObserver(dataObserver);
        }
        if (adapter != null) {
            adapter.registerAdapterDataObserver(dataObserver);
        }
        super.setAdapter(adapter);
        updateEmptyView();
    }

    private void updateEmptyView() {
        if (emptyView != null && getAdapter() != null) {
            boolean showEmptyView = getAdapter().getItemCount() == 0;
            emptyView.setVisibility(showEmptyView ? VISIBLE : GONE);
            setVisibility(showEmptyView ? GONE : VISIBLE);
        }
    }
}
