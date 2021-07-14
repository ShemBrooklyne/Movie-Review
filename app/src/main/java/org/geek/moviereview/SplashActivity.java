package org.geek.moviereview;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import maes.tech.intentanim.CustomIntent;

public class SplashActivity extends AppCompatActivity {

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        Action Bar hide function
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

//        Delay request
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, DiscoverActivity.class);
                startActivity(intent);

//              Activity transition animation
                CustomIntent.customType(SplashActivity.this, getString(R.string.fade_trans));
                finish();
            }
        }, 2500);
    }
}