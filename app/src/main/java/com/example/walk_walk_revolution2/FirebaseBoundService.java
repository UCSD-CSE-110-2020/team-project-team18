package com.example.walk_walk_revolution2;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseBoundService extends Service{
    private final IBinder iBinder = new LocalService();
    public static final String FIREBASE_SERVICE_KEY = "FIREBASE_SERVICE_KEY";
    FirebaseService firebaseService;
    String firebaseServiceKey;
    String email;
    public FirebaseBoundService(Activity activity) {
    }
    public FirebaseBoundService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
            firebaseServiceKey = intent.getStringExtra(FIREBASE_SERVICE_KEY);

            firebaseService = FirebaseServiceFactory.create(firebaseServiceKey, this);
           // firebaseService.setup(intent.getStringExtra("email"));
          //  firebaseService.addUserToDatabaseIfFirstUse(intent.getStringExtra("email"), intent.getStringExtra("firstName"), intent.getStringExtra("lastName"));
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public IBinder onBind(Intent intent) {
        if (email == null) {
            email = intent.getStringExtra("email");
            firebaseService.setup(email);
            firebaseService.addUserToDatabaseIfFirstUse(intent.getStringExtra("email"), intent.getStringExtra("firstName"), intent.getStringExtra("lastName"));
        }


        return iBinder;
    }

    public void setup(){
        firebaseService.getInvitation();
        firebaseService.getOnTeamStatus();
        firebaseService.getTeammates();

        if(firebaseService.retrieveTeammates() != null && !firebaseService.retrieveTeammates().isEmpty() ) {
            firebaseService.getTeamRouteList();
        }
    }
    class LocalService extends Binder {
        public FirebaseBoundService getService(){
            return FirebaseBoundService.this;
        }
    }



}
