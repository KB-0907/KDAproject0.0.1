package com.example.kdaproject001;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.kdaproject001.board.BoardListActivity;
import com.example.kdaproject001.mainViewPager.CreditFragment;
import com.example.kdaproject001.mainViewPager.ScheduleFragment;
import com.example.kdaproject001.schedule.scheduleActivity;
import com.example.kdaproject001.todo.ToDoActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    ImageButton boardBnt, schBtn, TodoBtn, creditBtn;
    ViewPager pager;
    TextView textText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        makeNotice();

        textText = findViewById(R.id.textView16);
        boardBnt = findViewById(R.id.move_to_board_btn);
        schBtn = findViewById(R.id.schedule_btn);
        TodoBtn = findViewById(R.id.move_to_do_btn);
        creditBtn = findViewById(R.id.move_to_grade_planner_btn);
        pager = findViewById(R.id.pager);
        pager.setOffscreenPageLimit(2);

        creditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreditActivity.class);
                startActivity(intent);
            }
        });

        MainPagerAdapter mainAdapter = new MainPagerAdapter(getSupportFragmentManager());

        ScheduleFragment schFragment = new ScheduleFragment();
        mainAdapter.addItem(schFragment);

        CreditFragment creFragment = new CreditFragment();
        mainAdapter.addItem(creFragment);

        pager.setAdapter(mainAdapter);

        boardBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent borIntent = new Intent(getApplicationContext(), BoardListActivity.class);
                startActivity(borIntent);
            }
        });

        schBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent schIntent = new Intent(getApplicationContext(), scheduleActivity.class);
                startActivity(schIntent);
            }
        });
    }

    public void moveTodoActivity(View view){
        Intent todoIntent = new Intent(getApplicationContext(), ToDoActivity.class);
        startActivity(todoIntent);
    }

    @Override
    public void onBackPressed() {
        FirebaseAuth.getInstance().signOut();
        super.onBackPressed();
    }

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

    private void makeNotice(){
        new Thread(() -> {
            trustAllHosts(); // network 동작, 인터넷에서 xml을 받아오는 코드
            try {
                trustAllHosts();
                // url을 열기 전에 trustAllHosts 호출
                String url = "https://iit.kw.ac.kr/servlet/controller.home.bbs.NoticeUserServlet?p_process=listPage&p_layerId=5&p_page=1";
                Document doc = Jsoup.connect(url).get();
                Elements titleEle = doc.select(".lft a");//공지사항의 제목
                Elements dateEle = doc.select(".tbl-type02* tr td");//첫행의 날짜

                String date = dateEle.get(9).text();
                Log.e("결과", date);

                //String date = dateEle.get(3).select("td").get(3).text();


                for (int i = 0; i < titleEle.size(); ++i){
                    String[] noticeTitle = new String[titleEle.size()];
                    noticeTitle[i] = titleEle.get(i).text();
                    //Log.e("결과", noticeTitle[i]);
                }

            } catch (IOException e) {
                e.printStackTrace();
                Log.d("결과", "실패");
            }
        }).start();
    }
}
