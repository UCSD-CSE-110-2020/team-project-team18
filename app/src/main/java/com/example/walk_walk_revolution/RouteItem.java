package com.example.walk_walk_revolution;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

public class RouteItem extends AppCompatActivity {
    private String name;
    private String startPoint;
    private int stepCount;
    private double distance;
    private String time;
    private String fileName;
    private RouteInterface routeScreen;

    public RouteItem(){

    }

    public RouteItem(String filename, String name, String startPoint, int stepCount, double distance, String time, RouteInterface routeScreen) {
        this.routeScreen = routeScreen;
        this.name = name;
        this.startPoint = startPoint;
        this.stepCount = stepCount;
        this.distance = distance;
        this.time = time;
        this.fileName = filename;
    }

    public void launchRouteDetails(){
        this.routeScreen.launchRouteDetails(this.fileName);
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
    public String getTime(){return this.time;}

    public String getFileName() {
        return this.fileName;
    }
}
