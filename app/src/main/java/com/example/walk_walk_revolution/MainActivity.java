package com.example.walk_walk_revolution;

import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {
    private String fitnessServiceKey = "GOOGLE_FIT";
    private int height;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        height = 0;
        Button btnLogin = findViewById(R.id.loginButton);
        FitnessServiceFactory.put(fitnessServiceKey, new FitnessServiceFactory.BluePrint() {
            @Override
            public FitnessService create(Home home) {
                return new GoogleFitAdapter(home);
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                launchHomeScreen();
            }
        });
    }
    public void launchHomeScreen(){
        Intent intent = new Intent(this, Home.class);
        intent.putExtra(Home.FITNESS_SERVICE_KEY, fitnessServiceKey);
        intent.putExtra(Home.HEIGHT_KEY, height);
        startActivity(intent);
    }
    public void setFitnessServiceKey(String fitnessServiceKey){
        this.fitnessServiceKey = fitnessServiceKey;
        if(fitnessServiceKey == "TEST_SERVICE"){
            SharedPreferences spfs = getSharedPreferences("user_height", MODE_PRIVATE);
            SharedPreferences.Editor editor = spfs.edit();


            editor.putInt("userHeight", -1);
            editor.apply();

        }
    }
    public void setHeight(int height){
        this.height = height;
    }
}
/**private String fitnessServiceKey = "GOOGLE_FIT";
    public static final String TAG = "MainActivity";

    private TextView textSteps;
    private TextView distanceTraveled;
    private FitnessService fitnessService;
    //  private UpdateCounter runner;
    private int start_steps;
    private boolean endingWalk;
    Walk currentWalk = null;
    TextView recentWalkDist;
    TextView recentWalkSteps;
    TextView recentWalkTime;
    TextView nameDisplay;
    TextView startPointDisplay;
    TextView walkStarted;
    private int numSteps;
    private DistanceCalculator calculator = new DistanceCalculator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button launchRoutesScreen = (Button) findViewById(R.id.routes_but_home);
        Button launchTestScreen = (Button) findViewById(R.id.test_but_home);
        Button connectToGoogle = (Button) findViewById(R.id.googleConnectButton);
        Button startWalkBut = (Button) findViewById(R.id.start_walk);
        Button endWalkBut = (Button) findViewById(R.id.end_walk);
        Button btnUpdateSteps = (Button) findViewById(R.id.buttonUpdateSteps);
        recentWalkDist = (TextView) findViewById(R.id.recentWalkDist);
        recentWalkSteps = (TextView) findViewById(R.id.recentWalkSteps);
        recentWalkTime = (TextView) findViewById(R.id.recentWalkTime);

        walkStarted = (TextView)findViewById(R.id.walkStarted);

        nameDisplay = (TextView)findViewById(R.id.nameDisplay);
        startPointDisplay = (TextView)findViewById(R.id.startPointDisplay);
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
        btnUpdateSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fitnessService.updateStepCount();
            }
        });
        connectToGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               setup();
            }
        });
        int heightNum = getHeight();
        if (heightNum <= 0) {
            launchHeight();
        }


        // GOOGLE FIT

        textSteps = findViewById(R.id.CurrentSteps);
        distanceTraveled = findViewById(R.id.distanceTraveled);
        //  runner = new UpdateCounter();

        FitnessServiceFactory.put(fitnessServiceKey, new FitnessServiceFactory.BluePrint() {
            @Override
            public FitnessService create(MainActivity activity) {
                return new GoogleFitAdapter(activity);
            }
        });

<<<<<<< HEAD
        fitnessService = FitnessServiceFactory.create(fitnessServiceKey, this);
        fitnessService.setup();

        if(!nameDisplay.getText().equals("")) {
            startWalk();
        }
=======

>>>>>>> 700ccfa39d91c0e79ec5aa81495a599d9f3c3f1f
        //runner.execute();
    }

    public void launchHeight() {
        Intent intent = new Intent(this, HeightScreen.class);
        startActivity(intent);
    }

<<<<<<< HEAD

=======
    public void setup(){
        fitnessService = FitnessServiceFactory.create(fitnessServiceKey, this);
        fitnessService.setup();
    }
>>>>>>> 700ccfa39d91c0e79ec5aa81495a599d9f3c3f1f
    public void launchRoutes() {
        Intent intent = new Intent(this, RoutesScreen.class);
        startActivity(intent);
    }

    public void launchRoutesTest(){
        Intent intent = new Intent(this, ViewRouteDetail.class);
        startActivity(intent);
    }

    public void launchTest() {
        Intent intent = new Intent(this, TestScreen.class);
        startActivity(intent);
    }


    public int getHeight() {
        SharedPreferences spfs = getSharedPreferences("user_height", MODE_PRIVATE);
        return spfs.getInt("userHeight", 0);
    }

    // GOOGLE FIT

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
        if(endingWalk){
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

    // class UpdateCounter extends AsyncTask<String, String, String> {

    //   @Override
    //    protected String doInBackground(String... strings) {
    //      while (!isCancelled()) {
    //          try {
    //              publishProgress();
    //             Thread.sleep(1000);
    //        } catch (InterruptedException e) {
    //            e.printStackTrace();
    //          }
    //     }
    //     return null;
    //   }

    // @Override
    //    protected void onProgressUpdate(String... text) {

    //       fitnessService.updateStepCount();
    //   }

    //  }

    public void setFitnessServiceKey(String fitnessServiceKey) {
    this.fitnessServiceKey = fitnessServiceKey;
    }


    // Display distant


    public void startWalk() {
<<<<<<< HEAD
        if (currentWalk == null) {
            walkStarted.setText("WALK IN PROGRESS");
=======
        if (currentWalk == null && fitnessService != null) {
>>>>>>> 700ccfa39d91c0e79ec5aa81495a599d9f3c3f1f
            currentWalk = new Walk();
            currentWalk.startWalk();

            fitnessService.updateStepCount();

            start_steps = numSteps;
            endingWalk = false;
        }else if( currentWalk == null && fitnessService == null){
            currentWalk = new Walk();
            currentWalk.startWalk();
            start_steps = numSteps;
            endingWalk = false;
        }
    }

    public void endWalk() {
<<<<<<< HEAD
        if (currentWalk != null) {
            walkStarted.setText("");
=======
        if (currentWalk != null && fitnessService != null) {
            endingWalk = true;
>>>>>>> 700ccfa39d91c0e79ec5aa81495a599d9f3c3f1f
            fitnessService.updateStepCount();

        }else if( currentWalk!= null && fitnessService == null){
            endingWalk = true;
<<<<<<< HEAD


=======
            walkCleanup();
>>>>>>> 700ccfa39d91c0e79ec5aa81495a599d9f3c3f1f
        }
    }

    public void walkCleanup() {
        if (endingWalk == true ) {
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

            Intent intent = new Intent(this, NewRoute.class);
            intent.putExtra("stepCount", Integer.parseInt(recentWalkSteps.getText().toString()));
            System.out.println(recentWalkDist.getText().toString());
            intent.putExtra("distance", recentWalkDist.getText().toString());
            intent.putExtra("time", recentWalkTime.getText().toString());


            startActivity(intent);
        }
    }
}*/

