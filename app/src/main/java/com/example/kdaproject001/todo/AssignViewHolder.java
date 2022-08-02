package com.example.kdaproject001.todo;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kdaproject001.R;

public class AssignViewHolder extends RecyclerView.ViewHolder {
    TextView todoTitleText;

    public AssignViewHolder(@NonNull View itemView) {
        super(itemView);
        todoTitleText = itemView.findViewById(R.id.assignment_title);
    }
}
