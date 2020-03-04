package com.example.walk_walk_revolution2;

public interface FirebaseService {
    public void addUserToDatabaseIfFirstUse(final String userEmail, final String firstName, final String lastName);
    public void addFriendToTeam(final String teamEmail);
}
