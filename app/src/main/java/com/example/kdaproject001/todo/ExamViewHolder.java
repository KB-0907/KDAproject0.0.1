package com.example.kdaproject001.todo;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kdaproject001.R;

public class ExamViewHolder extends RecyclerView.ViewHolder {
    TextView todoTitleText,todoDeadLineText;

    public ExamViewHolder(@NonNull View itemView) {
        super(itemView);
        todoTitleText = itemView.findViewById(R.id.exam_title);
        todoDeadLineText = itemView.findViewById(R.id.exam_day);
    }
}