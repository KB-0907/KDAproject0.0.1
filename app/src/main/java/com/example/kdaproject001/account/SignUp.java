package com.example.kdaproject001.account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kdaproject001.R;
import com.example.kdaproject001.UserAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    EditText webMail, PassWd, PWCheck;
    Button signBtn;

    private FirebaseAuth mFirebaseAuth;         //파이어베이스 인증처리
    private DatabaseReference mDatabaseRef;     //실시간 데이터베이스

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("KAD");
        webMail = findViewById(R.id.mail);
        PassWd = findViewById(R.id.pw);
        PWCheck = findViewById(R.id.pwCheck);
        signBtn = findViewById(R.id.signUp);

        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strEmail = webMail.getText().toString();
                String strPwd = PassWd.getText().toString();
                String strPwdCh = PWCheck.getText().toString();

                if (strEmail.length() > 0 && strPwd.length() > 0 && strPwdCh.length() > 0) {
                    if (strPwd.equals(strPwdCh)) {
                        mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd)
                                .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                                            UserAccount account = new UserAccount();
                                            account.setEmailId(firebaseUser.getEmail());
                                            account.setPassword(strPwd);
                                            account.setIdToken(firebaseUser.getUid());

                                            mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account); //database insert

                                            startToast("가입에 성공하였습니다.");
                                            finish();
                                        } else {
                                            if (task.getException() != null) {
                                            }
                                        }
                                    }
                                });
                    } else {
                        startToast("비밀번호가 일치하지 않습니다.");
                    }
                }else {
                    startToast("이메일 또는 비밀번호를 정확히 입력해주세요.");
                }
            }
        });
    }

    public void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}