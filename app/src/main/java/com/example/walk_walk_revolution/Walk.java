package com.example.walk_walk_revolution;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Walk extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);

        EditText displayName = (EditText) findViewById(R.id.txtName);

        displayName.setText(getIntent().getStringExtra("name"));

        Button btnStopWalk = (Button) findViewById(R.id.stopWalk);

        btnStopWalk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                launchWalk();
            }
        });
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
