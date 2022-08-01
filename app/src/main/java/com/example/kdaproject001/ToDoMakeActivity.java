package com.example.kdaproject001;

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
    Button addToDoBtn;
    private FirebaseUser user;
    private int y=0, m=0, d=0, h=0, mi=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_to_do_make);
        deadline = findViewById(R.id.get_deadline);
        ToDoTitle = findViewById(R.id.ToDo_title);
        addToDoBtn = findViewById(R.id.add_ToDo);

        Intent inIntent = getIntent();
        y = inIntent.getIntExtra("year", 0);
        m = inIntent.getIntExtra("month", 0);
        d = inIntent.getIntExtra("day", 0);
        String year = String.valueOf(y);
        String month = String.valueOf(m);
        String day = String.valueOf(d);
        String deadStr = year + "/" + month + "/" + day;

        addToDoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddToDo(deadStr);
            }
        });
        deadline.setText(deadStr);
    }

    private void AddToDo(String deadStr){
        user = FirebaseAuth.getInstance().getCurrentUser(); //파이어 베이스에서 현재 로그인한 유저의 UID
        Map<String, Object> data = new HashMap<>();
        data.put("title", ToDoTitle.getText().toString());
        data.put("deadline", deadStr);
        data.put("UID",user.getUid());


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("ToDo").add(data)
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