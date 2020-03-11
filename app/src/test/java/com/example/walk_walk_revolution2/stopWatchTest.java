package com.example.walk_walk_revolution2;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class stopWatchTest {
    @Test
    public void testTimeAsString(){
        StopWatch stop = new StopWatch(0L);
        stop.setAmountTime(10000L);
        assertEquals(stop.timeTakenString(), "00:00:10");
    }
    @Test
    public void testTimeAsLong(){
        StopWatch stop = new StopWatch(0L);
        stop.setAmountTime(720000);
        assertEquals(stop.timeTakenLong(), 720000L);
    }
}
