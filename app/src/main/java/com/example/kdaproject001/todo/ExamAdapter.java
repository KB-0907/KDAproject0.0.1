package com.example.kdaproject001.todo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kdaproject001.R;

import java.util.ArrayList;


public class ExamAdapter extends RecyclerView.Adapter<ExamViewHolder> {
    ArrayList<String> exams;
    private Activity activity;
    private Context context;

    public ExamAdapter(ToDoActivity toDoActivity, ArrayList<String> dataList, Context applicationContext) {
        this.activity = toDoActivity;
        this.exams = dataList;
        this.context = applicationContext;
    }

    @NonNull
    @Override
    public ExamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View examItemView = inflater.inflate(R.layout.item_exam, parent ,false);
        return new ExamViewHolder(examItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExamViewHolder holder, int position) {
        String exam = exams.get(position);
        holder.todoTitleText.setText(exam);
    }

    @Override
    public int getItemCount() {
        return exams.size();
    }
}




