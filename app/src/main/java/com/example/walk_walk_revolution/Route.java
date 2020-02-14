package com.example.walk_walk_revolution;

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
    //private String name;
    //private String startPoint;
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
        String name = sharedPreferences.getString("name", "");
        String startPoint = sharedPreferences.getString("startPoint", "");
        boolean loop = sharedPreferences.getBoolean("loop", false);
        boolean flat = sharedPreferences.getBoolean("flat", false);
        boolean street = sharedPreferences.getBoolean("street", false);
        boolean evenSurface = sharedPreferences.getBoolean("evenSurface", false);
        int difficulty = sharedPreferences.getInt("difficulty", 1);
        String note = sharedPreferences.getString("notes", "");

        displayName.setText("Name: " + name);
        displayStartPoint.setText("Starting Point: " + startPoint);

        TextView notes = (TextView)findViewById(R.id.txtNote);
        notes.setText(note);

        RadioButton radioButtonSelected;

        int checkedId;
        //display content of radio buttons
        if (loop) {
            checkedId = R.id.loop;
        } else {
            checkedId = R.id.outnback;
        }
        radioButtonSelected = (RadioButton)loopGroup.findViewById(checkedId);
        radioButtonSelected.setChecked(true);

        if (flat) {
            checkedId = R.id.flat;
        } else {
            checkedId = R.id.hilly;
        }
        radioButtonSelected = (RadioButton)flatGroup.findViewById(checkedId);
        radioButtonSelected.setChecked(true);

        if (street) {
            checkedId = R.id.street;
        } else {
            checkedId = R.id.trail;
        }
        radioButtonSelected = (RadioButton)streetGroup.findViewById(checkedId);
        radioButtonSelected.setChecked(true);

        if (evenSurface) {
            checkedId = R.id.evensurface;
        } else {
            checkedId = R.id.unevensurface;
        }
        radioButtonSelected = (RadioButton)surfaceGroup.findViewById(checkedId);
        radioButtonSelected.setChecked(true);

        if (difficulty == 1) {
            checkedId = R.id.easy;
        } else if(difficulty == 2){
            checkedId = R.id.moderate;
        } else {
            checkedId = R.id.difficult;
        }
        radioButtonSelected = (RadioButton)difficultyGroup.findViewById(checkedId);
        radioButtonSelected.setChecked(true);


        Button btnCancel = (Button) findViewById(R.id.cancel);
        Button btnStartWalk = (Button) findViewById(R.id.startWalk);

        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                launchRoutes();
            }
        });

        btnStartWalk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                launchWalk();
            }
        });
    }

    public void launchRoutes() {
        Intent intent = new Intent(this, RoutesScreen.class);
        startActivity(intent);
    }

    public void launchWalk() {
        Intent intent = new Intent(this, RoutesScreen.class);
        //intent.putExtra("name", name);
        //intent.putExtra("startPoint", startPoint);
        startActivity(intent);
    }
}