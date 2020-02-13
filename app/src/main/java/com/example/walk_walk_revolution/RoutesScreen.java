package com.example.walk_walk_revolution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RoutesScreen extends AppCompatActivity {
    public static final String FITNESS_SERVICE_KEY = "FITNESS_SERVICE_KEY";
    public static final String HEIGHT_KEY = "HEIGHT_KEY";
    public int fakeHeight;
    private String fitnessServiceKey;
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

    }

    public void launchHome(){
        Intent intent = new Intent(this, Home.class);
        intent.putExtra(Home.FITNESS_SERVICE_KEY, fitnessServiceKey);
        intent.putExtra(Home.HEIGHT_KEY, fakeHeight);
        startActivity(intent);
    }

    public void launchTest(){
        Intent intent = new Intent(this, TestScreen.class);
        intent.putExtra(Home.FITNESS_SERVICE_KEY, fitnessServiceKey);
        intent.putExtra(Home.HEIGHT_KEY, fakeHeight);
        startActivity(intent);
    }
}
