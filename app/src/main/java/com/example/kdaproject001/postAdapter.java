package com.example.kdaproject001;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kdaproject001.board.PostInfo;
import java.util.ArrayList;

public class postAdapter extends RecyclerView.Adapter<postViewHolder> {
    private ArrayList<PostInfo> posts;
    private Activity activity;

    public postAdapter(Activity activity, ArrayList<PostInfo> posts){
        this.activity = activity;
        this.posts = posts;

    }

    @NonNull
    @Override
    public postViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.activity_post_item, parent ,false);
        return new postViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull postViewHolder holder, int position) {
        PostInfo postInfo = posts.get(position);
        holder.titleText.setText(posts.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
