package com.example.walk_walk_revolution2;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WalkTest {
    Walk walk;

    @Before
    public void initalize() {
        walk = new Walk("1000", "1.5", "00:02:10");

    }

    @Test
    public void walkTest() {
        walk.startWalk();

        assertEquals(walk.getSteps(), 0);
        assertNotNull(walk.getStopWatch());
        assertTrue(walk.isActive);

        walk.endWalk();

        assertFalse(walk.isActive);

    }

}