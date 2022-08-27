package com.example.kdaproject001.account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
    EditText userMail, userPassword;
    TextView signUp, findIDPw;
    Button loginBnt;
    FirebaseAuth mFirebaseAuth;         //파이어베이스 인증처리
    DatabaseReference mDatabaseRef;
    FirebaseUser user;

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
                UserLogin();
            }
        }); //로그인 요청
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = FirebaseAuth.getInstance().getCurrentUser(); //파이어 베이스에서 현재 로그인한 유저의 UID
                if (user == null){
                    Toast.makeText(Login.this, "로그아웃 안댐.", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(Login.this, user.getUid(), Toast.LENGTH_SHORT).show();

            }
        });

        ((TextView)findViewById(R.id.move_to_find_password)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FindPwd.class);
                startActivity(intent);
            }
        });
    }

    public void KBButton(View view){
        userMail.setText("boomki0907@icloud.com");
        userPassword.setText("bkk97920");
        UserLogin();
    }

    public void SHButton(View view){
        userMail.setText("1022lsh@naver.com");
        userPassword.setText("123456789");
        UserLogin();
    }

    public void UserLogin(){
        String strEmail = userMail.getText().toString();// 사용자로 부터 문자를 입력 받아서 String 변수에 저장
        String strPwd = userPassword.getText().toString();

        if (strEmail.length() > 0 && strPwd.length() > 0) {
            mFirebaseAuth.signInWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){//로그인 성공
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(Login.this, "이메일 혹은 패스워드가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            Toast.makeText(Login.this, "이메일 혹은 패스워드를 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
    }
}