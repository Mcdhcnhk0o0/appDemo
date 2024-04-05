package com.example.appdemo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.example.appdemo.router.OneRouter;
import com.example.appdemo.util.StatusBarUtil;
import com.example.router.annotation.Router;

@Router(url = "native://splash", description = "闪屏页")
public class WelcomeActivity extends AppCompatActivity {

    private CountDownTimer timer;
    private CardView splashExit;
    private TextView splashIntroduction;
    private TextView splashCountdownText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.INSTANCE.transparentStatusBar(getWindow());
        setContentView(R.layout.activity_welcome);
        splashExit = findViewById(R.id.splash_exit);
        splashIntroduction = findViewById(R.id.splash_introduction);
        splashCountdownText = findViewById(R.id.splash_countdown_text);
        initAnimation();
        initSplashCountDown();
    }

    private void initAnimation() {
        Animation moveUpAnimation = new TranslateAnimation(0, 0, 300, 0);
        moveUpAnimation.setDuration(1000);
        moveUpAnimation.setInterpolator(new DecelerateInterpolator());
        splashIntroduction.startAnimation(moveUpAnimation);
    }

    private void initSplashCountDown() {
        splashExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OneRouter.getInstance().dispatch("native://main");
            }
        });
        timer = new CountDownTimer(3000L, 1000L) {
            @Override
            public void onTick(long millisUntilFinished) {
                long currentCountDown = millisUntilFinished / 1000;
                splashCountdownText.setText(currentCountDown + "s");
            }

            @Override
            public void onFinish() {
                OneRouter.getInstance().dispatch("native://main");
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