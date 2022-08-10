package com.example.kdaproject001.todo;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kdaproject001.R;
import com.example.kdaproject001.board.PostViewHolder;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AssignAdapter extends RecyclerView.Adapter<AssignViewHolder> implements ItemTouchHelperListener{
    ArrayList<TodoInfo> todos;
    ArrayList<String> assigns;
    ArrayList<String> deadline;
    FirebaseFirestore db = FirebaseFirestore.getInstance(); // 파이어 베이스 파이어스토어를 사용하기 위한 변수 생성 및 할당


    private Activity activity;
    private Context context;

    public AssignAdapter(ToDoActivity toDoActivity, ArrayList<TodoInfo> todos, Context applicationContext) {
        this.activity = toDoActivity;
        this.todos = todos;
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
        TodoInfo todoInfo = todos.get(position);
        holder.todoTitleText.setText(todos.get(position).getTitle());
        holder.todoDeadLineText.setText(todos.get(position).getDeadline());
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    @Override
    public boolean onItemMove(int from_position, int to_position) {
      //  ChattingListDataModel number = assigns.get(from_position); // 이동할 객체 저장
        todos.remove(from_position); // 이동할 객체 삭제
      //  assigns.add(to_position , number); // 이동하고 싶은 position 에 추가
        notifyItemMoved(from_position,to_position); //Adapter에 데이터 이동알림
        return true;
    }

    @Override
    public void onItemSwipe(int position) {
        Log.d("상태", "움직임");
        todos.remove(position);
        notifyItemRemoved(position);
       // String todoID = todos.get(position).getTodoID();
       // ItemRemove(todoID);//파이어베이스에서 삭제
    }

    public void ItemRemove(String todoID){
        db.collection("ToDo").document(todoID)//현재 스와이프한 todo 의 문서 아이디
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error deleting document", e);
                    }
                });
    }

}
