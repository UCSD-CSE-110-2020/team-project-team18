package com.example.walk_walk_revolution2;
import android.app.Service;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.walk_walk_revolution2.FirebaseService;
import com.example.walk_walk_revolution2.FirebaseServiceFactory;
import com.example.walk_walk_revolution2.FitnessService;
import com.example.walk_walk_revolution2.FitnessServiceFactory;
import com.example.walk_walk_revolution2.Home;
import com.example.walk_walk_revolution2.MainActivity;
import com.example.walk_walk_revolution2.R;
import com.example.walk_walk_revolution2.TestFirebaseService;
import com.example.walk_walk_revolution2.TestFitnessService;

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
public class inviteTeamMember {
    private static final String TEST_SERVICE = "TEST_SERVICE";
    private static final String FIREBASE_TEST_SERVICE = "FIREBASE_TEST";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void inviteTeamMember() {

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

        mActivityTestRule.getActivity().setFitnessServiceKey(TEST_SERVICE);
        mActivityTestRule.getActivity().setFirebaseServiceKey(FIREBASE_TEST_SERVICE);
        mActivityTestRule.getActivity().setHeight(60);
        ViewInteraction textView = onView(
                allOf(withId(R.id.loginButton), withText("Login"),

                        isDisplayed()));

        textView.check(matches(withText("Login")));
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.loginButton), withText("Login"),

                        isDisplayed()));
        appCompatButton.perform(click());
        ViewInteraction textView1 = onView(
                allOf(withId(R.id.team_but_home), withText("TEAM"),

                        isDisplayed()));

        textView1.check(matches(withText("TEAM")));
        ViewInteraction appCompatButton1 = onView(
                allOf(withId(R.id.team_but_home), withText("TEAM"),

                        isDisplayed()));
        appCompatButton1.perform(click());

    }
}
