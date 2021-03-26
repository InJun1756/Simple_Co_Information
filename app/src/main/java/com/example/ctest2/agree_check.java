package com.example.ctest2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class agree_check extends AppCompatActivity {

    TextView agree_check_tv;

    Button agree_check_done;

    @Override
    public void onBackPressed() {
        // 기존 뒤로가기 버튼의 기능을 막기위해 주석처리 또는 삭제
        // super.onBackPressed();
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agree_check);

        SharedPreferences pref = getSharedPreferences("app_state", MainActivity.MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();

        agree_check_tv = findViewById(R.id.agree_time_check_tv);
        agree_check_done = findViewById(R.id.agree_check_done_btn);

        String agree_date = pref.getString("Agree_Time", "");

        agree_check_tv.setText("'" + agree_date +"'에 동의함.");

        agree_check_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}