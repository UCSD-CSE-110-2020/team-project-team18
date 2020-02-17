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
public class StartStopWalkTest {

    private static final String TEST_SERVICE = "TEST_SERVICE";


    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void startStopWalkTest() {
        FitnessServiceFactory.put(TEST_SERVICE, new FitnessServiceFactory.BluePrint() {
            @Override
            public FitnessService create(Home home) {
                return new TestFitnessService(home);
            }
        });

        mActivityTestRule.getActivity().setFitnessServiceKey(TEST_SERVICE);
        mActivityTestRule.getActivity().setHeight(60);

        ViewInteraction textView0 = onView(
                allOf(withId(R.id.loginButton), withText("Login"),

                        isDisplayed()));

        textView0.check(matches(withText("Login")));
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.loginButton), withText("Login"),

                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.userHeight),
                        childAtPosition(
                                allOf(withId(R.id.heightConstraint),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("1000"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.heightSubmitButton), withText("Submit Height"),
                        childAtPosition(
                                allOf(withId(R.id.heightConstraint),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.home_title), withText("Home"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Home")));

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.start_walk), withText("Start Walk"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                8),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.end_walk), withText("End Walk"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                9),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.textName), withText("Name: *"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("Name: *")));

        ViewInteraction button = onView(
                allOf(withId(R.id.save),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                13),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.inputName),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("yfj"), closeSoftKeyboard());

        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.flat), withText("Flat"),
                        childAtPosition(
                                allOf(withId(R.id.groupFlat),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                6)),
                                0),
                        isDisplayed()));
        appCompatRadioButton.perform(click());

        ViewInteraction appCompatRadioButton2 = onView(
                allOf(withId(R.id.street), withText("Street"),
                        childAtPosition(
                                allOf(withId(R.id.groupStreet),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                7)),
                                0),
                        isDisplayed()));
        appCompatRadioButton2.perform(click());

        ViewInteraction appCompatRadioButton3 = onView(
                allOf(withId(R.id.moderate), withText("Moderate"),
                        childAtPosition(
                                allOf(withId(R.id.groupDifficulty),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                9)),
                                1),
                        isDisplayed()));
        appCompatRadioButton3.perform(click());

        pressBack();

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.save), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                13),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.name), withText("yfj"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.rvRoutes),
                                        0),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText("yfj")));
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
