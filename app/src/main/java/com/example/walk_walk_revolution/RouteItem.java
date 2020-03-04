package com.example.walk_walk_revolution;


public class RouteItem {
    private String name;
    private String startPoint;
    private int stepCount;
    private double distance;
    private String time;
    private String fileName;
    private RouteInterface routeScreen;

    private int loop;
    private int flat;
    private int street;
    private int surface;
    private int difficult;
    private String note;

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

    public RouteItem(String name, String startPoint, int loop, int flat, int street, int surface, int difficulty, int stepCount, double distance, String time, String note) {
        this.name = name;
        this.startPoint = startPoint;
        this.stepCount = stepCount;
        this.distance = distance;
        this.time = time;

        this.loop = loop;
        this.flat = flat;
        this.street = street;
        this.surface = surface;
        this.difficult = difficulty;
        this.time = time;
        this.note = note;
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
