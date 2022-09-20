package com.example.kdaproject001.account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.kdaproject001.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

public class SendEmail extends AppCompatActivity {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser;
    DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);


    }

    public void checkAuth(){
        mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    sendEmail(firebaseUser.getEmail());
                }
            }
        });
    }


    ActionCodeSettings actionCodeSettings = ActionCodeSettings.newBuilder()
            .setUrl("https://www.example.com/finishSignUp?cartId=1234")
            // This must be true
            .setHandleCodeInApp(true)
            .setIOSBundleId("com.example.ios")
            .setAndroidPackageName(
                    "com.example.android",
                    true, /* installIfNotAvailable */
                    "12"    /* minimumVersion */)
            .build();


    public void sendEmail(String email) {

        auth.sendSignInLinkToEmail(email,actionCodeSettings)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete (@NonNull Task < Void > task) {
            if (task.isSuccessful()) {
                Log.d("TAG", "Email sent.");
            }
        }
    });
}
}