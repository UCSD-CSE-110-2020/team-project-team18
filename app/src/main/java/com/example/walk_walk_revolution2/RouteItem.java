package com.example.walk_walk_revolution2;


import java.security.cert.CertPathValidatorException;

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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    private String note;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;

    public RouteItem(){

    }

    public RouteItem(String filename, String name, String startPoint, int stepCount, double distance, String time, RouteInterface routeScreen) {
        this.setRouteScreen(routeScreen);
        this.name = name;
        this.startPoint = startPoint;
        this.stepCount = stepCount;
        this.distance = distance;
        this.time = time;
        this.fileName = filename;
    }

    public RouteItem(String email, String name, String startPoint, int loop, int flat, int street, int surface, int difficulty, int stepCount, double distance, String time, String note) {
        this.email = email;
        this.name = name;
        this.startPoint = startPoint;
        this.stepCount = stepCount;
        this.distance = distance;
        this.time = time;

        this.setLoop(loop);
        this.setFlat(flat);
        this.setStreet(street);
        this.setSurface(surface);
        this.setDifficult(difficulty);
        this.time = time;
        this.note = note;
    }


    public void launchRouteDetails(){
        if(fileName == null || fileName.length() == 0)
            this.routeScreen.launchRouteDetails(name, startPoint, loop, flat, street, surface, difficult, note);
        else
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

    public RouteInterface getRouteScreen() {
        return routeScreen;
    }

    public void setRouteScreen(RouteInterface routeScreen) {
        this.routeScreen = routeScreen;
    }

    public int getLoop() {
        return loop;
    }

    public void setLoop(int loop) {
        this.loop = loop;
    }

    public int getFlat() {
        return flat;
    }

    public void setFlat(int flat) {
        this.flat = flat;
    }

    public int getStreet() {
        return street;
    }

    public void setStreet(int street) {
        this.street = street;
    }

    public int getSurface() {
        return surface;
    }

    public void setSurface(int surface) {
        this.surface = surface;
    }

    public int getDifficult() {
        return difficult;
    }

    public void setDifficult(int difficult) {
        this.difficult = difficult;
    }
}