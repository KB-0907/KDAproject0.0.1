package com.example.kdaproject001.account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.kdaproject001.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class FindIdPwd extends AppCompatActivity {

    EditText editTextPwdResetEmail;
    Button buttonPwdResetEmail;
    ProgressBar progressBar;
    FirebaseAuth authProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id_pwd);

        editTextPwdResetEmail = findViewById(R.id.editText_password_reset_email);
        buttonPwdResetEmail = findViewById(R.id.button_password_reset_email);
        progressBar = findViewById(R.id.progressBar);

        buttonPwdResetEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextPwdResetEmail.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(FindIdPwd.this,"이메일을 입력해주세요.",Toast.LENGTH_SHORT).show();
                    editTextPwdResetEmail.setError("Email is required");
                    editTextPwdResetEmail.requestFocus();
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    resetPassword(email);
                }

            }
        });

    }

    private void resetPassword(String email){
        authProfile = FirebaseAuth.getInstance();
        authProfile.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(FindIdPwd.this,"귀하의 이메일로 비빌번호 재설정 메일을 보냈습니다.",Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}