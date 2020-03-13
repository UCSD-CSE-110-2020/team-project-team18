package com.example.walk_walk_revolution2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.annotation.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;

public class ProposedWalk extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    //ProgressDialog progressDialog;
    List<String> list;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

    String TAG = ProposedWalk.class.getSimpleName();

    String COLLECTION_KEY = "users";
    String DOCUMENT_KEY = "la1661424@gmail.com";
    String SUBCOLLECTION_KEY = "proposals";
    String SUBDOCUMENT_KEY = "proposal";

    final DocumentReference docRef = db.collection(COLLECTION_KEY)
            .document(DOCUMENT_KEY)
            .collection(SUBCOLLECTION_KEY)
            .document(SUBDOCUMENT_KEY);

    String email;
    TextView proposedWalk, scheduledWalk;
    TextView displayWalkName, displayOwnerName, displayDate, displayTime, displayStartPoint;

    String propose_schedule = "proposed"; // flag to specify proposed/scheduled Walk
    String walkProposer = "";
    String walkName = "";
    String walkDate = "";
    String walkTime = "";
    String startPoint;

    public static final String FITNESS_SERVICE_KEY = "FITNESS_SERVICE_KEY";
    public static final String FIREBASE_SERVICE_KEY = "FIREBASE_SERVICE_KEY";
    public static final String HEIGHT_KEY = "HEIGHT_KEY";
    public static final String STEPS_KEY = "STEPS_KEY";
    public static final String TEST_KEY = "TEST_KEY";
    private String fitnessServiceKey;
    private String firebaseServiceKey;
    public int fakeHeight;
    private int numSteps;
    private int testSteps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proposed_walk_screen);

        fitnessServiceKey = getIntent().getStringExtra(FITNESS_SERVICE_KEY);
        firebaseServiceKey = getIntent().getStringExtra(FIREBASE_SERVICE_KEY);
        fakeHeight = getIntent().getIntExtra(HEIGHT_KEY, 0);
        numSteps = getIntent().getIntExtra(STEPS_KEY, 0);
        testSteps = getIntent().getIntExtra(TEST_KEY, 0);

        //progressDialog = new ProgressDialog(ProposedWalk.this);

        //progressDialog.setMessage("Loading Data from Firebase Database");

        //progressDialog.show();

        SharedPreferences sharedpreferences = getSharedPreferences("user_email", Context.MODE_PRIVATE);
        email = sharedpreferences.getString("userEmail", null);

        proposedWalk = (TextView) findViewById(R.id.proposedWalk);
        scheduledWalk = (TextView) findViewById(R.id.scheduledWalk);

        final Button scheduleWalk = (Button) findViewById(R.id.scheduleWalk);
        final Button withdrawnWalk = (Button) findViewById(R.id.withdrawWalk);
        final Button acceptWalk = (Button) findViewById(R.id.acceptPropose);
        final Button declineTime = (Button) findViewById(R.id.declineProposeBadTime);
        final Button declineRoute = (Button) findViewById(R.id.declineProposeRoute);

        if (email.toLowerCase().matches(DOCUMENT_KEY.toLowerCase())) {
            scheduledWalk.setVisibility(View.VISIBLE);
            proposedWalk.setVisibility(View.INVISIBLE);
            acceptWalk.setVisibility(View.INVISIBLE);
            declineTime.setVisibility(View.INVISIBLE);
            declineRoute.setVisibility(View.INVISIBLE);
            withdrawnWalk.setVisibility(View.VISIBLE);
            scheduleWalk.setVisibility(View.VISIBLE);
        } else {
            proposedWalk.setVisibility(View.VISIBLE);
            scheduledWalk.setVisibility(View.INVISIBLE);
            scheduleWalk.setVisibility(View.INVISIBLE);
            withdrawnWalk.setVisibility(View.INVISIBLE);
            acceptWalk.setVisibility(View.VISIBLE);
            declineTime.setVisibility(View.VISIBLE);
            declineRoute.setVisibility(View.VISIBLE);
        }

        //email = "test@gmail.com";
        list = new ArrayList<>();

        displayWalkName = (TextView) findViewById(R.id.nameWalk);
        displayOwnerName = (TextView) findViewById(R.id.nameOwner);
        displayDate = (TextView) findViewById(R.id.textDate);
        displayTime = (TextView) findViewById(R.id.textTime);
        displayStartPoint = (TextView) findViewById(R.id.start_point_text);

        displayStartPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String start_point_str = ((TextView) findViewById(R.id.start_point_text)).getText().toString();
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("google.navigation:q="+start_point_str));
                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProposedWalk.this));
        adapter = new ProposedWalkAdapter(ProposedWalk.this, list);
        recyclerView.setAdapter(adapter);

        acceptWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptProposedWalk();
            }
        });

        declineTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                declineWalkBadTime();
            }
        });

        declineRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                declineWalkBadRoute();
            }
        });

        scheduleWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scheduleProposedWalk();
            }
        });

        withdrawnWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                withdrawProposedWalk();
            }
        });

        // use FirestoreCallBack - onCallback to solve issue of Asynchronous in Firestore db
        getProposedData(new FirestoreCallBack() {
            @Override
            public void onCallback(String propose_schedule, String proposer, String walk, String date, String time, List<String> list, String startPoint) {
                //Log.d(TAG, list.toString());
                //System.out.println("Proposer1: " + proposer);
                displayOwnerName.setText(walkProposer);
                displayDate.setText(walkDate);
                displayTime.setText(walkTime);
                displayWalkName.setText(walkName);
                displayStartPoint.setText(startPoint);

                if (propose_schedule.matches("scheduled")) {
                    proposedWalk.setVisibility(View.INVISIBLE);
                    scheduledWalk.setVisibility(View.VISIBLE);
                    scheduleWalk.setVisibility(View.INVISIBLE);
                }

                adapter.notifyDataSetChanged();
                adapter = new ProposedWalkAdapter(ProposedWalk.this, list);
                recyclerView.setAdapter(adapter);
                //progressDialog.dismiss();
            }
        });

        //adapter.notifyDataSetChanged();
        //recyclerView.setAdapter(adapter);
    }

    private void getProposedData(final FirestoreCallBack firestoreCallBack) {
        // get data in cases View changes between snapshots
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot >() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot  document,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (document != null && document.exists()) {
                    Log.d(TAG, "Current data: " + document.getData());
                    propose_schedule = document.getString("propose_schedule");
                    walkProposer = document.getString("proposer");
                    walkName = document.getString("walk_name");
                    walkDate = document.getString("walk_date");
                    walkTime = document.getString("walk_time");
                    startPoint = document.getString("startingPoint");

                    Object object = document.get("attendees");
                    String[] values = String.valueOf(object).replace("[", "").replace("]", "").split(",");
                    list = new ArrayList<>();
                    for (String value : values) {
                        list.add(value);
                    }

                    firestoreCallBack.onCallback(propose_schedule, walkProposer, walkName, walkDate, walkTime, list, startPoint);
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });

        //Code worked, backup for changes to new code
        /*docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot document,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                String source = document != null && document.getMetadata().hasPendingWrites()
                        ? "Local" : "Server";

                if (document != null && document.exists()) {
                    Log.d(TAG, source + " data: " + document.getData());
                    walkProposer = document.getString("proposer");
                    walkName = document.getString("walk_name");
                    walkDate = document.getString("walk_date");
                    walkTime = document.getString("walk_time");
                    Object object = document.get("attends");
                    String[] values = String.valueOf(object).split(",");
                    for (String value : values) {
                        list.add(value);
                    }
                    firestoreCallBack.onCallback(walkProposer, walkName, walkDate, walkTime, list);
                } else {
                    Log.d(TAG, source + " data: null");
                }
            }
        });*/

        // Reference Link for Callback: https://www.youtube.com/watch?v=0ofkvm97i0s

    }

    private interface FirestoreCallBack {
        void onCallback(String propose_schedule, String proposer, String name, String date, String time, List<String> list, String startPoint);
    }

    public void acceptProposedWalk() {
        docRef.update("attendees", FieldValue.arrayUnion(email));
        //Map<String, Object> accepted = new HashMap<>();
        //accepted.put("accepted", email);
        // add this user into list of attendants
        /*db.collection(COLLECTION_KEY)
                .document(DOCUMENT_KEY)
                .collection(SUBCOLLECTION_KEY)
                .add(accepted)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot successfully written");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });*/
    }

    public void declineWalkBadTime() {
        docRef.update("attendees", FieldValue.arrayRemove(email));
        /*Map<String, Object> declined = new HashMap<>();
        declined.put("decline_bad_time", email);

        // add this user into list of attendants
        db.collection(COLLECTION_KEY)
                .document(DOCUMENT_KEY)
                .collection(SUBCOLLECTION_KEY)
                .add(declined)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot successfully written");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });*/
    }

    public void declineWalkBadRoute() {
        docRef.update("attendees", FieldValue.arrayRemove(email));
        /*Map<String, Object> declined = new HashMap<>();
        declined.put("decline_bad_route", email);

        // add this user into list of attendants
        db.collection(COLLECTION_KEY)
                .document(DOCUMENT_KEY)
                .collection(SUBCOLLECTION_KEY)
                .add(declined)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot successfully written");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });*/
    }

    public void scheduleProposedWalk() {
        docRef.update("propose_schedule", "scheduled");
    }

    public void withdrawProposedWalk() {
        docRef.delete();
        Intent intent = new Intent(this, RoutesScreen.class);
        intent.putExtra(Home.FITNESS_SERVICE_KEY, fitnessServiceKey);
        intent.putExtra(Home.FIREBASE_SERVICE_KEY, firebaseServiceKey);
        intent.putExtra(Home.HEIGHT_KEY, fakeHeight);
        intent.putExtra(Home.STEPS_KEY, numSteps);
        intent.putExtra(TEST_KEY, testSteps);

        startActivity(intent);
    }
}