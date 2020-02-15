package com.example.walk_walk_revolution;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.view.View;

public class Home extends AppCompatActivity {
    public static final String FITNESS_SERVICE_KEY = "FITNESS_SERVICE_KEY";
    public static final String HEIGHT_KEY = "HEIGHT_KEY";
    private static final String TAG = "HomeScreen";
    private FitnessService fitnessService;
    private TextView textSteps;
    private TextView distanceTraveled;
    private int start_steps;
    private boolean endingWalk;
    private int fakeHeight;
    Walk currentWalk = null;
    Walk recentWalk = null;
    TextView recentWalkDist;
    TextView recentWalkSteps;
    TextView recentWalkTime;
    TextView nameDisplay;
    TextView startPointDisplay;
    TextView walkStarted;
    private int numSteps;
    String fitnessServiceKey;
    String fileName;
    private DistanceCalculator calculator = new DistanceCalculator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fitnessServiceKey = getIntent().getStringExtra(FITNESS_SERVICE_KEY);
        fitnessService = FitnessServiceFactory.create(fitnessServiceKey, this);
        fakeHeight = getIntent().getIntExtra(HEIGHT_KEY, 0);
        int heightNum = getHeight();
        if (heightNum <= 0 && fakeHeight == 0) {
            launchHeight();
        }

        Button launchRoutesScreen = (Button) findViewById(R.id.routes_but_home);
        Button launchTestScreen = (Button) findViewById(R.id.test_but_home);
        Button startWalkBut = (Button) findViewById(R.id.start_walk);
        Button endWalkBut = (Button) findViewById(R.id.end_walk);

        recentWalkDist = (TextView) findViewById(R.id.recentWalkDist);
        recentWalkSteps = (TextView) findViewById(R.id.recentWalkSteps);
        recentWalkTime = (TextView) findViewById(R.id.recentWalkTime);

        walkStarted = (TextView)findViewById(R.id.walkStarted);

        nameDisplay = (TextView)findViewById(R.id.nameDisplay);
        startPointDisplay = (TextView)findViewById(R.id.startPointDisplay);
        fileName = getIntent().getStringExtra("fileName");

        nameDisplay.setText(getIntent().getStringExtra("name"));
        startPointDisplay.setText(getIntent().getStringExtra("startPoint"));

        launchRoutesScreen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                launchRoutes();
            }
        });
        startWalkBut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startWalk();
            }
        });
        endWalkBut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                endWalk();

            }
        });
        launchTestScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchTest();
            }
        });


        textSteps = findViewById(R.id.CurrentSteps);
        distanceTraveled = findViewById(R.id.distanceTraveled);
        fitnessService.setup();
        recentWalk = getRecentWalk();
        if(recentWalk != null){
            recentWalkTime.setText(recentWalk.getTimeTaken());
            recentWalkDist.setText(Double.toString(recentWalk.getDistance()));
            recentWalkSteps.setText(String.valueOf(recentWalk.getSteps()));
            recentWalkDist.setVisibility(TextView.VISIBLE);
            recentWalkTime.setVisibility(TextView.VISIBLE);
            recentWalkSteps.setVisibility(TextView.VISIBLE);
        }
        currentWalk = getCurrentWalk();
        updateCounter();
        fitnessService.updateStepCount();
        if(fileName != null) {
            startWalk();
        }

    }

    public void launchHeight() {
        Intent intent = new Intent(this, HeightScreen.class);
        intent.putExtra(Home.FITNESS_SERVICE_KEY, fitnessServiceKey);
        intent.putExtra(Home.HEIGHT_KEY, fakeHeight);
        startActivity(intent);
    }

    public void launchRoutes() {
        Intent intent = new Intent(this, RoutesScreen.class);
        intent.putExtra(Home.FITNESS_SERVICE_KEY, fitnessServiceKey);
        intent.putExtra(Home.HEIGHT_KEY, fakeHeight);
        if(currentWalk != null){
            saveCurrentWalk();
        }
        startActivity(intent);
    }

    public void launchTest() {
        Intent intent = new Intent(this, TestScreen.class);
        intent.putExtra(Home.FITNESS_SERVICE_KEY, fitnessServiceKey);
        intent.putExtra(Home.HEIGHT_KEY, fakeHeight);
            saveCurrentWalk();
        startActivity(intent);
    }

    public void updateCounter(){
        ScheduledExecutorService task = Executors.newScheduledThreadPool(1);
        task.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                fitnessService.updateStepCount();
            }
        } , 0, 30, TimeUnit.SECONDS);
    }

    public int getHeight() {
        SharedPreferences spfs = getSharedPreferences("user_height", MODE_PRIVATE);
        if(fakeHeight == 0){
            return spfs.getInt("userHeight", 0);
        }
       return fakeHeight;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//       If authentication was required during google fit setup, this will be called after the user authenticates
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == fitnessService.getRequestCode()) {
                fitnessService.updateStepCount();
            }
        } else {
            Log.e(TAG, "ERROR, google fit result code: " + resultCode);
        }
    }

    public void setStepCount(long stepCount) {
        numSteps = (int) stepCount;
        textSteps.setText(String.valueOf(stepCount));
        if (endingWalk) {
            walkCleanup();
            endingWalk = false;
        }
    }

    public void setDistanceTraveled() {
        double distance = calculator.calculateDistanceTraveled(getHeight(), this);

        DecimalFormat df = new DecimalFormat("#.##");
        double result = Double.valueOf(df.format(distance));

        distanceTraveled.setText(String.valueOf(result) + " miles");
    }

    public void startWalk() {
        if (currentWalk == null && fitnessService != null) {

            walkStarted.setText("WALK IN PROGRESS");
            currentWalk = new Walk();
            currentWalk.startWalk();

            fitnessService.updateStepCount();
            start_steps = numSteps;
            endingWalk = false;
        } else if (currentWalk == null && fitnessService == null) {
            walkStarted.setText("WALK IN PROGRESS");
            currentWalk = new Walk();
            currentWalk.startWalk();
            start_steps = numSteps;
            endingWalk = false;
        }
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

                editor.putInt("current_walk_steps", start_steps);
                editor.putLong("current_walk_time", currentWalk.getStartTime());
                double distanceTraveled = calculator.calculateDistanceUsingSteps(numSteps, getHeight());

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
                 int steps = spfs.getInt("current_walk_steps", 0);
                 start_steps = steps;
                 long time = spfs.getLong("current_walk_time", 0L);
                 String dist = spfs.getString("current_walk_dist", null);
                 return new Walk(steps, dist, time);
            }
        }
        public void saveRecentWalk(){
            SharedPreferences spfs = getSharedPreferences("recent_walk", MODE_PRIVATE);
            SharedPreferences.Editor editor = spfs.edit();


            editor.putString("recent_walk_steps", recentWalkSteps.getText().toString());
            editor.putString("recent_walk_time", recentWalkTime.getText().toString());
            editor.putString("recent_walk_dist", recentWalkDist.getText().toString());
            editor.apply();
        }
        public Walk getRecentWalk(){
            SharedPreferences spfs = getSharedPreferences("recent_walk", MODE_PRIVATE);
            if(spfs.getString("recent_walk_steps", null) == null){
                return null;
            }
            else{
                String steps = spfs.getString("recent_walk_steps", null);
                String dist = spfs.getString("recent_walk_dist", null);
                String time = spfs.getString("recent_walk_time", null);
                return new Walk(steps, dist, time);
            }
        }
    public void endWalk() {
        if (currentWalk != null && fitnessService != null) {
            walkStarted.setText("");
            endingWalk = true;
            fitnessService.updateStepCount();

        } else if (currentWalk != null && fitnessService == null) {
            walkStarted.setText("");
            endingWalk = true;
            walkCleanup();
        }
    }
    public void setFakeHeight(int fakeHeight){
        this.fakeHeight = fakeHeight;
    }

    public void walkCleanup() {
        if (endingWalk == true) {
            int walkSteps = numSteps - start_steps;
            DistanceCalculator calculator = new DistanceCalculator();
            double distanceTraveled = calculator.calculateDistanceUsingSteps(walkSteps, getHeight());

            DecimalFormat df = new DecimalFormat("#.##");
            double result = Double.valueOf(df.format(distanceTraveled));
            currentWalk.setDistance(result);
            currentWalk.setSteps(walkSteps);
            currentWalk.endWalk();
            recentWalkTime.setText(currentWalk.getTimeTaken());
            recentWalkDist.setText(Double.toString(currentWalk.getDistance()));
            recentWalkSteps.setText(String.valueOf(walkSteps));
            recentWalkDist.setVisibility(TextView.VISIBLE);
            recentWalkTime.setVisibility(TextView.VISIBLE);
            recentWalkSteps.setVisibility(TextView.VISIBLE);
            currentWalk = null;
            saveRecentWalk();
            endingWalk = false;

            Intent intent = new Intent(this, NewRoute.class);
            intent.putExtra("stepCount", Integer.parseInt(recentWalkSteps.getText().toString()));
            System.out.println(recentWalkDist.getText().toString());
            intent.putExtra("distance", recentWalkDist.getText().toString());
            intent.putExtra("time", recentWalkTime.getText().toString());
            intent.putExtra(Home.FITNESS_SERVICE_KEY, fitnessServiceKey);
            intent.putExtra(Home.HEIGHT_KEY, fakeHeight);

            startActivity(intent);
        }
    }
}
