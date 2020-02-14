package com.example.walk_walk_revolution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.text.DecimalFormat;

public class RoutesScreen extends AppCompatActivity {
    public static final String FITNESS_SERVICE_KEY = "FITNESS_SERVICE_KEY";
    public static final String HEIGHT_KEY = "HEIGHT_KEY";
    public int fakeHeight;
    private int steps;
    private Walk currentWalk;
    private String fitnessServiceKey;
    private DistanceCalculator calculator = new DistanceCalculator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes_screen);
        fitnessServiceKey = getIntent().getStringExtra(FITNESS_SERVICE_KEY);
        fakeHeight = getIntent().getIntExtra(HEIGHT_KEY, 0);
        Button launchHomeScreen = (Button)findViewById(R.id.home_but_routes);
        Button launchTestScreen = (Button)findViewById(R.id.test_but_routes);

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
        currentWalk = getCurrentWalk();
    }

    public void launchHome(){
        Intent intent = new Intent(this, Home.class);
        intent.putExtra(Home.FITNESS_SERVICE_KEY, fitnessServiceKey);
        intent.putExtra(Home.HEIGHT_KEY, fakeHeight);
        saveCurrentWalk();
        startActivity(intent);
    }

    public void launchTest(){
        Intent intent = new Intent(this, TestScreen.class);
        intent.putExtra(Home.FITNESS_SERVICE_KEY, fitnessServiceKey);
        intent.putExtra(Home.HEIGHT_KEY, fakeHeight);
        saveCurrentWalk();
        startActivity(intent);
    }
    public void saveCurrentWalk(){
        SharedPreferences spfs = getSharedPreferences("current_walk", MODE_PRIVATE);
        SharedPreferences.Editor editor = spfs.edit();

        if(currentWalk == null){
            editor.putInt("current_walk_steps", -1);
            editor.putLong("current_walk_time", 0L);
            double distanceTraveled = calculator.calculateDistanceUsingSteps(-1, getHeight());

            DecimalFormat df = new DecimalFormat("#.##");
            double result = Double.valueOf(df.format(distanceTraveled));
            editor.putString("current_walk_dist", Double.toString(result));
            editor.apply();
        }else {
            editor.putInt("current_walk_steps", steps);
            editor.putLong("current_walk_time", currentWalk.getStartTime());
            double distanceTraveled = calculator.calculateDistanceUsingSteps(steps, getHeight());

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
