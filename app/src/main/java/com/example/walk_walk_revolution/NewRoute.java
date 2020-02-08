package com.example.walk_walk_revolution;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class NewRoute extends AppCompatActivity {

    public static final String PREF_FILE_NAME = "PrefFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        final EditText displayName = (EditText) findViewById(R.id.txtName);
        final EditText displayStartPoint = (EditText) findViewById(R.id.txtStartPoint);

        displayName.setText(getIntent().getStringExtra("name"));
        displayStartPoint.setText(getIntent().getStringExtra("startPoint"));


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
                SharedPreferences preferences = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor;
                editor = preferences.edit();
                editor.putString("name", displayName.getText().toString());
                editor.putString("startPoint", displayStartPoint.getText().toString());
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
        Intent intent = new Intent(this, RoutesScreen.class);
        startActivity(intent);
    }
}
