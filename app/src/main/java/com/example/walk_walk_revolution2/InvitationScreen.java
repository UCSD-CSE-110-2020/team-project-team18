package com.example.walk_walk_revolution2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InvitationScreen extends AppCompatActivity {
    public static final String FITNESS_SERVICE_KEY = "FITNESS_SERVICE_KEY";
    public static final String FIREBASE_SERVICE_KEY = "FIREBASE_SERVICE_KEY";
    public static final String STEPS_KEY = "STEPS_KEY";
    public static final String HEIGHT_KEY = "HEIGHT_KEY";
    public static final String TEST_KEY = "TEST_KEY";
    private int numSteps;
    private int testSteps;
    public int fakeHeight;
    private String firebaseServiceKey;
    private String fitnessServiceKey;

    EditText gmailInput;
    private String user_email;

    private FirebaseService firebaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation_screen);

        fitnessServiceKey = getIntent().getStringExtra(FITNESS_SERVICE_KEY);
        firebaseServiceKey = getIntent().getStringExtra(FIREBASE_SERVICE_KEY);
        firebaseService = FirebaseServiceFactory.create(firebaseServiceKey, this);

        fakeHeight = getIntent().getIntExtra(HEIGHT_KEY, 0);
        numSteps = getIntent().getIntExtra(STEPS_KEY, 0);
        testSteps = getIntent().getIntExtra(TEST_KEY, 0);

        SharedPreferences spfs = getSharedPreferences("user_email", MODE_PRIVATE);
        firebaseService.setup(user_email);

        gmailInput = (EditText)findViewById(R.id.gmail_input);

        Button inviteBut = (Button)findViewById(R.id.invite_but);
        Button cancelBut = (Button)findViewById(R.id.cancel_invite_but);


        inviteBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inviteMember();
            }
        });

        cancelBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchHome();
            }
        });
    }

    public void inviteMember(){
        String gmailAddress = gmailInput.getText().toString();

        // TODO: SEND TO FIREBASE STORAGE

        gmailInput.setText("");
    }

    public void launchHome(){
        Intent intent = new Intent(this, Home.class);
        intent.putExtra(Home.FITNESS_SERVICE_KEY, fitnessServiceKey);
        intent.putExtra(Home.FIREBASE_SERVICE_KEY, firebaseServiceKey);
        intent.putExtra(Home.HEIGHT_KEY, fakeHeight);
        intent.putExtra(Home.STEPS_KEY, numSteps);
        intent.putExtra(Home.TEST_KEY, testSteps);
        startActivity(intent);
    }

}
