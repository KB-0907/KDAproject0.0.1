package com.example.kdaproject001.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kdaproject001.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ToDoMakeActivity extends AppCompatActivity {
    TextView deadline;
    EditText ToDoTitle;
    Button addToDo;
    private FirebaseUser user;
    private int y=0, m=0, d=0;
    private String sort;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_to_do_make);

        deadline = findViewById(R.id.get_deadline);
        ToDoTitle = findViewById(R.id.ToDo_title);
        addToDo = findViewById(R.id.add_ToDo);

        Intent inIntent = getIntent();
        y = inIntent.getIntExtra("year", 0);
        m = inIntent.getIntExtra("month", 0);
        d = inIntent.getIntExtra("day", 0);
        sort = inIntent.getStringExtra("sort");
        Log.d("sort", sort);

        String year = String.valueOf(y);
        String month = String.valueOf(m);
        String day = String.valueOf(d);
        String deadStr = year + "/" + month + "/" + day;

        addToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddToDo(deadStr);
            }
        });
        deadline.setText(deadStr);
    }

    private void AddToDo(String deadStr){
        user = FirebaseAuth.getInstance().getCurrentUser(); //파이어 베이스에서 현재 로그인한 유저의 UID
        String todoID = "1";
        TodoInfo todoInfo = new TodoInfo(ToDoTitle.getText().toString(), deadStr, System.currentTimeMillis(), todoID, sort, user.getUid());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("ToDo").add(todoInfo)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
        finish();
    }
}