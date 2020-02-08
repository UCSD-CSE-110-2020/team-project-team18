package com.example.walk_walk_revolution;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private String fitnessServiceKey = "GOOGLE_FIT";
    public static final String TAG = "MainActivity";

    private TextView textSteps;
    private FitnessService fitnessService;
    private UpdateCounter runner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button launchRoutesScreen = (Button)findViewById(R.id.routes_but_home);
        Button launchTestScreen = (Button)findViewById(R.id.test_but_home);



        launchRoutesScreen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                launchRoutes();
            }
        });

        launchTestScreen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                launchTest();
            }
        });

        int heightNum = getHeight();
        if(heightNum <= 0){
            launchHeight();
        }


        // GOOGLE FIT

        textSteps = findViewById(R.id.CurrentSteps);
        runner = new UpdateCounter();

        FitnessServiceFactory.put(fitnessServiceKey, new FitnessServiceFactory.BluePrint() {
            @Override
            public FitnessService create(MainActivity activity) {
                return new GoogleFitAdapter(activity);
            }
        });

        fitnessService = FitnessServiceFactory.create(fitnessServiceKey, this);
        fitnessService.setup();
        runner.execute();
    }

    public void launchHeight(){
        Intent intent = new Intent(this, HeightScreen.class);
        startActivity(intent);
    }

    public void launchRoutes(){
        Intent intent = new Intent(this, RoutesScreen.class);
        startActivity(intent);
    }

    public void launchTest(){
        Intent intent = new Intent(this, TestScreen.class);
        startActivity(intent);
    }

    public int getHeight(){
        SharedPreferences spfs = getSharedPreferences("user_height", MODE_PRIVATE);
        return spfs.getInt("userHeight",0);
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
        textSteps.setText(String.valueOf(stepCount));
    }

    class UpdateCounter extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            while(!isCancelled())
            {
                try{
                    publishProgress();
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... text)
        {

            fitnessService.updateStepCount();
        }

    }

    public void setFitnessServiceKey(String fitnessServiceKey) {
        this.fitnessServiceKey = fitnessServiceKey;
    }



    // Display distant

    public void updateTextView()
    {
        //initializing a new DistanceCalculator object.
        DistanceCalculator calculator = new DistanceCalculator();
        double distanceTraveled = calculator.calculateDistanceTraveled(60, 2000);

        DecimalFormat df = new DecimalFormat("#.##");
        double result = Double.valueOf(df.format(distanceTraveled));

        TextView distance = (TextView) findViewById(R.id.distanceTraveled);
        distance.setText(String.valueOf(result) + " miles");
    }
}
