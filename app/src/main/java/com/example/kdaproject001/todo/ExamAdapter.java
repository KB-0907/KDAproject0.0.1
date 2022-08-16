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

public class ExamAdapter extends RecyclerView.Adapter<ExamViewHolder> implements ItemTouchHelperListener{
    ArrayList<TodoInfo> todos;
    FirebaseFirestore db = FirebaseFirestore.getInstance(); // 파이어 베이스 파이어스토어를 사용하기 위한 변수 생성 및 할당
    private Activity activity;
    private Context context;

    public ExamAdapter(ToDoActivity toDoActivity, ArrayList<TodoInfo> todos, Context applicationContext) {
        this.activity = toDoActivity;
        this.todos = todos;
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
        TodoInfo todoInfo = todos.get(position);

        holder.setExamTodo(todoInfo);
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
        Log.d("상태", String.valueOf(position));
        String todoID = todos.get(position).getTodoID();
        ItemRemove(todoID);//파이어베이스에서 삭제
        todos.remove(position);
        notifyItemRemoved(position);
        Log.d("아이디", todoID);
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




