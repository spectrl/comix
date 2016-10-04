package com.spectrl.comix.collection;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.spectrl.comix.BaseActivity;
import com.spectrl.comix.R;
import com.spectrl.comix.collection.presenter.CollectionPresenter;
import com.spectrl.comix.collection.view.CollectionView;
import com.spectrl.comix.di.activity.ActivityComponent;
import com.spectrl.comix.di.activity.ActivityModule;
import com.spectrl.comix.di.application.Injector;

import javax.inject.Inject;

import butterknife.BindView;

public class CollectionActivity extends BaseActivity<ActivityComponent> {

    @Inject CollectionPresenter collectionPresenter;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.collection_view) CollectionView collectionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        collectionPresenter.takeView(collectionView);
        collectionPresenter.enter();
    }

    @Override
    protected void onPause() {
        collectionPresenter.exit();
        collectionPresenter.dropView(collectionView);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected ActivityComponent initComponent() {
        return Injector.obtain(getApplicationContext())
                .plus(new ActivityModule(this));
    }

    @Override
    protected void injectDependencies() {
        getComponent().inject(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_collection;
    }
}
