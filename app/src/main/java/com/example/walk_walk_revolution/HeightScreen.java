package com.example.walk_walk_revolution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HeightScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.height_prompt);

        Button submitButton = findViewById(R.id.heightSubmitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveHeight();
            }
        });
    }

    public void saveHeight(){
        EditText userHeight = findViewById(R.id.userHeight);

        int heightNum = Integer.parseInt(userHeight.getText().toString());
        if(heightNum <= 0){
            Toast.makeText(HeightScreen.this, "Must Enter Height", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences spfs = getSharedPreferences("userHeight", MODE_PRIVATE);
        SharedPreferences.Editor editor = spfs.edit();


        editor.putInt("userHeight", heightNum);

        editor.apply();
        Toast.makeText(HeightScreen.this, "Saved Height", Toast.LENGTH_SHORT).show();
    }
}
