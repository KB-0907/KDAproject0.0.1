package com.example.kdaproject001.board;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.kdaproject001.R;
import com.example.kdaproject001.postAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BoardListActivity extends AppCompatActivity {
    public final static String TAG = "BoardListActivity";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borad_list);



        ArrayList<PostInfo> postList = new ArrayList<>();

        db.collection("posts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                postList.add(new PostInfo(document.getData().get("title").toString(),
                                        document.getData().get("content").toString()));
                            }
                            RecyclerView postRecyclerView;
                            postRecyclerView = findViewById(R.id.post_recyclerview);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(BoardListActivity.this, LinearLayoutManager.VERTICAL, false);
                            postRecyclerView.setLayoutManager(layoutManager);

                            RecyclerView.Adapter mAdapter = new postAdapter(BoardListActivity.this, postList);
                            postRecyclerView.setAdapter(mAdapter);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }


    public void moveToContent(View view){
        Intent intent = new Intent(getApplicationContext(), WriteContentActivity.class);
        startActivity(intent);
    }
}