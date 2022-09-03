package com.example.kdaproject001.account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kdaproject001.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    EditText webMail, PassWd, PWCheck, Department, StudentID, StudentName;
    Button signBtn, checkBtn;

    DatabaseReference mDatabaseRef;     //실시간 데이터베이스

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("KAD");
        webMail = findViewById(R.id.mail);
        PassWd = findViewById(R.id.pw);
        PWCheck = findViewById(R.id.pwCheck);
        signBtn = findViewById(R.id.signUp);
        checkBtn = findViewById(R.id.check);
        StudentID = findViewById(R.id.get_student_id);
        Department = findViewById(R.id.get_department);
        StudentName = findViewById(R.id.get_name);


        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInfo();
            }
        });

        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpCheck();
            }
        });
    }

    private void checkInfo(){
    }

    private void signUpCheck(){
        String strEmail = webMail.getText().toString() + "@iit.kw.ac.kr";
        String strPwd = PassWd.getText().toString();
        String strPwdCh = PWCheck.getText().toString();
        String strStudentId = StudentID.getText().toString();
        String strDepartment = Department.getText().toString();
        String strName = StudentName.getText().toString();

        //  웹메일 아이디가 4자 이상, 비번, 비번확인은 6자 이상,
        if (strEmail.length() > 16 && strPwd.length() > 5 && strPwdCh.length() > 5 && strStudentId.length() > 9 && strDepartment.length()>4 && strName.length()>1) {
            if (strPwd.equals(strPwdCh)) { signUpFirebase(strEmail,strPwd); }
            else { Log.e("뭐냐", "진짜 뭐지"); }
        }
        if (strEmail.length()<=16){ startToast("웹메일 아이디는 최소 4자리 이상입니다."); }
        else if (strPwd.length()<=5){ startToast("비밀번호는 최소 6자리 이상입니다."); }
        else if (strStudentId.length()<=9){ startToast("학번은 최소 10자리 이상입니다."); }
        else if (strDepartment.length()<=4){ startToast("학과는 최소 4글자 이상입니다."); }
        else { startToast("비밀번호가 일치하지 않습니다."); }
    }

    private void signUpFirebase(String email, String password){
        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        String strStudentId = StudentID.getText().toString();
        String strDepartment = Department.getText().toString();
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                            UserAccount account = new UserAccount();
                            account.setEmailId(firebaseUser.getEmail());
                            account.setPassword(password);
                            account.setIdToken(firebaseUser.getUid());
                            account.setAuthentication(false);
                            account.setStudentId(strStudentId);
                            account.setDepartment(strDepartment);
                            //파이어베이스 회원가입 시 비밀번호는 최소 6자리
                            mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account); //database insert
                            finish();

                         /*   firebaseUser.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("TAG", "Email sent.");
                                                startToast("웹메일 인증을 완료해주세요;.");

                                            }else {
                                                Log.d("TAG", "실패.");

                                            }
                                        }
                                    });*/

                        } else {
                            if (task.getException() != null) {
                                Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                Log.d("TAG", "가입 실패.");
                            }
                        }
                    }
                });
       /* boolean check = mFirebaseAuth.getCurrentUser().isEmailVerified();
        if (check == true){
            startToast("인증됨");


        }else {
            startToast("인증 안된 계정");
        }*/
    }


    public void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}