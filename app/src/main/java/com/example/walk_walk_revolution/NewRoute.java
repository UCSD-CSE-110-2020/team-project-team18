package com.example.walk_walk_revolution;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class NewRoute extends AppCompatActivity {

    public static final String PREF_FILE_NAME = "PrefFile";
    private RadioGroup loopGroup;
    private RadioGroup flatGroup;
    private RadioGroup streetGroup;
    private RadioGroup surfaceGroup;
    private RadioGroup difficultyGroup;
    private EditText notes;

    private EditText displayName;
    private EditText displayStartPoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_route);

        displayName = (EditText) findViewById(R.id.inputName);
        displayStartPoint = (EditText) findViewById(R.id.inputStartPoint);


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
    }

    public void launchRoutes() {
        Intent intent = new Intent(this, RoutesScreen.class);
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

        Intent intent = new Intent(this, RoutesScreen.class);
        startActivity(intent);
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
            loop = 1;
        } else if(selectedId == R.id.unevensurface) {
            loop = 2;
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

        SharedPreferences preferences = getSharedPreferences(newWalkFile, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString("name", displayName.getText().toString());
        editor.putString("startPoint", displayStartPoint.getText().toString());
        editor.putInt("loop", loop);
        editor.putInt("flat", flat);
        editor.putInt("street", street);
        editor.putInt("surface", surface);
        editor.putInt("difficulty", difficulty);
        editor.putInt("stepCount", getIntent().getIntExtra("stepCount", 0));
        editor.putFloat("distance", Float.parseFloat(getIntent().getStringExtra("distance")));
        editor.putString("time", getIntent().getStringExtra("time"));
        editor.putString("notes", note);
        editor.apply();
        launchRoutes();
    }
}
