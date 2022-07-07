package com.example.kdaproject001;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.zardozz.FixedHeaderTableLayout.FixedHeaderSubTableLayout;
import com.github.zardozz.FixedHeaderTableLayout.FixedHeaderTableLayout;
import com.github.zardozz.FixedHeaderTableLayout.FixedHeaderTableRow;

import java.lang.ref.SoftReference;
import java.util.Locale;
import java.util.Random;

public class makeSchedule extends AppCompatActivity {
    private FixedHeaderTableLayout fixedHeaderTableLayout;
    private ProgressBar pgsBar;
    FixedHeaderSubTableLayout mainTable;
    FixedHeaderSubTableLayout columnHeaderTable;
    FixedHeaderSubTableLayout rowHeaderTable;
    FixedHeaderSubTableLayout cornerTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_schedule);

        fixedHeaderTableLayout = findViewById(R.id.FixedHeaderTableLayout);
        pgsBar = findViewById(R.id.pBar);

        GenerateTables generateTables = new GenerateTables(this);
        generateTables.execute();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Redraw screen calculating the new boundaries without new pan or scale
            fixedHeaderTableLayout.calculatePanScale(0,0, 0, 0, 1f);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            // Redraw screen calculating the new boundaries without new pan or scale
            fixedHeaderTableLayout.calculatePanScale(0,0, 0, 0, 1f);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GenerateTables extends AsyncTask<Void, Integer, Void> {

        private final SoftReference<makeSchedule> activityReference;
        private final Context mContext;

        // only retain a soft reference to the activity
        GenerateTables(makeSchedule context) {
            mContext = context;
            activityReference = new SoftReference<>(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // get a reference to the activity if it is still there
            makeSchedule activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            activity.pgsBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            createTable(mContext);
            return null;
        }


        @Override
        protected void onPostExecute(Void param) {

            // get a reference to the activity if it is still there
            makeSchedule activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            // Setup FixHeader Table
            fixedHeaderTableLayout.addViews(mainTable, columnHeaderTable, rowHeaderTable, cornerTable);

            activity.pgsBar.setVisibility(View.GONE);
        }
    }

    private static final String ALLOWED_CHARACTERS ="qwertyuiopasdfghjklzxcvbnm";


    private static String getRandomString(int maxLength)
    {
        final Random random=new Random();
        final int sizeOfRandomString = random.nextInt(maxLength);
        final StringBuilder sb=new StringBuilder(sizeOfRandomString);
        for(int i=0;i<sizeOfRandomString;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }

    private void createTable(Context mContext){
        // Create our 4 Sub Tables
        mainTable = new FixedHeaderSubTableLayout(mContext);
        // 25 x 5 in size
        float textSize = 20.0f;
        for (int i = 1; i <=9; i++) {
            FixedHeaderTableRow tableRowData = new FixedHeaderTableRow(mContext);
            // Add some data
            for (int j = 1; j <= 5; j++) {
                // Add a Textview
                TextView textView = new TextView(mContext);
                textView.setGravity(Gravity.CENTER);
                textView.setText(String.format(Locale.ROOT,"%s", j, i, getRandomString(j)));
                textView.setBackgroundResource(R.drawable.list_border);
                textView.setPadding(5 ,5,5,5);
                textView.setTextSize(textSize * 1.5f);
                textView.setTextColor(Color.parseColor("#000000"));
                textView.setOnClickListener(v -> {
                    if (v.isSelected()){
                        v.setSelected(false);
                        v.setBackgroundResource(R.drawable.list_border);
                    } else {
                        v.setSelected(true);
                        v.setBackgroundResource(R.drawable.selected_border);
                    }
                });
                tableRowData.addView(textView);
            }
            mainTable.addView(tableRowData);
        }

        columnHeaderTable = new FixedHeaderSubTableLayout(mContext);
        // 2 x 5 in size
        for (int i = 1; i <= 1; i++) {
            FixedHeaderTableRow tableRowData = new FixedHeaderTableRow(mContext);
            // Add some data
            for (int j = 1; j <= 5; j++) {
                // Add a Textview
                TextView textView = new TextView(mContext);
                textView.setGravity(Gravity.CENTER);
                textView.setText(String.format(Locale.ROOT,"C%d:%d", i, j));
                textView.setBackgroundResource(R.drawable.list_border);
                textView.setPadding(5 ,5,5,5);
                textView.setTextSize(textSize);
                textView.setTextColor(Color.parseColor("#000000"));
                textView.setOnClickListener(v -> {
                    if (v.isSelected()){
                        v.setSelected(false);
                        v.setBackgroundResource(R.drawable.list_border);
                    } else {
                        v.setSelected(true);
                        v.setBackgroundResource(R.drawable.selected_border);
                    }
                });
                tableRowData.addView(textView);
            }
            columnHeaderTable.addView(tableRowData);
        }
        columnHeaderTable.setBackgroundResource(R.drawable.bottom_border);

        rowHeaderTable = new FixedHeaderSubTableLayout(mContext);
        // 25 x 1 in size
        for (int i = 1; i <= 9; i++) {
            FixedHeaderTableRow tableRowData = new FixedHeaderTableRow(mContext);
            // Add some data
            for (int j = 1; j <= 1; j++) {
                // Add a Textview
                TextView textView = new TextView(mContext);
                textView.setGravity(Gravity.CENTER);
                textView.setText(String.format(Locale.ROOT,"R%d", i));
                textView.setBackgroundResource(R.drawable.list_border);
                textView.setPadding(5 ,5,5,5);
                textView.setTextSize(textSize);
                textView.setTextColor(Color.parseColor("#000000"));
                textView.setOnClickListener(v -> {
                    if (v.isSelected()){
                        v.setSelected(false);
                        v.setBackgroundResource(R.drawable.list_border);
                    } else {
                        v.setSelected(true);
                        v.setBackgroundResource(R.drawable.selected_border);
                    }
                });
                tableRowData.addView(textView);
            }
            rowHeaderTable.addView(tableRowData);
        }
        rowHeaderTable.setBackgroundResource(R.drawable.right_border);

        cornerTable = new FixedHeaderSubTableLayout(mContext);
        // 2 x 1 in size
        for (int i = 1; i <= 1; i++) {
            FixedHeaderTableRow tableRowData = new FixedHeaderTableRow(mContext);
            // Add some data
            for (int j = 1; j <= 1; j++) {
                // Add a Textview
                TextView textView = new TextView(mContext);
                textView.setGravity(Gravity.CENTER);
                textView.setText(String.format(Locale.ROOT,"A%d:%d",i , j));
                textView.setBackgroundResource(R.drawable.list_border);
                textView.setPadding(5 ,5,5,5);
                textView.setTextSize(textSize * 1.5f);
                textView.setTextColor(Color.parseColor("#000000"));
                textView.setOnClickListener(v -> {
                    if (v.isSelected()){
                        v.setSelected(false);
                        v.setBackgroundResource(R.drawable.list_border);
                    } else {
                        v.setSelected(true);
                        v.setBackgroundResource(R.drawable.selected_border);
                    }
                });
                tableRowData.addView(textView);
            }
            cornerTable.addView(tableRowData);
        }
        cornerTable.setBackgroundResource(R.drawable.corner_border);

        fixedHeaderTableLayout.setMinScale(0.1f);
    }


}