package com.example.walk_walk_revolution;

public class Walk {
    private int steps;
    private int distance;
    private StopWatch stopWatch;
    private long timeTaken;

    public void startWalk(){
        this.steps = 0;
        this.distance = 0;
        this.timeTaken = 0;
        this.stopWatch = new StopWatch();
        stopWatch.start();
    }
    public void endWalk(){
        stopWatch.end();
        this.timeTaken = stopWatch.timeTaken();
    }
    public int getSteps(){
        return steps;
    }
    public int getDistance(){
        return distance;
    }

    public void setSteps(int numSteps){
        this.steps = numSteps;
    }
    public void setDistance(int totalDist){
        this.distance = totalDist;
    }

}
