package com.example.kdaproject001.schedule;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.kdaproject001.R;
import com.example.kdaproject001.board.WatchPostActivity;
import com.example.kdaproject001.comments.CommentAdapter;
import com.example.kdaproject001.comments.CommentInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScheduleActivity extends AppCompatActivity {
    public static final int REQUEST_ADD = 1;
    public static final int REQUEST_EDIT = 2;
    private TimetableView timetable;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        loadSchedule();
        findViewById(R.id.save_schedule).setOnClickListener(viewOnClick);
        findViewById(R.id.add_class_tv).setOnClickListener(viewOnClick);

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

    View.OnClickListener viewOnClick = v -> {
        switch (v.getId()){
            case R.id.add_class_tv:
                Intent intent = new Intent(getApplicationContext(), MakeClassActivity.class);
                intent.putExtra("mode", REQUEST_ADD);
                startActivityForResult(intent, REQUEST_ADD);
                break;
            case R.id.save_schedule:
                uploadSchedule();
                break;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_ADD:
                if (resultCode == MakeClassActivity.RESULT_OK_ADD) {
                    ArrayList<Schedule> item = (ArrayList<Schedule>) data.getSerializableExtra("schedules");
                    timetable.add(item);
                }
                break;
            case REQUEST_EDIT:
                /** Edit -> Submit */
                if (resultCode == MakeClassActivity.RESULT_OK_EDIT) {
                    int idx = data.getIntExtra("idx", -1);
                    ArrayList<Schedule> item = (ArrayList<Schedule>) data.getSerializableExtra("schedules");
                    timetable.edit(idx, item);
                }
                /** Edit -> Delete */
                else if (resultCode == MakeClassActivity.RESULT_OK_DELETE) {
                    int idx = data.getIntExtra("idx", -1);
                    timetable.remove(idx);
                }
                break;
        }
    }

    public void uploadSchedule(){
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser(); //파이어 베이스에서 현재 로그인한 유저의 UID
        Map<String, Object> schedule = new HashMap<>();
        schedule.put("data", timetable.createSaveData());

        db.collection("Schedules").document(user.getUid())
                .set(schedule)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully written!");
                        Toast.makeText(getApplicationContext(), "저장되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error writing document", e);
                    }
                });
    }

    public void loadSchedule(){
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
                    timetable.load(savedData);
                } else {
                    Log.d("TAG", "No such document");
                }
            } else {
                Log.d("TAG", "get failed with ", task.getException());
            }
        });
    }

}
