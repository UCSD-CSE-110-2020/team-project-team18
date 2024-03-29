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

import org.w3c.dom.Document;

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
    ArrayList<String> fakeInvite;
    boolean onTeam;
    String firstName;
    String lastName;
    DocumentSnapshot inviteDoc;
    ArrayList<RouteItem> teamRoutes;

    List<String> teammates;


    public FirebaseAdapter(Service service){
    }

    public void setup(String userEmail){
        if(db == null) {
            this.db = FirebaseFirestore.getInstance();
            this.userEmail = userEmail;
            this.teammates = new ArrayList<>();
            this.teamRoutes = new ArrayList<>();
            Log.d(TAG, "building real db");
            //getOurFirstName(userEmail);
            //getOurLastName(userEmail);
            //getOnTeamStatus();
        }
    }
    public DocumentSnapshot retrieveInvitation(){
        getInvitation();
        return inviteDoc;
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
                        getInvitation();
                        getOnTeamStatus();
                        getTeammates();

                    } else {
                        Map<String, Object> user = new HashMap<>();
                        user.put("email", userEmail);
                        user.put("firstName", firstName);
                        user.put("lastName", lastName);
                        user.put("onTeam", false);
                        user.put("teammates", Arrays.asList());
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
                }
            }
        });
        this.firstName = firstName;
        this.lastName = lastName;
        this.onTeam = false;
    }
//
//    public void addUserToOwnTeam(String userEmail){
//        DocumentReference docRef = db.collection("teams").document(userEmail);
//        Map<String,Object> data = new HashMap<>();
//        data.put("teammates", Arrays.asList(userEmail));
//        docRef.set(data);
//    }

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
                            addInvite(newInvite, holdEmail, userEmail);
                        }
                    }
                }
            }
        });

    }

    public ArrayList<String> retrieveTeammates(){
        return (ArrayList<String>)this.teammates;
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
    public void getTeammates(){
       DocumentReference docRef = db.collection("users").document(userEmail);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                   if(document.exists()){
                       List<String> teamList;
                       DocumentSnapshot snap = task.getResult();
                       teammates = new ArrayList<>();
                       teammates = (ArrayList<String>) snap.get("teammates");
                           getTeamRouteList();
                   }
                }
            }
        });


    }

    public Task<?> addInvite(Map<String, String> message, String toEmail, String fromEmail) {
        return db.collection("users").document(toEmail).collection("invites").document(fromEmail).set(message);
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


    public void getInvitation(){
        CollectionReference invite = db.collection("users").document(userEmail).collection("invites");
        invite
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            if (task.getResult().getDocuments().size() != 0 ) {
                                inviteDoc = task.getResult().getDocuments().get(0);
                                System.out.println(inviteDoc);
                            }
                        }
                    }
                });

    }

    public void acceptInvite(final String senderEmail) {
        DocumentReference docRef = db.collection("users").document(userEmail);

        docRef.update("onTeam", true);
        docRef
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            List<String> teamList;
                            DocumentSnapshot snap = task.getResult();

                            teamList = (ArrayList<String>) snap.get("teammates");
                            System.out.println("myList: " + teamList);

                            teamList.add(senderEmail);
                            updateList(userEmail, teamList);
                            removeInvite(senderEmail);
                        }
                    }
                });

        DocumentReference senderDoc = db.collection("users").document(senderEmail);

        senderDoc.update("onTeam", true);
        senderDoc
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            List<String> teamList;
                            DocumentSnapshot snap = task.getResult();

                            teamList = (ArrayList<String>) snap.get("teammates");
                            System.out.println("myList: " + teamList);

                            teamList.add(userEmail);
                            updateList(senderEmail, teamList);
                        }

                    }

                });
    }

    public void updateList(String email, List<String> teamList) {
        DocumentReference docRef = db.collection("users").document(email);

        docRef.update("teammates", teamList);
    }

    public void getOnTeamStatus(){
        DocumentReference docRef = db.collection("users").document(userEmail);
        docRef
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            onTeam = task.getResult().getBoolean("onTeam");
                        }

                    }

                });

    }

    public boolean retrieveTeamStatus(){
        return onTeam;

    }
    public void removeInvite(String fromEmail) {
        DocumentReference invite = db.collection("users").document(userEmail).collection("invites").document(
fromEmail
        );
                invite.delete()
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "DocumentSnapshot successfully deleted!");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
        inviteDoc = null;
    }

    /*TODO:
        Current issues:
        1. First load into teamRouteScreen gives blank screen
        2. TeamRoutesList does not clear when exiting and entering screen -> appends to list

        Solution:
        1. OnGoing
        2. Clearing list after retrieve is called in TeamRouteScreen
    */
    public void getTeamRouteList(){
        System.out.println(teammates);
        for(String memeber: teammates) {

            db.collection("users").document(memeber).collection("routes")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    System.out.println(document);

                                    boolean shouldAdd = true;
                                    for(RouteItem item: teamRoutes){
                                        if(item.getName().equals(document.getString("name"))){
                                            shouldAdd = false;
                                            break;
                                        }
                                    }

                                    if(shouldAdd)
                                        teamRoutes.add(document.toObject(RouteItem.class));

                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }

    }


    public ArrayList<RouteItem> retrieveTeamRouteList(){
        System.out.println("FirebaseAdapter" + teamRoutes);
        return teamRoutes;
    }

    public void clearTeamRouteList(){
        teamRoutes = new ArrayList<>();
    }

    }

