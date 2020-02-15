package com.example.walk_walk_revolution;

import android.util.Log;

import com.google.android.gms.fitness.Fitness;

import java.util.HashMap;
import java.util.Map;

public class FitnessServiceFactory {
    private static final String TAG = "[FitnessServiceFactory]";

    private static Map<String, BluePrint> blueprints = new HashMap<>();

    public static void put(String key, BluePrint bluePrint) {
        blueprints.put(key, bluePrint);
    }

    public static FitnessService create(String key, Home home) {
        Log.i(TAG, String.format("creating FitnessService with key %s", key));
        return blueprints.get(key).create(home);
    }

    public interface BluePrint {
        FitnessService create(Home home);
    }
}
