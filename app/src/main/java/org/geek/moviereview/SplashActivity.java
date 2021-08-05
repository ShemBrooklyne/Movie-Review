package org.geek.moviereview;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import butterknife.BindView;
import butterknife.ButterKnife;
import maes.tech.intentanim.CustomIntent;

public class SplashActivity extends AppCompatActivity {

    Handler handler;
    @BindView(R.id.splash_icon)
    ImageView splashIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

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

                YoYo.with(Techniques.RubberBand)
                        .duration(700)
                        .repeat(5)
                        .playOn(splashIcon);
            }
        }, 3000);
    }
}