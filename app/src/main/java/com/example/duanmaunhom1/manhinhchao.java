package com.example.duanmaunhom1;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class manhinhchao extends AppCompatActivity {
Animation hh;
ImageView ivhinh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manhinhchao);
        ivhinh=findViewById(R.id.iv1);

        CountDownTimer timer = new CountDownTimer(6000,6000) {
            @Override
            public void onTick(long millisUntilFinished) {
                hh= AnimationUtils.loadAnimation(manhinhchao.this,R.anim.alphaanim);
                ivhinh.startAnimation(hh);
            }

            @Override
            public void onFinish() {
                Intent i= new Intent(manhinhchao.this, DangNhap.class);
                startActivity(i);
                finish();
            }
        }.start();
    }
}