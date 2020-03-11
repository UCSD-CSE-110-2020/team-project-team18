package com.example.walk_walk_revolution2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;

import java.text.DecimalFormat;

public class RoutesScreen extends AppCompatActivity implements RouteInterface {
    public static final String FITNESS_SERVICE_KEY = "FITNESS_SERVICE_KEY";
    public static final String FIREBASE_SERVICE_KEY = "FIREBASE_SERVICE_KEY";
    public static final String STEPS_KEY = "STEPS_KEY";
    public static final String HEIGHT_KEY = "HEIGHT_KEY";
    public static final String TEST_KEY = "TEST_KEY";
    private int numSteps;
    private int testSteps;
    public int fakeHeight;
    private int steps;
    private Walk currentWalk;
    private String fitnessServiceKey;
    private String firebaseServiceKey;
    private DistanceCalculator calculator = new DistanceCalculator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes_screen);
        fitnessServiceKey = getIntent().getStringExtra(FITNESS_SERVICE_KEY);
        firebaseServiceKey = getIntent().getStringExtra(FIREBASE_SERVICE_KEY);
        fakeHeight = getIntent().getIntExtra(HEIGHT_KEY, 0);
        Button launchHomeScreen = (Button)findViewById(R.id.home_but_routes);
        Button launchTestScreen = (Button)findViewById(R.id.test_but_routes);
        Button launchNewRouteScreen = (Button)findViewById(R.id.addNewWalk);
        Button launchTeamRoutesScreen = (Button)findViewById(R.id.team_routes_but);


        numSteps = getIntent().getIntExtra(STEPS_KEY, 0);
        testSteps = getIntent().getIntExtra(TEST_KEY, 0);
        System.out.println("numSteps: " + numSteps);
        System.out.println("testSteps: " + testSteps);
        launchHomeScreen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                launchHome();
            }
        });
        launchTestScreen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                launchTest();
            }
        });
        launchTeamRoutesScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchTeamWalkScreen();
            }
        });

        launchNewRouteScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchNewRouteScreen();
            }
        });

        // Lookup the recyclerview in activity layout
        RecyclerView rvRoutes = (RecyclerView) findViewById(R.id.rvRoutes);

        ArrayList<RouteItem> listItems = new ArrayList<RouteItem>();

        SharedPreferences routeData = getSharedPreferences("numWalks", Context.MODE_PRIVATE);
        int totalNumWalks = routeData.getInt("totalWalks", 0);
        System.out.println(totalNumWalks);




        for(int i = 1; i <= totalNumWalks; i++) {
            String fileName = "walk_" + i;
            SharedPreferences routeInfo = getSharedPreferences(fileName, MODE_PRIVATE);
            String name = routeInfo.getString("name", "ERROR");
            String startPoint = routeInfo.getString("startPoint", "ERROR");
            int stepCount = routeInfo.getInt("stepCount", 0);
            float distance = routeInfo.getFloat("distance", 0.0f);
            String time = routeInfo.getString("time", "00:00:00");

            System.out.println(fileName);
            System.out.println(name);
            System.out.println(startPoint);
            System.out.println(stepCount);
            System.out.println(distance);

            RouteItem item = new RouteItem(fileName, name, startPoint, stepCount, distance, time, this);
            listItems.add(item);
        }


        // Create adapter passing in the sample user data
        RouteItemsAdapter adapter = new RouteItemsAdapter(listItems);
        // Attach the adapter to the recyclerview to populate items
        rvRoutes.setAdapter(adapter);
        // Set layout manager to position the items
        rvRoutes.setLayoutManager(new LinearLayoutManager(this));
        // That's all!

        currentWalk = getCurrentWalk();

    }

    public void launchHome(){
        System.out.println("numSteps: " + numSteps);
        System.out.println("testSteps: " + testSteps);
        Intent intent = new Intent(this, Home.class);
        intent.putExtra(Home.FITNESS_SERVICE_KEY, fitnessServiceKey);
        intent.putExtra(Home.FIREBASE_SERVICE_KEY, firebaseServiceKey);
        intent.putExtra(Home.HEIGHT_KEY, fakeHeight);
        intent.putExtra(Home.STEPS_KEY, numSteps);
        intent.putExtra(TEST_KEY, testSteps);
        saveCurrentWalk();
        startActivity(intent);
    }

    public void launchTest(){
        Intent intent = new Intent(this, TestScreen.class);
        intent.putExtra(Home.FITNESS_SERVICE_KEY, fitnessServiceKey);
        intent.putExtra(Home.FIREBASE_SERVICE_KEY, firebaseServiceKey);
        intent.putExtra(Home.HEIGHT_KEY, fakeHeight);
        intent.putExtra(Home.STEPS_KEY, numSteps);
        intent.putExtra(TEST_KEY, testSteps);

        saveCurrentWalk();
        startActivity(intent);
    }

    public void launchNewRouteScreen(){
        Intent intent = new Intent(this, NewRoute.class);
        intent.putExtra(Home.FITNESS_SERVICE_KEY, fitnessServiceKey);
        intent.putExtra(Home.FIREBASE_SERVICE_KEY, firebaseServiceKey);
        intent.putExtra(Home.HEIGHT_KEY, fakeHeight);
        intent.putExtra(Home.STEPS_KEY, numSteps);
        intent.putExtra(TEST_KEY, testSteps);

        startActivity(intent);
    }

    public void launchRouteDetails(String fileName) {
        Intent intent = new Intent(this, Route.class);
        intent.putExtra(Home.FITNESS_SERVICE_KEY, fitnessServiceKey);
        intent.putExtra(Home.FIREBASE_SERVICE_KEY, firebaseServiceKey);
        intent.putExtra(Home.HEIGHT_KEY, fakeHeight);
        intent.putExtra(Home.STEPS_KEY, numSteps);
        intent.putExtra(TEST_KEY, testSteps);
        intent.putExtra("fileName", fileName);
        startActivity(intent);
    }

    public void launchTeamWalkScreen(){
        Intent intent = new Intent(this, TeamRoutesScreen.class);

        intent.putExtra(Home.FITNESS_SERVICE_KEY, fitnessServiceKey);
        intent.putExtra(Home.FIREBASE_SERVICE_KEY, firebaseServiceKey);
        intent.putExtra(Home.HEIGHT_KEY, fakeHeight);
        intent.putExtra(Home.STEPS_KEY, numSteps);
        intent.putExtra(TEST_KEY, testSteps);

        startActivity(intent);


    }

    public void saveCurrentWalk(){
        SharedPreferences spfs = getSharedPreferences("current_walk", MODE_PRIVATE);
        SharedPreferences.Editor editor = spfs.edit();

        if(currentWalk == null){
            editor.putInt("current_walk_steps", -1);
            editor.putInt("current_test_steps", 0);
            editor.putLong("current_walk_time", 0L);
            double distanceTraveled = calculator.calculateDistanceUsingSteps(-1, getHeight());

            DecimalFormat df = new DecimalFormat("#.##");
            double result = Double.valueOf(df.format(distanceTraveled));
            editor.putString("current_walk_dist", Double.toString(result));
            editor.apply();
        }else {

            editor.putInt("current_walk_steps", steps);
            editor.putInt("current_test_steps", testSteps);
            editor.putLong("current_walk_time", currentWalk.getStartTime());
            double distanceTraveled = calculator.calculateDistanceUsingSteps(numSteps + testSteps, getHeight());

            DecimalFormat df = new DecimalFormat("#.##");
            double result = Double.valueOf(df.format(distanceTraveled));
            editor.putString("current_walk_dist", Double.toString(result));
            editor.apply();
        }
    }
    public Walk getCurrentWalk(){
        SharedPreferences spfs = getSharedPreferences("current_walk", MODE_PRIVATE);
        if(spfs.getInt("current_walk_steps", -1) == -1){
            return null;
        }
        else{
            steps = spfs.getInt("current_walk_steps", 0);

            long time = spfs.getLong("current_walk_time", 0L);
            String dist = spfs.getString("current_walk_dist", null);
            return new Walk(steps, dist, time);
        }
    }
    public int getHeight() {
        SharedPreferences spfs = getSharedPreferences("user_height", MODE_PRIVATE);
        if(fakeHeight == 0) {
            return spfs.getInt("userHeight", 0);
        }
        return fakeHeight;

    }
}
