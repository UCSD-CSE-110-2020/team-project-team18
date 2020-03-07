package com.example.walk_walk_revolution2;

import android.app.Activity;

import java.util.ArrayList;

public class TestFirebaseService implements FirebaseService{
        private static final String TAG = "[TestFitnessService]: ";
        private String userEmail;
        private String firstName;
        private String lastName;
        private ArrayList<String> friends;
        public TestFirebaseService(Activity mainActivity) {}


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
        public void sendInvite(String toEmail){}
}
