package com.example.kdaproject001.board;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kdaproject001.R;

public class PostViewHolder extends RecyclerView.ViewHolder {
    TextView titleText, contentText;
    CardView postItemView;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
        postItemView = itemView.findViewById(R.id.post_item_view);
        titleText = itemView.findViewById(R.id.card_post_title);
        contentText = itemView.findViewById(R.id.card_post_content);
    }

    public void setItem(String postInfo){
    }
}
