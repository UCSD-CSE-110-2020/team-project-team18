package com.example.walk_walk_revolution2;


import android.app.Activity;
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
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestWalkInviteUI {

    private static final String TEST_SERVICE = "TEST_SERVICE";
    private static final String FIREBASE_TEST_SERVICE = "FIREBASE_TEST";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testWalkInviteUI() {
        FitnessServiceFactory.put(TEST_SERVICE, new FitnessServiceFactory.BluePrint() {
            @Override
            public FitnessService create(Home home) {
                return new TestFitnessService(home);
            }
        });
        FirebaseServiceFactory.put(FIREBASE_TEST_SERVICE, new FirebaseServiceFactory.BluePrint() {
            @Override
            public FirebaseService create(Activity home) {
                return new TestFirebaseService(home);
            }
        });
        mActivityTestRule.getActivity().setFitnessServiceKey(TEST_SERVICE);
        mActivityTestRule.getActivity().setFirebaseServiceKey(FIREBASE_TEST_SERVICE);
        mActivityTestRule.getActivity().setHeight(60);

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.loginButton), withText("Login"),

                        isDisplayed()));
        appCompatButton.perform(click());

    /*    ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.userHeight),

                        isDisplayed()));
        appCompatEditText.perform(replaceText("60"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.userEmail),

                        isDisplayed()));
        appCompatEditText2.perform(replaceText("test@test"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.firstname), //withText("First Name"),

                        isDisplayed()));
        //appCompatEditText3.perform(replaceText("test"));

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.firstname), //withText("test"),

                        isDisplayed()));
        //appCompatEditText4.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.lastname), //withText("Last Name"),

                        isDisplayed()));
        //appCompatEditText5.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.lastname), //withText("Last Name"),

                        isDisplayed()));
       // appCompatEditText6.perform(replaceText("test"));

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.lastname), //withText("test"),

                        isDisplayed()));
        // appCompatEditText7.perform(closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.submitButton), //withText("Submit All"),

                        isDisplayed()));
        appCompatButton2.perform(click());
*/
        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.routes_but_home), withText("Routes"),

                        isDisplayed()));
        appCompatButton3.perform(click());
/*
        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.addNewWalk), withText("+"),

                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.inputName),

                        isDisplayed()));
        appCompatEditText8.perform(replaceText("Geisel"), closeSoftKeyboard());

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.inputStartPoint),

                        isDisplayed()));
        appCompatEditText9.perform(replaceText("UCSD"), closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.save), withText("Save"),

                        isDisplayed()));
        //appCompatButton5.perform(click());
*/
        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.viewDetailsButton), withText("View Route"),

                        isDisplayed()));
        appCompatButton6.perform(click());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.propose_walk), withText("Propose Walk"),

                        isDisplayed()));
        appCompatButton7.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.walk_name), withText("new walk"),

                        isDisplayed()));
        textView.check(matches(withText("new walk")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.starting_point), withText("pepper canyon"),

                        isDisplayed()));
        textView2.check(matches(withText("pepper canyon")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.walk_date), withText("Date:"),

                        isDisplayed()));
        textView3.check(matches(withText("Date:")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.walk_time), withText("Time:"),

                        isDisplayed()));
        textView4.check(matches(withText("Time:")));

        ViewInteraction button = onView(
                allOf(withId(R.id.send_invite),

                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.cancel),

                        isDisplayed()));
        button2.check(matches(isDisplayed()));
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
