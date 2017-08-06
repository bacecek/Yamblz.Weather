package me.grechka.yamblz.yamblzweatherapp.presentation.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.arellomobile.mvp.MvpAppCompatActivity;

import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by alexander on 31/07/2017.
 */

public abstract class BaseActivity extends MvpAppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onLoad();

        setContentView(obtainLayoutView());
        ButterKnife.bind(this);

        onLoaded();

        if (savedInstanceState == null) onInit();
        onViewsCreated(savedInstanceState);
    }

    @LayoutRes
    protected abstract int obtainLayoutView();

    /***
     * Method called when activity created, but views not initialized.
     * No butterknife view are not bind. Content not obtained.
     * Use for views independent operations.
     */
    protected void onLoad() {
    }

    /***
     * First call after views init.
     */
    protected void onLoaded() {
    }

    /***
     * Method called when activity created and views initialized.
     * However, called once per activity launch, when state is empty.
     * Use to obtain the first activity launch.
     */
    protected void onInit() {
    }

    /***
     * Method called when activity create and views initailized.
     * Use to setup views at the screen.
     *
     * @param savedInstanceState The activity state
     */
    protected void onViewsCreated(@Nullable Bundle savedInstanceState) {
    }

    /***
     * Set the toolbar as action bar.
     * #Calls setSupportActionBar
     *
     * @param toolbarId The toolbar id from resources file.
     */
    protected void setSupportActionBar(@IdRes int toolbarId) {
        this.toolbar = (Toolbar) findViewById(toolbarId);
        setSupportActionBar(toolbar);
    }

    /***
     * Get the toolbar.
     *
     * @return null if toolbar not presenter and toolbar if presenter
     */
    @Nullable
    protected Toolbar getToolbar() {
        return toolbar;
    }
}
