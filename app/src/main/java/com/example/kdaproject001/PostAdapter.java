package com.example.kdaproject001;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kdaproject001.board.PostInfo;
import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {
    private ArrayList<PostInfo> posts;
    private Activity activity;

    public PostAdapter(Activity activity, ArrayList<PostInfo> posts){
        this.activity = activity;
        this.posts = posts;

    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.activity_post_item, parent ,false);
        return new PostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        PostInfo postInfo = posts.get(position);
        holder.titleText.setText(posts.get(position).getTitle());
        holder.contentText.setText(posts.get(position).getContent());
        holder.postItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//게시판 올린 포스트 클릭시 작성된 글 보기 화면으로 이동

            }
        });


    }


    @Override
    public int getItemCount() {
        return posts.size();
    }
}
