package com.example.kdaproject001.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.kdaproject001.R;
import com.example.kdaproject001.ToDoMakeActivity;
import com.example.kdaproject001.board.FreeBoardListActivity;
import com.example.kdaproject001.board.PostAdapter;
import com.example.kdaproject001.board.PostInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//과제랑 시험 추가 버튼 누르면 달력 팝업 -> 달력에서 날짜(마감 일) 선택 후, 제목 입력 받음
// 과제나 시험 추가 시 일정 달력에 색깔로 표시

public class ToDoActivity extends AppCompatActivity {
    public final static String TAG = "ToDoActivity";
    FirebaseFirestore db = FirebaseFirestore.getInstance(); //파이어 베이스 파이어스토어를 사용하기 위한 변수 생성 및 할당
    RecyclerView assignRCV, examRCV;
    int y=0, m=0, d=0, h=0, mi=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
    }

    public void showDialog(View view){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                y = year;
                m = month+1;
                d = dayOfMonth;
                Intent intent = new Intent(getApplicationContext(), ToDoMakeActivity.class);
                intent.putExtra("year", y);
                intent.putExtra("month", m);
                intent.putExtra("day", d);
                startActivity(intent);
            }
            },2022, 1, 11);

            datePickerDialog.setMessage("메시지");
            datePickerDialog.show();
        }
}