package com.example.walk_walk_revolution;

import android.app.Activity;
import android.widget.TextView;
import android.view.View;

import java.text.DecimalFormat;


public class DistanceCalculator {

    //returns the number of steps needed to walk one mile.
    public int calculateStepsPerMile(int height)
    {
        double strideLengthInInches = height * 0.413;
        double strideLengthInFeet = strideLengthInInches / 12;
        int stepsPerMile = (int) (5280 / strideLengthInFeet);
        return stepsPerMile;
    }

    //converts the number of steps taken into the distance traveled (in miles).
    public double calculateDistanceTraveled(int height, MainActivity activity)
    {
        TextView steps = activity.findViewById(R.id.CurrentSteps);
        int stepsTaken = Integer.parseInt(steps.getText().toString());

        int stepsPerMile = calculateStepsPerMile(height);
        double distanceTraveled = (double) stepsTaken / (double) stepsPerMile;
        return distanceTraveled;
    }
}
