package jp.gr.java_conf.cookie91.delay_counter;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    private ImageView cookie_t;
    private ImageView cookie_p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setTheme(R.style.CookieSplashTheme);

        cookie_t = findViewById(R.id.cookie_t);
        cookie_p = findViewById(R.id.cookie_p);

        Handler handler = new Handler();
        handler.postDelayed(new animeHandler(), 1500);
        handler.postDelayed(new splashHandler(), 2000);
        fadein();
        move();
    }

    class animeHandler implements  Runnable{
        public void run(){
            fadeout();
        }
    }

    class splashHandler implements Runnable{
        public void run(){
            Intent inte = new Intent(getApplication(), MainActivity.class);
            startActivity(inte);
            SplashActivity.this.finish();
            overridePendingTransition(0, 0);
        }
    }

    private void move(){
        // TranslateAnimation(float fromX, float toX, float fromY, float toY)
        // fromX/fromY : 移動元X座標/Y座標、toX/toY : 移動先X座標/Y座標
        TranslateAnimation translate = new TranslateAnimation(-100, 00, 0, 0);
        // 1000ms間
        translate.setDuration(600);
        // アニメーションスタート
        cookie_p.startAnimation(translate);
    }

    private void fadeout(){
        // 透明度を1から0に変化
        AlphaAnimation alphaFadeout = new AlphaAnimation(1.0f, 0.0f);
        // animation時間 msec
        alphaFadeout.setDuration(500);
        // animationが終わったそのまま表示にする
        alphaFadeout.setFillAfter(true);

        cookie_t.startAnimation(alphaFadeout);
        cookie_p.startAnimation(alphaFadeout);
    }

    private void fadein(){
        // 透明度を0から1に変化
        AlphaAnimation alphaFadeIn = new AlphaAnimation(0.0f, 1.0f);
        // animation時間 msec
        alphaFadeIn.setDuration(500);
        // animationが終わったそのまま表示にする
        alphaFadeIn.setFillAfter(true);

        cookie_t.startAnimation(alphaFadeIn);
    }
}
