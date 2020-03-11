package com.example.walk_walk_revolution2;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class stringFormatTest {
    public StopWatch stopWatch;

    @Before
    public void setup(){
    stopWatch = new StopWatch();
    stopWatch.end();
    }
    @Test
    public void ExactCase_isCorrect() {
        assertEquals(stopWatch.stringFormat(7200000L), "02:00:00");
    }
    @Test
    public void smallValue_isCorrect(){
        assertEquals(stopWatch.stringFormat(500000L), "00:08:20");
    }
    @Test
    public void smallerValue_isCorrect(){
        assertEquals(stopWatch.stringFormat(14700L), "00:00:14");
    }
    @Test
    public void smallestValue_isCorrect(){
        assertEquals(stopWatch.stringFormat(0L), "00:00:00");
    }
    @Test
    public void everyPlaceFilled_isCorrect(){
        assertEquals(stopWatch.stringFormat(87435268L), "24:17:15");
    }
}