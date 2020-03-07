package com.example.walk_walk_revolution;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.annotations.Nullable;
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
    String DOCUMENT_KEY = "gt@gmail.com";
    String SUBCOLLECTION_KEY = "proposals";
    String SUBDOCUMENT_KEY = "proposal";

    final DocumentReference docRef = db.collection(COLLECTION_KEY)
            .document(DOCUMENT_KEY)
            .collection(SUBCOLLECTION_KEY)
            .document(SUBDOCUMENT_KEY);

    String email;
    TextView displayWalkName, displayOwnerName, displayDate, displayTime;

    String walkProposer = "";
    String walkName = "";
    String walkDate = "";
    String walkTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proposed_walk_screen);

        //progressDialog = new ProgressDialog(ProposedWalk.this);

        //progressDialog.setMessage("Loading Data from Firebase Database");

        //progressDialog.show();

        SharedPreferences sharedpreferences = getSharedPreferences("user_email", Context.MODE_PRIVATE);
        email = sharedpreferences.getString("userEmail", null);
        //name = sharedpreferences.getString("name", null);

        //email = "test@gmail.com";
        list = new ArrayList<>();

        displayWalkName = (TextView) findViewById(R.id.nameWalk);
        displayOwnerName = (TextView) findViewById(R.id.nameOwner);
        displayDate = (TextView) findViewById(R.id.textDate);
        displayTime = (TextView) findViewById(R.id.textTime);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProposedWalk.this));
        adapter = new ProposedWalkAdapter(ProposedWalk.this, list);
        recyclerView.setAdapter(adapter);

        Button acceptWalk = (Button) findViewById(R.id.acceptPropose);
        Button declineTime = (Button) findViewById(R.id.declineProposeBadTime);
        Button declineRoute = (Button) findViewById(R.id.declineProposeRoute);

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

        // use FirestoreCallBack - onCallback to solve issue of Asynchronous in Firestore db
        getProposedData(new FirestoreCallBack() {
            @Override
            public void onCallback(String proposer, String walk, String date, String time, List<String> list) {
                //Log.d(TAG, list.toString());
                //System.out.println("Proposer1: " + proposer);
                displayOwnerName.setText(walkProposer);
                displayDate.setText(walkDate);
                displayTime.setText(walkTime);
                displayWalkName.setText(walkName);

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
                    walkProposer = document.getString("proposer");
                    walkName = document.getString("walk_name");
                    walkDate = document.getString("walk_date");
                    walkTime = document.getString("walk_time");
                    Object object = document.get("attends");
                    String[] values = String.valueOf(object).replace("[", "").replace("]", "").split(",");
                    list = new ArrayList<>();
                    for (String value : values) {
                        list.add(value);
                    }

                    firestoreCallBack.onCallback(walkProposer, walkName, walkDate, walkTime, list);
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

        // Link for Callback: https://www.youtube.com/watch?v=0ofkvm97i0s

    }

    private interface FirestoreCallBack {
        void onCallback(String proposer, String name, String date, String time, List<String> list);
    }

    public void acceptProposedWalk() {
        docRef.update("attends", FieldValue.arrayUnion(email));
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
        docRef.update("attends", FieldValue.arrayRemove(email));
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
        docRef.update("attends", FieldValue.arrayRemove(email));
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
}