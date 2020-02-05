package com.example.walk_walk_revolution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TestScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_screen);
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

    }

    public void launchHome(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void launchRoutes(){
        Intent intent = new Intent(this, RoutesScreen.class);
        startActivity(intent);
    }
}
