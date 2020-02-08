package com.example.walk_walk_revolution;

import android.widget.TextView;

import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
public class BasicWalkAndroidTest {
   private Walk walk;

    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Before
    public void setup(){
        walk = new Walk();
        walk.startWalk();
    }

    @Test
    public void testFieldsSetCorrectly(){
        walk.setTime(7200000L);
        walk.setDistance(1.5);
        walk.setSteps(5000);

        assertEquals(walk.getTimeTaken(), "02:00:00");
        assertEquals(Double.toString(walk.getDistance()), "1.5");
        assertEquals(String.valueOf(walk.getSteps()), "5000");

    }
}
