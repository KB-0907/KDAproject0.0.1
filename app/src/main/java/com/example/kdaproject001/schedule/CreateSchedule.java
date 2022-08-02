package com.example.kdaproject001.schedule;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.kdaproject001.R;

public class CreateSchedule extends AppCompatActivity {
    TextView textView00, textView01, textView02, textView03, textView04, textView05, textView06
            , textView07, textView08, textView09,textView10, textView11, textView12, textView13, textView14, textView15, textView16
            , textView17, textView18, textView19,textView20, textView21, textView22, textView23, textView24, textView25, textView26
            , textView27, textView28, textView29,textView30, textView31, textView32, textView33, textView34, textView35, textView36
            , textView37, textView38, textView39,textView40, textView41, textView42, textView43, textView44, textView45, textView46
            , textView47, textView48, textView49,textView50, textView51, textView52, textView53, textView54, textView55, textView56
            , textView57, textView58, textView59;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_schedule);
        textView00 = findViewById(R.id.textView11);
        textView00.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) { //눌렀을 때 동작
                    textView00.setBackgroundColor(Color.parseColor("#AFAFAF"));
                }
                if (event.getAction() == MotionEvent.ACTION_UP) { //뗐을 때 동작
                    textView00.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }
                return true;
            }
        });

        textView01 = findViewById(R.id.textView21);
        textView01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView01.setBackgroundColor(Color.parseColor("#AFAFAF"));
            }
        });
    }
    public void textTouch(View v){
    }

}