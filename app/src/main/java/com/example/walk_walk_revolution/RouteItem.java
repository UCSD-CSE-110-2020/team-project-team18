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
    private RoutesScreen routeScreen;

    public RouteItem(String fileName, RoutesScreen routeScreen){
        this.fileName = fileName;
        this.routeScreen = routeScreen;
        SharedPreferences preferences = getSharedPreferences(fileName, Context.MODE_PRIVATE);
        System.out.println(fileName);

        this.name = preferences.getString("name","Error");
        this.startPoint = preferences.getString("startPoint","Error");
        this.stepCount = preferences.getInt("stepCount", -1);
        this.distance = preferences.getInt("distance", -1);
    }

    public RouteItem(String filename, String name, String startPoint, int stepCount, double distance, String time, RoutesScreen routeScreen) {
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
