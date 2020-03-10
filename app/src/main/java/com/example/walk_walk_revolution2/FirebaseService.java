package com.example.walk_walk_revolution2;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public interface FirebaseService {
    public void addUserToDatabaseIfFirstUse(final String userEmail, final String firstName, final String lastName);
//    public void addFriendToTeam(final String teamEmail);
    public void setup(String userEmail);
    public void getInvitation();
    public void sendInvite(String toEmail);
    public DocumentSnapshot retrieveInvitation();
    public ArrayList<RouteItem> retrieveTeamRouteList();
    public void acceptInvite(String senderEmail);
    public void updateList(String email, List<String> teamList);
    public void getOnTeamStatus();
    public boolean retrieveTeamStatus();
    public void removeInvite(String fromEmail);
    public void getTeamRouteList();
    public void getTeammates();
    public ArrayList<String> retrieveTeammates();
    public void clearTeamRouteList();


    }
