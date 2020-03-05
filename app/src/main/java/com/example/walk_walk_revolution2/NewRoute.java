package com.example.walk_walk_revolution2;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;

public class NewRoute extends AppCompatActivity {
    public static final String FITNESS_SERVICE_KEY = "FITNESS_SERVICE_KEY";
    public static final String HEIGHT_KEY = "HEIGHT_KEY";
    public static final String PREF_FILE_NAME = "PrefFile";
    public static final String STEPS_KEY = "STEPS_KEY";
    public static final String TEST_KEY = "TEST_KEY";

    private static final String TAG = "NewRoutes";

    private int numSteps;
    private int test_start_steps;
    private int testSteps;

    private RadioGroup loopGroup;
    private RadioGroup flatGroup;
    private RadioGroup streetGroup;
    private RadioGroup surfaceGroup;
    private int start_steps;
    private RadioGroup difficultyGroup;
    private EditText notes;
    private Walk currentWalk;
    private String fitnessServiceKey;
    public int fakeHeight;
    private DistanceCalculator calculator = new DistanceCalculator();
    private EditText displayName;
    private EditText displayStartPoint;

    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_route);
        fitnessServiceKey = getIntent().getStringExtra(FITNESS_SERVICE_KEY);
        fakeHeight = getIntent().getIntExtra(HEIGHT_KEY, 0);
        displayName = (EditText) findViewById(R.id.inputName);
        displayStartPoint = (EditText) findViewById(R.id.inputStartPoint);

        numSteps = getIntent().getIntExtra(STEPS_KEY, 0);
        testSteps = getIntent().getIntExtra(TEST_KEY,0);

        displayName.setText(getIntent().getStringExtra("name"));
        displayStartPoint.setText(getIntent().getStringExtra("startPoint"));

        loopGroup = (RadioGroup)findViewById(R.id.groupLoop);

        loopGroup.clearCheck();

        loopGroup.setOnCheckedChangeListener(
                new RadioGroup
                        .OnCheckedChangeListener() {
                    @Override

                    // The flow will come here when
                    // any of the radio buttons in the radioGroup
                    // has been clicked

                    // Check which radio button has been clicked
                    public void onCheckedChanged(RadioGroup group,
                                                 int checkedId)
                    {

                        // Get the selected Radio Button
                        RadioButton radioButton1 = (RadioButton)group.findViewById(checkedId);
                    }
                });

        flatGroup = (RadioGroup)findViewById(R.id.groupFlat);

        flatGroup.clearCheck();

        flatGroup.setOnCheckedChangeListener(
                new RadioGroup
                        .OnCheckedChangeListener() {
                    @Override

                    // The flow will come here when
                    // any of the radio buttons in the radioGroup
                    // has been clicked

                    // Check which radio button has been clicked
                    public void onCheckedChanged(RadioGroup group,
                                                 int checkedId)
                    {

                        // Get the selected Radio Button
                        RadioButton radioButton2 = (RadioButton)group.findViewById(checkedId);
                    }
                });

        streetGroup = (RadioGroup)findViewById(R.id.groupStreet);
        streetGroup.clearCheck();

        streetGroup.setOnCheckedChangeListener(
                new RadioGroup
                        .OnCheckedChangeListener() {
                    @Override

                    // The flow will come here when
                    // any of the radio buttons in the radioGroup
                    // has been clicked

                    // Check which radio button has been clicked
                    public void onCheckedChanged(RadioGroup group,
                                                 int checkedId)
                    {

                        // Get the selected Radio Button
                        RadioButton radioButton3 = (RadioButton)group.findViewById(checkedId);
                    }
                });

        surfaceGroup = (RadioGroup)findViewById(R.id.groupSurface);

        surfaceGroup.clearCheck();

        surfaceGroup.setOnCheckedChangeListener(
                new RadioGroup
                        .OnCheckedChangeListener() {
                    @Override

                    // The flow will come here when
                    // any of the radio buttons in the radioGroup
                    // has been clicked

                    // Check which radio button has been clicked
                    public void onCheckedChanged(RadioGroup group,
                                                 int checkedId)
                    {

                        // Get the selected Radio Button
                        RadioButton radioButton4 = (RadioButton)group.findViewById(checkedId);
                    }
                });

        difficultyGroup= (RadioGroup)findViewById(R.id.groupDifficulty);

        difficultyGroup.clearCheck();

        difficultyGroup.setOnCheckedChangeListener(
                new RadioGroup
                        .OnCheckedChangeListener() {
                    @Override

                    // The flow will come here when
                    // any of the radio buttons in the radioGroup
                    // has been clicked

                    // Check which radio button has been clicked
                    public void onCheckedChanged(RadioGroup group,
                                                 int checkedId)
                    {

                        // Get the selected Radio Button
                        RadioButton radioButton5 = (RadioButton)group.findViewById(checkedId);
                    }
                });

        notes = (EditText)findViewById(R.id.inputNote);

        Button btnCancel = (Button) findViewById(R.id.cancel);
        Button btnSave = (Button) findViewById(R.id.save);

        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                alertDialog();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action();
            }
        });
        currentWalk = getCurrentWalk();
    }

    public void launchRoutes() {
        Intent intent = new Intent(this, RoutesScreen.class);
        intent.putExtra(Home.FITNESS_SERVICE_KEY, fitnessServiceKey);
        intent.putExtra(Home.HEIGHT_KEY, fakeHeight);
        intent.putExtra(Home.STEPS_KEY, numSteps);
        intent.putExtra(TEST_KEY, testSteps);
        startActivity(intent);
    }

    public void alertDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Are you sure to cancel?");
        dialog.setTitle("Confirmation:");
        dialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        cancelRoute();
                    }
                });
        dialog.setNegativeButton("NO",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

    public void cancelRoute() {
        loopGroup.clearCheck();
        flatGroup.clearCheck();
        streetGroup.clearCheck();
        surfaceGroup.clearCheck();
        difficultyGroup.clearCheck();


        SharedPreferences numWalkFile = getSharedPreferences("numWalks", MODE_PRIVATE);
        int totalWalks = numWalkFile.getInt("totalWalks", 0);

        SharedPreferences.Editor editor = numWalkFile.edit();

        if(totalWalks > 0) {
            totalWalks--;
            editor.putInt("totalWalks", totalWalks);
        }
        currentWalk = null;
        saveCurrentWalk();
        Intent intent = new Intent(this, Home.class);
        intent.putExtra(Home.FITNESS_SERVICE_KEY, fitnessServiceKey);
        intent.putExtra(Home.HEIGHT_KEY, fakeHeight);
        intent.putExtra(Home.STEPS_KEY, numSteps);
        intent.putExtra(Home.TEST_KEY, testSteps);
        startActivity(intent);
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

            editor.putInt("current_walk_steps", start_steps);
            editor.putInt("current_test_steps", test_start_steps);
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
            int steps = spfs.getInt("current_walk_steps", 0);
            int testSteps = spfs.getInt("current_test_steps", 0);
            start_steps = steps;
            test_start_steps = testSteps;
            long time = spfs.getLong("current_walk_time", 0L);
            String dist = spfs.getString("current_walk_dist", null);
            return new Walk(steps, dist, time);
        }
    }
    public void action(){
        int loop = 0;
        int flat = 0;
        int street = 0;
        int surface = 0;
        int difficulty = 0;


        int selectedId = loopGroup.getCheckedRadioButtonId();
        if (selectedId == -1) {
            loop = 0;
        } else if(selectedId == R.id.loop){
            loop = 1;
        } else if(selectedId == R.id.outnback) {
            loop = 2;
        }

        selectedId = flatGroup.getCheckedRadioButtonId();

        if (selectedId == -1) {
            flat = 0;
        } else if(selectedId == R.id.flat){
            flat = 1;
        } else if(selectedId == R.id.hilly) {
            flat = 2;
        }

        selectedId = streetGroup.getCheckedRadioButtonId();

        if (selectedId == -1) {
            street = 0;
        } else if(selectedId == R.id.street){
            street = 1;
        } else if(selectedId == R.id.trail) {
            street = 2;
        }

        selectedId = surfaceGroup.getCheckedRadioButtonId();

        if (selectedId == -1) {
            surface = 0;
        } else if(selectedId == R.id.evensurface){
            surface = 1;
        } else if(selectedId == R.id.unevensurface) {
            surface = 2;
        }

        selectedId = difficultyGroup.getCheckedRadioButtonId();

        if (selectedId == -1) {
            difficulty = 0;
        } else if(selectedId == R.id.easy){
            difficulty = 1;
        } else if(selectedId == R.id.moderate) {
            difficulty = 2;
        } else if(selectedId == R.id.difficult) {
            difficulty = 3;
        }
        String note = notes.getText().toString();

        SharedPreferences.Editor editor;

        SharedPreferences numWalkFile = getSharedPreferences("numWalks", MODE_PRIVATE);
        int totalWalks = numWalkFile.getInt("totalWalks", 0);
        totalWalks++;

        editor = numWalkFile.edit();
        editor.putInt("totalWalks", totalWalks);
        editor.apply();

        String newWalkFile = "walk_" + totalWalks;
        System.out.println(newWalkFile);
        int step = getIntent().getIntExtra("stepCount", 0);

        String name = displayName.getText().toString();
        String startPoint = displayStartPoint.getText().toString();
        SharedPreferences preferences = getSharedPreferences(newWalkFile, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString("name", name);
        editor.putString("startPoint", startPoint);
        editor.putInt("loop", loop);
        editor.putInt("flat", flat);
        editor.putInt("street", street);
        editor.putInt("surface", surface);
        editor.putInt("difficulty", difficulty);
        editor.putInt("stepCount", step);
        String distance = getIntent().getStringExtra("distance");
        double distance_double = 0.0f;
        if(distance == null) {
            editor.putFloat("distance", 0.0f);
        } else {
            distance_double = Float.parseFloat(distance);
            editor.putFloat("distance", Float.parseFloat(distance));

        }
        String time = getIntent().getStringExtra("time");
        editor.putString("time", time);
        editor.putString("notes", note);
        editor.apply();


        db = FirebaseFirestore.getInstance();

        //TODO: Create RouteItem from data
        RouteItem item = new RouteItem(name, startPoint, loop, flat, street, surface, difficulty, step, distance_double, time, note);
        storeRoute(item);

        launchRoutes();
    }
    public int getHeight() {
        SharedPreferences spfs = getSharedPreferences("user_height", MODE_PRIVATE);
        if(fakeHeight == 0) {
            return spfs.getInt("userHeight", 0);
        }
        return fakeHeight;

    }

    public void storeRoute(RouteItem item) {
        SharedPreferences pref = getSharedPreferences("user_email", MODE_PRIVATE);
        String email = pref.getString("userEmail", "");

        db.collection("users").document(email).collection("routes")
                .add(item)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }
}