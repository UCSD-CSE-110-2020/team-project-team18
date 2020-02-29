package com.example.walk_walk_revolution;

import org.junit.Test;
import java.text.*;

import static org.junit.Assert.*;

public class DistanceTraveledTest {

    private int stepsPerMile;
    private final int heightInInches = 60;
    private final int stepsTaken = 2000;

    @Test
    public void testStepsPerMile()
    {
        //initializing a new DistanceCalculator class and height.
        DistanceCalculator calc = new DistanceCalculator();

        stepsPerMile = calc.calculateStepsPerMile(heightInInches);
        assertEquals(stepsPerMile, 2556);
    }

    @Test
    public void testDistanceTraveled()
    {
        //initializing a new DistanceCalculator class, height, and steps taken.
        DistanceCalculator calc = new DistanceCalculator();

        stepsPerMile = calc.calculateStepsPerMile(heightInInches);
        double distanceTraveled = (double) stepsTaken / stepsPerMile;

        assertEquals(distanceTraveled, 0.78, 0.01);
    }
}