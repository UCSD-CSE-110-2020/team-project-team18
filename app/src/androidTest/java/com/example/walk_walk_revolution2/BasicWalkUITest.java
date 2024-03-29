package com.example.walk_walk_revolution2;


import android.app.Activity;
import android.app.Service;
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
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class BasicWalkUITest {
    private static final String TEST_SERVICE = "TEST_SERVICE";
    private static final String FIREBASE_TEST_SERVICE = "FIREBASE_TEST";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void basicWalkUITest() {

        FitnessServiceFactory.put(TEST_SERVICE, new FitnessServiceFactory.BluePrint() {
            @Override
            public FitnessService create(Home home) {
                return new TestFitnessService(home);
            }
        });
        FirebaseServiceFactory.put(FIREBASE_TEST_SERVICE, new FirebaseServiceFactory.BluePrint() {
            @Override
            public FirebaseService create(Service home) {
                return new TestFirebaseService(home);
            }
        });

        mActivityTestRule.getActivity().setFitnessServiceKey(TEST_SERVICE);
        mActivityTestRule.getActivity().setFirebaseServiceKey(FIREBASE_TEST_SERVICE);
        mActivityTestRule.getActivity().setHeight(60);
        ViewInteraction textView = onView(
                allOf(withId(R.id.loginButton), withText("Login"),

                        isDisplayed()));

        textView.check(matches(withText("Login")));
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.loginButton), withText("Login"),

                        isDisplayed()));
        appCompatButton.perform(click());


        ViewInteraction textView8 = onView(
                allOf(withId(R.id.recent_stats_text), withText("Recent Walk Stats"),

                        isDisplayed()));
        textView8.check(matches(withText("Recent Walk Stats")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.textView13), withText("Total Steps"),

                        isDisplayed()));
        textView2.check(matches(withText("Total Steps")));

        ViewInteraction button = onView(
                allOf(withId(R.id.start_walk),

                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.end_walk),

                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.textView11), withText("Distance"),

                        isDisplayed()));
        textView3.check(matches(withText("Distance")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.textView12), withText("Time"),

                        isDisplayed()));
        textView4.check(matches(withText("Time")));

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.start_walk), withText("Start Walk"),

                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.end_walk), withText("End Walk"),

                        isDisplayed()));
        appCompatButton2.perform(click());

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
