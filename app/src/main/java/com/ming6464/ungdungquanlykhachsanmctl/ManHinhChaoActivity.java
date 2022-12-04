package com.ming6464.ungdungquanlykhachsanmctl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class ManHinhChaoActivity extends AppCompatActivity {
    ImageView img_load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chao);

        img_load = findViewById(R.id.img_load);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate_load);
        img_load.startAnimation(animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(ManHinhChaoActivity.this, LoginAcitivty.class));
            }
        },2000);
    }
}