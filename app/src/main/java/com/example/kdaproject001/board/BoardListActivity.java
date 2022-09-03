package com.example.kdaproject001.board;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.kdaproject001.CreditActivity;
import com.example.kdaproject001.EmailCertification;
import com.example.kdaproject001.R;
import com.example.kdaproject001.myInfo.MyInfoActivity;
import com.example.kdaproject001.schedule.ScheduleActivity;
import com.example.kdaproject001.todo.ToDoActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BoardListActivity extends AppCompatActivity {
    String boardSort;
    boolean auth;
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("KAD");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_list);

        findViewById(R.id.freeBoard).setOnClickListener(onBoardClickListener);
        findViewById(R.id.academicBoard).setOnClickListener(onBoardClickListener);
        findViewById(R.id.courseBoard).setOnClickListener(onBoardClickListener);
        findViewById(R.id.professorBoard).setOnClickListener(onBoardClickListener);
        findViewById(R.id.TextBookBoard).setOnClickListener(onBoardClickListener);
        findViewById(R.id.move_to_board_btn).setOnClickListener(moveActivityClickListener);
        findViewById(R.id.schedule_btn).setOnClickListener(moveActivityClickListener);
        findViewById(R.id.move_to_do_btn).setOnClickListener(moveActivityClickListener);
        findViewById(R.id.move_to_grade_planner_btn).setOnClickListener(moveActivityClickListener);
        checkAuth();

    }

    View.OnClickListener moveActivityClickListener = v -> {
        switch (v.getId()){
            case R.id.move_to_board_btn:
                //moveActivity(BoardListActivity.class);
                break;
            case R.id.schedule_btn:
                moveActivity(ScheduleActivity.class);
                break;
            case R.id.move_to_do_btn:
                moveActivity(ToDoActivity.class);
                break;
            case R.id.move_to_grade_planner_btn:
                if (auth == true){
                    moveActivity(CreditActivity.class);
                } else {
                    Intent i = new Intent(getApplicationContext(), EmailCertification.class);
                    startActivity(i);
                }
                break;
            case R.id.MyInfo_btn:
                if (auth == true){
                    moveActivity(MyInfoActivity.class);
                } else {
                    //이메일 인증 액티비티로
                }
                break;
        }
    };

    View.OnClickListener onBoardClickListener = v -> {
        switch (v.getId()) {
            case R.id.freeBoard:
                boardSort = "free";
                moveToBoard(boardSort);

                break;
            case R.id.academicBoard:
                boardSort = "academic";
                moveToBoard(boardSort);
                break;
            case R.id.courseBoard:
                boardSort = "course";
                moveToBoard(boardSort);
                break;
            case R.id.professorBoard:
                boardSort = "professor";
                moveToBoard(boardSort);
                break;
            case R.id.TextBookBoard:
                boardSort = "textbook";
                moveToBoard(boardSort);
                break;
        }

    };

    private void moveActivity(Class moveClass){
        Intent moveIntent = new Intent(getApplicationContext(), moveClass);
        startActivity(moveIntent);
    }


    private void moveToBoard(String boardSort){
        Intent boardIntent = new Intent(getApplicationContext(), FreeBoardListActivity.class);
        boardIntent.putExtra("boardSort", boardSort);
        startActivity(boardIntent);
    }

    public void checkAuth(){
        mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e ("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    String authCheck = String.valueOf(task.getResult().child("authentication").getValue());
                    if (authCheck.equals("false")){
                        Log.d("인증 확인", "인증 false");
                        auth = false;
                    } else {
                        Log.d("인증 확인", "인증");
                        auth = true;
                    }

                }
            }
        });
    }

    public void finishAct() { finish(); }
}