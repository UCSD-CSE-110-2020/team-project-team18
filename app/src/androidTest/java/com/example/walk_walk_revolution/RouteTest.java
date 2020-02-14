package com.example.walk_walk_revolution;

import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static org.junit.Assert.*;

public class RouteTest extends RoutesScreen {
    private ArrayList<RouteItem> listItems;

    @Before
    public void initialize() {
        RecyclerView rvRoutes = (RecyclerView) findViewById(R.id.rvRoutes);

        // Initialize testFile
        SharedPreferences spfs = getSharedPreferences("route_test", MODE_PRIVATE);
        SharedPreferences.Editor editor = spfs.edit();

        editor.putString("name", "Test Name");
        editor.putString("startPoint", "Test Start");
        editor.putBoolean("loop", true);
        editor.putBoolean("flat", true);
        editor.putBoolean("street", false);
        editor.putBoolean("evenSurface", true);
        editor.putInt("difficulty", 2);
        editor.putString("notes", "Test Notes");

        editor.apply();
        //TESTING RV CREATION
        RouteItem route1 = new RouteItem("route_test", this);

        listItems = new ArrayList<RouteItem>();
        listItems.add(route1);

        // Create adapter passing in the sample user data
        RouteItemsAdapter adapter = new RouteItemsAdapter(listItems);
        // Attach the adapter to the recyclerview to populate items
        rvRoutes.setAdapter(adapter);
        // Set layout manager to position the items
        rvRoutes.setLayoutManager(new LinearLayoutManager(this));
    }

    @Test
    public void elementTest(){

    }

}