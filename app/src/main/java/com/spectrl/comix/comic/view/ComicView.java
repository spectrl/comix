package com.spectrl.comix.comic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spectrl.comix.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Kavi @ SPECTRL Ltd. on 06/10/2016.
 */

public class ComicView extends LinearLayout implements ComicContract.ComicView {

    @BindView(R.id.cover) ImageView cover;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.page_count) TextView pageCount;
    @BindView(R.id.price) TextView price;
    @BindView(R.id.description) TextView description;

    private Unbinder unbinder;

    public ComicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View.inflate(getContext(), R.layout.comic_view, this);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        unbinder.unbind();
        super.onDetachedFromWindow();
    }

    @Override
    public void setCover(String coverUrl) {
        Glide.with(getContext())
                .load(coverUrl)
                .crossFade()
                .into(cover);
    }

    @Override
    public void setTitle(String title) {
        this.title.setText(title);
    }

    @Override
    public void setPageCount(String pageCount) {
        this.pageCount.setText(pageCount);
    }

    @Override
    public void setPrice(String price) {
        this.price.setText(price);
    }

    @Override
    public void setDescription(String description) {
        this.description.setText(description);
    }

    @Override
    public void attach(ComicContract.ComicInteractionListener interactionListener) {
        // Do nothing as there is no interaction
    }

    @Override
    public void detach(ComicContract.ComicInteractionListener interactionListener) {
        // Do nothing as there is no interaction
    }
}
