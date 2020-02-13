package com.example.walk_walk_revolution;

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

import org.junit.Rule;
import org.junit.Test;

public class HeightTest {
    private static final String TEST_SERVICE = "TEST_SERVICE";
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);



    @Test
    public void testSaveHeight() {
        FitnessServiceFactory.put(TEST_SERVICE, new FitnessServiceFactory.BluePrint() {
            @Override
            public FitnessService create(Home home) {
                return new TestFitnessService(home);
            }
        });

        mActivityTestRule.getActivity().setFitnessServiceKey(TEST_SERVICE);

        ViewInteraction textView = onView(
                allOf(withId(R.id.loginButton), withText("Login"),

                        isDisplayed()));

        textView.check(matches(withText("Login")));
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.loginButton), withText("Login"),

                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.userHeight),

                        isDisplayed()));
        appCompatEditText.perform(replaceText("60"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.heightSubmitButton), withText("Submit Height"),

                        isDisplayed()));
        appCompatButton2.perform(click());


    }
}
