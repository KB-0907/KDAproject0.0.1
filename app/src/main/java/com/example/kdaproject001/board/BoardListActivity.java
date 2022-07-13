package com.example.kdaproject001.board;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kdaproject001.R;

public class BoardListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borad_list);
    }

    public void moveToContent(View view){
        Intent intent = new Intent(getApplicationContext(), WriteContentActivity.class);
        startActivity(intent);
    }
}