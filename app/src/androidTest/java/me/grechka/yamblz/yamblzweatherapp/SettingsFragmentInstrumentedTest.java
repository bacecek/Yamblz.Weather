package me.grechka.yamblz.yamblzweatherapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import me.grechka.yamblz.yamblzweatherapp.presentation.main.MainActivity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.is;

/**
 * Created by alexander on 26/07/2017.
 */

@RunWith(AndroidJUnit4.class)
public class SettingsFragmentInstrumentedTest {

    private SharedPreferences preferences;

    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Before
    public void onInit() {
        Context context = getInstrumentation().getTargetContext();
        preferences = context.getSharedPreferences("weather_preferences", Context.MODE_PRIVATE);
    }

    @Test
    public void settingsFragment_checkRadioGroupState_lastSavedEnabled() {
        String frequency = preferences.getString("frequency", "60");

        navigateToSettings();

        onView(withTagValue(is(frequency)))
                .check(matches(isChecked()));
    }

    @Test
    public void settingsFragment_chooseNewInRadioGroup_clickedItemSavedAndEnabled() {
        navigateToSettings();

        onView(withId(R.id.frequency_180))
                .perform(click());

        pressBack();
        navigateToSettings();

        onView(withId(R.id.frequency_180))
                .check(matches(isChecked()));
    }

    private void navigateToSettings() {
        onView(withContentDescription(R.string.navigation_drawer_open)).perform(click());

        onView(withText(R.string.main_activity_navigation_settings))
                .check(matches(isDisplayed()))
                .perform(click());
    }
}
