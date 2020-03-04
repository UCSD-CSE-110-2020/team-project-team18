package com.example.walk_walk_revolution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.core.ApiFunction;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TeamRoutesScreen extends AppCompatActivity implements RouteInterface {

    public static final String FITNESS_SERVICE_KEY = "FITNESS_SERVICE_KEY";
    public static final String STEPS_KEY = "STEPS_KEY";
    public static final String HEIGHT_KEY = "HEIGHT_KEY";
    public static final String TEST_KEY = "TEST_KEY";

    private static final String TAG = "TeamRouteScreen";

    private int numSteps;
    private int testSteps;
    public int fakeHeight;
    private int steps;
    private Walk currentWalk;
    private String fitnessServiceKey;
    private DistanceCalculator calculator = new DistanceCalculator();

    private FirebaseFirestore db;
    private List<QueryDocumentSnapshot> team_walk_list;

    private ArrayList<RouteItem> listItems;
    private String teamMemebers[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_routes_screen);

        numSteps = getIntent().getIntExtra(STEPS_KEY, 0);
        testSteps = getIntent().getIntExtra(TEST_KEY, 0);

        fitnessServiceKey = getIntent().getStringExtra(FITNESS_SERVICE_KEY);
        fakeHeight = getIntent().getIntExtra(HEIGHT_KEY, 0);
        Button launchMyRoutesScreen = (Button) findViewById(R.id.my_routes);


        launchMyRoutesScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchRoutes();
            }
        });


        // Lookup the recyclerview in activity layout
        RecyclerView rvRoutes = (RecyclerView) findViewById(R.id.team_rvRoutes);

        listItems = new ArrayList<RouteItem>();

        //TODO: PULL DOWN ALL STORED ROUTE INFO FROM FIREBASE

        db = FirebaseFirestore.getInstance();

        //TODO: change email
        db.collection("users").document("vhploc@gmail.com")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {

                                getTeamWalks( (String[]) document.get("teamMembers"));

                                Log.d(TAG, "DocumentSnapshot data: " + document.get("teamMembers"));

                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }

                    }
                });


        // Create adapter passing in the sample user data
        RouteItemsAdapter adapter = new RouteItemsAdapter(listItems);
        // Attach the adapter to the recyclerview to populate items
        rvRoutes.setAdapter(adapter);
        // Set layout manager to position the items
        rvRoutes.setLayoutManager(new LinearLayoutManager(this));
        // That's all!


        currentWalk = getCurrentWalk();

    }

    public void launchRoutes(){
        Intent intent = new Intent(this, RoutesScreen.class);

        intent.putExtra(Home.FITNESS_SERVICE_KEY, fitnessServiceKey);
        intent.putExtra(Home.HEIGHT_KEY, fakeHeight);
        intent.putExtra(Home.STEPS_KEY, numSteps);
        intent.putExtra(TEST_KEY, testSteps);

        saveCurrentWalk();
        startActivity(intent);

    }

    public void getTeamWalks(String[] memberList) {
        for(String memeber: memberList) {
            //TODO: change email
            db.collection("users").document("vhploc@gmail.com").collection("routes")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    listItems.add(document.toObject(RouteItem.class));
                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
    }

    public void saveCurrentWalk(){
        SharedPreferences spfs = getSharedPreferences("current_walk", MODE_PRIVATE);
        SharedPreferences.Editor editor = spfs.edit();

        if(currentWalk == null){
            editor.putInt("current_walk_steps", -1);
            editor.putInt("current_test_steps", 0);
            editor.putLong("current_walk_time", 0L);
            double distanceTraveled = calculator.calculateDistanceUsingSteps(-1, getHeight());

            DecimalFormat df = new DecimalFormat("#.##");
            double result = Double.valueOf(df.format(distanceTraveled));
            editor.putString("current_walk_dist", Double.toString(result));
            editor.apply();
        }else {

            editor.putInt("current_walk_steps", steps);
            editor.putInt("current_test_steps", testSteps);
            editor.putLong("current_walk_time", currentWalk.getStartTime());
            double distanceTraveled = calculator.calculateDistanceUsingSteps(numSteps + testSteps, getHeight());

            DecimalFormat df = new DecimalFormat("#.##");
            double result = Double.valueOf(df.format(distanceTraveled));
            editor.putString("current_walk_dist", Double.toString(result));
            editor.apply();
        }
    }

    public Walk getCurrentWalk(){
        SharedPreferences spfs = getSharedPreferences("current_walk", MODE_PRIVATE);
        if(spfs.getInt("current_walk_steps", -1) == -1){
            return null;
        }
        else{
            steps = spfs.getInt("current_walk_steps", 0);

            long time = spfs.getLong("current_walk_time", 0L);
            String dist = spfs.getString("current_walk_dist", null);
            return new Walk(steps, dist, time);
        }
    }
    public int getHeight() {
        SharedPreferences spfs = getSharedPreferences("user_height", MODE_PRIVATE);
        if(fakeHeight == 0) {
            return spfs.getInt("userHeight", 0);
        }
        return fakeHeight;

    }

    public void launchRouteDetails(String fileName) {
        Intent intent = new Intent(this, Route.class);
        intent.putExtra(Home.FITNESS_SERVICE_KEY, fitnessServiceKey);
        intent.putExtra(Home.HEIGHT_KEY, fakeHeight);
        intent.putExtra(Home.STEPS_KEY, numSteps);
        intent.putExtra(TEST_KEY, testSteps);
        intent.putExtra("fileName", fileName);
        startActivity(intent);
    }

}
