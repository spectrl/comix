package com.spectrl.comix.collection.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spectrl.comix.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kavi @ SPECTRL Ltd. on 05/10/2016.
 */

public class ComicBookView extends FrameLayout {
    private static final float ASPECT_RATIO = 1.5f;

    @BindView(R.id.thumbnail) ImageView thumbnail;
    @BindView(R.id.title) TextView title;

    public ComicBookView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int desiredHeight = (int) (width * ASPECT_RATIO);

        int desiredHeightMeasureSpec = MeasureSpec.makeMeasureSpec(desiredHeight, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, desiredHeightMeasureSpec);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View.inflate(getContext(), R.layout.merge_comic_view, this);
        ButterKnife.bind(this);
    }

    public void setCover(String imageUrl) {
        // TODO: 04/10/2016 Add placeholder and error
        Glide.with(getContext())
                .load(imageUrl)
                .crossFade()
                .into(thumbnail);
    }

    public void setTitle(String titleText) {
        title.setText(titleText);
    }
}
