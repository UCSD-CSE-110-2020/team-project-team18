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
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestNewRouteUI {
    private static final String TEST_SERVICE = "TEST_SERVICE";
    private static final String FIREBASE_TEST_SERVICE = "FIREBASE_TEST";
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testNewRouteUI() {
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
        mActivityTestRule.getActivity().setHeight(60);
        mActivityTestRule.getActivity().setFirebaseServiceKey(FIREBASE_TEST_SERVICE);
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

   //     ViewInteraction appCompatButton2 = onView(
          //     allOf(withId(R.id.heightSubmitButton), withText("Submit Height"),

          //              isDisplayed()));
        //appCompatButton2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.routes_but_home), withText("Routes"),

                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.addNewWalk), withText("+"),

                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.textName), withText("Name: *"),

                        isDisplayed()));
        textView.check(matches(withText("Name: *")));

        ViewInteraction editText = onView(
                allOf(withId(R.id.inputName), withHint("Walk Name"),

                        isDisplayed()));
        editText.check(matches(withHint("Walk Name")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.textStartPoint), withText("Starting Point:"),

                        isDisplayed()));
        textView2.check(matches(withText("Starting Point:")));

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.inputStartPoint), withHint("Starting Point"),

                        isDisplayed()));
        editText2.check(matches(withHint("Starting Point")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.textFeature), withText("Features:"),

                        isDisplayed()));
        textView4.check(matches(withText("Features:")));

        ViewInteraction radioButton = onView(
                allOf(withId(R.id.loop),

                        isDisplayed()));
        radioButton.check(matches(isDisplayed()));

        ViewInteraction radioButton2 = onView(
                allOf(withId(R.id.outnback),

                        isDisplayed()));
        radioButton2.check(matches(isDisplayed()));

        ViewInteraction radioButton3 = onView(
                allOf(withId(R.id.flat),

                        isDisplayed()));
        radioButton3.check(matches(isDisplayed()));

        ViewInteraction radioButton4 = onView(
                allOf(withId(R.id.hilly),

                        isDisplayed()));
        radioButton4.check(matches(isDisplayed()));

        ViewInteraction radioButton5 = onView(
                allOf(withId(R.id.street),

                        isDisplayed()));
        radioButton5.check(matches(isDisplayed()));

        ViewInteraction radioButton6 = onView(
                allOf(withId(R.id.trail),

                        isDisplayed()));
        radioButton6.check(matches(isDisplayed()));

        ViewInteraction radioButton7 = onView(
                allOf(withId(R.id.evensurface),

                        isDisplayed()));
        radioButton7.check(matches(isDisplayed()));

        ViewInteraction radioButton8 = onView(
                allOf(withId(R.id.unevensurface),

                        isDisplayed()));
        radioButton8.check(matches(isDisplayed()));

        ViewInteraction radioButton9 = onView(
                allOf(withId(R.id.easy),
                        childAtPosition(
                                allOf(withId(R.id.groupDifficulty),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                                9)),
                                0),
                        isDisplayed()));
        radioButton9.check(matches(isDisplayed()));

        ViewInteraction radioButton10 = onView(
                allOf(withId(R.id.moderate),
                        childAtPosition(
                                allOf(withId(R.id.groupDifficulty),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                                9)),
                                1),
                        isDisplayed()));
        radioButton10.check(matches(isDisplayed()));

        ViewInteraction radioButton11 = onView(
                allOf(withId(R.id.difficult),
                        childAtPosition(
                                allOf(withId(R.id.groupDifficulty),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                                9)),
                                2),
                        isDisplayed()));
        radioButton11.check(matches(isDisplayed()));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.textNote), withText("Notes:"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                10),
                        isDisplayed()));
        textView5.check(matches(withText("Notes:")));

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.inputNote),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                11),
                        isDisplayed()));
        editText3.check(matches(withText("")));

        ViewInteraction button = onView(
                allOf(withId(R.id.cancel),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                12),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.save),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                13),
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
