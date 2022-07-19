package com.example.kdaproject001;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kdaproject001.account.Login;
import com.example.kdaproject001.board.BoardListActivity;
import com.example.kdaproject001.board.ModifyPostActivity;
import com.example.kdaproject001.board.PostInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.acl.Group;

public class WatchPostActivity extends AppCompatActivity {
    public final static String TAG = "WatchPostActivity";
    TextView postTitle, postContents;
    ImageButton postMenu;
    FirebaseFirestore db = FirebaseFirestore.getInstance(); //파이어 베이스 파이어스토어를 사용하기 위한 변수 생성 및 할당
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_post);

        postTitle = findViewById(R.id.post_title);
        postContents = findViewById(R.id.post_contents);
        postMenu = findViewById(R.id.post_menu);
        Intent genIntent = getIntent();
        String getPostID = genIntent.getStringExtra("ClickPostID"); // PostAdapter 로 부터 받은 선택한 문서 ID 변수에 저장
        generatePost(getPostID);
        postMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(postMenu, getPostID);
            }
        });

    }

    public void generatePost(String getPostID){
        DocumentReference docRef = db.collection("posts").document(getPostID); // 선택한 문서 ID 로 그 ID 에 해당하는 문서의 제목과 내용을 가져오는 코드
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        String getPostTitle = document.getData().get("title").toString();
                        String getPostContents = document.getData().get("content").toString();
                        postTitle.setText(getPostTitle);
                        postContents.setText(getPostContents);
                        userID = document.getData().get("publisher").toString();
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    public void showMenu(View view, String getPostId){
        PopupMenu popupMenu = new PopupMenu(getApplicationContext(), postMenu);
        popupMenu.inflate(R.menu.post_pop_menu);
        MenuItem moItem = popupMenu.getMenu().findItem(R.id.modify);
        MenuItem deItem = popupMenu.getMenu().findItem(R.id.delete);
        MenuItem reItem = popupMenu.getMenu().findItem(R.id.report);

        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(userID)){
            reItem.setVisible(false);
        } else {
            deItem.setVisible(false);
            moItem.setVisible(false);
        }

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.modify:
                        Intent intent = new Intent(getApplicationContext(), ModifyPostActivity.class);
                        intent.putExtra("PostID", getPostId);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.delete:// 정말 삭제하시겠습니까? 질문박스 띄우기
                        db.collection("posts").document(getPostId)
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error deleting document", e);
                                    }
                                });
                        finish();
                        break;
                    case R.id.report:
                        break;
                }
                return false;
            }
        });popupMenu.show();
    }

}