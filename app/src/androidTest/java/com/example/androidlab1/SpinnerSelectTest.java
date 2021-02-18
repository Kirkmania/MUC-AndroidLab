package com.example.androidlab1;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyAbove;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SpinnerSelectTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("cs.bham.ac.uk.week3lab", appContext.getPackageName());
    }
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void spinnerExists(){
        onView(withId(R.id.spinner)).check(matches(isDisplayed()));
    }

    @Test
    public void spinnerAboveButton(){
        onView(withId(R.id.spinner)).check(isCompletelyAbove(withId(R.id.button)));
    }

    @Test
    public void spinnerSelectAndUpdate() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        String[] spinOpts = appContext.getResources().getStringArray(R.array.spinneroptions);

        String firstItem = spinOpts[0];

        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(firstItem))).perform(click());    //in case spinner options are too long, onData instead of onView
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText(firstItem)));
    }
}