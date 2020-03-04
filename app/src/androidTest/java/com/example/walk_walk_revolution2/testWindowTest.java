package com.example.walk_walk_revolution;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

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
public class testWindowTest {
    private static final String TEST_SERVICE = "TEST_SERVICE";


    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void setTestValueTest(){

        FitnessServiceFactory.put(TEST_SERVICE, new FitnessServiceFactory.BluePrint() {
            @Override
            public FitnessService create(Home home) {
                return new TestFitnessService(home);
            }
        });

        mActivityTestRule.getActivity().setFitnessServiceKey(TEST_SERVICE);
        mActivityTestRule.getActivity().setHeight(60);
        ViewInteraction textView = onView(
                allOf(withId(R.id.loginButton), withText("Login"),

                        isDisplayed()));

        textView.check(matches(withText("Login")));
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.loginButton), withText("Login"),

                        isDisplayed()));
        appCompatButton.perform(click());
        ViewInteraction button2 = onView(
                allOf(withId(R.id.test_but_home),

                        isDisplayed()));
        button2.check(matches(isDisplayed()));
        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.test_but_home), withText("TESTING"),

                        isDisplayed()));
        appCompatButton2.perform(click());
        ViewInteraction button3 = onView(
                allOf(withId(R.id.step_but),

                        isDisplayed()));
        button3.check(matches(isDisplayed()));
        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.step_but), withText("ADD 500 STEPS"),

                        isDisplayed()));
        appCompatButton4.perform(click());
        appCompatButton4 = onView(
                allOf(withId(R.id.step_but), withText("ADD 500 STEPS"),

                        isDisplayed()));
        appCompatButton4.perform(click());
        appCompatButton4 = onView(
                allOf(withId(R.id.step_but), withText("ADD 500 STEPS"),

                        isDisplayed()));
        appCompatButton4.perform(click());
        ViewInteraction textView5 = onView(
                allOf(withId(R.id.curr_step), withText("1500"),

                        isDisplayed()));

        textView5.check(matches(withText("1500")));
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.time_input),

                        isDisplayed()));
        appCompatEditText.perform(replaceText("30000"), closeSoftKeyboard());

       ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.submit_but), withText("SUBMIT"),

                        isDisplayed()));
        appCompatButton6.perform(click());
        ViewInteraction textView6 = onView(
                allOf(withId(R.id.curr_time), withText("30000 ms"),

                        isDisplayed()));

        textView6.check(matches(withText("30000 ms")));
    }

}
