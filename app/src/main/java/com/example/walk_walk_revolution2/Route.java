package com.example.walk_walk_revolution2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class Route extends AppCompatActivity {
    public static final String FITNESS_SERVICE_KEY = "FITNESS_SERVICE_KEY";
    public static final String FIREBASE_SERVICE_KEY = "FIREBASE_SERVICE_KEY";
    public static final String HEIGHT_KEY = "HEIGHT_KEY";
    public static final String STEPS_KEY = "STEPS_KEY";
    public static final String TEST_KEY = "TEST_KEY";
    private int numSteps;
    private int testSteps;
    private String fitnessServiceKey;
    private String firebaseServiceKey;
    public int fakeHeight;
    private String name;
    private String startPoint;
    private String fileName;
    private RadioGroup loopGroup;
    private RadioGroup flatGroup;
    private RadioGroup streetGroup;
    private RadioGroup surfaceGroup;
    private RadioGroup difficultyGroup;
    //private EditText notes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("2");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        fitnessServiceKey = getIntent().getStringExtra(FITNESS_SERVICE_KEY);
        firebaseServiceKey = getIntent().getStringExtra(FIREBASE_SERVICE_KEY);
        fakeHeight = getIntent().getIntExtra(HEIGHT_KEY, 0);

        numSteps = getIntent().getIntExtra(STEPS_KEY, 0);
        testSteps = getIntent().getIntExtra(TEST_KEY, 0);

        TextView displayName = (TextView) findViewById(R.id.textName);
        TextView displayStartPoint = (TextView) findViewById(R.id.textStartPoint);

        loopGroup = (RadioGroup)findViewById(R.id.groupLoop);
        flatGroup = (RadioGroup)findViewById(R.id.groupFlat);
        streetGroup = (RadioGroup)findViewById(R.id.groupStreet);
        surfaceGroup = (RadioGroup)findViewById(R.id.groupSurface);
        difficultyGroup = (RadioGroup)findViewById(R.id.groupDifficulty);

        fileName = getIntent().getStringExtra("fileName");
        //System.out.println("3");
        //displayName.setText(getIntent().getStringExtra("name"));
        //displayStartPoint.setText(getIntent().getStringExtra("startPoint"));


        //name = displayName.getText().toString();
        //startPoint = displayStartPoint.getText().toString();



        SharedPreferences sharedPreferences = getSharedPreferences(fileName, MODE_PRIVATE);
        this.name = sharedPreferences.getString("name", "");
        this.startPoint = sharedPreferences.getString("startPoint", "");
        int loop = sharedPreferences.getInt("loop", 0);
        int flat = sharedPreferences.getInt("flat", 0);
        int street = sharedPreferences.getInt("street", 0);
        int evenSurface = sharedPreferences.getInt("surface", 0);
        int difficulty = sharedPreferences.getInt("difficulty", 0);
        String note = sharedPreferences.getString("notes", "");

        displayName.setText("Name: " + name);
        displayStartPoint.setText("Starting Point: " + startPoint);

        TextView notes = (TextView)findViewById(R.id.txtNote);
        notes.setText(note);

        RadioButton radioButtonSelected;

        int checkedId;
        //display content of radio buttons
        if (loop == 1) {
            checkedId = R.id.loop;
            radioButtonSelected = (RadioButton)loopGroup.findViewById(checkedId);
            radioButtonSelected.setChecked(true);
        } else if (loop == 2) {
            checkedId = R.id.outnback;
            radioButtonSelected = (RadioButton)loopGroup.findViewById(checkedId);
            radioButtonSelected.setChecked(true);
        }


        if (flat == 1) {
            checkedId = R.id.flat;
            radioButtonSelected = (RadioButton)flatGroup.findViewById(checkedId);
            radioButtonSelected.setChecked(true);
        } else if(flat == 2) {
            checkedId = R.id.hilly;
            radioButtonSelected = (RadioButton)flatGroup.findViewById(checkedId);
            radioButtonSelected.setChecked(true);
        }


        if (street == 1) {
            checkedId = R.id.street;
            radioButtonSelected = (RadioButton)streetGroup.findViewById(checkedId);
            radioButtonSelected.setChecked(true);
        } else if(street == 2){
            checkedId = R.id.trail;
            radioButtonSelected = (RadioButton)streetGroup.findViewById(checkedId);
            radioButtonSelected.setChecked(true);
        }

        if (evenSurface == 1) {
            checkedId = R.id.evensurface;
            radioButtonSelected = (RadioButton)surfaceGroup.findViewById(checkedId);
            radioButtonSelected.setChecked(true);
        } else if(evenSurface == 2){
            checkedId = R.id.unevensurface;
            radioButtonSelected = (RadioButton)surfaceGroup.findViewById(checkedId);
            radioButtonSelected.setChecked(true);
        }


        if (difficulty == 1) {
            checkedId = R.id.easy;
            radioButtonSelected = (RadioButton)difficultyGroup.findViewById(checkedId);
            radioButtonSelected.setChecked(true);
        } else if(difficulty == 2){
            checkedId = R.id.moderate;
            radioButtonSelected = (RadioButton)difficultyGroup.findViewById(checkedId);
            radioButtonSelected.setChecked(true);
        } else if (difficulty == 3){
            checkedId = R.id.difficult;
            radioButtonSelected = (RadioButton)difficultyGroup.findViewById(checkedId);
            radioButtonSelected.setChecked(true);
        }



        Button btnCancel = (Button) findViewById(R.id.cancel);
        Button btnStartWalk = (Button) findViewById(R.id.startWalk);
        Button btnProposeWalk = (Button) findViewById(R.id.propose_walk);

        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                launchRoutes();
            }
        });

        btnStartWalk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                launchWalk(name, startPoint);
            }
        });

        btnProposeWalk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                launchWalkInvite(name, startPoint);
            }
        });

    }

    public void launchRoutes() {
        Intent intent = new Intent(this, RoutesScreen.class);
        intent.putExtra(Home.FITNESS_SERVICE_KEY, fitnessServiceKey);
        intent.putExtra(Home.FIREBASE_SERVICE_KEY, firebaseServiceKey);
        intent.putExtra(Home.HEIGHT_KEY, fakeHeight);
        intent.putExtra(Home.STEPS_KEY, numSteps);
        intent.putExtra(Home.TEST_KEY, testSteps);

        startActivity(intent);
    }

    public void launchWalk(String name, String startPoint) {
        Intent intent = new Intent(this, Home.class);
        intent.putExtra(Home.FITNESS_SERVICE_KEY, fitnessServiceKey);
        intent.putExtra(Home.FIREBASE_SERVICE_KEY, firebaseServiceKey);
        intent.putExtra(Home.HEIGHT_KEY, fakeHeight);
        intent.putExtra(Home.STEPS_KEY, numSteps);
        intent.putExtra(Home.TEST_KEY, testSteps);
        intent.putExtra("name", name);
        intent.putExtra("startPoint", startPoint);
        intent.putExtra("fileName", fileName);
        startActivity(intent);
    }

    public void launchWalkInvite(String name, String startPoint) {
        Intent intent = new Intent(this, WalkInvite.class);
        intent.putExtra(Home.FITNESS_SERVICE_KEY, fitnessServiceKey);
        intent.putExtra(Home.HEIGHT_KEY, fakeHeight);
        intent.putExtra(Home.STEPS_KEY, numSteps);
        intent.putExtra(Home.TEST_KEY, testSteps);
        intent.putExtra("name", name);
        intent.putExtra("startPoint", startPoint);
        intent.putExtra("fileName", fileName);
        startActivity(intent);
    }
}