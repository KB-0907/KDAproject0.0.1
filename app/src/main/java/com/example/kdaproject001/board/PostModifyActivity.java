package com.example.kdaproject001.board;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kdaproject001.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PostModifyActivity extends AppCompatActivity {
    public final static String TAG = "PostModifyActivity";
    FirebaseFirestore db = FirebaseFirestore.getInstance(); //파이어 베이스 파이어스토어를 사용하기 위한 변수 생성 및 할당
    Button modifyBtn;
    EditText moTitle, moContents;
    String getPostID, boardSort;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_post);

        moTitle = findViewById(R.id.mo_post_title);
        moContents = findViewById(R.id.mo_post_content);
        modifyBtn = findViewById(R.id.modify_Button);

        modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fireStoreUpdate();
            }
        });

        Intent moIntent = getIntent();
        getPostID = moIntent.getStringExtra("PostID");
        boardSort = moIntent.getStringExtra("boardSort");

        modifyGenerate(getPostID, boardSort);
    }

    public void modifyGenerate(String getPostID, String boardSort){
        DocumentReference docRef = db.collection(boardSort).document(getPostID); // 선택한 문서 ID 로 그 ID 에 해당하는 문서의 제목과 내용을 가져오는 코드
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        moTitle.setText(document.getData().get("title").toString());
                        moContents.setText(document.getData().get("content").toString());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    private void fireStoreUpdate(){
        Map<String, Object> data = new HashMap<>();
        data.put("title", moTitle.getText().toString());
        data.put("content", moContents.getText().toString());

        DocumentReference washingtonRef = db.collection(boardSort).document(getPostID);
        washingtonRef
                .update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });
        finish();
    }

}