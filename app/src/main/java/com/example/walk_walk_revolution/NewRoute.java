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

        final EditText displayName = (EditText) findViewById(R.id.txtName);
        final EditText displayStartPoint = (EditText) findViewById(R.id.txtStartPoint);


        displayName.setText(getIntent().getStringExtra("name"));
        displayStartPoint.setText(getIntent().getStringExtra("startPoint"));

        radioGroup1 = (RadioGroup)findViewById(R.id.groupLoop);

        radioGroup1.clearCheck();

        radioGroup1.setOnCheckedChangeListener(
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

        radioGroup2 = (RadioGroup)findViewById(R.id.groupFlat);

        radioGroup2.clearCheck();

        radioGroup2.setOnCheckedChangeListener(
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

        radioGroup3 = (RadioGroup)findViewById(R.id.groupStreet);

        radioGroup3.clearCheck();

        radioGroup3.setOnCheckedChangeListener(
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

        radioGroup4 = (RadioGroup)findViewById(R.id.groupSurface);

        radioGroup4.clearCheck();

        radioGroup4.setOnCheckedChangeListener(
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

        radioGroup5 = (RadioGroup)findViewById(R.id.groupEasy);

        radioGroup5.clearCheck();

        radioGroup5.setOnCheckedChangeListener(
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
                String loop, flat, street, surface, easy;
                int selectedId = radioGroup1.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    loop = "";
                } else {
                    RadioButton radioButton = (RadioButton)radioGroup1.findViewById(selectedId);
                    loop = radioButton.getText().toString();
                }

                selectedId = radioGroup2.getCheckedRadioButtonId();

                if (selectedId == -1) {
                    flat = "";
                } else {
                    RadioButton radioButton = (RadioButton)radioGroup2.findViewById(selectedId);
                    flat = radioButton.getText().toString();
                }

                selectedId = radioGroup3.getCheckedRadioButtonId();

                if (selectedId == -1) {
                    street = "";
                } else {
                    RadioButton radioButton = (RadioButton)radioGroup3.findViewById(selectedId);
                    street = radioButton.getText().toString();
                }

                selectedId = radioGroup4.getCheckedRadioButtonId();

                if (selectedId == -1) {
                    surface = "";
                } else {
                    RadioButton radioButton = (RadioButton)radioGroup4.findViewById(selectedId);
                    surface = radioButton.getText().toString();
                }

                selectedId = radioGroup5.getCheckedRadioButtonId();

                if (selectedId == -1) {
                    easy = "";
                } else {
                    RadioButton radioButton = (RadioButton)radioGroup5.findViewById(selectedId);
                    easy = radioButton.getText().toString();
                }

                String note = notes.getText().toString();

                SharedPreferences preferences = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor;
                editor = preferences.edit();
                editor.putString("name", displayName.getText().toString());
                editor.putString("startPoint", displayStartPoint.getText().toString());
                editor.putString("loop", loop);
                editor.putString("flat", flat);
                editor.putString("street", street);
                editor.putString("surface", surface);
                editor.putString("easy", easy);
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
