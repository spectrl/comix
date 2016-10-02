package com.spectrl.comix;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import com.spectrl.comix.di.HasComponent;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<C> extends AppCompatActivity implements HasComponent<C> {

    private C component;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getContentView());
        unbinder = ButterKnife.bind(this);

        setComponent(initComponent());
        injectDependencies();
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        component = null;
        super.onDestroy();
    }

    @Override
    public void setComponent(C component) {
        this.component = component;
    }

    @Override
    public C getComponent() {
        return component;
    }

    protected abstract C initComponent();
    protected abstract void injectDependencies();
    protected abstract @LayoutRes int getContentView();
}
