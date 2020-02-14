package com.example.walk_walk_revolution;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Route extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        TextView displayName = (TextView) findViewById(R.id.txtName);
        TextView displayStartPoint = (TextView) findViewById(R.id.txtStartPoint);

        displayName.setText(getIntent().getStringExtra("name"));
        displayStartPoint.setText(getIntent().getStringExtra("startPoint"));


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
        EditText name = findViewById(R.id.txtName);
        EditText startPoint = findViewById(R.id.txtStartPoint);
        Intent intent = new Intent(this, Walk.class);
        intent.putExtra("name", name.getText().toString());
        intent.putExtra("startPoint", startPoint.getText().toString());
        startActivity(intent);
    }
}
