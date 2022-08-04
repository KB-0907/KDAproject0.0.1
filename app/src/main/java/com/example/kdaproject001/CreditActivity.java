package com.example.kdaproject001;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class CreditActivity extends AppCompatActivity {
    EditText major_credit,general_credit,culture_credit,certificate_credit1,certificate_credit2,certificate_credit3,self_learn_credit,final_total_credit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);

     /*  // int majorP =  Integer.parseInt (((EditText)findViewById(R.id.major_credit)).getText().toString());
        int generalP = Integer.parseInt(((EditText)findViewById(R.id.general_credit)).getText().toString());
        int cultureP = Integer.parseInt(((EditText)findViewById(R.id.culture_credit)).getText().toString());
        //int creditTotal = majorP + generalP + cultureP;
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


}