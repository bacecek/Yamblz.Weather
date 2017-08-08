package me.grechka.yamblz.yamblzweatherapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import me.grechka.yamblz.yamblzweatherapp.presentation.main.MainActivity;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.openDrawer;
import static android.support.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityUnitTestInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivity_navigateToAbout_aboutFragmentOpenedSuccessfully() {
        onView(withContentDescription(R.string.navigation_drawer_open)).perform(click());

        onView(withText(R.string.main_activity_navigation_about))
                .check(matches(isDisplayed()))
                .perform(click());
    }
}
