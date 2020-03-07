package com.example.walk_walk_revolution;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class RouteItem extends AppCompatActivity {
    private String name;
    private String startPoint;
    private int stepCount;
    private double distance;
    private String time;
    private String fileName;
    private RoutesScreen routeScreen;
    private boolean isFavorite;
    private boolean hasWalked;

    public RouteItem(){

    }

    public RouteItem(String filename, String name, String startPoint, int stepCount, double distance,
                     String time, RoutesScreen routeScreen, boolean isFavorite, boolean hasWalked) {
        this.routeScreen = routeScreen;
        this.name = name;
        this.startPoint = startPoint;
        this.stepCount = stepCount;
        this.distance = distance;
        this.time = time;
        this.fileName = filename;
        this.isFavorite = isFavorite;
        this.hasWalked = hasWalked;
    }

    public void launchRouteDetails(){
        this.routeScreen.launchRouteDetails(this.fileName);
    }

    public void changeFavorite()
    {
        isFavorite = !isFavorite;
        this.routeScreen.saveIsFavorite(fileName, isFavorite);
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

    public boolean getIsFavorite() { return this.isFavorite; }

    public boolean getHasWalked() {return this.hasWalked; }
}
