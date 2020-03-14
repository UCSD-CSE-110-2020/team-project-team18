package com.example.walk_walk_revolution2;

import android.app.Activity;
import android.app.Service;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.OnLifecycleEvent;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class TestFirebaseService extends AppCompatActivity implements FirebaseService {
        private static final String TAG = "[TestFireBaseService]: ";
        private String userEmail;
        private String firstName;
        private String lastName;
        private ArrayList<String> teammates;
        private ArrayList<RouteItem> teamRoutes;
        private DocumentSnapshot documentSnapshot;
        public TestFirebaseService(Service service) {}


        @Override
        public void setup(String userEmail) {
           this.userEmail = userEmail;
           teammates = new ArrayList<>();
           teamRoutes = new ArrayList<>();
                Log.d(TAG, "building fake db");
        }

        @Override
        public void addUserToDatabaseIfFirstUse(final String userEmail, final String firstName, final String lastName) {
           this.firstName = firstName;
           this.lastName = lastName;
                teamRoutes = new ArrayList<>();
        }

//        @Override
//        public void addFriendToTeam(String friendEmail){
//            friends.add(friendEmail);
//        }

        @Override

        public void getInvitation(){ }

        @Override
        public DocumentSnapshot retrieveInvitation(){
            return null;
        }

        @Override
        public void getTeammates(){}

        @Override
        public ArrayList<String> retrieveTeammates(){

                if(teammates == null){
                        teammates = new ArrayList<>();
                }
                return teammates;
        }
        @Override
        public void sendInvite(String toEmail){}

        @Override
        public void updateList(String email, List<String> teamList){}

        @Override
        public void removeInvite(String fromEmail){}
        @Override
        public void acceptInvite(String senderEmail){}

        @Override
        public void getOnTeamStatus(){}

        @Override
        public boolean retrieveTeamStatus() {return false;};

        @Override
        public void getTeamRouteList() {}

        @Override
        public ArrayList<RouteItem> retrieveTeamRouteList(){
               // teamRoutes = new ArrayList<>();
                if(teamRoutes == null){
                        teamRoutes = new ArrayList<>();
                }
                return teamRoutes;
        }

        @Override
        public void clearTeamRouteList(){}


        public String getInviteEmail(){

                SharedPreferences spfs = getSharedPreferences("fake_invite", MODE_PRIVATE);
                return spfs.getString("userEmail", null);
        }
        public String getInviteFirst(){

                SharedPreferences spfs = getSharedPreferences("fake_invite", MODE_PRIVATE);
                return spfs.getString("firstName", null);
        }
        public String getInviteLast(){

                SharedPreferences spfs = getSharedPreferences("fake_invite", MODE_PRIVATE);
                return spfs.getString("lastName", null);
        }

}
