package com.spectrl.comix.collection;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.spectrl.comix.R;
import com.spectrl.comix.collection.data.model.Comic;
import com.spectrl.comix.collection.view.CollectionContract;

import java.util.List;

/**
 * Created by Kavi @ SPECTRL Ltd. on 24/09/2016.
 */

public class CollectionView extends FrameLayout implements CollectionContract.CollectionView {

    public CollectionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View.inflate(getContext(), R.layout.collection_view, this);
    }

    @Override
    public void displayComics(List<Comic> comics) {

    }

    @Override
    public void setProgressIndicator(boolean active) {

    }

    @Override
    public void attach(CollectionContract.CollectionInteractionListener interactionListener) {

    }

    @Override
    public void detach(CollectionContract.CollectionInteractionListener interactionListener) {

    }
}
