package com.example.walk_walk_revolution;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;

public class RoutesScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes_screen);

        Button launchHomeScreen = (Button)findViewById(R.id.home_but_routes);
        Button launchTestScreen = (Button)findViewById(R.id.test_but_routes);
        Button launchNewRouteScreen = (Button)findViewById(R.id.addNewWalk);

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
        launchNewRouteScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchNewRouteScreen();
            }
        });

        // Lookup the recyclerview in activity layout
        RecyclerView rvRoutes = (RecyclerView) findViewById(R.id.rvRoutes);

        ArrayList<RouteItem> listItems = new ArrayList<RouteItem>();

        SharedPreferences routeData = getSharedPreferences("numWalks", Context.MODE_PRIVATE);
        int totalNumWalks = routeData.getInt("totalWalks", 0);
        System.out.println(totalNumWalks);




        for(int i = 1; i <= totalNumWalks; i++) {
            String fileName = "walk_" + i;
            SharedPreferences routeInfo = getSharedPreferences(fileName, MODE_PRIVATE);
            String name = routeInfo.getString("name", "ERROR");
            String startPoint = routeInfo.getString("startPoint", "ERROR");
            int stepCount = routeInfo.getInt("stepCount", 0);
            float distance = routeInfo.getFloat("distance", 1.4f);
            String time = routeInfo.getString("time", "00:00:00");

            System.out.println(fileName);
            System.out.println(name);
            System.out.println(startPoint);
            System.out.println(stepCount);
            System.out.println(distance);

            RouteItem item = new RouteItem(fileName, name, startPoint, stepCount, distance, time, this);
            listItems.add(item);
        }


        // Create adapter passing in the sample user data
        RouteItemsAdapter adapter = new RouteItemsAdapter(listItems);
        // Attach the adapter to the recyclerview to populate items
        rvRoutes.setAdapter(adapter);
        // Set layout manager to position the items
        rvRoutes.setLayoutManager(new LinearLayoutManager(this));
        // That's all!
    }

    public void launchHome(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void launchTest(){
        Intent intent = new Intent(this, TestScreen.class);
        startActivity(intent);
    }

    public void launchNewRouteScreen(){
        Intent intent = new Intent(this, NewRoute.class);
        startActivity(intent);
    }

    public void launchRouteDetails(String fileName){
        System.out.println("0.5");
        Intent intent = new Intent(this, Route.class);
        System.out.println("0.6");
        intent.putExtra("fileName", fileName);
        System.out.println("0");
        startActivity(intent);
        System.out.println("1");
    }
}
