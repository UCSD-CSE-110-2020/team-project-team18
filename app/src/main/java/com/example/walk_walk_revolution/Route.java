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

public class Route extends AppCompatActivity {
    //private String name;
    //private String startPoint;
    private String fileName;
    private RadioGroup radioGroup1;
    private RadioGroup radioGroup2;
    private RadioGroup radioGroup3;
    private RadioGroup radioGroup4;
    private RadioGroup radioGroup5;
    //private EditText notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("2");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        TextView displayName = (TextView) findViewById(R.id.txtName);
        TextView displayStartPoint = (TextView) findViewById(R.id.txtStartPoint);

        radioGroup1 = (RadioGroup)findViewById(R.id.groupLoop);
        radioGroup2 = (RadioGroup)findViewById(R.id.groupFlat);
        radioGroup3 = (RadioGroup)findViewById(R.id.groupStreet);
        radioGroup4 = (RadioGroup)findViewById(R.id.groupSurface);
        radioGroup5 = (RadioGroup)findViewById(R.id.groupEasy);

        RadioButton radioButton1;
        RadioButton radioButton2;
        RadioButton radioButton3;
        RadioButton radioButton4;
        RadioButton radioButton5;

        fileName = getIntent().getStringExtra("fileName");
        System.out.println("3");
        //displayName.setText(getIntent().getStringExtra("name"));
        //displayStartPoint.setText(getIntent().getStringExtra("startPoint"));


        //name = displayName.getText().toString();
        //startPoint = displayStartPoint.getText().toString();

        int checkedId;

        SharedPreferences sharedPreferences = getSharedPreferences(fileName, MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        String startPoint = sharedPreferences.getString("startPoint", "");
        String loop = sharedPreferences.getString("loop", "");
        String flat = sharedPreferences.getString("flat", "");
        String street = sharedPreferences.getString("street", "");
        String surface = sharedPreferences.getString("surface", "");
        String easy = sharedPreferences.getString("easy", "");
        String note = sharedPreferences.getString("notes", "");

        displayName.setText(name);
        displayStartPoint.setText(startPoint);

        EditText notes = (EditText)findViewById(R.id.txtNote);
        notes.setText(note);

//        //display content of radio buttons
//        if (loop == "Loop") {
//            checkedId = 1;
//        } else {
//            checkedId = 2;
//        }
//
//        radioButton1 = (RadioButton)radioGroup1.findViewById(checkedId);
//        radioButton1.setChecked(true);
//
//        if (flat == "Flat") {
//            checkedId = 1;
//        } else {
//            checkedId = 2;
//        }
//
//        radioButton2 = (RadioButton)radioGroup2.findViewById(checkedId);
//        radioButton2.setChecked(true);
//
//        if (street == "Street") {
//            checkedId = 1;
//        } else {
//            checkedId = 2;
//        }
//
//        radioButton3 = (RadioButton)radioGroup3.findViewById(checkedId);
//        radioButton3.setChecked(true);
//
//        if (surface == "Even Surface") {
//            checkedId = 1;
//        } else {
//            checkedId = 2;
//        }
//
//        radioButton4 = (RadioButton)radioGroup4.findViewById(checkedId);
//        radioButton4.setChecked(true);
//
//        if (easy == "Easy") {
//            checkedId = 1;
//        } else if (easy == "Moderate"){
//            checkedId = 2;
//        } else {
//            checkedId = 3;
//        }
//
//        radioButton5 = (RadioButton)radioGroup5.findViewById(checkedId);
//        radioButton5.setChecked(true);

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