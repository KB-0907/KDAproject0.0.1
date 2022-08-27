package com.example.kdaproject001.comments;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kdaproject001.R;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> implements CommentItemListener {
    private ArrayList<CommentInfo> comments;
    Activity activity;
    FragmentManager fragmentManager;

    public CommentAdapter(ArrayList<CommentInfo> commentInfo, FragmentManager fragmentManager) {
        this.comments = commentInfo;
        this.fragmentManager = fragmentManager;
    }


    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_comment, parent ,false);
        return new CommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        CommentInfo commentInfo = comments.get(position);
        holder.commentText.setText(comments.get(position).getComment());
        holder.coReportBtn.setOnClickListener(v -> {
            CommentReportFragment fragment = new CommentReportFragment();
            fragment.show(fragmentManager, "dialog");
            //댓글 신고 프래그먼트 띄우기
        });
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}
