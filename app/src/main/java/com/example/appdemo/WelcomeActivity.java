package com.example.appdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.TextView;

import com.example.appdemo.router.OneRouter;
import com.example.appdemo.util.StatusBarUtil;

public class WelcomeActivity extends AppCompatActivity {

    private CountDownTimer timer;
    private TextView splashCountdownText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        splashCountdownText = findViewById(R.id.splash_countdown_text);
        initSplashCountDown();
    }

    private void initSplashCountDown() {
        timer = new CountDownTimer(3000L, 1000L) {
            @Override
            public void onTick(long millisUntilFinished) {
                long currentCountDown = millisUntilFinished / 1000;
                splashCountdownText.setText(currentCountDown + "s");
            }

            @Override
            public void onFinish() {
                OneRouter.getInstance().dispatch("native://main", WelcomeActivity.this);
            }
        };
        timer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}