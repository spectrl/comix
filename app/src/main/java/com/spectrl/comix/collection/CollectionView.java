package com.spectrl.comix.collection;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.spectrl.comix.R;
import com.spectrl.comix.collection.data.model.Comic;
import com.spectrl.comix.collection.view.CollectionContract;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.spectrl.comix.collection.view.CollectionContract.*;

/**
 * Created by Kavi @ SPECTRL Ltd. on 24/09/2016.
 */

public class CollectionView extends FrameLayout implements CollectionContract.CollectionView {

    @BindView(R.id.swipe_layout) SwipeRefreshLayout swipeLayout;

    private Unbinder unbinder;

    private CollectionInteractionListener interactionListener;

    public CollectionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View.inflate(getContext(), R.layout.collection_view, this);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        unbinder.unbind();
        super.onDetachedFromWindow();
    }

    @Override
    public void displayComics(List<Comic> comics) {
        // TODO: 24/09/2016 Add comics to recyclerview
    }

    @Override
    public void setProgressIndicator(boolean active) {
        swipeLayout.setRefreshing(active);
    }

    @Override
    public void attach(final CollectionInteractionListener interactionListener) {
        this.interactionListener = interactionListener;
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                interactionListener.refreshComics();
                // TODO: 24/09/2016 setProgressIndicator to false on refresh complete
            }
        });
    }

    @Override
    public void detach(CollectionInteractionListener interactionListener) {
        this.interactionListener = null;
    }
}
