package me.grechka.yamblz.yamblzweatherapp.presentation.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by alexander on 06/08/2017.
 */

public abstract class AdaptiveFragment extends BaseFragment {

    private int currentOrientation;

    @Override
    protected void onViewsCreated(@Nullable Bundle savedInstanceState) {
        super.onViewsCreated(savedInstanceState);
        currentOrientation = getResources().getConfiguration().orientation;

        switch (currentOrientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                onLandscape();
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                onPortrait();
                break;
        }
    }

    protected void onPortrait(){
    }

    protected void onLandscape(){
    }
}
