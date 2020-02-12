package com.example.walk_walk_revolution;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Route extends AppCompatActivity {
    private String name;
    private String startPoint;
    private String fileName;
    private RadioGroup radioGroup1;
    private RadioGroup radioGroup2;
    private RadioGroup radioGroup3;
    private RadioGroup radioGroup4;
    private RadioGroup radioGroup5;
    private EditText notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        TextView displayName = (TextView) findViewById(R.id.txtName);
        TextView displayStartPoint = (TextView) findViewById(R.id.txtStartPoint);

        displayName.setText(getIntent().getStringExtra("name"));
        displayStartPoint.setText(getIntent().getStringExtra("startPoint"));
        fileName = getIntent().getStringExtra("fileName");

        name = displayName.getText().toString();
        startPoint = displayStartPoint.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences(fileName, MODE_PRIVATE);
        String loop = sharedPreferences.getString("loop", "");
        String flat = sharedPreferences.getString("flat", "");
        String street = sharedPreferences.getString("street", "");
        String surface = sharedPreferences.getString("surface", "");
        String easy = sharedPreferences.getString("easy", "");
        String note = sharedPreferences.getString("notes", "");

        EditText notes = (EditText)findViewById(R.id.txtNote);
        notes.setText(note);

        //display content of radio buttons
        //if (loop)

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
