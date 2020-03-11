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
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ProposedWalkUITest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void proposedWalkUITest() {
        ViewInteraction textView = onView(
                allOf(withId(R.id.proposedWalk), withText("Proposed Walk"),

                        isDisplayed()));
        textView.check(matches(withText("Proposed Walk")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.walkName), withText("Walk:"),

                        isDisplayed()));
        textView2.check(matches(withText("Walk:")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.nameWalk), withText("HCM Trail"),

                        isDisplayed()));
        textView3.check(matches(withText("HCM Trail")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.ownerName), withText("Proposer:"),

                        isDisplayed()));
        textView4.check(matches(withText("Proposer:")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.nameOwner), withText("Gina T."),

                        isDisplayed()));
        textView5.check(matches(withText("Gina T.")));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.date), withText("Date:"),

                        isDisplayed()));
        textView6.check(matches(withText("Date:")));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.textDate), withText("03/05/2020"),

                        isDisplayed()));
        textView7.check(matches(withText("03/05/2020")));

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.time), withText("Time:"),

                        isDisplayed()));
        textView8.check(matches(withText("Time:")));

        ViewInteraction textView9 = onView(
                allOf(withId(R.id.textTime), withText("7:00 AM"),

                        isDisplayed()));
        textView9.check(matches(withText("7:00 AM")));

        ViewInteraction relativeLayout = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.recyclerView),
                                0),
                        0),
                        isDisplayed()));
        relativeLayout.check(matches(isDisplayed()));

        ViewInteraction textView10 = onView(
                allOf(withId(R.id.emailTextView), withText("a1@gmail.com"),

                        isDisplayed()));
        textView10.check(matches(withText("a1@gmail.com")));

        ViewInteraction relativeLayout2 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.recyclerView),
                                1),
                        0),
                        isDisplayed()));
        relativeLayout2.check(matches(isDisplayed()));

        ViewInteraction textView11 = onView(
                allOf(withId(R.id.emailTextView), withText(" a2@gmail.com"),

                        isDisplayed()));
        textView11.check(matches(withText(" a2@gmail.com")));

        ViewInteraction relativeLayout3 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.recyclerView),
                                2),
                        0),
                        isDisplayed()));
        relativeLayout3.check(matches(isDisplayed()));

        ViewInteraction textView12 = onView(
                allOf(withId(R.id.emailTextView), withText(" a3@gmail.com"),

                        isDisplayed()));
        textView12.check(matches(withText(" a3@gmail.com")));

        ViewInteraction relativeLayout4 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.recyclerView),
                                3),
                        0),
                        isDisplayed()));
        relativeLayout4.check(matches(isDisplayed()));

        ViewInteraction textView13 = onView(
                allOf(withId(R.id.emailTextView), withText(" a4@gmail.com"),

                        isDisplayed()));
        textView13.check(matches(withText(" a4@gmail.com")));

        ViewInteraction relativeLayout5 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.recyclerView),
                                4),
                        0),
                        isDisplayed()));
        relativeLayout5.check(matches(isDisplayed()));

        ViewInteraction textView14 = onView(
                allOf(withId(R.id.emailTextView), withText(" a5@gmail.com"),

                        isDisplayed()));
        textView14.check(matches(withText(" a5@gmail.com")));

        ViewInteraction relativeLayout6 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.recyclerView),
                                5),
                        0),
                        isDisplayed()));
        relativeLayout6.check(matches(isDisplayed()));

        ViewInteraction textView15 = onView(
                allOf(withId(R.id.emailTextView), withText(" a6@gmail.com"),

                        isDisplayed()));
        textView15.check(matches(withText(" a6@gmail.com")));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.acceptPropose),

                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction button3 = onView(
                allOf(withId(R.id.declineProposeBadTime),

                        isDisplayed()));
        button3.check(matches(isDisplayed()));

        ViewInteraction button4 = onView(
                allOf(withId(R.id.declineProposeRoute),

                        isDisplayed()));
        button4.check(matches(isDisplayed()));

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.acceptPropose), withText("Accept"),

                        isDisplayed()));
//        appCompatButton3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.declineProposeBadTime), withText("Decline (bad time)"),

                        isDisplayed()));
//        appCompatButton4.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.declineProposeRoute), withText("Decline (not a good route for me)"),

                        isDisplayed()));
//        appCompatButton5.perform(click());
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

