package me.grechka.yamblz.yamblzweatherapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import me.grechka.yamblz.yamblzweatherapp.presentation.main.MainActivity;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static me.grechka.yamblz.yamblzweatherapp.utils.WaitActions.waitFor;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by alexander on 28/07/2017.
 */

@RunWith(AndroidJUnit4.class)
public class CitySearchFragmentInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void citySearchFragment_findCity_changeCityAndUpdateWeather() {
        onView(withContentDescription(R.string.navigation_drawer_open))
                .perform(click());

        onView(withId(R.id.fragment_weather_header_cities_search))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.fragment_city_search_edittext))
                .check(matches(isDisplayed()))
                .perform(typeText("Orel"));

        onView(isRoot())
                .perform(waitFor(TimeUnit.SECONDS.toMillis(5)));

        closeSoftKeyboard();

        onView(isRoot())
                .perform(waitFor(TimeUnit.SECONDS.toMillis(2)));

        onView(withId(R.id.fragment_city_search_recycler_view))
        .perform(actionOnItemAtPosition(4, click()));

        onView(isRoot())
                .perform(waitFor(TimeUnit.SECONDS.toMillis(2)));

//        onView(allOf(withId(R.id.city),
//                withText("Orel"))).check(matches(isDisplayed()));
    }

}
