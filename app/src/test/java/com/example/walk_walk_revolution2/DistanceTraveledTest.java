package com.example.walk_walk_revolution2;

import org.junit.Test;
import java.text.*;

import static org.junit.Assert.*;

public class DistanceTraveledTest {

    @Test
    public void testStepsPerMile()
    {
        //initializing a new DistanceCalculator class and height.
        DistanceCalculator calc = new DistanceCalculator();
        int heightInInches = 60;

        int stepsPerMile = calc.calculateStepsPerMile(heightInInches);
        assertEquals(stepsPerMile, 2556);
    }

    @Test
    public void testDistanceTraveled()
    {
        //initializing a new DistanceCalculator class, height, and steps taken.
        DistanceCalculator calc = new DistanceCalculator();
        int heightInInches = 60;
        int stepsTaken = 2000;

        //double distanceTraveled = calc.calculateDistanceTraveled(heightInInches, );

        //Truncating the distance (in miles) to two numbers after the decimal.
        DecimalFormat df = new DecimalFormat("#.##");
        //double result = Double.valueOf(df.format(distanceTraveled));

        //assertEquals(result, 0.78, 0.01);
    }
}