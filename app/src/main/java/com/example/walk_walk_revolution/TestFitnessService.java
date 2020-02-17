package com.example.walk_walk_revolution;

    public class TestFitnessService implements FitnessService {
        private static final String TAG = "[TestFitnessService]: ";
        private Home mainActivity;

        public TestFitnessService(Home mainActivity) {
            this.mainActivity = mainActivity;
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
           // mainActivity.currentWalk.setTime(0L);
       //     mainActivity.walkCleanup();
            // mainActivity.setStepCount(nextStepCount);
        }
    }
