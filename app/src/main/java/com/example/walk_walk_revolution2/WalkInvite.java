package com.example.walk_walk_revolution2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WalkInvite extends AppCompatActivity {
    public static final String FITNESS_SERVICE_KEY = "FITNESS_SERVICE_KEY";
    public static final String HEIGHT_KEY = "HEIGHT_KEY";
    public static final String STEPS_KEY = "STEPS_KEY";
    public static final String TEST_KEY = "TEST_KEY";
    public String TAG = WalkInvite.class.getSimpleName();
    private String fitnessServiceKey;
    public int fakeHeight;
    private String walkName;
    private String startingPoint;
    private String fileName;
    private int numSteps;
    private int testSteps;
    private TimePicker time;
    private DatePicker date;
    private Toast sentInvite;
    private int currentMonth;
    private int currentDay;
    private int currentYear;
    private int month;
    private int day;
    private int year;
    private int currentHour;
    private int currentMinute;
    private int hour;
    private int minutes;
    String walkTime;
    String walkDate;
    FirebaseFirestore db;
    private ArrayList<String> team = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_invite);
        setVariables();

        Button btnSendInvite = findViewById(R.id.send_invite);
        Button btnCancel = findViewById(R.id.cancel);

        time = findViewById(R.id.pick_time);
        date = findViewById(R.id.pick_date);

        setupInvite();

        btnSendInvite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                sendInvite();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                launchRouteDetails(fileName);
            }
        });
    }

    private void setVariables(){
        fitnessServiceKey = getIntent().getStringExtra(FITNESS_SERVICE_KEY);
        fakeHeight = getIntent().getIntExtra(HEIGHT_KEY, 0);
        numSteps = getIntent().getIntExtra(STEPS_KEY, 0);
        testSteps = getIntent().getIntExtra(TEST_KEY, 0);
        walkName = getIntent().getStringExtra("name");
        startingPoint = getIntent().getStringExtra("startPoint");
        fileName = getIntent().getStringExtra("fileName");
    }

    private void setupInvite(){
        TextView name = findViewById(R.id.walk_name);
        name.setText(walkName);
        name.setGravity(Gravity.CENTER);

        TextView startPoint = findViewById(R.id.starting_point);
        startPoint.setText(startingPoint);
        startPoint.setGravity(Gravity.CENTER);

        currentMonth = date.getMonth() + 1;
        currentDay = date.getDayOfMonth();
        currentYear = date.getYear();

        currentHour = time.getCurrentHour();
        currentMinute = time.getCurrentMinute();
    }

    private void sendInvite(){
        SharedPreferences sharedPreferences = getSharedPreferences("user_email", MODE_PRIVATE);
        final String email = sharedPreferences.getString("userEmail", "");

        db = FirebaseFirestore.getInstance();
        DocumentReference ref = db.collection("users").document(email);

        if(validDate() && validTime()){
            walkTime = getTime();
            walkDate = getDate();

            ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            team = (ArrayList<String>)document.get("teammates");


                            team.add(email);
                            System.out.println("TeamList ---------->"+team);

                            ArrayList<String> attend = new ArrayList<>();
                            attend.add(email);

                            for(String teamEmail: team){

                                Map<String, Object> walkProposal = new HashMap<>();
                                walkProposal.put("proposer", email);
                                walkProposal.put("propose_schedule", "proposed");
                                walkProposal.put("walk_name", walkName);
                                walkProposal.put("startingPoint", startingPoint);
                                walkProposal.put("walk_date", walkDate);
                                walkProposal.put("walk_time", walkTime);
                                walkProposal.put("attendees", attend);

                                db.collection("users").document(teamEmail).collection("proposals")
                                        .document("proposal").set(walkProposal)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error writing document", e);
                                            }
                                        });
                            }

                            sentInvite = Toast.makeText(getApplicationContext(), "Sent Invite", Toast.LENGTH_LONG);
                            sentInvite.show();

                            launchRouteDetails(fileName);

                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }



                }
            });

        }
        else if(!validDate()) {
            sentInvite = Toast.makeText(getApplicationContext(), "Enter Valid Date", Toast.LENGTH_LONG);
            sentInvite.show();
        }
        else {
            sentInvite = Toast.makeText(getApplicationContext(), "Enter Valid Time", Toast.LENGTH_LONG);
            sentInvite.show();
        }
    }

    private String getTime(){
        int hour12;
        String am_pm;
        String min;

        if( hour == 12 || hour == 0 ) { hour12 = 12; }
        else if(  hour < 12 ) { hour12 = hour; }
        else { hour12 = hour%12; }

        if(minutes < 10) { min = "0" + minutes; }
        else { min = "" + minutes; }

        if(hour < 12) { am_pm = " AM"; }
        else { am_pm = " PM"; }

        return hour12 + ":" + min + am_pm;
    }

    private String getDate(){
        return month + "/" + day + "/" + year;
    }

    private void launchRouteDetails(String fileName){
        Intent intent = new Intent(this, Route.class);
        intent.putExtra(Home.FITNESS_SERVICE_KEY, fitnessServiceKey);
        intent.putExtra(Home.HEIGHT_KEY, fakeHeight);
        intent.putExtra(Home.STEPS_KEY, numSteps);
        intent.putExtra(TEST_KEY, testSteps);
        intent.putExtra("fileName", fileName);
        startActivity(intent);
    }

    private boolean validDate(){
        month = date.getMonth() + 1;
        day = date.getDayOfMonth();
        year = date.getYear();
        if(currentYear > year)  return  false;
        if(currentYear == year && currentMonth > month) return false;
        if(currentYear == year && currentMonth == month && currentDay > day) return false;
        return true;
    }

    private boolean validTime(){
        hour = time.getCurrentHour();
        minutes = time.getCurrentMinute();
        if(currentYear == year && currentMonth == month && currentDay == day){
            if((currentHour > hour) || (currentHour == hour && currentMinute > minutes)){
                return false;
            }
        }
        return true;
    }
}
