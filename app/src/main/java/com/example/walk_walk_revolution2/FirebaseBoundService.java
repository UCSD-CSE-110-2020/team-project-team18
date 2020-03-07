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
    public FirebaseBoundService(Activity activity) {
    }
    public FirebaseBoundService() {
    }
    @Override
    public IBinder onBind(Intent intent) {

        if(firebaseService==null) {
            firebaseServiceKey = intent.getStringExtra(FIREBASE_SERVICE_KEY);
            firebaseService = FirebaseServiceFactory.create(firebaseServiceKey, this);
            firebaseService.setup(intent.getStringExtra("email"));
            firebaseService.addUserToDatabaseIfFirstUse(intent.getStringExtra("email"), intent.getStringExtra("firstName"), intent.getStringExtra("lastName"));
        }
        return iBinder;
    }
    class LocalService extends Binder {
        public FirebaseBoundService getService(){
            return FirebaseBoundService.this;
        }
    }



}
