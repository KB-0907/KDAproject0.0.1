package com.example.kdaproject001;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kdaproject001.board.BoardListActivity;
import com.example.kdaproject001.mainViewPager.CreditFragment;
import com.example.kdaproject001.mainViewPager.ScheduleFragment;
import com.example.kdaproject001.myInfo.ChangePwd;
import com.example.kdaproject001.myInfo.MyInfoActivity;
import com.example.kdaproject001.schedule.ScheduleActivity;
import com.example.kdaproject001.schedule.ScheduleActivity;
import com.example.kdaproject001.todo.ToDoActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class MainActivity extends AppCompatActivity{
    ViewPager pager;
    TextView textText;
    String[] noticeTitle = new String[5];
    String[] noticeUrl = new String[5];
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("KAD");
    boolean auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        makeNotice();

        checkAuth();

        findViewById(R.id.move_to_board_btn).setOnClickListener(moveActivityClickListener);
        findViewById(R.id.schedule_btn).setOnClickListener(moveActivityClickListener);
        findViewById(R.id.move_to_do_btn).setOnClickListener(moveActivityClickListener);
        findViewById(R.id.move_to_grade_planner_btn).setOnClickListener(moveActivityClickListener);
        findViewById(R.id.MyInfo_btn).setOnClickListener(moveActivityClickListener);

        pager = findViewById(R.id.pager);
        pager.setOffscreenPageLimit(2);

        MainPagerAdapter mainAdapter = new MainPagerAdapter(getSupportFragmentManager());

        ScheduleFragment schFragment = new ScheduleFragment();
        mainAdapter.addItem(schFragment);

        CreditFragment creFragment = new CreditFragment();
        mainAdapter.addItem(creFragment);

        pager.setAdapter(mainAdapter);
    }



    View.OnClickListener moveActivityClickListener = v -> {
        switch (v.getId()){
            case R.id.move_to_board_btn:
                moveActivity(BoardListActivity.class);
                break;
            case R.id.schedule_btn:
                moveActivity(ScheduleActivity.class);
                break;
            case R.id.move_to_do_btn:
                moveActivity(ToDoActivity.class);
                break;
            case R.id.move_to_grade_planner_btn:
                if (auth == true){
                    moveActivity(CreditActivity.class);
                } else {
                    //이메일 인증 액티비티로
                }

                break;
            case R.id.MyInfo_btn:
                if (auth == true){
                    moveActivity(MyInfoActivity.class);
                } else {
                    //이메일 인증 액티비티로
                }
                break;
        }
    };

    private void moveActivity(Class moveClass){
        Intent moveIntent = new Intent(getApplicationContext(), moveClass);
        startActivity(moveIntent);
    }

    @Override
    public void onBackPressed() {
        FirebaseAuth.getInstance().signOut();
        super.onBackPressed();
    }

    private void makeNotice(){
        new Thread(() -> {
            trustAllHosts();
            try {
                trustAllHosts();
                // url을 열기 전에 trustAllHosts 호출
                String url = "https://iit.kw.ac.kr/servlet/controller.home.bbs.NoticeUserServlet?p_process=listPage&p_layerId=5&p_page=1";
                Document doc = Jsoup.connect(url).get();
                Elements titleEle = doc.select(".lft a");//공지사항의 제목
                Elements dateEle = doc.select(".tbl-type02* tr td");//첫행의 날짜

                String date = dateEle.get(9).text();
               // String[] noticeTitle = new String[titleEle.size()];

                for (int i = 0; i < 5; ++i){
                    noticeTitle[i] = titleEle.get(i).text().replace("&nbsp", "");
                    Log.e("결과", noticeTitle[i]);

                }
                Message msg = handler.obtainMessage();
                handler.sendMessage(msg);

            } catch (IOException e) {
                e.printStackTrace();
                Log.d("결과", "실패");
            }
        }).start();
    }

    final Handler handler = new Handler(){
        public void handleMessage(Message msg){
            ((TextView)findViewById(R.id.notice0)).setText(noticeTitle[0]);
            ((TextView)findViewById(R.id.notice1)).setText(noticeTitle[1]);
            ((TextView)findViewById(R.id.notice2)).setText(noticeTitle[2]);
            ((TextView)findViewById(R.id.notice3)).setText(noticeTitle[3]);
            ((TextView)findViewById(R.id.notice4)).setText(noticeTitle[4]);
        }
    };

    private void trustAllHosts() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[] {};
            }
            @Override
            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }
            @Override
            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }
        }};
        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkAuth(){
        mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e ("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    String authCheck = String.valueOf(task.getResult().child("authentication").getValue());
                    if (authCheck.equals("false")){
                        Log.d("인증 확인", "인증 false");
                        auth = false;
                    } else {
                        Log.d("인증 확인", "인증");
                        auth = true;


                    }

                }
            }
        });
    }

}
