package com.example.kdaproject001.myInfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kdaproject001.MainActivity;
import com.example.kdaproject001.R;
import com.example.kdaproject001.account.Login;
import com.example.kdaproject001.account.SignUp;
import com.example.kdaproject001.account.UserAccount;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.regex.Pattern;

public class  ChangePwd extends AppCompatActivity {
    TextView textViewCurrentPwd, textViewNewPwd, textViewCheckPwd;
    ProgressBar progressBar;
    Button saveBtn, cancelBtn;
    FirebaseAuth mFirebaseAuth;         //파이어베이스 인증처리
    DatabaseReference mDatabaseRef;     //실시간 데이터베이스
    private FirebaseUser firebaseUser = mFirebaseAuth.getInstance().getCurrentUser();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);

        mFirebaseAuth = FirebaseAuth.getInstance();

        saveBtn = findViewById(R.id.save2_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdatePwd();
                finish();
            }
        });

        cancelBtn = findViewById(R.id.cancel2_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void UpdatePwd() {

        String editTextCurrentPwd = ((EditText) findViewById(R.id.editText_current_pwd)).getText().toString();
        String editTextNewPwd = ((EditText) findViewById(R.id.editText_new_pwd)).getText().toString();
        String editTextCheckPwd = ((EditText) findViewById(R.id.editText_check_pwd)).getText().toString();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("KAD");

        mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e ("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    if (editTextCurrentPwd.length() > 5 && editTextNewPwd.length() > 5 && editTextCheckPwd.length() > 5) {
                        if (editTextNewPwd.equals(editTextCheckPwd) &&
                                (editTextCurrentPwd.equals(String.valueOf(task.getResult().child("password").getValue())))) {

                            firebaseUser.updatePassword(editTextNewPwd)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Log.e("firebase", "변경");
                                                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("password").setValue(editTextNewPwd);
                                                finish();
                                            }
                                        }
                                    });
                                }
                        else { startToast("새 비밀번호와 확인 비밀번호가 일치하지 않거나,\n 현재 비밀번호가 잘못되었습니다."); }
                    }
                    else {
                        if (editTextCurrentPwd.length()<=5 && editTextCheckPwd.length()<=5 && editTextNewPwd.length()<=5){ startToast("비밀번호는 최소 6자리 이상입니다."); }
                    }
                }
            }
        });
    }

    public void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}