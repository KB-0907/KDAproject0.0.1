package com.example.kdaproject001.todo;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kdaproject001.R;

public class ExamViewHolder extends RecyclerView.ViewHolder {
    TextView todoTitleText,todoDeadLineText,todoStartTimeText;

    public ExamViewHolder(@NonNull View itemView) {
        super(itemView);
        todoTitleText = itemView.findViewById(R.id.exam_title);
        todoDeadLineText = itemView.findViewById(R.id.exam_day);
        todoStartTimeText = itemView.findViewById(R.id.exam_time);
    }

    public void setExamTodo(TodoInfo todoInfo){
        String timeText = String.format("%02d:%02d", todoInfo.getHour(), todoInfo.getMinute());
        todoTitleText.setText(todoInfo.getTitle());
        todoDeadLineText.setText(todoInfo.getDeadline());
        todoStartTimeText.setText(timeText);
    }
}