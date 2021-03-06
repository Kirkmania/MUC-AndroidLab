package com.example.androidlab1;


import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyAbove;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LayoutTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    // THIS IS MY ACTUAL TEST.
    @Test
    public void isLinearLayoutAndIsVertical(){
        onView(withId(R.id.layout)).check(matches(instanceOf(LinearLayout.class)));
        onView(withId(R.id.layout)).check(matches(hasVerticalOrientation()));
    }
    Matcher<View> hasVerticalOrientation(){
        return new BoundedMatcher<View, LinearLayout>(LinearLayout.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("LinearLayout has vertical orientation?");
            }

            @Override
            public boolean matchesSafely(LinearLayout lo) {
                return lo.getOrientation() == LinearLayout.VERTICAL;
            }
        };
    }

    @Test
    public void stackingOrder() {
        onView(withId(R.id.textView)).check(matches(isDisplayed()));
        onView(withId(R.id.spinner)).check(matches(isDisplayed()));
        onView(withId(R.id.button)).check(matches(isDisplayed()));
        onView(withId(R.id.spinner)).check(isCompletelyAbove(withId(R.id.button)));
        onView(withId(R.id.textView)).check(isCompletelyAbove(withId(R.id.spinner)));
    }

    @Test
    public void textViewConstraints() {
        onView(withId(R.id.textView)).check(matches(hasCorrectTextViewConstraints()));
    }

    Matcher<View> hasCorrectTextViewConstraints() {
        return new BoundedMatcher<View, TextView>(TextView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("Check if this TextView has the correct constraints set.");
            }

            @Override
            public boolean matchesSafely(TextView tv) {
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) tv.getLayoutParams();
                DisplayMetrics displayMetrics = tv.getContext().getResources().getDisplayMetrics();
                int dpSize = (int) ((params.topMargin/displayMetrics.density)+0.5);
                return dpSize == 32;
            }
        };
    }

    // THIS STUFF WAS WRITTEN BY ESPRESSO RECORDER!
    @Test
    public void layoutTest() {
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.button), withText("Update"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
