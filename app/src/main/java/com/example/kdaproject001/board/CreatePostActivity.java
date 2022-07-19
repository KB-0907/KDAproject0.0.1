package com.example.kdaproject001.board;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kdaproject001.PostAdapter;
import com.example.kdaproject001.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreatePostActivity extends AppCompatActivity {
    Button writePostBtn;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        writePostBtn = findViewById(R.id.writeButton);
        writePostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatePost();
            }
        });
    }

    private void CreatePost(){
        final String title = ((EditText) findViewById(R.id.post_title_et)).getText().toString();
        final String contents = ((EditText)findViewById(R.id.post_content_et)).getText().toString();
        String ste = "1";

        if (title.length() > 0 && contents.length() > 0){
            user = FirebaseAuth.getInstance().getCurrentUser(); //파이어 베이스에서 현재 로그인한 유저의 UID
            PostInfo postInfo = new PostInfo(title, contents, user.getUid(), ste, System.currentTimeMillis());
            //현재 로그인 사용자 UID 받아 변수로 저장 후 PostInfo 에 등록
            uploader(postInfo);
        }else {
            Toast.makeText(this, "제목과 내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploader(PostInfo postInfo){// 실제 파이어베이스 파이어스토어의 posts 컬렉션에 작성된 글 등록 함수
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts").add(postInfo)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });

        finish();
    }

}