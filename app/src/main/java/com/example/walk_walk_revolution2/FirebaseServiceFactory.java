package com.example.walk_walk_revolution2;

import android.app.Activity;
import android.app.Service;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class FirebaseServiceFactory {
    private static final String TAG = "[FirebaseServiceFactory]";

    private static Map<String, BluePrint> blueprints = new HashMap<>();

    public static void put(String key, BluePrint bluePrint) {
        blueprints.put(key, bluePrint);
    }

    public static FirebaseService create(String key, Service service) {
        Log.i(TAG, String.format("creating FitnessService with key %s", key));
        return blueprints.get(key).create(service);
    }

    public interface BluePrint {
        FirebaseService create(Service service);
    }
}

