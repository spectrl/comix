package com.spectrl.comix.collection.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spectrl.comix.R;
import com.spectrl.comix.collection.data.model.Comic;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kavi @ SPECTRL Ltd. on 04/10/2016.
 */

public class ComicViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.thumbnail) ImageView thumbnail;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.issueNumber) TextView issueNumber;

    public ComicViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindTo(final Comic comic, final ComicSelectionListener listener) {
        // TODO: 04/10/2016 Add placeholder and error 
        Glide.with(itemView.getContext())
                .load(comic.thumbnail().imageUrl())
                .crossFade()
                .into(thumbnail);
        title.setText(comic.title());
        issueNumber.setText(String.valueOf(comic.issueNumber()));

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onComicSelected(comic);
            }
        });
    }

    public interface ComicSelectionListener {
        void onComicSelected(Comic comic);
    }
}
