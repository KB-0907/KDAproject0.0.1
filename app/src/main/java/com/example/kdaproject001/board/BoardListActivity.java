package com.example.kdaproject001.board;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.kdaproject001.R;

public class BoardListActivity extends AppCompatActivity {
    TextView moveToFreeBoard, moveToAcademicBoard , textView19;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_list);

        moveToFreeBoard = findViewById(R.id.moveToFreeBoard);
        moveToFreeBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent freeIntent = new Intent(getApplicationContext(),FreeBoardListActivity.class);
                startActivity(freeIntent);
            }
        });

        moveToAcademicBoard = findViewById(R.id.moveToAcademicBoard);
        moveToAcademicBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent academicIntent = new Intent(getApplicationContext(),AcademicBoardActivity.class);

                startActivity(academicIntent);
            }
        });

    }
    public void finishAct(View view) { finish(); }
}