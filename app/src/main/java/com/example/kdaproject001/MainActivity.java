package com.example.kdaproject001;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.kdaproject001.board.FreeBoardListActivity;
import com.example.kdaproject001.mainViewPager.CreditFragment;
import com.example.kdaproject001.mainViewPager.ScheduleFragment;
import com.example.kdaproject001.schedule.scheduleActivity;

public class MainActivity extends AppCompatActivity{
    ImageButton boardBnt, schBtn, TodoBtn;
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boardBnt = findViewById(R.id.move_to_board_btn);
        schBtn = findViewById(R.id.schedule_btn);
        TodoBtn = findViewById(R.id.move_to_do_btn);
        pager = findViewById(R.id.pager);
        pager.setOffscreenPageLimit(2);

        MainPagerAdapter mainAdapter = new MainPagerAdapter(getSupportFragmentManager());

        ScheduleFragment schFragment = new ScheduleFragment();
        mainAdapter.addItem(schFragment);

        CreditFragment creFragment = new CreditFragment();
        mainAdapter.addItem(creFragment);

        pager.setAdapter(mainAdapter);

        boardBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent borIntent = new Intent(getApplicationContext(), FreeBoardListActivity.class);
                startActivity(borIntent);
            }
        });

        schBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent schIntent = new Intent(getApplicationContext(), scheduleActivity.class);
                startActivity(schIntent);
            }
        });
    }

    public void moveTodoActivity(View view){
        Intent todoIntent = new Intent(getApplicationContext(), ToDoActivity.class);
        startActivity(todoIntent);
    }

}