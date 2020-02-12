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
import org.junit.Before;
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
public class BasicWalkUITest {
    private static final String TEST_SERVICE = "TEST_SERVICE";
    @Before
    public void setUp() {
        hActivity.getActivity().saveHeight(72);
        hActivity.getActivity().launchHome();
        FitnessServiceFactory.put(TEST_SERVICE, new FitnessServiceFactory.BluePrint() {
            @Override
            public FitnessService create(Home activity) {
                return new TestFitnessService(activity);
            }
        });
        //mActivityTestRule.getActivity().setFitnessServiceKey(TEST_SERVICE);
    //    mActivityTestRule.getActivity().setup();
     //   mActivityTestRule.getActivity().setup();
    }
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public ActivityTestRule<HeightScreen> hActivity = new ActivityTestRule<>(HeightScreen.class);

    @Test
    public void basicWalkUITest() {
        mActivityTestRule.getActivity().setFitnessServiceKey(TEST_SERVICE);
        mActivityTestRule.getActivity();
        ViewInteraction textView = onView(
                allOf(withId(R.id.recent_stats_text), withText("Recent Walk Stats"),

                        isDisplayed()));
        textView.check(matches(withText("Recent Walk Stats")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.textView13), withText("Total Steps"),

                        isDisplayed()));
        textView2.check(matches(withText("Total Steps")));

        ViewInteraction button = onView(
                allOf(withId(R.id.start_walk),

                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.end_walk),

                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.textView11), withText("Distance"),

                        isDisplayed()));
        textView3.check(matches(withText("Distance")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.textView12), withText("Time"),

                        isDisplayed()));
        textView4.check(matches(withText("Time")));

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.start_walk), withText("Start Walk"),

                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.end_walk), withText("End Walk"),

                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.recentWalkTime), withText("00:00:00"),

                        isDisplayed()));
        textView5.check(matches(withText("00:00:00")));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.recentWalkSteps), withText("0"),

                        isDisplayed()));
        textView6.check(matches(withText("0")));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.recentWalkDist), withText("0.0"),

                        isDisplayed()));
        textView7.check(matches(withText("0.0")));
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
    private class TestFitnessService implements FitnessService {
        private static final String TAG = "[TestFitnessService]: ";
        private Home mainActivity;

        public TestFitnessService(Home mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        public int getRequestCode() {
            return 0;
        }

        @Override
        public void setup() {
            System.out.println(TAG + "setup");
        }

        @Override
        public void updateStepCount() {
            System.out.println(TAG + "updateStepCount");
            mainActivity.currentWalk.setTime(0L);
            mainActivity.walkCleanup();
           // mainActivity.setStepCount(nextStepCount);
        }
    }
}
