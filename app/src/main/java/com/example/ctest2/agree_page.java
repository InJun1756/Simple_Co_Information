package com.example.ctest2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class agree_page extends AppCompatActivity {

    Button confirm_button, finish_button;

    CheckBox agree_state;

    private Context mContext;

    public void SlideMenuView(Context context) {
        this.mContext = context;
    }

    @Override
    public void onBackPressed() {
        // 기존 뒤로가기 버튼의 기능을 막기위해 주석처리 또는 삭제
        // super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agree_page);

        SharedPreferences pref = getSharedPreferences("app_state", MainActivity.MODE_PRIVATE);

        final SharedPreferences.Editor editor = pref.edit();

        confirm_button = findViewById(R.id.Confirm_btn);
        finish_button = findViewById(R.id.Finish_btn);

        agree_state = findViewById(R.id.agree_check);

        agree_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(agree_state.isChecked()){
                    //체크함
                    confirm_button.setBackgroundResource(R.color.colorbackground);
                }else {
                    //체크하지 않음
                    confirm_button.setBackgroundResource(R.color.colorGray);
                }
            }
        });


        //동의 버튼
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(agree_state.isChecked()){
                    //체크함
                    String agree_time_str = agree_time_set();

                    editor.putBoolean("isFirst",true);
                    editor.commit();
                    editor.putString("Agree_Time", agree_time_str);
                    editor.commit();
                    Intent agree_page = new Intent(agree_page.this,MainActivity.class);
                    startActivity(agree_page);
                    finish();
                }else {
                    //체크하지 않음
                    ((MainActivity) mContext).showToast(getApplicationContext(), "'이용 약관'에 동의하지 않으셨습니다.");

                }
            }
        });

        //앱 종료 버튼
        finish_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("isFirst",false);
                editor.commit();
                finishAffinity();
            }
        });
    }

    //동의 시간 반환
    public String agree_time_set() {
        Date today_with_time = new Date();
        SimpleDateFormat date_with_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        final String toDay_with_time = date_with_time.format(today_with_time);

        return toDay_with_time;
    }

}