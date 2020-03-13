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
public class TeamRoutesFunctionalTest {
    private static final String TEST_SERVICE = "TEST_SERVICE";
    private static final String FIREBASE_TEST_SERVICE = "FIREBASE_TEST";
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void teamRoutesFunctionalTest() {
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
        mActivityTestRule.getActivity().setFirebaseServiceKey(FIREBASE_TEST_SERVICE);
        mActivityTestRule.getActivity().setFitnessServiceKey(TEST_SERVICE);
        mActivityTestRule.getActivity().setHeight(60);
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.loginButton), withText("Login"),

                        isDisplayed()));
        appCompatButton.perform(click());

//        ViewInteraction appCompatEditText = onView(
//                allOf(withId(R.id.userHeight),
//
//                        isDisplayed()));
//        //appCompatEditText.perform(click());
//
//        ViewInteraction appCompatEditText2 = onView(
//                allOf(withId(R.id.userHeight),
//
//                        isDisplayed()));
//        //appCompatEditText2.perform(replaceText("5"), closeSoftKeyboard());
//
//        ViewInteraction appCompatButton2 = onView(
//                allOf(withId(R.id.heightSubmitButton), withText("Submit Height"),
//
//                        isDisplayed()));
//        //appCompatButton2.perform(click());
//
//
        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.routes_but_home), withText("Routes"),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.team_routes_but), withText("Team Routes"),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.team_routes_title), withText("Team Routes"),
                        isDisplayed()));
        textView.check(matches(withText("Team Routes")));

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.my_routes), withText("My Routes"),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.routes_title), withText("Routes"),
                        isDisplayed()));
        textView2.check(matches(withText("Routes")));
    }


}
