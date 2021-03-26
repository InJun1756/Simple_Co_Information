package com.example.ctest2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class info_pop extends Dialog implements View.OnClickListener {

    private Context mContext;

    private TextView btn_ok;
    private Button btn_agree_check;

    public info_pop(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pop);

        btn_ok = (TextView) findViewById(R.id.Confirm_btn);
        btn_agree_check = findViewById(R.id.agree_check_btn);

        btn_ok.setOnClickListener(this);

        btn_agree_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent agree_page = new Intent(getContext(), agree_check.class);
                getContext().startActivity(agree_page);
                dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Confirm_btn:
                dismiss();
                break;
        }
    }
}
