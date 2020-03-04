package com.example.walk_walk_revolution2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HeightScreen extends AppCompatActivity {
    public static final String FITNESS_SERVICE_KEY = "FITNESS_SERVICE_KEY";

    private String fitnessServiceKey;
    private int heightNum;
    private String email;
    private boolean heightDone = false;
    private boolean emailDone = false;
    private boolean firstNameDone = false;
    private boolean lastNameDone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.height_prompt);
        fitnessServiceKey = getIntent().getStringExtra(FITNESS_SERVICE_KEY);

       final Button submitButton = findViewById(R.id.submitButton);
      submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitHeight();
                submitEmail();
                submitFirstname();
                submitLastname();
           }
        });

    }

    public void submitHeight(){
        EditText userHeight = findViewById(R.id.userHeight);

         heightNum = Integer.parseInt(userHeight.getText().toString());
        if(heightNum <= 0) {
            Toast.makeText(HeightScreen.this, "Must Enter Height", Toast.LENGTH_SHORT).show();
            return;
        }

        saveHeight(heightNum);

      //  Toast.makeText(HeightScreen.this, "Saved Height", Toast.LENGTH_SHORT).show();
         heightDone = true;
         launchHome();
    }
    public void submitEmail(){
        EditText userEmail = findViewById(R.id.userEmail);

        email = userEmail.getText().toString();
        if(email.equals("")) {
            Toast.makeText(HeightScreen.this, "Must Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }

        saveEmail(email);

       // Toast.makeText(HeightScreen.this, "Saved Email", Toast.LENGTH_SHORT).show();
        emailDone = true;
        launchHome();
    }
    public void submitFirstname(){
        EditText firstName = findViewById(R.id.firstname);

        String firstname = firstName.getText().toString();
        if(firstname.equals("")) {
            Toast.makeText(HeightScreen.this, "Must Enter First Name", Toast.LENGTH_SHORT).show();
            return;
        }

        saveFirstName(firstname);

        //Toast.makeText(HeightScreen.this, "Saved First", Toast.LENGTH_SHORT).show();
        firstNameDone = true;
        launchHome();
    }
    public void submitLastname(){
        EditText lastName = findViewById(R.id.lastname);

        String lastname = lastName.getText().toString();
        if(lastname.equals("")) {
            Toast.makeText(HeightScreen.this, "Must Enter Last Name", Toast.LENGTH_SHORT).show();
            return;
        }

        saveLastName(lastname);

        //Toast.makeText(HeightScreen.this, "Saved First", Toast.LENGTH_SHORT).show();
        lastNameDone = true;
        launchHome();
    }
    public void saveHeight(int heightNum){
        SharedPreferences spfs = getSharedPreferences("user_height", MODE_PRIVATE);
        SharedPreferences.Editor editor = spfs.edit();


        editor.putInt("userHeight", heightNum);
        editor.apply();
    }
    public void saveEmail(String email){
        SharedPreferences spfs = getSharedPreferences("user_email", MODE_PRIVATE);
        SharedPreferences.Editor editor = spfs.edit();


        editor.putString("userEmail", email);
        editor.apply();
    }
    public void saveFirstName(String firstName){
        SharedPreferences spfs = getSharedPreferences("first_Name", MODE_PRIVATE);
        SharedPreferences.Editor editor = spfs.edit();


        editor.putString("firstName", firstName);
        editor.apply();
    }

    public void saveLastName(String lastName){
        SharedPreferences spfs = getSharedPreferences("last_Name", MODE_PRIVATE);
        SharedPreferences.Editor editor = spfs.edit();


        editor.putString("lastName", lastName);
        editor.apply();
    }
    public void launchHome(){
        if(heightDone && emailDone && firstNameDone && lastNameDone) {
            Intent intent = new Intent(this, Home.class);
            intent.putExtra(Home.FITNESS_SERVICE_KEY, fitnessServiceKey);
            startActivity(intent);
        }
    }
    public void setHeight(int height){

    }
}
