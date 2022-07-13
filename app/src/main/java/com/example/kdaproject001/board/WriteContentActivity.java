package com.example.kdaproject001.board;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.kdaproject001.R;

public class WriteContentActivity extends AppCompatActivity {
    Button writeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_content);

        writeBtn = findViewById(R.id.writeButton);
    }
}