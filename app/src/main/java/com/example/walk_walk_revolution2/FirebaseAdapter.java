package com.example.walk_walk_revolution2;

import android.app.Activity;
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
    String userEmail;

    public FirebaseAdapter(Activity activity){
    }

    public void setup(String userEmail){
        this.db = FirebaseFirestore.getInstance();
        this.userEmail = userEmail;
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
    }

    public void addUserToOwnTeam(String userEmail){
        DocumentReference docRef = db.collection("teams").document(userEmail);
        Map<String,Object> data = new HashMap<>();
        data.put("teammates", Arrays.asList(userEmail));
        docRef.set(data);
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
}
