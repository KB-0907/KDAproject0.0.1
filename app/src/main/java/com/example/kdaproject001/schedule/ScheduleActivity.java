package com.example.kdaproject001.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kdaproject001.R;

import java.util.ArrayList;

public class ScheduleActivity extends AppCompatActivity {
    public static final int REQUEST_ADD = 1;
    public static final int REQUEST_EDIT = 2;
    private TimetableView timetable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        findViewById(R.id.save_schedule).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 파이어베이스에 저장
            }
        });

        findViewById(R.id.add_class_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MakeClassActivity.class);
                intent.putExtra("mode", REQUEST_ADD);
                startActivityForResult(intent, REQUEST_ADD);
            }
        });


        timetable = findViewById(R.id.timetable);
        timetable.setHeaderHighlight(2);
        timetable.setOnStickerSelectEventListener(new TimetableView.OnStickerSelectedListener() {
            @Override
            public void OnStickerSelected(int idx, ArrayList<Schedule> schedules) {
                Intent i = new Intent(getApplicationContext(), MakeClassActivity.class);
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
                if(resultCode == MakeClassActivity.RESULT_OK_ADD){
                    ArrayList<Schedule> item = (ArrayList<Schedule>)data.getSerializableExtra("schedules");
                    timetable.add(item);
                }
                break;
            case REQUEST_EDIT:
                /** Edit -> Submit */
                if(resultCode == MakeClassActivity.RESULT_OK_EDIT){
                    int idx = data.getIntExtra("idx",-1);
                    ArrayList<Schedule> item = (ArrayList<Schedule>)data.getSerializableExtra("schedules");
                    timetable.edit(idx,item);
                }
                /** Edit -> Delete */
                else if(resultCode == MakeClassActivity.RESULT_OK_DELETE){
                    int idx = data.getIntExtra("idx",-1);
                    timetable.remove(idx);
                }
                break;
        }
    }

}
