package com.example.kdaproject001.comments;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kdaproject001.R;


public class CommentViewHolder extends RecyclerView.ViewHolder {
    TextView commentText;
    CardView commentCardView;

    public CommentViewHolder(@NonNull View itemView) {
        super(itemView);
        commentCardView = itemView.findViewById(R.id.comment_item);
        commentText = itemView.findViewById(R.id.comment_content);
    }
}
