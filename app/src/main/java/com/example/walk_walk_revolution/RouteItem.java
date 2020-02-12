package com.example.walk_walk_revolution;

import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

public class RouteItem extends AppCompatActivity {
    private String name;
    private String startPoint;
    private int stepCount;
    private double distance;
    private String fileName;

    public RouteItem(String fileName){
        this.fileName = fileName;

        SharedPreferences spfs = getSharedPreferences(fileName,MODE_PRIVATE);

        this.name = spfs.getString("name","Error");
        this.startPoint = spfs.getString("startPoint","Error");
        this.stepCount = spfs.getInt("stepCount", -1);
        this.distance = spfs.getInt("distance", -1);
    }

    public RouteItem(String name, String startPoint, int stepCount, double distance) {
        this.name = name;
        this.startPoint = startPoint;
        this.stepCount = stepCount;
        this.distance = distance;
    }

    public String getName() {
        return this.name;
    }

    public String getStartPoint() {
        return this.startPoint;
    }

    public int getStepCount() {
        return this.stepCount;
    }

    public double getDistance() {
        return this.distance;
    }

    public String getFileName() {
        return this.fileName;
    }
}
