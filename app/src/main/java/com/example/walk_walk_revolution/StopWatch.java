package com.example.walk_walk_revolution;

public class StopWatch {
    private long start;
    private long end;
    private boolean isOn;

    public void start(){
        this.start = System.currentTimeMillis();
        isOn = true;
    }
    public void end(){
        this.end = System.currentTimeMillis();
        isOn = false;
    }
    public long timeTaken(){
        if(isOn){
            return System.currentTimeMillis() - start;
        }else{
            return end-start;
        }
    }
    public void setStart(){
        this.start = 0;
        isOn = true;
    }
    public void setEnd(long time){
        this.end = 0;
        isOn = false;
    }

}
