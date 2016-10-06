package com.spectrl.comix.comic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spectrl.comix.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Kavi @ SPECTRL Ltd. on 06/10/2016.
 */

public class ComicView extends LinearLayout {

    @BindView(R.id.cover) ImageView cover;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.issue_number) TextView issueNumber;
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
}
