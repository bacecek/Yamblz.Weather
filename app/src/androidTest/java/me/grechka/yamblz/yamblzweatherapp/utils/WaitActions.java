package me.grechka.yamblz.yamblzweatherapp.utils;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;

import org.hamcrest.Matcher;

import java.util.Locale;

import static android.support.test.espresso.matcher.ViewMatchers.isRoot;

/**
 * Created by alexander on 28/07/2017.
 */

public class WaitActions {

    private WaitActions() {
    }

    public static ViewAction waitFor(final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return String.format(Locale.US, "wait for a root view during %1$d millis", millis);
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                uiController.loopMainThreadForAtLeast(millis);
            }
        };
    }
}
