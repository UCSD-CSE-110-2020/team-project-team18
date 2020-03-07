package com.example.walk_walk_revolution2;

import android.app.Activity;
import android.app.Service;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class TestFirebaseService implements FirebaseService{
        private static final String TAG = "[TestFitnessService]: ";
        private String userEmail;
        private String firstName;
        private String lastName;
        private ArrayList<String> friends;
        private DocumentSnapshot documentSnapshot;
        public TestFirebaseService(Service service) {}


        @Override
        public void setup(String userEmail) {
           this.userEmail = userEmail;
           friends = new ArrayList<>();
        }

        @Override
        public void addUserToDatabaseIfFirstUse(final String userEmail, final String firstName, final String lastName) {
           this.firstName = firstName;
           this.lastName = lastName;
        }

        @Override
        public void addFriendToTeam(String friendEmail){
            friends.add(friendEmail);
        }

        @Override
        public void getInvitation(){ }

        @Override
        public DocumentSnapshot retrieveInvitation(){
            return null;
        }
@Override
public void removeInvite(String fromEmail){}
        @Override
        public void sendInvite(String toEmail){}
}
