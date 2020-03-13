package com.example.walk_walk_revolution2;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import javax.xml.parsers.SAXParser;

public class Home extends AppCompatActivity {
    public static final String FITNESS_SERVICE_KEY = "FITNESS_SERVICE_KEY";
    public static final String FIREBASE_SERVICE_KEY = "FIREBASE_SERVICE_KEY";
    public static final String HEIGHT_KEY = "HEIGHT_KEY";
    public static final String STEPS_KEY = "STEPS_KEY";
    private static final String TAG = "HomeScreen";
    public static final String TEST_KEY = "TEST_KEY";
    private int stepCount;
    private FitnessService fitnessService;
    String firebaseServiceKey;
    private FirebaseService firebaseService;
    private TextView textSteps;
    private TextView distanceTraveled;
    private int start_steps;
    private int test_start_steps;
    private boolean endingWalk;
    private int fakeHeight;
    Walk currentWalk = null;
    Walk recentWalk = null;
    TextView recentWalkDist;
    TextView recentWalkSteps;
    TextView recentWalkTime;
    TextView nameDisplay;
    TextView startPointDisplay;
    TextView walkStarted;
    private int numSteps;
    private int testSteps;
    String fitnessServiceKey;
    String fileName;
    FirebaseBoundService firebaseBoundService;
    boolean isBound;
    private FirebaseAdapter firebase;
    private DistanceCalculator calculator = new DistanceCalculator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fitnessServiceKey = getIntent().getStringExtra(FITNESS_SERVICE_KEY);
        System.out.println(fitnessServiceKey);
        fitnessService = FitnessServiceFactory.create(fitnessServiceKey, this);
        firebaseServiceKey = getIntent().getStringExtra(FIREBASE_SERVICE_KEY);
        //firebaseService = FirebaseServiceFactory.create(firebaseServiceKey, this);

        //firebaseBoundService.setup();
        fakeHeight = getIntent().getIntExtra(HEIGHT_KEY, 0);

        //int heightNum = getHeight();
       // if (heightNum <= 0 && fakeHeight == 0) {
         //   launchHeightAndEmailScreen();
        //}



        Button launchRoutesScreen = (Button) findViewById(R.id.routes_but_home);
        Button launchTestScreen = (Button) findViewById(R.id.test_but_home);
        final Button launchTeamScreen = (Button) findViewById(R.id.team_but_home);
        Button startWalkBut = (Button) findViewById(R.id.start_walk);
        Button endWalkBut = (Button) findViewById(R.id.end_walk);
        Button inviteTeamBut = (Button)findViewById(R.id.invite_team_but);
        Button launchProposedWalk = (Button) findViewById(R.id.proposed_walk);

        recentWalkDist = (TextView) findViewById(R.id.recentWalkDist);
        recentWalkSteps = (TextView) findViewById(R.id.recentWalkSteps);
        recentWalkTime = (TextView) findViewById(R.id.recentWalkTime);

        walkStarted = (TextView)findViewById(R.id.walkStarted);

        nameDisplay = (TextView)findViewById(R.id.nameDisplay);
        startPointDisplay = (TextView)findViewById(R.id.startPointDisplay);
        fileName = getIntent().getStringExtra("fileName");

        nameDisplay.setText(getIntent().getStringExtra("name"));
        startPointDisplay.setText(getIntent().getStringExtra("startPoint"));

        launchRoutesScreen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                launchRoutes();
            }
        });

        launchTeamScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchTeamScreen();
            }
        });

        startWalkBut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startWalk();
            }
        });
        endWalkBut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                endWalk();

            }
        });
        launchTestScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchTest();
            }
        });
        inviteTeamBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchInviteScreen();
            }
        });
        launchProposedWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchProposedWalkScreen();
            }
        });


        textSteps = findViewById(R.id.CurrentSteps);

        distanceTraveled = findViewById(R.id.distanceTraveled);
        fitnessService.setup();
        recentWalk = getRecentWalk();
        if(recentWalk != null){
            recentWalkTime.setText(recentWalk.getTimeTaken());
            recentWalkDist.setText(Double.toString(recentWalk.getDistance()));
            recentWalkSteps.setText(String.valueOf(recentWalk.getSteps()));
            recentWalkDist.setVisibility(TextView.VISIBLE);
            recentWalkTime.setVisibility(TextView.VISIBLE);
            recentWalkSteps.setVisibility(TextView.VISIBLE);
        }
        currentWalk = getCurrentWalk();


        updateCounter();
        fitnessService.updateStepCount();

        numSteps = getIntent().getIntExtra(STEPS_KEY, 0);
        testSteps = getIntent().getIntExtra(TEST_KEY, 0);
        System.out.println(numSteps);
        System.out.println(testSteps);
        setStepCount(numSteps);
        if(fileName != null) {
            startWalk();
        }




    }
    @Override
    public void onStart(){
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        String email = getEmail();
        String firstName = getFirstName();
        String lastName = getLastName();
        Intent intent = new Intent(this, FirebaseBoundService.class);
        intent.putExtra(Home.FIREBASE_SERVICE_KEY, firebaseServiceKey);
        intent.putExtra("email", email);
        intent.putExtra("firstName", firstName);
        intent.putExtra("lastName", lastName);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }
    private ServiceConnection serviceConnection = new ServiceConnection(){
        @Override
        public void onServiceConnected(ComponentName name, IBinder service){
            FirebaseBoundService.LocalService localService = (FirebaseBoundService.LocalService)service;
            firebaseBoundService = localService.getService();
            //firebaseBoundService.setup();
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
    public void launchHeightAndEmailScreen() {
        Intent intent = new Intent(this, HeightScreen.class);
        intent.putExtra(Home.FITNESS_SERVICE_KEY, fitnessServiceKey);
        intent.putExtra(Home.HEIGHT_KEY, fakeHeight);
        intent.putExtra(Home.STEPS_KEY, numSteps);
        intent.putExtra(Home.FIREBASE_SERVICE_KEY, firebaseServiceKey);
        startActivity(intent);
    }


    public void launchRoutes() {
        Intent intent = new Intent(this, RoutesScreen.class);
        intent.putExtra(Home.FITNESS_SERVICE_KEY, fitnessServiceKey);
        intent.putExtra(Home.HEIGHT_KEY, fakeHeight);
        intent.putExtra(Home.STEPS_KEY, numSteps);
        intent.putExtra(Home.TEST_KEY, testSteps);
        intent.putExtra(Home.FIREBASE_SERVICE_KEY, firebaseServiceKey);


        if(currentWalk != null){
            saveCurrentWalk();
        }
        startActivity(intent);
    }

    public void launchTeamScreen()
    {
        Intent intent = new Intent(this, TeamScreen.class);
        intent.putExtra(Home.FITNESS_SERVICE_KEY, fitnessServiceKey);
        intent.putExtra(Home.HEIGHT_KEY, fakeHeight);
        intent.putExtra(Home.STEPS_KEY, numSteps);
        intent.putExtra(Home.TEST_KEY, testSteps);
        intent.putExtra(Home.FIREBASE_SERVICE_KEY, firebaseServiceKey);
        startActivity(intent);
    }

    public void launchTest() {

        Intent intent = new Intent(this, TestScreen.class);
        intent.putExtra(Home.FITNESS_SERVICE_KEY, fitnessServiceKey);
        intent.putExtra(Home.HEIGHT_KEY, fakeHeight);

        intent.putExtra(Home.STEPS_KEY, numSteps);
        intent.putExtra(Home.TEST_KEY, testSteps);
        intent.putExtra(Home.FIREBASE_SERVICE_KEY, firebaseServiceKey);
            saveCurrentWalk();
        startActivity(intent);
    }

    public void launchInviteScreen(){
        Intent intent = new Intent(this, InvitationScreen.class);
        intent.putExtra(Home.FITNESS_SERVICE_KEY, fitnessServiceKey);
        intent.putExtra(Home.HEIGHT_KEY, fakeHeight);
        intent.putExtra(Home.STEPS_KEY, numSteps);
        intent.putExtra(Home.TEST_KEY, testSteps);
        intent.putExtra(Home.FIREBASE_SERVICE_KEY, firebaseServiceKey);
        startActivity(intent);


    }

    public void launchProposedWalkScreen(){
        Intent intent = new Intent(this, ProposedWalk.class);
        intent.putExtra(Home.FITNESS_SERVICE_KEY, fitnessServiceKey);
        intent.putExtra(Home.HEIGHT_KEY, fakeHeight);
        intent.putExtra(Home.STEPS_KEY, numSteps);
        intent.putExtra(Home.TEST_KEY, testSteps);
        intent.putExtra(Home.FIREBASE_SERVICE_KEY, firebaseServiceKey);
        startActivity(intent);
    }

    public void updateCounter(){
        ScheduledExecutorService task = Executors.newScheduledThreadPool(1);
        task.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                fitnessService.updateStepCount();
            }
        } , 0, 30, TimeUnit.SECONDS);
    }

    public int getHeight() {
        SharedPreferences spfs = getSharedPreferences("user_height", MODE_PRIVATE);
        if(fakeHeight == 0){
            return spfs.getInt("userHeight", 0);
        }
       return fakeHeight;
    }
    public String getEmail(){

        SharedPreferences spfs = getSharedPreferences("user_email", MODE_PRIVATE);
        return spfs.getString("userEmail", null);
    }
    public String getFirstName(){
        SharedPreferences spfs = getSharedPreferences("first_Name", MODE_PRIVATE);
        return spfs.getString("firstName", null);
    }
    public String getLastName(){
        SharedPreferences spfs = getSharedPreferences("last_Name", MODE_PRIVATE);
        return spfs.getString("lastName", null);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//       If authentication was required during google fit setup, this will be called after the user authenticates
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == fitnessService.getRequestCode()) {
                fitnessService.updateStepCount();
            }
        } else {
            Log.e(TAG, "ERROR, google fit result code: " + resultCode);
        }
    }

    public void setStepCount(long stepCount) {
        numSteps = (int) stepCount;


        textSteps.setText(String.valueOf(numSteps + testSteps));

        if (endingWalk) {
            walkCleanup();
            endingWalk = false;
        }
    }

    public void setDistanceTraveled() {
        double distance = calculator.calculateDistanceTraveled(getHeight(), this);

        DecimalFormat df = new DecimalFormat("#.##");
        double result = Double.valueOf(df.format(distance));

        distanceTraveled.setText(String.valueOf(result) + " miles");
    }

    public void startWalk() {
        if (currentWalk == null && fitnessService != null) {
            walkStarted.setText("WALK IN PROGRESS");

            currentWalk = new Walk();
            currentWalk.startWalk();

            fitnessService.updateStepCount();
            start_steps = numSteps;
            test_start_steps = testSteps;
            endingWalk = false;
        } else if (currentWalk == null && fitnessService == null) {
            walkStarted.setText("WALK IN PROGRESS");

            currentWalk = new Walk();
            currentWalk.startWalk();
            start_steps = numSteps;
            test_start_steps = testSteps;
            endingWalk = false;
        }
    }

    public void saveCurrentWalk(){
            SharedPreferences spfs = getSharedPreferences("current_walk", MODE_PRIVATE);
            SharedPreferences.Editor editor = spfs.edit();

            if(currentWalk == null){
                editor.putInt("current_walk_steps", -1);
                editor.putInt("current_test_steps", 0);
                editor.putLong("current_walk_time", 0L);
                double distanceTraveled = calculator.calculateDistanceUsingSteps(-1, getHeight());

                DecimalFormat df = new DecimalFormat("#.##");
                double result = Double.valueOf(df.format(distanceTraveled));
                editor.putString("current_walk_dist", Double.toString(result));
                editor.apply();
            }else {

                editor.putInt("current_walk_steps", start_steps);
                editor.putInt("current_test_steps", test_start_steps);
                editor.putLong("current_walk_time", currentWalk.getStartTime());
                double distanceTraveled = calculator.calculateDistanceUsingSteps(numSteps + testSteps, getHeight());

                DecimalFormat df = new DecimalFormat("#.##");
                double result = Double.valueOf(df.format(distanceTraveled));
                editor.putString("current_walk_dist", Double.toString(result));
                editor.apply();
            }
        }
        public Walk getCurrentWalk(){
            SharedPreferences spfs = getSharedPreferences("current_walk", MODE_PRIVATE);
            if(spfs.getInt("current_walk_steps", -1) == -1){
                return null;
            }
            else{
                 int steps = spfs.getInt("current_walk_steps", 0);
                 int testSteps = spfs.getInt("current_test_steps", 0);
                 start_steps = steps;
                 test_start_steps = testSteps;
                 long time = spfs.getLong("current_walk_time", 0L);
                 String dist = spfs.getString("current_walk_dist", null);
                 return new Walk(steps, dist, time);
            }
        }
        public void saveRecentWalk(){
            SharedPreferences spfs = getSharedPreferences("recent_walk", MODE_PRIVATE);
            SharedPreferences.Editor editor = spfs.edit();


            editor.putString("recent_walk_steps", recentWalkSteps.getText().toString());
            editor.putString("recent_walk_time", recentWalkTime.getText().toString());
            editor.putString("recent_walk_dist", recentWalkDist.getText().toString());
            editor.apply();
        }
        public Walk getRecentWalk(){
            SharedPreferences spfs = getSharedPreferences("recent_walk", MODE_PRIVATE);
            if(spfs.getString("recent_walk_steps", null) == null){
                return null;
            }
            else{
                String steps = spfs.getString("recent_walk_steps", null);
                String dist = spfs.getString("recent_walk_dist", null);
                String time = spfs.getString("recent_walk_time", null);
                return new Walk(steps, dist, time);
            }
        }
    public void endWalk() {
        if (currentWalk != null && fitnessService != null) {
            walkStarted.setText("");
            endingWalk = true;
            fitnessService.updateStepCount();
            if (endingWalk) {
                walkCleanup();
                endingWalk = false;
            }
        } else if (currentWalk != null && fitnessService == null) {
            walkStarted.setText("");
            endingWalk = true;
            walkCleanup();
        }
    }
    public void setFakeHeight(int fakeHeight){
        this.fakeHeight = fakeHeight;
    }

    public void walkCleanup() {
        if (endingWalk == true) {
            int walkSteps = numSteps + testSteps - start_steps - test_start_steps;
            DistanceCalculator calculator = new DistanceCalculator();
            double distanceTraveled = calculator.calculateDistanceUsingSteps(walkSteps, getHeight());

            DecimalFormat df = new DecimalFormat("#.##");
            double result = Double.valueOf(df.format(distanceTraveled));
            currentWalk.setDistance(result);
            currentWalk.setSteps(walkSteps);
            currentWalk.endWalk();
            recentWalkTime.setText(currentWalk.getTimeTaken());
            recentWalkDist.setText(Double.toString(currentWalk.getDistance()));
            recentWalkSteps.setText(String.valueOf(walkSteps));
            recentWalkDist.setVisibility(TextView.VISIBLE);
            recentWalkTime.setVisibility(TextView.VISIBLE);
            recentWalkSteps.setVisibility(TextView.VISIBLE);

            System.out.println(fileName);
            if(fileName != null) {
                SharedPreferences pref = getSharedPreferences(fileName, MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();

                editor.putInt("stepCount", walkSteps);
                editor.putFloat("distance", Float.parseFloat(recentWalkDist.getText().toString()));
                editor.putString("time", currentWalk.getTimeTaken());
                editor.apply();

                currentWalk = null;
                saveRecentWalk();
                endingWalk = false;
                
                launchRoutes();
                return;
            }

            currentWalk = null;
            saveRecentWalk();
            saveCurrentWalk();
            endingWalk = false;

            Intent intent = new Intent(this, NewRoute.class);
            intent.putExtra("stepCount", Integer.parseInt(recentWalkSteps.getText().toString()));
            System.out.println(recentWalkDist.getText().toString());
            intent.putExtra("distance", recentWalkDist.getText().toString());
            intent.putExtra("time", recentWalkTime.getText().toString());
            intent.putExtra(Home.FITNESS_SERVICE_KEY, fitnessServiceKey);
            intent.putExtra(Home.FIREBASE_SERVICE_KEY, firebaseServiceKey);
            intent.putExtra(Home.HEIGHT_KEY, fakeHeight);
            intent.putExtra(Home.TEST_KEY, testSteps);

            startActivity(intent);
        }
    }
}
