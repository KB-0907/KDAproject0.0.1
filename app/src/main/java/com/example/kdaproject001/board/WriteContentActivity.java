package com.example.kdaproject001.board;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kdaproject001.R;
import com.example.kdaproject001.account.Login;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class WriteContentActivity extends AppCompatActivity {
    Button writePostBtn;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_content);

        writePostBtn = findViewById(R.id.writeButton);
        writePostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WritePost();
            }
        });
    }

    private void WritePost(){
        final String title = ((EditText) findViewById(R.id.post_title_et)).getText().toString();
        final String contents = ((EditText)findViewById(R.id.post_content_et)).getText().toString();

        if (title.length() > 0 && contents.length() > 0){
            PostInfo postInfo = new PostInfo(title, contents);
            uploader(postInfo);
        }else {
            Toast.makeText(this, "제목과 내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploader(PostInfo postInfo){
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
    }

}