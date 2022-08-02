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
import com.example.kdaproject001.board.FreeBoardListActivity;
import com.example.kdaproject001.board.PostAdapter;
import com.example.kdaproject001.board.PostInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

//과제랑 시험 추가 버튼 누르면 달력 팝업 -> 달력에서 날짜(마감 일) 선택 후, 제목 입력 받음
// 과제나 시험 추가 시 일정 달력에 색깔로 표시

public class ToDoActivity extends AppCompatActivity {
    Date dateNow = Calendar.getInstance().getTime();
    SimpleDateFormat toY = new SimpleDateFormat("yyyy");
    SimpleDateFormat toM = new SimpleDateFormat("MM");
    SimpleDateFormat toD = new SimpleDateFormat("dd");
    public final static String TAG = "ToDoActivity";
    FirebaseFirestore db = FirebaseFirestore.getInstance(); // 파이어 베이스 파이어스토어를 사용하기 위한 변수 생성 및 할당
    RecyclerView assignRCV, examRCV;
    AssignAdapter AsAdapter;
    int y=0, m=0, d=0, h=0, mi=0;

    Map<String, Object> data = new HashMap<>();
    ArrayList<String> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        assignRCV = findViewById(R.id.assign_RCV);

        generateRCV();

    }

    public void showDialog1(View view){
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
                intent.putExtra("sort", "assign");
                startActivity(intent);
            }
            },Integer.parseInt(toY.format(dateNow)), Integer.parseInt(toM.format(dateNow)) -1, Integer.parseInt(toD.format(dateNow)));
        datePickerDialog.setMessage("마감 기한");
        datePickerDialog.show();
    }

    public void showDialog2(View view){
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
                intent.putExtra("sort", "exam");
                startActivity(intent);
            }
        },Integer.parseInt(toY.format(dateNow)), Integer.parseInt(toM.format(dateNow)) -1, Integer.parseInt(toD.format(dateNow)));

        datePickerDialog.setMessage("시험 일시");
        datePickerDialog.show();
    }
//jhh

    private void generateRCV(){//파이어 베이스 db 에서 todo 를 가져와 리싸이클러뷰에 보여주기 위한 코드
        db.collection("ToDo")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            dataList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                if (document.getData().get("sort").toString().equals("assign"))
                                    dataList.add(document.getData().get("title").toString());
                            }

                            LinearLayoutManager layoutManager = new LinearLayoutManager(ToDoActivity.this, LinearLayoutManager.VERTICAL, false);
                            assignRCV.setLayoutManager(layoutManager);
                            AsAdapter = new AssignAdapter(ToDoActivity.this, dataList, getApplicationContext()); //리싸이클러뷰를 보이게 하기 위한 어댑터 생성
                            assignRCV.setAdapter(AsAdapter); //리싸이클러뷰에 위에서 생성한 어댑터 설정
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}