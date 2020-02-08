package com.example.walk_walk_revolution;

import androidx.test.rule.ActivityTestRule;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;

public class HeightTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Rule
    public ActivityTestRule<HeightScreen> heightActivity = new ActivityTestRule<HeightScreen>(HeightScreen.class);

    @Test
    public void testSaveHeight() {

        heightActivity.getActivity().saveHeight(0);
        int height = mainActivity.getActivity().getHeight();
        assertEquals(0,height);
        heightActivity.getActivity().saveHeight(72);
        height = mainActivity.getActivity().getHeight();
        assertEquals(72,height);

    }
}
