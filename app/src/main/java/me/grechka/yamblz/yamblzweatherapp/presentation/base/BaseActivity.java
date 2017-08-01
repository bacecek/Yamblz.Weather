package me.grechka.yamblz.yamblzweatherapp.presentation.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;

import com.arellomobile.mvp.MvpAppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by alexander on 31/07/2017.
 */

public abstract class BaseActivity extends MvpAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(obtainLayoutView());
        ButterKnife.bind(this);

        if (savedInstanceState == null) onInit();
        onViewsCreated(savedInstanceState);
    }

    @LayoutRes
    protected abstract int obtainLayoutView();

    protected void onInit() {
    }

    protected void onViewsCreated(Bundle savedInstanceState) {
    }
}
