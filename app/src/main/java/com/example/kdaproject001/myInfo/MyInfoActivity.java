package com.example.kdaproject001.myInfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kdaproject001.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyInfoActivity extends AppCompatActivity {
    TextView changePwd,useRules,move_to_logout,move_to_withdrawal;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        changePwd = findViewById(R.id.move_to_change_pwd);
        changePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changeIntent = new Intent(MyInfoActivity.this, ChangePwd.class);
                startActivity(changeIntent);
            }
        });

        useRules = findViewById(R.id.move_to_use_rules);
        useRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent UseIntent = new Intent(getApplicationContext(), UseRules.class);
                startActivity(UseIntent);
            }
        });
    }
}

/*

fAuth = FirebaseAuth.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();


        move_to_change_pwd.setOnClickListener(v -> {
final EditText resetPassword = new EditText(v.getContext());

final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
        passwordResetDialog.setTitle("비밀번호를 초기화하시겠습니까?");
        passwordResetDialog.setMessage("비밀번호는 10자리 이상으로 해주세요.");
        passwordResetDialog.setView(resetPassword);

        passwordResetDialog.setPositiveButton("변경", onClick (dialog, which) -> {
        String newPassword = resetPassword.getText().toString();
        })

        };

 */