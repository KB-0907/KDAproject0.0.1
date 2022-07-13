package com.example.kdaproject001;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kdaproject001.board.PostInfo;

public class postViewHolder extends RecyclerView.ViewHolder {
    TextView titleText;

    public postViewHolder(@NonNull View itemView) {
        super(itemView);
        titleText = itemView.findViewById(R.id.post_title);
    }

    public void setItem(String postInfo){
    }
}
