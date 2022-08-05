package com.example.kdaproject001;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kdaproject001.board.FreeBoardListActivity;
import com.example.kdaproject001.board.PostAdapter;
import com.example.kdaproject001.board.PostInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class CreditActivity extends AppCompatActivity {
    public final static String TAG = "CreditActivity";
    EditText self_learn_credit,major_credit,general_credit,culture_credit,certificate_credit1,certificate_credit2,certificate_credit3,etc_credit;
    TextView totalCredit,totalCertificate,final_total_credit,self_learn_credit_name,etc_name;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    Button button, allClear;
    FirebaseFirestore db = FirebaseFirestore.getInstance(); //파이어 베이스 파이어스토어를 사용하기 위한 변수 생성 및 할당




    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);

        major_credit = findViewById(R.id.major_credit);
        general_credit = findViewById(R.id.general_credit);
        culture_credit = findViewById(R.id.culture_credit);
        totalCredit = findViewById(R.id.total_credit);
        certificate_credit1 = findViewById(R.id.certificate_credit1);
        certificate_credit2 = findViewById(R.id.certificate_credit2);
        certificate_credit3 = findViewById(R.id.certificate_credit3);
        totalCertificate = findViewById(R.id.total_certificate);
        self_learn_credit = findViewById(R.id.self_learn_credit);
        final_total_credit = findViewById(R.id.final_total_credit);
        etc_credit = findViewById(R.id.etc_credit);

        // Intent getIntent = getIntent();
        // boardSort = getIntent.getStringExtra("boardSort");

        button = findViewById(R.id.save_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCredit();
            }
        });

        allClear = findViewById(R.id.all_clear_btn);
        allClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        generateCredit();

    }
    public void finishAct(View view){
        finish();
    }

    private void generateCredit(){
        db.collection("Credit")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                if (user.getUid().equals(document.getData().get("writer").toString())){
                                   major_credit.setText(document.getData().get("majorCredit").toString());
                                    // major_credit.setText();
                                    // general_credit,culture_credit,certificate_credit1,certificate_credit2,certificate_credit3,etc_credit;
                                    // TextView totalCredit,totalCertificate,final_total_credit,self_learn_credit_name,etc_name;
                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void saveCredit() {
        String majorPStr = major_credit.getText().toString();
        String generalPStr = general_credit.getText().toString();
        String cultureStr = culture_credit.getText().toString();
        String certificate1 = certificate_credit1.getText().toString();
        String certificate2 = certificate_credit2.getText().toString();
        String certificate3 = certificate_credit3.getText().toString();
        String selfLearnStr = self_learn_credit.getText().toString();
        String etcStr = etc_credit.getText().toString();
        String certificate_name1 = ((EditText)findViewById(R.id.certificate_name1)).getText().toString();
        String certificate_name2 = ((EditText)findViewById(R.id.certificate_name2)).getText().toString();
        String certificate_name3 = ((EditText)findViewById(R.id.certificate_name3)).getText().toString();

        try {
            int majorP = Integer.parseInt(majorPStr);
            int generalP = Integer.parseInt(generalPStr);
            int cultureP = Integer.parseInt(cultureStr);
            int totalP = majorP+generalP+cultureP;
            int certificate1P = Integer.parseInt(certificate1);
            int certificate2P = Integer.parseInt(certificate2);
            int certificate3P = Integer.parseInt(certificate3);
            int totalCertificateN = certificate1P+certificate2P+certificate3P;
            int selfLearnP = Integer.parseInt(selfLearnStr);
            int etcP = Integer.parseInt(etcStr);
            int finalP = totalP+totalCertificateN+etcP+selfLearnP;


            String totalStr = String.valueOf(totalP);
            totalCredit.setText("평가인정 대상 : " + totalStr + " 학점");

            String totalCertificateP = String.valueOf(totalCertificateN);
            totalCertificate.setText("자격증 : " + totalCertificateP + " 학점");

            String selfLearnCreditStr = String.valueOf(selfLearnP);
            self_learn_credit.setText(selfLearnCreditStr);

            String etcN = String.valueOf(etcP);
            etc_credit.setText(etcN);

            String finalNStr = String.valueOf(finalP);
            final_total_credit.setText("최종 학점 : "+ finalNStr + " 학점");

            user = FirebaseAuth.getInstance().getCurrentUser(); //파이어 베이스에서 현재 로그인한 유저의 UID
            MyCreditInfo myCreditInfo = new MyCreditInfo(certificate1, certificate2, certificate3, certificate_name1,
                    certificate_name2, certificate_name3, cultureStr, etcStr, majorPStr, generalPStr, selfLearnStr ,user.getUid());

            uploadMyCreditInfo(myCreditInfo);


        } catch (NumberFormatException e){
            Toast.makeText(getApplicationContext(), "학점을 입력해 주세요.", Toast.LENGTH_SHORT).show();

        } catch (Exception e){
        }

    }

    private void uploadMyCreditInfo(MyCreditInfo myCreditInfo){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Credit").document(user.getUid())
                .set(myCreditInfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }
}