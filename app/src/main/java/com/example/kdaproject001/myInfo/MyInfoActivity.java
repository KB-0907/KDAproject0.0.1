package com.example.kdaproject001.myInfo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kdaproject001.CreditActivity;
import com.example.kdaproject001.R;
import com.example.kdaproject001.account.Login;
import com.example.kdaproject001.account.UserAccount;
import com.example.kdaproject001.board.BoardListActivity;
import com.example.kdaproject001.schedule.ScheduleActivity;
import com.example.kdaproject001.todo.ToDoActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MyInfoActivity extends AppCompatActivity {
    TextView withdrawal, Name, StudentID, Department;
    FirebaseAuth mFirebaseAuth;         //파이어베이스 인증처리wal;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser firebaseUser = mFirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference mDatabaseRef;
    String DefaultID, getPostID, boardSort;
    ImageButton beforeBtn;
    Class moveClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        showInfo();

        findViewById(R.id.before_btn).setOnClickListener(moveActivityClickListener);
        findViewById(R.id.move_to_change_pwd).setOnClickListener(moveActivityClickListener);
        findViewById(R.id.move_to_use_rules).setOnClickListener(moveActivityClickListener);
        findViewById(R.id.move_to_logout).setOnClickListener(moveActivityClickListener);
        findViewById(R.id.move_to_suggest).setOnClickListener(moveActivityClickListener);

    }



    private void moveActivity(Class moveClass){
        Intent moveIntent = new Intent(getApplicationContext(), moveClass);
        startActivity(moveIntent);
    }


    View.OnClickListener moveActivityClickListener = v -> {
        switch (v.getId()){
            case R.id.before_btn:
                finish();
                break;
            case R.id.move_to_change_pwd:
                moveActivity(ChangePwd.class);
                break;
            case R.id.move_to_use_rules:
                moveActivity(UseRules.class);
                break;
            case R.id.move_to_logout:
                moveActivity(Login.class);
                break;
            case R.id.move_to_suggest:
                moveActivity(Suggest.class);
                break;
        }       // 회원 탈퇴는 xml 에서 onClick 으로 설정했음
    };



    public void showInfo () {
        Name = findViewById(R.id.textView_Name);
        StudentID = findViewById(R.id.textView_student_ID);
        Department = findViewById(R.id.textView_department);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("KAD");

        mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    Log.d("---------------------", String.valueOf(task.getResult().getValue()));
                    Name.setText(String.valueOf(task.getResult().child("name").getValue()));
                    StudentID.setText(String.valueOf(task.getResult().child("studentId").getValue()));

                }
            }
        });
    }

    public void OnClickHandler(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("경고").setMessage("정말로 회원 탈퇴를 하시겠습니까?");

        builder.setPositiveButton("예", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                user.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    FirebaseAuth.getInstance().signOut();
                                    Intent moveIntent = new Intent(getApplicationContext(),Login.class);
                                    startActivity(moveIntent);
                                }
                            }
                        });
            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}


