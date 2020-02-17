package com.example.walk_walk_revolution;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestRoutesScreenUI {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testRoutesScreenUI() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.loginButton), withText("Login"),

                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.userHeight),

                        isDisplayed()));
        //appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.userHeight),

                        isDisplayed()));
        //appCompatEditText2.perform(replaceText("5"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.heightSubmitButton), withText("Submit Height"),

                        isDisplayed()));
        //appCompatButton2.perform(click());


        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.routes_but_home), withText("Routes"),

                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.addNewWalk), withText("+"),

                        isDisplayed()));
        //appCompatButton5.perform(click());


        ViewInteraction textView = onView(
                allOf(withId(R.id.routes_title), withText("Routes"),

                        isDisplayed()));
        textView.check(matches(withText("Routes")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.nameHeader), withText("Route Name"),

                        isDisplayed()));
        textView2.check(matches(withText("Route Name")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.startPointHeader), withText("Starting Point"),

                        isDisplayed()));
        textView3.check(matches(withText("Starting Point")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.stepCountHeader), withText("Step Count"),

                        isDisplayed()));
        textView4.check(matches(withText("Step Count")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.distanceHeader), withText("Distance"),

                        isDisplayed()));
        textView5.check(matches(withText("Distance")));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.timeCount), withText("Time"),

                        isDisplayed()));
        textView6.check(matches(withText("Time")));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.buttonHeader), withText("View Details"),

                        isDisplayed()));
        textView7.check(matches(withText("View Details")));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.addNewWalk),

                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction button3 = onView(
                allOf(withId(R.id.home_but_routes),

                        isDisplayed()));
        button3.check(matches(isDisplayed()));

        ViewInteraction button4 = onView(
                allOf(withId(R.id.routes_but_routes),

                        isDisplayed()));
        button4.check(matches(isDisplayed()));

        ViewInteraction button5 = onView(
                allOf(withId(R.id.test_but_routes),

                        isDisplayed()));
        button5.check(matches(isDisplayed()));
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
