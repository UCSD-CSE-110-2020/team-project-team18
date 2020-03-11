package com.example.walk_walk_revolution2;


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
public class TestRouteDetailUI {

    @Rule
    public ActivityTestRule<Route> mActivityTestRule = new ActivityTestRule<>(Route.class);

    @Test
    public void testRouteDetailUI() {
        ViewInteraction textView = onView(
                allOf(withId(R.id.textName), withText("Name:*"),

                        isDisplayed()));
        //textView.check(matches(withText("Name:*")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.textStartPoint), withText("Starting Point:"),

                        isDisplayed()));
        //textView2.check(matches(withText("Starting Point:")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.textFeature), withText("Features:"),

                        isDisplayed()));
        textView3.check(matches(withText("Features:")));

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
                allOf(withId(R.id.street),

                        isDisplayed()));
        radioButton4.check(matches(isDisplayed()));

        ViewInteraction radioButton5 = onView(
                allOf(withId(R.id.trail),

                        isDisplayed()));
        radioButton5.check(matches(isDisplayed()));

        ViewInteraction radioButton6 = onView(
                allOf(withId(R.id.evensurface),

                        isDisplayed()));
        radioButton6.check(matches(isDisplayed()));

        ViewInteraction radioButton7 = onView(
                allOf(withId(R.id.unevensurface),

                        isDisplayed()));
        radioButton7.check(matches(isDisplayed()));

        ViewInteraction radioButton8 = onView(
                allOf(withId(R.id.easy),

                        isDisplayed()));
        radioButton8.check(matches(isDisplayed()));

        ViewInteraction radioButton9 = onView(
                allOf(withId(R.id.moderate),

                        isDisplayed()));
        radioButton9.check(matches(isDisplayed()));

        ViewInteraction radioButton10 = onView(
                allOf(withId(R.id.difficult),

                        isDisplayed()));
        radioButton10.check(matches(isDisplayed()));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.textNote), withText("Notes:"),

                        isDisplayed()));
        textView4.check(matches(withText("Notes:")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.textNote), withText("Notes:"),

                        isDisplayed()));
        textView5.check(matches(withText("Notes:")));

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.inputNote),

                        isDisplayed()));
        //editText3.check(matches(withText("")));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.cancel),

                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction button3 = onView(
                allOf(withId(R.id.startWalk),

                        isDisplayed()));
        button3.check(matches(isDisplayed()));
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
