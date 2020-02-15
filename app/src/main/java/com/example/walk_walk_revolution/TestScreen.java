package com.example.walk_walk_revolution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TestScreen extends AppCompatActivity {
    private final int STEP_500 = 500;
    private TextView currSteps;
    private TextView currTime;
    private EditText timeInput;
    private String fitnessServiceKey;
    public static final String FITNESS_SERVICE_KEY = "FITNESS_SERVICE_KEY";
    public static final String HEIGHT_KEY = "HEIGHT_KEY";
    public int fakeHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_screen);
        fitnessServiceKey = getIntent().getStringExtra(FITNESS_SERVICE_KEY);
        fakeHeight = getIntent().getIntExtra(HEIGHT_KEY, 0);
        // Screen switching task
        Button launchHomeScreen = (Button)findViewById(R.id.home_but_test);
        Button launchRoutesScreen = (Button)findViewById(R.id.routes_but_test);

        launchHomeScreen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                launchHome();
            }
        });
        launchRoutesScreen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                launchRoutes();
            }
        });


        // Add step counter
        currSteps = findViewById(R.id.curr_step);

        Button addMockStep = (Button)findViewById(R.id.step_but);
        addMockStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numSteps = Integer.parseInt(currSteps.getText().toString()) + STEP_500;
                currSteps.setText(Integer.toString(numSteps));
            }
        });

        // Add time counter
        currTime = (TextView)findViewById(R.id.curr_time);
        timeInput = (EditText)findViewById(R.id.time_input);

        Button submitTime = (Button)findViewById(R.id.submit_but);
        submitTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time = timeInput.getText().toString();

                if(!time.equals(""))
                    currTime.setText(time + " ms");

                timeInput.setText("");
            }
        });

    }

    public void launchHome(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Home.FITNESS_SERVICE_KEY, fitnessServiceKey);
        intent.putExtra(Home.HEIGHT_KEY, fakeHeight);
        startActivity(intent);
    }

    public void launchRoutes(){
        Intent intent = new Intent(this, RoutesScreen.class);
        intent.putExtra(Home.FITNESS_SERVICE_KEY, fitnessServiceKey);
        intent.putExtra(Home.HEIGHT_KEY, fakeHeight);
        startActivity(intent);
    }
}
