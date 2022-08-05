package com.example.kdaproject001;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class CreditActivity extends AppCompatActivity {
    EditText self_learn_credit,major_credit,general_credit,culture_credit,certificate_credit1,certificate_credit2,certificate_credit3,etc_credit;
    TextView totalCredit,totalCertificate,final_total_credit,self_learn_credit_name,etc_name;
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



    }
    public void finishAct(View view){
        finish();
    }

    public void saveCredit(View view){
        String majorPStr = major_credit.getText().toString();
        String generalPStr = general_credit.getText().toString();
        String cultureStr = culture_credit.getText().toString();
        String certificate1 = certificate_credit1.getText().toString();
        String certificate2 = certificate_credit2.getText().toString();
        String certificate3 = certificate_credit3.getText().toString();
        String selfLearnStr = self_learn_credit.getText().toString();
        String etcStr = etc_credit.getText().toString();


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


            String certificate_name1 = ((EditText)findViewById(R.id.certificate_name1)).getText().toString();

            String certificate_name2 = ((EditText)findViewById(R.id.certificate_name2)).getText().toString();
            String certificate_name3 = ((EditText)findViewById(R.id.certificate_name3)).getText().toString();
            String planner = ((TextView)findViewById(R.id.planner)).getText().toString();


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

        } catch (NumberFormatException e){
            Toast.makeText(getApplicationContext(), "학점을 입력해 주세요.", Toast.LENGTH_SHORT).show();

        } catch (Exception e){

        }

    }


}