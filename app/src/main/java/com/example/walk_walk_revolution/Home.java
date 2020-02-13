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
    TextView recentWalkDist;
    TextView recentWalkSteps;
    TextView recentWalkTime;
    private int numSteps;
    String fitnessServiceKey;
    private DistanceCalculator calculator = new DistanceCalculator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fitnessServiceKey = getIntent().getStringExtra(FITNESS_SERVICE_KEY);
        fitnessService = FitnessServiceFactory.create(fitnessServiceKey, this);
        fakeHeight = getIntent().getIntExtra(HEIGHT_KEY, 0);
        int heightNum = getHeight();
        if (heightNum <= 0 || fakeHeight == 0) {
            launchHeight();
        }



        Button launchRoutesScreen = (Button) findViewById(R.id.routes_but_home);
        Button launchTestScreen = (Button) findViewById(R.id.test_but_home);
        Button startWalkBut = (Button) findViewById(R.id.start_walk);
        Button endWalkBut = (Button) findViewById(R.id.end_walk);
        Button btnUpdateSteps = (Button) findViewById(R.id.buttonUpdateSteps);
        recentWalkDist = (TextView) findViewById(R.id.recentWalkDist);
        recentWalkSteps = (TextView) findViewById(R.id.recentWalkSteps);
        recentWalkTime = (TextView) findViewById(R.id.recentWalkTime);

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
        btnUpdateSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fitnessService.updateStepCount();
            }
        });


        textSteps = findViewById(R.id.CurrentSteps);
        distanceTraveled = findViewById(R.id.distanceTraveled);
        fitnessService.setup();
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
        startActivity(intent);
    }

    public void launchTest() {
        Intent intent = new Intent(this, TestScreen.class);
        intent.putExtra(Home.FITNESS_SERVICE_KEY, fitnessServiceKey);
        intent.putExtra(Home.HEIGHT_KEY, fakeHeight);
        startActivity(intent);
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
            currentWalk = new Walk();
            currentWalk.startWalk();

            fitnessService.updateStepCount();

            start_steps = numSteps;
            endingWalk = false;
        } else if (currentWalk == null && fitnessService == null) {
            currentWalk = new Walk();
            currentWalk.startWalk();
            start_steps = numSteps;
            endingWalk = false;
        }
    }

    public void endWalk() {
        if (currentWalk != null && fitnessService != null) {
            endingWalk = true;
            fitnessService.updateStepCount();

        } else if (currentWalk != null && fitnessService == null) {
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
            endingWalk = false;
        }
    }
}
