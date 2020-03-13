package com.example.walk_walk_revolution2;

import android.app.Activity;
import android.app.Service;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.*;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class HeightTest {
    private static final String TEST_SERVICE = "TEST_SERVICE";
    private static final String FIREBASE_TEST_SERVICE = "FIREBASE_TEST";
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testSaveHeight() {
        FitnessServiceFactory.put(TEST_SERVICE, new FitnessServiceFactory.BluePrint() {
            @Override
            public FitnessService create(Home home) {
                return new TestFitnessService(home);
            }
        });
        FirebaseServiceFactory.put(FIREBASE_TEST_SERVICE, new FirebaseServiceFactory.BluePrint() {
            @Override
            public FirebaseService create(Service service) {
                return new TestFirebaseService(service);
            }
        });

        mActivityTestRule.getActivity().setFitnessServiceKey(TEST_SERVICE);
        mActivityTestRule.getActivity().setFirebaseServiceKey(FIREBASE_TEST_SERVICE);
        mActivityTestRule.getActivity().setHeight(0);
        ViewInteraction button = onView(
                allOf(withId(R.id.loginButton),

                isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.loginButton), withText("Login"),

                        isDisplayed()));
        
        appCompatButton.perform(click());

       ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.userHeight),

                        isDisplayed()));
       appCompatEditText.perform(replaceText("60"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.userEmail),

                        isDisplayed()));
        appCompatEditText2.perform(replaceText("hello@yeet.com"), closeSoftKeyboard());
        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.firstname),

                        isDisplayed()));
        appCompatEditText3.perform(replaceText("john"), closeSoftKeyboard());
        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.lastname),

                        isDisplayed()));
        appCompatEditText4.perform(replaceText("apple"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.submitButton), withText("Submit All"),

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
