package com.example.kdaproject001.board;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kdaproject001.CreditActivity;
import com.example.kdaproject001.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {
    private ArrayList<PostInfo> posts;
    private Activity activity;
    private Context context;
    boolean auth;
    long delay;
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("KAD");



    public PostAdapter(Activity activity, ArrayList<PostInfo> posts, Context context){
        this.activity = activity;
        this.posts = posts;
        this.context = context;
    }


    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View postItemView = inflater.inflate(R.layout.item_post, parent ,false);
        return new PostViewHolder(postItemView);

    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        PostInfo postInfo = posts.get(position);
        holder.titleText.setText(posts.get(position).getTitle());
        holder.contentText.setText(posts.get(position).getContent());
        holder.postItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//게시판 올린 포스트 클릭시 작성된 글 보기 화면으로 이동
                checkAuth(postInfo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void postClick(PostInfo postInfo){
        Intent intent = new Intent(context, WatchPostActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("ClickPostID", postInfo.getPostID()); //포스트 클릭시 클릭한 포스트의 문서 ID를 받아 WatchPostActivity 에 전달
        intent.putExtra("boardSort", postInfo.getBoardSort());
        context.startActivity(intent);
    }


    public void checkAuth(PostInfo postInfo){
        mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e ("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    String authCheck = String.valueOf(task.getResult().child("authentication").getValue());
                    if (authCheck.equals("false")){
                        Log.d("인증 확인", "인증 false");
                        auth = false;
                    } else if(authCheck.equals("true")){
                        Log.d("인증 확인", "인증");
                        auth = true;

                    }else    Log.d("인증 확인", "dbjsdbvjksd;b");
                }

                if (auth == true){
                    postClick(postInfo);
                } else {
                    Log.e("와이","?");
                }

            }
        });
    }
}
