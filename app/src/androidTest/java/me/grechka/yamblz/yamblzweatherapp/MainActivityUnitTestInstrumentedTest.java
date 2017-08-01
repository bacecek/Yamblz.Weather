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

    @Test
    public void mainActivity_navigateToSettings_settingsFragmentOpenedSuccessfully() {
        onView(withContentDescription(R.string.navigation_drawer_open)).perform(click());

        onView(withText(R.string.main_activity_navigation_settings))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.update_frequency_group))
                .check(matches(isDisplayed()));

        onView(withId(R.id.frequency_manual))
                .check(matches(isDisplayed()));

        onView(withId(R.id.frequency_15))
                .check(matches(isDisplayed()));

        onView(withId(R.id.frequency_30))
                .check(matches(isDisplayed()));

        onView(withId(R.id.frequency_60))
                .check(matches(isDisplayed()));

        onView(withId(R.id.frequency_180))
                .check(matches(isDisplayed()));
    }
}
