package me.grechka.yamblz.yamblzweatherapp.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by alexander on 05/08/2017.
 */

public final class MetricsUtils {

    private static final int TABLET_SMALLEST_WIDTH = 600;

    private float scaleFactor;
    private DisplayMetrics displayMetrics;

    public MetricsUtils(@NonNull Context context) {
        displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(displayMetrics);
        scaleFactor = displayMetrics.density;
    }

    public int getSmallestWidth() {
        return (int) Math.min(displayMetrics.widthPixels / scaleFactor, displayMetrics.heightPixels / scaleFactor);
    }

    public boolean isTablet() {
        return getSmallestWidth() > TABLET_SMALLEST_WIDTH;
    }
}
