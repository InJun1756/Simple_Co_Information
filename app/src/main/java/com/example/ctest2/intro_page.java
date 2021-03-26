package com.example.ctest2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

public class intro_page extends AppCompatActivity {

    ImageView intro_Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_page);

        intro_Image = findViewById(R.id.intro_image);

        Animation animation = new AlphaAnimation(0, 1);
        animation.setDuration(1000);
        intro_Image.setVisibility(View.VISIBLE);
        intro_Image.setAnimation(animation);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intent = new Intent (getApplicationContext(), MainActivity.class);
                startActivity(intent); //다음화면으로 넘어감
                finish();
            }
        },1000); //1000 = 1초

    }
/*
    @Override
    protected void onResume() {
        super.onResume();
        finish();
    }*/
}