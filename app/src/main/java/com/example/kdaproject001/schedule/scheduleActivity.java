package com.example.kdaproject001.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kdaproject001.R;

public class scheduleActivity extends AppCompatActivity {
    Button makeSchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        makeSchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent makeIntent =  new Intent(scheduleActivity.this, CreateSchedule.class);
                startActivity(makeIntent);
            }
        });
    }
}
