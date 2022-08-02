package com.example.kdaproject001.board;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.kdaproject001.R;

public class BoardListActivity extends AppCompatActivity {
    TextView moveToFreeBoard, textView16, textView19;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_list);

        moveToFreeBoard = findViewById(R.id.moveToFreeBoard);
        moveToFreeBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),FreeBoardListActivity.class);
                startActivity(intent);
            }
        });

    }
    public void finishAct(View view) { finish(); }
}