package com.example.kdaproject001.schedule;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.kdaproject001.R;

import java.util.ArrayList;

public class CreateSchedule extends AppCompatActivity {
    public static final int REQUEST_ADD = 1;
    public static final int REQUEST_EDIT = 2;
    private TimetableView timetable;

    //mainActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_schedule);

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MakeSchedule.class);
                intent.putExtra("mode", REQUEST_ADD);
                startActivityForResult(intent, REQUEST_ADD);
            }
        });

        timetable = findViewById(R.id.timetable);
        timetable.setHeaderHighlight(2);
        timetable.setOnStickerSelectEventListener(new TimetableView.OnStickerSelectedListener() {
            @Override
            public void OnStickerSelected(int idx, ArrayList<Schedule> schedules) {
                Intent i = new Intent(getApplicationContext(), MakeSchedule.class);
                i.putExtra("mode",REQUEST_EDIT);
                i.putExtra("idx", idx);
                i.putExtra("schedules", schedules);
                startActivityForResult(i,REQUEST_EDIT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case REQUEST_ADD:
                if(resultCode == MakeSchedule.RESULT_OK_ADD){
                    ArrayList<Schedule> item = (ArrayList<Schedule>)data.getSerializableExtra("schedules");
                    timetable.add(item);
                }
                break;
            case REQUEST_EDIT:
                /** Edit -> Submit */
                if(resultCode == MakeSchedule.RESULT_OK_EDIT){
                    int idx = data.getIntExtra("idx",-1);
                    ArrayList<Schedule> item = (ArrayList<Schedule>)data.getSerializableExtra("schedules");
                    timetable.edit(idx,item);
                }
                /** Edit -> Delete */
                else if(resultCode == MakeSchedule.RESULT_OK_DELETE){
                    int idx = data.getIntExtra("idx",-1);
                    timetable.remove(idx);
                }
                break;
        }
    }

}


 /*       textView00 = findViewById(R.id.textView11);
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
        });*/

/*    TextView textView00, textView01, textView02, textView03, textView04, textView05, textView06
            , textView07, textView08, textView09,textView10, textView11, textView12, textView13, textView14, textView15, textView16
            , textView17, textView18, textView19,textView20, textView21, textView22, textView23, textView24, textView25, textView26
            , textView27, textView28, textView29,textView30, textView31, textView32, textView33, textView34, textView35, textView36
            , textView37, textView38, textView39,textView40, textView41, textView42, textView43, textView44, textView45, textView46
            , textView47, textView48, textView49,textView50, textView51, textView52, textView53, textView54, textView55, textView56
            , textView57, textView58, textView59;*/