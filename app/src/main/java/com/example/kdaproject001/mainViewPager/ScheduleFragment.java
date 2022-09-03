package com.example.kdaproject001.mainViewPager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kdaproject001.R;
import com.example.kdaproject001.schedule.Schedule;
import com.example.kdaproject001.schedule.Sticker;
import com.example.kdaproject001.schedule.Time;
import com.example.kdaproject001.schedule.TimetableView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class ScheduleFragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user;
    private TimetableView timetable;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    TextView class1, class2;
    int weekDayInt;
    String[] todayClassData = new String[5];
    int a = 0;


    public ScheduleFragment() {
    }

    public static ScheduleFragment newInstance(String param1, String param2) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        class1 = view.findViewById(R.id.class1);
        class2 = view.findViewById(R.id.class2);
        CheckTodayClass();
        return view;
    }

    public void CheckTodayClass(){
        weekDayInt = CheckToday();
        loadSchedule();
        Log.d("=================", String.valueOf(weekDayInt));
    }


    public int CheckToday(){
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat weekdayFormat = new SimpleDateFormat("EE", Locale.getDefault());
        int dayInt = 0;
        String weekDay = weekdayFormat.format(currentTime);
        String[] weekDays = {"월", "화", "수", "목", "금"};
        for (String i : weekDays){
            if (weekDay.equals(i)){
                switch (i){
                    case "월" :
                        dayInt = 0;
                        break;
                    case "화" :
                        dayInt = 1;
                        break;
                    case "수" :
                        dayInt = 2;
                        break;
                    case "목" :
                        dayInt = 3;
                        break;
                    case "금" :
                        dayInt = 4;
                        break;
                }
            }
        }
        return dayInt;
        //   Toast.makeText(getContext(), String.valueOf(weekDayInt), Toast.LENGTH_SHORT).show();
    }


    public void loadSchedule(){//메인 뷰페이저 프래그먼트에서 스케줄 데이터 로드  timetable.load(savedData);
        if (timetable != null){
            timetable.removeAll();
        }
        user = FirebaseAuth.getInstance().getCurrentUser(); //파이어 베이스에서 현재 로그인한 유저의 UID
        DocumentReference docRef = db.collection("Schedules").document(user.getUid()); // 선택한 문서 ID 로 그 ID 에 해당하는 문서의 제목과 내용을 가져오는 코드
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                    String savedData = document.getData().get("data").toString();
                    loadSticker(savedData);
                } else {
                    Log.d("TAG", "No such document");
                }
            } else {
                Log.d("TAG", "get failed with ", task.getException());
            }
        });
    }

    public void loadSticker(String json){
        HashMap<Integer, Sticker> stickers = new HashMap<Integer,Sticker>();
        JsonParser parser = new JsonParser();
        JsonObject obj1 = (JsonObject)parser.parse(json);
        JsonArray arr1 = obj1.getAsJsonArray("sticker");
        for(int i = 0 ; i < arr1.size(); i++){
            Sticker sticker = new Sticker();
            JsonObject obj2 = (JsonObject)arr1.get(i);
            int idx = obj2.get("idx").getAsInt();
            JsonArray arr2 = (JsonArray)obj2.get("schedule");
            for(int k = 0 ; k < arr2.size(); k++){
                Schedule schedule = new Schedule();
                JsonObject obj3 = (JsonObject)arr2.get(k);
                if (obj3.get("day").getAsInt() == weekDayInt){ //파이어베이스에 있는 시간표 요일 데이터와 오늘 요일
                    todayClassData[a] = (obj3.get("classTitle").getAsString() + obj3.get("classPlace").getAsString());
                    a = a + 1;
                    Log.d("-----------------------", todayClassData[0]);

                }
                schedule.setClassTitle(obj3.get("classTitle").getAsString());
                schedule.setClassPlace(obj3.get("classPlace").getAsString());
                schedule.setProfessorName(obj3.get("professorName").getAsString());
                schedule.setDay(obj3.get("day").getAsInt());
                Time startTime = new Time();
                JsonObject obj4 = (JsonObject)obj3.get("startTime");
                startTime.setHour(obj4.get("hour").getAsInt());
                startTime.setMinute(obj4.get("minute").getAsInt());
                Time endTime = new Time();
                JsonObject obj5 = (JsonObject)obj3.get("endTime");
                endTime.setHour(obj5.get("hour").getAsInt());
                endTime.setMinute(obj5.get("minute").getAsInt());
                schedule.setStartTime(startTime);
                schedule.setEndTime(endTime);
                sticker.addSchedule(schedule);
            }
            stickers.put(idx,sticker);
        }
        setClassText();
    }

    public void setClassText(){
        if (todayClassData[0] == null && todayClassData[1] == null && todayClassData[2]
                == null && todayClassData[3] == null && todayClassData[4] == null){
            class1.setText("강의 없음");

        } else {
            class1.setText(todayClassData[0]);
            class2.setText(todayClassData[1]);
        }
    }

}