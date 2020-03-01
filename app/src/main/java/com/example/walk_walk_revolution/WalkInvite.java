package com.example.walk_walk_revolution;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

public class WalkInvite extends AppCompatActivity {
    public static final String FITNESS_SERVICE_KEY = "FITNESS_SERVICE_KEY";
    public static final String HEIGHT_KEY = "HEIGHT_KEY";
    public static final String STEPS_KEY = "STEPS_KEY";
    public static final String TEST_KEY = "TEST_KEY";
    private String fitnessServiceKey;
    public int fakeHeight;
    private String walkName;
    private String startingPoint;
    private String fileName;
    private int numSteps;
    private int testSteps;
    private TimePicker time;
    private DatePicker date;
    private TextView textDate;
    private TextView textTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_invite);
        setVariables();
        setupInvite();

        textDate = findViewById(R.id.walk_date);
        textTime = findViewById(R.id.walk_time);

        Button btnSendInvite = findViewById(R.id.send_invite);
        Button btnCancel = findViewById(R.id.cancel);

        time = findViewById(R.id.pick_time);
        date = findViewById(R.id.pick_date);

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
    }

    private void sendInvite(){
        if(validDate()){
            int hour = time.getCurrentHour();
            int minutes = time.getCurrentMinute();
            String meetupTime = "Time: " + hour + ":" + minutes;
            textTime.setText(meetupTime);
            textTime.setGravity(Gravity.CENTER);

            int month = date.getMonth() + 1;
            int day = date.getDayOfMonth();
            int year = date.getYear();
            String meetupDay = month + "/" + day + "/" + year;
            textDate.setText(meetupDay);
            textDate.setGravity(Gravity.CENTER);
        }
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
        return true;
    }
}
