package com.example.kdaproject001.board;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.kdaproject001.R;

public class BoardListActivity extends AppCompatActivity {
    String boardSort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_list);

        findViewById(R.id.freeBoard).setOnClickListener(onBoardClickListener);
        findViewById(R.id.academicBoard).setOnClickListener(onBoardClickListener);
        findViewById(R.id.courseBoard).setOnClickListener(onBoardClickListener);
        findViewById(R.id.professorBoard).setOnClickListener(onBoardClickListener);
        findViewById(R.id.TextBookBoard).setOnClickListener(onBoardClickListener);
    }

    View.OnClickListener onBoardClickListener = v -> {
        switch (v.getId()){
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

    private void moveToBoard(String boardSort){
        Intent boardIntent = new Intent(getApplicationContext(), FreeBoardListActivity.class);
        boardIntent.putExtra("boardSort", boardSort);
        startActivity(boardIntent);
    }

    public void finishAct(View view) { finish(); }
}