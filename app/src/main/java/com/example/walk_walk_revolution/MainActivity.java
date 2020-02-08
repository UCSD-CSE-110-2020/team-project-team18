package com.example.walk_walk_revolution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

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
}
