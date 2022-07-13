package com.example.kdaproject001;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kdaproject001.board.BoardListActivity;
import com.example.kdaproject001.mainViewPager.CreditFragment;
import com.example.kdaproject001.mainViewPager.ScheduleFragment;

public class MainActivity extends AppCompatActivity{
    Button schButton, boardBnt;
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        schButton = findViewById(R.id.schedule);
        boardBnt = findViewById(R.id.move_to_board_btn);
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
                Intent borIntent = new Intent(getApplicationContext(), BoardListActivity.class);
                startActivity(borIntent);
            }
        });

        schButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent schIntent = new Intent(getApplicationContext(), scheduleActivity.class);
                startActivity(schIntent);
            }
        });
    }
}