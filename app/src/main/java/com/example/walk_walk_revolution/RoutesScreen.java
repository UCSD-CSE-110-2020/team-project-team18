package com.example.walk_walk_revolution;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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


        // Lookup the recyclerview in activity layout
        RecyclerView rvRoutes = (RecyclerView) findViewById(R.id.rvRoutes);

        // Initialize contacts

        //TESTING RV CREATION
//        RouteItem route1 = new RouteItem("Current Route 0", "ucsd", 1000, 10.0);
//        RouteItem route2 = new RouteItem("Current Route 1", "ucsd 2.0", 100, 11.0);
//        RouteItem route3 = new RouteItem("Current Route 2", "ucsd 3.0", 10, 12.0);
//
//        ArrayList<RouteItem> listItems = new ArrayList<RouteItem>();
//        listItems.add(route1);
//        listItems.add(route2);
//        listItems.add(route3);
//        listItems.add(route3);
//        listItems.add(route3);
//        listItems.add(route3);
//        listItems.add(route3);
//        listItems.add(route3);
//        listItems.add(route3);
//        listItems.add(route3);
//        listItems.add(route3);
//        listItems.add(route3);
//
//        // Create adapter passing in the sample user data
//        RouteItemsAdapter adapter = new RouteItemsAdapter(listItems);
//        // Attach the adapter to the recyclerview to populate items
//        rvRoutes.setAdapter(adapter);
//        // Set layout manager to position the items
//        rvRoutes.setLayoutManager(new LinearLayoutManager(this));
//        // That's all!
    }

    public void launchHome(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void launchTest(){
        Intent intent = new Intent(this, TestScreen.class);
        startActivity(intent);
    }
}
