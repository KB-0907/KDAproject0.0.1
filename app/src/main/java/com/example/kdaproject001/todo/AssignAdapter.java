package com.example.kdaproject001.todo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kdaproject001.R;
import com.example.kdaproject001.board.PostViewHolder;

import java.util.ArrayList;

public class AssignAdapter extends RecyclerView.Adapter<AssignViewHolder> {
    ArrayList<String> assigns;
    private Activity activity;
    private Context context;

    public AssignAdapter(ToDoActivity toDoActivity, ArrayList<String> dataList, Context applicationContext) {
        this.activity = toDoActivity;
        this.assigns = dataList;
        this.context = applicationContext;
    }

    @NonNull
    @Override
    public AssignViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View assignItemView = inflater.inflate(R.layout.item_assignment, parent ,false);
        return new AssignViewHolder(assignItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignViewHolder holder, int position) {
        String assign = assigns.get(position);
        holder.todoTitleText.setText(assign);
    }

    @Override
    public int getItemCount() {
        return assigns.size();
    }
}
