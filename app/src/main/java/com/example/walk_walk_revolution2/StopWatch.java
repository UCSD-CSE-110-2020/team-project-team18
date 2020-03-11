package com.example.walk_walk_revolution2;

public class StopWatch {
    private long start;
    private long end;
    private boolean isOn;

    public StopWatch() {
        this.start = System.currentTimeMillis();
        isOn = true;
    }
    public StopWatch(long time){
        this.start = time;
        isOn = true;
    }
    public boolean isRunning(){
        if(isOn){
            return true;
        }else{
            return false;
        }
    }
    public long getStart(){
        return this.start;
    }
    public void end(){
        if(isOn) {
            this.end = System.currentTimeMillis();
            isOn = false;
            timeTakenLong();
        }
    }
    public long timeTakenLong(){
        if(isOn){
            return System.currentTimeMillis() - start;
        }else{
            return end-start;
        }
    }
    public void setAmountTime(long time){
        this.start = 0;
        this.end = time;
        isOn = false;
    }
    public String timeTakenString() {
        if (isOn) {
            return stringFormat(System.currentTimeMillis() - start);
        } else {
            return stringFormat(end-start);
        }
    }



    public String stringFormat(long time){
        String toReturn;
        long totalSecs = time/1000;
        long hours = (totalSecs / 3600);
        long mins = (totalSecs / 60) % 60;
        long secs = totalSecs % 60;

        toReturn = String.format("%02d:%02d:%02d", hours, mins, secs);
        return toReturn;
    }
}


