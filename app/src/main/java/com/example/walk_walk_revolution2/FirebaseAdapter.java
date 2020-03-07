package com.example.walk_walk_revolution2;

import android.app.Activity;
import android.app.Service;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FirebaseAdapter implements FirebaseService{
    FirebaseFirestore db;
    String FROM_KEY = "FROM";
    String FIRST_NAME_KEY = "FIRST_NAME";
    String LAST_NAME_KEY = "LAST_NAME";
    String userEmail;

    String firstName;
    String lastName;
    DocumentSnapshot inviteDoc;
    public FirebaseAdapter(Service service){
    }

    public void setup(String userEmail){
        if(db == null) {
            this.db = FirebaseFirestore.getInstance();
            this.userEmail = userEmail;
            getOurFirstName(userEmail);
            getOurLastName(userEmail);
        }
    }
    public void addUserToDatabaseIfFirstUse(final String userEmail, final String firstName, final String lastName) {
        DocumentReference docRef = db.collection("users").document(userEmail);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //user has already been added to database
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        ArrayList<String> teamMembers = new ArrayList<String>();

                        Map<String, Object> user = new HashMap<>();
                        user.put("email", userEmail);
                        user.put("firstName", firstName);
                        user.put("lastName", lastName);
                        user.put("onTeam", false);
                        user.put("teamMembers", teamMembers);
                        db.collection("users").document(userEmail)
                                .set(user)
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
                } else {
                  Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        addUserToOwnTeam(userEmail);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addUserToOwnTeam(String userEmail){
        DocumentReference docRef = db.collection("teams").document(userEmail);
        Map<String,Object> data = new HashMap<>();
        data.put("teammates", Arrays.asList(userEmail));
        docRef.set(data);
    }

    public void sendInvite(String toEmail) {
        final String holdEmail = toEmail;
        //check to see if teamEmail is on a team or not
        DocumentReference docRef = db.collection("users").document(holdEmail);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    //potential teammate is in database
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if (!document.getBoolean("onTeam")) {
                            DocumentReference docRef = db.collection("users").document(holdEmail);
                            Map<String, String> newInvite = new HashMap<>();
                            newInvite.put(FROM_KEY, userEmail);
                            newInvite.put(FIRST_NAME_KEY, firstName);
                            newInvite.put(LAST_NAME_KEY, lastName);
                            addInvite(newInvite, holdEmail);
                        }
                    }
                }
            }
        });

    }

    public void getOurLastName(String email){
        DocumentReference docRef = db.collection("users").document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    //potential teammate is in database
                    lastName = task.getResult().getString("lastName");
                }
            }
        });
    }
    public void getOurFirstName(String email){
        DocumentReference docRef = db.collection("users").document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    //potential teammate is in database
                    lastName = task.getResult().getString("lastName");
                }
            }
        });
    }
    public Task<?> addInvite(Map<String, String> message, String toEmail) {
        return db.collection("users").document(toEmail).collection("invites").add(message);
    }
    public void addFriendToTeam(final String teamEmail){
       //check to see if teamEmail is on a team or not
        DocumentReference docRef = db.collection("users").document(teamEmail);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    //potential teammate is in database
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> user = document.getData();
                        //potential teammate is not on a team already
                        if(user.containsValue(false)){
                            user.replace("onTeam", true);
                            DocumentReference docRef = db.collection("teams").document(userEmail);
                            docRef.update("teammates", FieldValue.arrayUnion(teamEmail));
                            docRef = db.collection("users").document(userEmail);
                            docRef.update("onTeam", true);
                        } else {
                       //person I'm inviting is already on a team
                        }
                    } else {
                        //person im inviting is not in database
                       Log.d(TAG, "get failed with ", task.getException());
                }
            }
        }
    });
    }


    public DocumentSnapshot getInvitation(){
        CollectionReference invite = db.collection("users").document(userEmail).collection("invites");
        invite
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        inviteDoc = task.getResult().getDocuments().get(0);
                        System.out.println(inviteDoc);

                    }
                });

//                .get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()) {
//
//                            inviteDoc = task.getResult();
//                            System.out.println(inviteDoc);
//
//                        } else {
//                            Log.d(TAG, "get failed with ", task.getException());
//                        }
//                    }
//                });

        return inviteDoc;
    }
}
