package com.example.walk_walk_revolution2;

import com.google.firebase.firestore.DocumentSnapshot;

public interface FirebaseService {
    public void addUserToDatabaseIfFirstUse(final String userEmail, final String firstName, final String lastName);
    public void addFriendToTeam(final String teamEmail);
    public void setup(String userEmail);
    public DocumentSnapshot getInvitation();

    }
