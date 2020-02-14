package com.example.walk_walk_revolution;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        final EditText displayName = (EditText) findViewById(R.id.txtName);
        final EditText displayStartPoint = (EditText) findViewById(R.id.txtStartPoint);


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

        notes = (EditText)findViewById(R.id.txtNote);

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
            public void onClick(View v) {
                boolean loop, flat, street, surface;
                int difficulty;

                int selectedId = loopGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    loop = false;
                } else {
                    loop = true;
                }

                selectedId = flatGroup.getCheckedRadioButtonId();

                if (selectedId == -1) {
                    flat = false;
                } else {
                    flat = true;
                }

                selectedId = streetGroup.getCheckedRadioButtonId();

                if (selectedId == -1) {
                    street = false;
                } else {
                    street = true;
                }

                selectedId = surfaceGroup.getCheckedRadioButtonId();

                if (selectedId == -1) {
                    surface = false;
                } else {
                    surface = true;
                }

                selectedId = difficultyGroup.getCheckedRadioButtonId();

                if (selectedId == -1) {
                    easy = ;
                } else {
                    RadioButton radioButton = (RadioButton)radioGroup5.findViewById(selectedId);
                    easy = radioButton.getText().toString();
                }

                String note = notes.getText().toString();

                SharedPreferences.Editor editor;

                SharedPreferences numWalkFile = getSharedPreferences("numWalk", MODE_PRIVATE);
                int totalWalks = numWalkFile.getInt("totalWalks", 0);
                String newWalkFile = "walk_" + totalWalks;
                totalWalks++;

                editor = numWalkFile.edit();
                editor.putInt("totalWalks", totalWalks);

                SharedPreferences preferences = getSharedPreferences(newWalkFile, MODE_PRIVATE);
                editor = preferences.edit();
                editor.putString("name", displayName.getText().toString());
                editor.putString("startPoint", displayStartPoint.getText().toString());
                editor.putBoolean("loop", loop);
                editor.putBoolean("flat", flat);
                editor.putBoolean("street", street);
                editor.putBoolean("surface", surface);
                editor.putInt("difficulty", difficulty);
                editor.putString("notes", note);
                editor.apply();
                launchRoutes();
            }
        });
    }

    public void launchRoutes() {
        EditText name = findViewById(R.id.txtName);
        EditText startPoint = findViewById(R.id.txtStartPoint);
        Intent intent = new Intent(this, RoutesScreen.class);
        intent.putExtra("name", name.getText().toString());
        intent.putExtra("startPoint", startPoint.getText().toString());
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
        radioGroup1.clearCheck();
        radioGroup2.clearCheck();
        radioGroup3.clearCheck();
        radioGroup4.clearCheck();
        radioGroup5.clearCheck();
        Intent intent = new Intent(this, RoutesScreen.class);
        startActivity(intent);
    }
}
