package com.example.kdaproject001.account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kdaproject001.MainActivity;
import com.example.kdaproject001.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
    EditText userMail, userPassword;
    TextView signUp, findIDPw;
    Button loginBnt;
    private FirebaseAuth mFirebaseAuth;         //파이어베이스 인증처리
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("KAD");
        userMail = findViewById(R.id.eMail);
        userPassword = findViewById(R.id.Password);
        signUp = findViewById(R.id.signUpText);
        loginBnt = findViewById(R.id.login);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIntent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(signIntent);
            }
        });
        loginBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strEmail = userMail.getText().toString();
                String strPwd = userPassword.getText().toString();

                mFirebaseAuth.signInWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){//로그인 성공
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(Login.this, "이메일 혹은 패스워드가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }); //로그인 요청

    }
    public void button(View view){
        Intent goIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(goIntent);
    }
}