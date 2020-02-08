package com.example.walk_walk_revolution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Walk currentWalk = null;
    TextView recentWalkDist;
    TextView recentWalkSteps;
    TextView recentWalkTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button launchRoutesScreen = (Button)findViewById(R.id.routes_but_home);
        Button launchTestScreen = (Button)findViewById(R.id.test_but_home);
        Button startWalkBut = (Button)findViewById(R.id.start_walk);
        Button endWalkBut = (Button)findViewById(R.id.end_walk);
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

    }

    public void launchRoutes(){
        Intent intent = new Intent(this, RoutesScreen.class);
        startActivity(intent);
    }

    public void launchTest(){
        Intent intent = new Intent(this, TestScreen.class);
        startActivity(intent);
    }

    public void startWalk(){
        if(currentWalk == null) {
            currentWalk = new Walk();
            currentWalk.startWalk();
        }
    }
    public void endWalk(){
        if(currentWalk != null){
            currentWalk.endWalk();
            recentWalkTime.setText(currentWalk.getTimeTaken());
            recentWalkDist.setText(Double.toString(currentWalk.getDistance()));
            recentWalkSteps.setText(String.valueOf(currentWalk.getSteps()));
            recentWalkDist.setVisibility(TextView.VISIBLE);
            recentWalkTime.setVisibility(TextView.VISIBLE);
            recentWalkSteps.setVisibility(TextView.VISIBLE);
            currentWalk = null;
        }
    }
}
