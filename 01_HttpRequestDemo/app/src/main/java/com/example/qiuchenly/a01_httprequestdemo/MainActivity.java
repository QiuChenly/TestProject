package com.example.qiuchenly.a01_httprequestdemo;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.qiuchenly.a01_httprequestdemo.BaseHttp.http;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ProgressBar aaaaa, progressBar;
    Button click;
    ToggleButton tog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        click = findViewById(R.id.click);

        click.setOnClickListener(this);
        aaaaa = findViewById(R.id.aaaaa);
        progressBar = findViewById(R.id.progressBar);
        tog = findViewById(R.id.toggle);
        tog.setTextOn("打开");
        tog.setTextOff("关闭");

        tog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    progressBar.setVisibility(View.VISIBLE);

                }
                else{
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        new Thread() {
            @Override
            public void run() {
                String result = "";
                try {
                    result = http.request("http://ptlogin.4399.com/ptlogin/login.do?v=1", 1, "loginFrom=uframe&postLoginHandler=default&layoutSelfAdapting=true&externalLogin=qq&displayMode=popup&layout=vertical&appId=www_home&gameId=&css=&redirectUrl=&sessionId=&mainDivId=popup_login_div&includeFcmInfo=false&userNameLabel=4399%E7%94%A8%E6%88%B7%E5%90%8D&userNameTip=%E8%AF%B7%E8%BE%93%E5%85%A54399%E7%94%A8%E6%88%B7%E5%90%8D&welcomeTip=%E6%AC%A2%E8%BF%8E%E5%9B%9E%E5%88%B04399&username=123&password=123&autoLogin=on", "home4399=yes; UM_distinctid=15eaea458775f6-0dc9df9adc9c4f-31657c00-1fa400-15eaea45878908; Hm_lvt_334aca66d28b3b338a76075366b2b9e8=1506168560; Hm_lpvt_334aca66d28b3b338a76075366b2b9e8=1506168560; USESSIONID=39ebae00-2333-4eeb-a336-54cc1f1b3048", "Referer:http://ptlogin.4399.com/ptlogin/loginFrame.do?postLoginHandler=default&redirectUrl=&displayMode=popup&css=&appId=www_home&gameId=&username=&externalLogin=qq&password=&mainDivId=popup_login_div&autoLogin=false&includeFcmInfo=false&qrLogin=true&v=1506168561154", false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                final String finalResult = result;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, finalResult, Toast.LENGTH_LONG).show();
                    }
                });
            }
        }.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.click:
                int totalProgress = aaaaa.getProgress() + 10;
                if (totalProgress > 100) {
                    totalProgress = 0;
                }
                aaaaa.setProgress(totalProgress, true);
                break;
            case R.id.toggle:

                break;
        }
    }
}
