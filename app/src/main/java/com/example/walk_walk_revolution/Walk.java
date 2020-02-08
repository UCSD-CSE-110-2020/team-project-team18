package com.example.walk_walk_revolution;

public class Walk {
    private int steps;
    private int distance;
    private StopWatch stopWatch;
    private String timeTaken;
    public boolean isActive;

    public void startWalk(){
        this.steps = 0;
        this.distance = 0;
        this.stopWatch = new StopWatch();
        this.timeTaken = stopWatch.timeTakenString();
        isActive = true;
    }
    public void updateWalk(){
        this.timeTaken = stopWatch.timeTakenString();
        if(stopWatch.isRunning()){
            isActive = true;
        }else{
            isActive = false;
        }
    }
    public void endWalk(){
        stopWatch.end();
        isActive = false;
        this.timeTaken = stopWatch.timeTakenString();
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
