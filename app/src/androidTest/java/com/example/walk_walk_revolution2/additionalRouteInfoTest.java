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
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
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
public class additionalRouteInfoTest {
    private static final String TEST_SERVICE = "TEST_SERVICE";
    private static final String FIREBASE_TEST_SERVICE = "FIREBASE_TEST";
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void additionalRouteInfoTest() {
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

        ViewInteraction textView0 = onView(
                allOf(withId(R.id.loginButton), withText("Login"),

                        isDisplayed()));

        textView0.check(matches(withText("Login")));
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.loginButton), withText("Login"),

                        isDisplayed()));
        appCompatButton.perform(click());


        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.start_walk), withText("Start Walk"),

                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.end_walk), withText("End Walk"),

                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.loop), withText("Loop"),

                        isDisplayed()));
        appCompatRadioButton.perform(click());


        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.inputName),

                        isDisplayed()));
        appCompatEditText3.perform(replaceText("new walk"));

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.inputName), withText("new walk"),

                        isDisplayed()));
        appCompatEditText4.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.inputStartPoint),

                        isDisplayed()));
        appCompatEditText5.perform(replaceText("pepper canyon"), closeSoftKeyboard());


        ViewInteraction appCompatRadioButton2 = onView(
                allOf(withId(R.id.flat), withText("Flat"),

                        isDisplayed()));
        appCompatRadioButton2.perform(click());

        ViewInteraction appCompatRadioButton3 = onView(
                allOf(withId(R.id.street), withText("Street"),

                        isDisplayed()));
        appCompatRadioButton3.perform(click());

        ViewInteraction appCompatRadioButton4 = onView(
                allOf(withId(R.id.moderate), withText("Moderate"),

                        isDisplayed()));
        appCompatRadioButton4.perform(click());

        ViewInteraction appCompatRadioButton5 = onView(
                allOf(withId(R.id.evensurface), withText("Even Surface"),

                        isDisplayed()));
        appCompatRadioButton5.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.save), withText("Save"),

                        isDisplayed()));
        appCompatButton5.perform(click());

    }

}
