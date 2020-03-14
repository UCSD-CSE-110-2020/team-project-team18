package com.example.walk_walk_revolution2;

import android.app.Activity;
import android.app.Service;
import android.widget.TextView;

import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
public class BasicWalkAndroidTest {
   private Walk walk;
    private static final String TEST_SERVICE = "TEST_SERVICE";
    private static final String FIREBASE_TEST_SERVICE = "FIREBASE_TEST";
    @Rule
    public ActivityTestRule<MainActivity> home = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Before
    public void setup(){
        walk = new Walk();
        walk.startWalk();
    }

    @Test
    public void testFieldsSetCorrectly(){
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

        home.getActivity().setFitnessServiceKey(TEST_SERVICE);
        home.getActivity().setFirebaseServiceKey(FIREBASE_TEST_SERVICE);
        home.getActivity().setHeight(60);
        walk.setTime(7200000L);
        walk.setDistance(1.5);
        walk.setSteps(5000);

        assertEquals(walk.getTimeTaken(), "02:00:00");
        assertEquals(Double.toString(walk.getDistance()), "1.5");
        assertEquals(String.valueOf(walk.getSteps()), "5000");

    }
}
