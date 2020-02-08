package com.example.walk_walk_revolution;

public class DistanceCalculator {

    private int heightInInches = 60;

    //returns the number of steps needed to walk one mile.
    public static int calculateStepsPerMile(int height)
    {
        height = 60;
        double strideLengthInInches = height * 0.413;
        double strideLengthInFeet = strideLengthInInches / 12;
        int stepsPerMile = (int) (5280 / strideLengthInFeet);
        return stepsPerMile;
    }

    //converts the number of steps taken into the distance traveled (in miles).
    public static double calculateDistanceTraveled(int height, int stepsTaken)
    {
        height = 60;
        stepsTaken = 2000;
        int stepsPerMile = calculateStepsPerMile(height);
        double distanceTraveled = (double) stepsTaken / (double) stepsPerMile;
        return distanceTraveled;
    }
}
