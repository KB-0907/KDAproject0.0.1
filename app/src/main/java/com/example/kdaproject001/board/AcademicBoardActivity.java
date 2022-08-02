package com.example.kdaproject001.board;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.kdaproject001.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AcademicBoardActivity extends AppCompatActivity {

    public final static String TAG = "AcademicBoardListActivity";
    FirebaseFirestore db = FirebaseFirestore.getInstance(); //파이어 베이스 파이어스토어를 사용하기 위한 변수 생성 및 할당
    PostAdapter mAdapter;
    private ArrayList<PostInfo> postList; // PostInfo 로 실제 입력 받은 글을 파이어 스토어에 널기 위한 ArrayList 변수 생성
    RecyclerView postRecyclerView;
    ImageButton beforeBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acadamic_board);
        postRecyclerView = findViewById(R.id.post_recyclerview);

        beforeBtn = findViewById(R.id.before_btn);
        beforeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        db.collection("posts").orderBy("created", Query.Direction.DESCENDING) //파이어베이스에서 모든 포스트의 문서(글)룰 생성일자순으로 가져옴
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            postList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                postList.add(new PostInfo(document.getData().get("title").toString(), //postList 에 가져온 데이터 추가
                                        document.getData().get("content").toString(),
                                        document.getData().get("publisher").toString(),
                                        document.getId(),
                                        (Long) document.getData().get("created")
                                ));
                            }
                            LinearLayoutManager layoutManager = new LinearLayoutManager(AcademicBoardActivity.this, LinearLayoutManager.VERTICAL, false);
                            postRecyclerView.setLayoutManager(layoutManager);
                            mAdapter = new PostAdapter(AcademicBoardActivity.this, postList, getApplicationContext()); //리싸이클러뷰를 보이게 하기 위한 어댑터 생성
                            postRecyclerView.setAdapter(mAdapter); //리싸이클러뷰에 위에서 생성한 어댑터 설정
                        } else {
                            finish();
                        }
                    }
                });
    }

    public void CreatePostBtn(View view){
        Intent intent = new Intent(getApplicationContext(), CreatePostActivity.class);
        //인텐트애 학업게시판에서 호출햇다는 데이터 놓고 전달고
        //아카데미디 영어로 보내고
        startActivity(intent);
    }
}








