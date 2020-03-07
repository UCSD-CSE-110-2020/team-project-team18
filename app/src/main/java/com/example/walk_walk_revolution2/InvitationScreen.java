package com.example.walk_walk_revolution2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
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
    private FirebaseBoundService firebaseBoundService;
    private boolean isBound;
    EditText gmailInput;
    private String user_email;

    private FirebaseService firebaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation_screen);

        fitnessServiceKey = getIntent().getStringExtra(FITNESS_SERVICE_KEY);
        firebaseServiceKey = getIntent().getStringExtra(FIREBASE_SERVICE_KEY);

        fakeHeight = getIntent().getIntExtra(HEIGHT_KEY, 0);
        numSteps = getIntent().getIntExtra(STEPS_KEY, 0);
        testSteps = getIntent().getIntExtra(TEST_KEY, 0);

        Intent intent = new Intent(this, FirebaseBoundService.class);
        intent.putExtra(Home.FITNESS_SERVICE_KEY, fitnessServiceKey);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

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
    private ServiceConnection serviceConnection = new ServiceConnection(){
        @Override
        public void onServiceConnected(ComponentName name, IBinder service){
            FirebaseBoundService.LocalService localService = (FirebaseBoundService.LocalService)service;
            firebaseBoundService = localService.getService();
            isBound = true;

        }
        @Override
        public void onServiceDisconnected(ComponentName name){
            isBound = false;
        }
    };
    @Override
    protected void onDestroy(){
        if(isBound){
            unbindService(serviceConnection);
            isBound = false;
        }
        super.onDestroy();
    }
    public void inviteMember(){
        String gmailAddress = gmailInput.getText().toString();

        firebaseBoundService.firebaseService.sendInvite(gmailAddress);

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
