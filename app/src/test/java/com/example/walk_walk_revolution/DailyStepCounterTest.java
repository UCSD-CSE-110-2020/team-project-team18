package com.example.walk_walk_revolution;


import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import org.apache.tools.ant.Main;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import static com.google.common.truth.Truth.assertThat;


@RunWith(AndroidJUnit4.class)
public class DailyStepCounterTest {
    private static final String TEST_SERVICE = "TEST_SERVICE";
    private long nextStepCount = 1398;

    private MainActivity activity;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(MainActivity.class).create().resume().get();
        activity.setStepCount(nextStepCount);
    }

    @Test
    public void testStepCounter() {
        TextView textSteps = activity.findViewById(R.id.CurrentSteps);
        assertThat(textSteps.getText().toString()).isEqualTo(String.valueOf(nextStepCount));
    }

    private class TestFitnessService implements FitnessService {
        private static final String TAG = "[TestFitnessService]: ";
        private MainActivity stepActivity;

        public TestFitnessService(MainActivity stepActivity) {
            this.stepActivity = stepActivity;
        }

        @Override
        public int getRequestCode() {
            return 0;
        }

        @Override
        public void setup() {
            System.out.println(TAG + "setup");
        }

        @Override
        public void updateStepCount() {
            System.out.println(TAG + "updateStepCount");
            stepActivity.setStepCount(nextStepCount);
        }
    }

}
