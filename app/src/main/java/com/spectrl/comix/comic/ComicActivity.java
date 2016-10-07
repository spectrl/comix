package com.spectrl.comix.comic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.spectrl.comix.BaseActivity;
import com.spectrl.comix.R;
import com.spectrl.comix.comic.di.ComicComponent;
import com.spectrl.comix.comic.di.ComicModule;
import com.spectrl.comix.comic.presenter.ComicPresenter;
import com.spectrl.comix.comic.view.ComicView;
import com.spectrl.comix.di.ActivityScope;
import com.spectrl.comix.di.activity.ActivityModule;
import com.spectrl.comix.di.application.Injector;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Kavi @ SPECTRL Ltd. on 06/10/2016.
 */
@ActivityScope
public class ComicActivity extends BaseActivity<ComicComponent> {

    private static final String EXTRA_COMIC_ID = "comic_id";

    @BindView(R.id.comic_view) ComicView comicView;

    private int comicId;

    public static Intent createIntent(Context context, int id) {
        Intent intent = new Intent(context, ComicActivity.class);
        intent.putExtra(EXTRA_COMIC_ID, id);
        return intent;
    }

    @Inject ComicPresenter comicPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        comicId = getIntent().getIntExtra(EXTRA_COMIC_ID, -1);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        comicPresenter.takeView(comicView);
        comicPresenter.enter();
    }

    @Override
    protected void onPause() {
        comicPresenter.exit();
        comicPresenter.dropView(comicView);
        super.onPause();
    }

    @Override
    protected ComicComponent initComponent() {
        return Injector.obtain(getApplicationContext())
                .plus(new ActivityModule(this))
                .plus(new ComicModule(comicId));
    }

    @Override
    protected void injectDependencies() {
        getComponent().inject(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_comic;
    }
}
