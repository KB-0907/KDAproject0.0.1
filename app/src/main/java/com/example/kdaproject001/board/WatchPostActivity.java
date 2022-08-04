package com.example.kdaproject001.board;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kdaproject001.R;
import com.example.kdaproject001.comments.CommentAdapter;
import com.example.kdaproject001.comments.CommentInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class WatchPostActivity extends AppCompatActivity {
    public final static String TAG = "WatchPostActivity";
    TextView postTitle, postContents;
    Button coReBtn;
    ImageButton postMenu;
    FirebaseFirestore db = FirebaseFirestore.getInstance(); //파이어 베이스 파이어스토어를 사용하기 위한 변수 생성 및 할당
    String postUserID;
    private FirebaseUser commentUser;
    RecyclerView commentRecyclerView;
    private ArrayList<CommentInfo> commentInfo; // PostInfo 로 실제 입력 받은 글을 파이어 스토어에 널기 위한 ArrayList 변수 생성
    CommentAdapter commentAdapter;
    EditText commentText;
    String boardSort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_post);

        commentText = findViewById(R.id.comment_text);
        postTitle = findViewById(R.id.board_sort_title);
        postContents = findViewById(R.id.post_contents);
        postMenu = findViewById(R.id.post_menu23);
        coReBtn = findViewById(R.id.comment_register_btn);
        commentRecyclerView = findViewById(R.id.comments_recyclerview);

        Intent genIntent = getIntent();
        String getPostID = genIntent.getStringExtra("ClickPostID"); // PostAdapter 로 부터 받은 선택한 문서 ID 변수에 저장
        boardSort = getIntent().getStringExtra("boardSort");

        generatePost(getPostID);

        postMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(postMenu, getPostID);
            }
        });

        coReBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createComment(getPostID);
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        //현재 누른 게시글과 댓글에 저장된 댓글을 단 게시글 아이디가 같을 경우 표시
        recyclerUpdate();
    }

    private void recyclerUpdate(){
        Intent genIntent = getIntent();
        String getPostID = genIntent.getStringExtra("ClickPostID"); // 현재 보고 있는 게시글 아이디
        Log.d("댓글", getPostID);

        db.collection("comments").orderBy("comCreated", Query.Direction.ASCENDING) //파이어베이스에서 모든 포스트의 문서(글)룰 생성일자순으로 가져옴
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            commentInfo = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                if (getPostID.equals(document.getData().get("comPostID").toString())){
                                    commentInfo.add(new CommentInfo(document.getData().get("comment").toString(), //postList 에 가져온 데이터 추가
                                            document.getId(),
                                            document.getData().get("comPostID").toString(),
                                            (Long) document.getData().get("comCreated")));
                                }else {
                                    Log.d("결과", "실패");
                                }

                            }
                            LinearLayoutManager layoutManager = new LinearLayoutManager(WatchPostActivity.this, LinearLayoutManager.VERTICAL, false);
                            commentRecyclerView.setLayoutManager(layoutManager);
                            commentAdapter = new CommentAdapter(commentInfo); //리싸이클러뷰를 보이게 하기 위한 어댑터 생성
                            commentRecyclerView.setAdapter(commentAdapter); //리싸이클러뷰에 위에서 생성한 어댑터 설정

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void generatePost(String getPostID){
        DocumentReference docRef = db.collection(boardSort).document(getPostID); // 선택한 문서 ID 로 그 ID 에 해당하는 문서의 제목과 내용을 가져오는 코드
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    String getPostTitle = document.getData().get("title").toString();
                    String getPostContents = document.getData().get("content").toString();
                    postTitle.setText(getPostTitle);
                    postContents.setText(getPostContents);
                    postUserID = document.getData().get("publisher").toString();
                } else {
                    Log.d(TAG, "No such document");
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });
    }

    public void showMenu(View view, String getPostId){
        PopupMenu popupMenu = new PopupMenu(getApplicationContext(), postMenu);
        popupMenu.inflate(R.menu.post_pop_menu);
        MenuItem moItem = popupMenu.getMenu().findItem(R.id.modify);
        MenuItem deItem = popupMenu.getMenu().findItem(R.id.delete);
        MenuItem reItem = popupMenu.getMenu().findItem(R.id.report);

        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(postUserID)){
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
                        intent.putExtra("boardSort", boardSort);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.delete:// 정말 삭제하시겠습니까? 질문박스 띄우기
                        db.collection(boardSort).document(getPostId)
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

    private void createComment(String getPostID){
        final String comment = commentText.getText().toString();

        if (comment.length() > 0){
            commentUser = FirebaseAuth.getInstance().getCurrentUser();
            CommentInfo commentInfo = new CommentInfo(comment, commentUser.getUid(), getPostID, System.currentTimeMillis());
            uploadComment(commentInfo);
        } else {
            Toast.makeText(this, "댓글 내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadComment(CommentInfo commentInfo){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("comments").add(commentInfo)
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
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(commentText.getWindowToken(), 0);
        commentText.setText(null);
        recyclerUpdate();
    }

    public void finishAct(View view){
        finish();
    }

}