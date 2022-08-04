package com.example.kdaproject001;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class CreditActivity extends AppCompatActivity {
    EditText major_credit,general_credit,culture_credit,certificate_credit1,certificate_credit2,certificate_credit3,self_learn_credit,final_total_credit;
    TextView totalCredit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);

        major_credit = findViewById(R.id.major_credit);
        general_credit = findViewById(R.id.general_credit);
        culture_credit = findViewById(R.id.culture_credit);
        totalCredit = findViewById(R.id.total_credit);



       /* int majorP =  Integer.parseInt (((EditText)findViewById(R.id.major_credit)).getText().toString());
        int generalP = Integer.parseInt(((EditText)findViewById(R.id.general_credit)).getText().toString());
        int cultureP = Integer.parseInt(((EditText)findViewById(R.id.culture_credit)).getText().toString());
        int creditTotal = majorP + generalP + cultureP;
        int C1 = Integer.parseInt(((EditText)findViewById(R.id.certificate_credit1)).getText().toString());
        int C2 = Integer.parseInt(((EditText)findViewById(R.id.certificate_credit2)).getText().toString());
        int C3 = Integer.parseInt(((EditText)findViewById(R.id.certificate_credit3)).getText().toString());
        int self = Integer.parseInt(((TextView)findViewById(R.id.self_learn_credit)).getText().toString());
        int total = Integer.parseInt(((EditText)findViewById(R.id.final_total_credit)).getText().toString());
        String certificate_name1 = ((EditText)findViewById(R.id.certificate_name1)).getText().toString();
        String certificate_name2 = ((EditText)findViewById(R.id.certificate_name2)).getText().toString();
        String certificate_name3 = ((EditText)findViewById(R.id.certificate_name3)).getText().toString();
        String planner = ((TextView)findViewById(R.id.planner)).getText().toString();

        ((TextView)findViewById(R.id.total_credit)).setText(creditTotal); */



    }
    public void finishAct(View view){
        finish();
    }

    public void saveCredit(View view){
        String majorPStr = major_credit.getText().toString();
        String generalPStr = general_credit.getText().toString();
        String cultureStr = culture_credit.getText().toString();

        try {
            int majorP = Integer.parseInt(majorPStr);
            int generalP = Integer.parseInt(generalPStr);
            int cultureP = Integer.parseInt(cultureStr);
            int totalP =Integer.parseInt(majorPStr)+Integer.parseInt(generalPStr)+Integer.parseInt(cultureStr);
                    //majorP+generalP+cultureP;

            String totalStr = String.valueOf(totalP);
            totalCredit.setText("평가인정 대상 : "+ totalStr);

        } catch (NumberFormatException e){
            Toast.makeText(getApplicationContext(), "학점을 입력해 주세요.", Toast.LENGTH_SHORT).show();

        } catch (Exception e){

        }

    }


}