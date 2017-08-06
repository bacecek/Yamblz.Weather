package me.grechka.yamblz.yamblzweatherapp.presentation.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by alexander on 06/08/2017.
 */

public abstract class AdaptiveActivity extends BaseActivity {

    public static final int UNSPECIFIED = -1;
    public static final int PHONE = 0;
    public static final int TABLET = 1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({UNSPECIFIED,PHONE,TABLET})
    public @interface AdaptationMode {}

    private int currentAdaptationMode = UNSPECIFIED;

    @AdaptationMode
    protected abstract int obtainAdaptationMode();

    @Override
    protected void onLoaded() {
        super.onLoaded();
        currentAdaptationMode = obtainAdaptationMode();
    }

    @Override
    protected void onInit() {
        super.onInit();

        int currentOrientation = getResources().getConfiguration().orientation;

        switch (currentOrientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                onLandscape();
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                onPortrait();
                break;
        }

        onDeviceInit();

        switch (currentAdaptationMode) {
            case PHONE:
                onPhoneInit();
                break;
            case TABLET:
                onTabletInit();
                break;
            case UNSPECIFIED:
            default:
                break;
        }
    }

    @Override
    protected void onViewsCreated(@Nullable Bundle savedInstanceState) {
        super.onViewsCreated(savedInstanceState);

        onDeviceViewsCreated(savedInstanceState);

        switch (currentAdaptationMode) {
            case PHONE:
                onPhoneViewsCreated(savedInstanceState);
                break;
            case TABLET:
                onTabletViewsCreated(savedInstanceState);
                break;
            case UNSPECIFIED:
            default:
                break;
        }
    }

    protected void onLandscape() {
    }

    protected void onPortrait() {
    }

    protected void onDeviceInit() {
    }

    protected void onTabletInit() {
    }

    protected void onPhoneInit() {
    }

    protected void onDeviceViewsCreated(@Nullable Bundle savedInstanceState) {
    }

    protected void onTabletViewsCreated(@Nullable Bundle savedInstanceState) {
    }

    protected void onPhoneViewsCreated(@Nullable Bundle savedInstanceState) {
    }

    protected boolean isPhone() {
        return currentAdaptationMode == PHONE;
    }

    private boolean isTablet() {
        return currentAdaptationMode == TABLET;
    }
}
