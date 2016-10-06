package com.spectrl.comix.comic;

import android.content.Context;
import android.content.Intent;

import com.spectrl.comix.BaseActivity;
import com.spectrl.comix.R;
import com.spectrl.comix.di.activity.ActivityComponent;
import com.spectrl.comix.di.activity.ActivityModule;
import com.spectrl.comix.di.application.Injector;

/**
 * Created by Kavi @ SPECTRL Ltd. on 06/10/2016.
 */

public class ComicActivity extends BaseActivity<ActivityComponent> {

    public static Intent createIntent(Context context) {
        return new Intent(context, ComicActivity.class);
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
        return R.layout.activity_comic;
    }
}
