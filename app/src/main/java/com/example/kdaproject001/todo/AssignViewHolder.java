package com.example.kdaproject001.todo;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kdaproject001.R;

public class AssignViewHolder extends RecyclerView.ViewHolder {
    TextView todoTitleText, todoDeadLineText, todoTime;

    public AssignViewHolder(@NonNull View itemView) {
        super(itemView);
        todoTitleText = itemView.findViewById(R.id.assignment_title);
        todoDeadLineText = itemView.findViewById(R.id.assignment_deadline);
        todoTime = itemView.findViewById(R.id.assignment_time);
    }

    public void setAssignTodo(TodoInfo todoInfo){
        String timeText = String.format("%02d:%02d", todoInfo.getHour(), todoInfo.getMinute());
        todoTitleText.setText(todoInfo.getTitle());
        todoDeadLineText.setText(todoInfo.getDeadline());
        todoTime.setText(timeText);
    }
}
