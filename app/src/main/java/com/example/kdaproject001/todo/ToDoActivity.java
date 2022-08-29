package com.example.kdaproject001.todo;
// 도레미파솔라시
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;

import com.example.kdaproject001.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

//과제랑 시험 추가 버튼 누르면 달력 팝업 -> 달력에서 날짜(마감 일) 선택 후, 제목 입력 받음
// 과제나 시험 추가 시 일정 달력에 색깔로 표시
//ㄱ저ㅗㅜㅕㅈ초셔미ㅠㅈ펴ㅛ무;속쟈ㅜㅐ셔ㅗㅛ쵸그ㅑㅐ;ㅗ쇼먀ㅐㅜㅙ

public class ToDoActivity extends AppCompatActivity {
    public final static String TAG = "ToDoActivity";
    Date dateNow = Calendar.getInstance().getTime();
    SimpleDateFormat toY = new SimpleDateFormat("yyyy");
    SimpleDateFormat toM = new SimpleDateFormat("MM");
    SimpleDateFormat toD = new SimpleDateFormat("dd");
    FirebaseFirestore db = FirebaseFirestore.getInstance(); // 파이어 베이스 파이어스토어를 사용하기 위한 변수 생성 및 할당
    RecyclerView assignRCV, examRCV;
    AssignAdapter AsAdapter;
    ExamAdapter examAdapter;
    ImageButton beforeBtn;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); //파이어 베이스에서 현재 로그인한 유저
    String currentUserID = user.getUid(); //현재 로그인한 사용자 uid
    String sort;
    int y = 0, m = 0, d = 0;
    ArrayList<TodoInfo> todoInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        assignRCV = findViewById(R.id.assign_RCV);
        examRCV = findViewById(R.id.exam_RCV);
        findViewById(R.id.add_assignment).setOnClickListener(onClickListener);
        findViewById(R.id.add_exam).setOnClickListener(onClickListener);


        generateAssignRCV();
        generateExamRCV();

        beforeBtn = findViewById(R.id.before_btn);
        beforeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    View.OnClickListener onClickListener = v -> {
        switch (v.getId()){
            case R.id.add_assignment:
                sort = "assign";
                generateToDo(sort);
                break;
            case R.id.add_exam:
                sort = "exam";
                generateToDo(sort);
        }
    };

    public void generateToDo(String sort) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                y = year;
                m = month + 1;
                d = dayOfMonth;
                Intent intent = new Intent(getApplicationContext(), ToDoMakeActivity.class);
                intent.putExtra("year", y);
                intent.putExtra("month", m);
                intent.putExtra("day", d);
                intent.putExtra("sort", sort);
                startActivity(intent);
            }
        }, Integer.parseInt(toY.format(dateNow)), Integer.parseInt(toM.format(dateNow)) - 1, Integer.parseInt(toD.format(dateNow)));
        if (sort.equals("assign")){
            datePickerDialog.setMessage("마감 기한");
        } else if (sort.equals("exam")){
            datePickerDialog.setMessage("시험 일시");
        }
        datePickerDialog.show();
    }

    private void generateAssignRCV() {    //파이어 베이스 db 에서 todo 를 가져와 리싸이클러뷰에 보여주기 위한 코드
        db.collection("ToDo").orderBy("deadline", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            todoInfo = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                if (document.getData().get("sort").toString().equals("assign") && currentUserID.equals(document.getData().get("uid").toString())) {
                                    todoInfo.add(new TodoInfo(document.getData().get("title").toString(),
                                            document.getData().get("deadline").toString(),
                                            (Long) document.getData().get("created"),
                                            document.getId(),
                                            document.getData().get("sort").toString(),
                                            document.getData().get("uid").toString(),
                                            Integer.parseInt(String.valueOf(document.getData().get("hour"))), //Map 자료형을 int 로 받아 올 때
                                            Integer.parseInt(String.valueOf(document.getData().get("minute")))
                                            ));
                                }
                            }
                            LinearLayoutManager layoutManager = new LinearLayoutManager(ToDoActivity.this, LinearLayoutManager.VERTICAL, false);
                            assignRCV.setLayoutManager(layoutManager);
                            AsAdapter = new AssignAdapter(ToDoActivity.this, todoInfo, getApplicationContext()); //리싸이클러뷰를 보이게 하기 위한 어댑터 생성
                            assignRCV.setAdapter(AsAdapter); //리싸이클러뷰에 위에서 생성한 어댑터 설정

                            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN, ItemTouchHelper.START|ItemTouchHelper.END) {
                                @Override
                                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                                    return AsAdapter.onItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
                                }

                                @Override
                                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                                    AsAdapter.onItemSwipe(viewHolder.getAdapterPosition()); //화면에서 삭제
                                    Log.d("상태1", String.valueOf(viewHolder.getAdapterPosition()));

                                    //Log.d("값", todoInfo.get(viewHolder.getAdapterPosition()).getTodoID());

                                    //삭제하기 위해 - 내가 얻을 수 있는 것 : 현재 클릭한 아이템의 문자열들 혹은
                                }
                            });
                            itemTouchHelper.attachToRecyclerView(assignRCV);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    private void generateExamRCV() {    //파이어 베이스 db 에서 todo 를 가져와 리싸이클러뷰에 보여주기 위한 코드
        db.collection("ToDo")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            todoInfo = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                if (document.getData().get("sort").toString().equals("exam") && currentUserID.equals(document.getData().get("uid").toString())) {
                                    todoInfo.add(new TodoInfo(document.getData().get("title").toString(),
                                            document.getData().get("deadline").toString(),
                                            (Long) document.getData().get("created"),
                                            document.getId(),
                                            document.getData().get("sort").toString(),
                                            document.getData().get("uid").toString(),
                                            Integer.parseInt(String.valueOf(document.getData().get("hour"))),
                                            Integer.parseInt(String.valueOf(document.getData().get("minute")))
                                    ));
                                }
                            }
                            LinearLayoutManager layoutManager = new LinearLayoutManager(ToDoActivity.this, LinearLayoutManager.VERTICAL, false);
                            examRCV.setLayoutManager(layoutManager);
                            examAdapter = new ExamAdapter(ToDoActivity.this, todoInfo, getApplicationContext()); //리싸이클러뷰를 보이게 하기 위한 어댑터 생성
                            examRCV.setAdapter(examAdapter); //리싸이클러뷰에 위에서 생성한 어댑터 설정

                            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN, ItemTouchHelper.START|ItemTouchHelper.END) {
                                @Override
                                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                                    return examAdapter.onItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
                                }

                                @Override
                                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                                    examAdapter.onItemSwipe(viewHolder.getAdapterPosition()); //화면에서 삭제
                                    Log.d("상태1", String.valueOf(viewHolder.getAdapterPosition()));

                                    //Log.d("값", todoInfo.get(viewHolder.getAdapterPosition()).getTodoID());

                                    //삭제하기 위해 - 내가 얻을 수 있는 것 : 현재 클릭한 아이템의 문자열들 혹은
                                }
                            });
                            itemTouchHelper.attachToRecyclerView(examRCV);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


}