package com.spectrl.comix.collection;

import android.os.Bundle;

import com.spectrl.comix.BaseActivity;
import com.spectrl.comix.R;
import com.spectrl.comix.collection.presenter.CollectionPresenter;
import com.spectrl.comix.di.activity.ActivityComponent;
import com.spectrl.comix.di.activity.ActivityModule;
import com.spectrl.comix.di.application.Injector;

import javax.inject.Inject;

import butterknife.BindView;

public class CollectionActivity extends BaseActivity<ActivityComponent> {

    @Inject CollectionPresenter collectionPresenter;

    @BindView(R.id.collection_view) CollectionView collectionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        collectionPresenter.takeView(collectionView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        collectionPresenter.enter();
    }

    @Override
    protected void onPause() {
        collectionPresenter.exit();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        collectionPresenter.dropView(collectionView);
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
