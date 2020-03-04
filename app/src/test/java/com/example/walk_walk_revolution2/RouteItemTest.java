package com.example.walk_walk_revolution2;


import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class RouteItemTest {
    String fileName;
    String name;
    String startPoint;
    String time;
    int stepCount;
    double distance;

    RouteItem item;

    ActivityTestRule<RoutesScreen> routeScreenTestRule = new ActivityTestRule<RoutesScreen>(RoutesScreen.class);

    @Before
    public void initializePrefs() {
        this.fileName = "testing_filename";
        this.name = "testName";
        this.startPoint = "testStartPoint";
        this.time = "10:00:01";
        this.stepCount = 200;
        this.distance = 1.5f;

        this.item = new RouteItem(fileName, name, startPoint, stepCount, distance, time, routeScreenTestRule.getActivity());
    }

    @Test
    public void testGetters() {
        assertEquals(item.getFileName(), fileName);
        assertEquals(item.getName(), name);
        assertEquals(item.getStartPoint(), startPoint);
        assertEquals(item.getTime(), time);
        assertEquals(item.getStepCount(), stepCount);

    }

}

