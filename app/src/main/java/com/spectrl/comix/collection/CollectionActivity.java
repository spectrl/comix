package com.spectrl.comix.collection;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

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
    @BindView(R.id.budget) EditText budget;
    @BindView(R.id.collection_view) CollectionView collectionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);

        budget.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(null, 2)});
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_collection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_budget) {
            final boolean isVisible = budget.getVisibility() == View.VISIBLE;
            if (isVisible) {
                item.setIcon(R.drawable.ic_local_atm_white_24dp);
                budget.setVisibility(View.GONE);
                budget.setText("");
                View view = getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm =
                            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            } else {
                item.setIcon(R.drawable.ic_close_white_24dp);
                budget.setVisibility(View.VISIBLE);
                budget.requestFocus();
                InputMethodManager imm =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(budget, InputMethodManager.SHOW_IMPLICIT);
            }
            return true;
        }
        if (item.getItemId() == R.id.action_page_count) {
            collectionPresenter.onPageCount();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
