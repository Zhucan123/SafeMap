package com.example.zhucan.safemap.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.zhucan.safemap.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhucan on 2017/4/13.
 */

public class WelcomeActivity extends Activity {
    private int timeCount = 3;
    private Timer timer;
    private Button button;


    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.welcome_activity);
        Window window = getWindow();
        // Translucent status bar
        window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        button = (Button) findViewById(R.id.welcome_button);
        button.setTextSize(15);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                Intent intent = new Intent(WelcomeActivity.this, MainIndexActivity.class);
                startActivity(intent);
                finish();
            }
        });

        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 0x123) {
                    button.setText("跳过" + timeCount + "秒");
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (timeCount >= 0) {
                        Message message = new Message();
                        message.what = 0x123;
                        message.obj = timeCount;
                        handler.sendEmptyMessage(0x123);
                        Thread.sleep(1000);
                        timeCount--;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainIndexActivity.class);
                startActivity(intent);
                timer.cancel();
                finish();
            }
        }, 3000);

    }
}
