package com.example.ctest2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
//수정시간 : 2021-03-01 18:11

//[UI 화면 TextView 변수]---------------------------------------------------------------------------------------------------

    TextView dc_tv,
             dc_tv_date,
             today_dc_tv,
             today_dc_tv_date,
             excount,
             today_excount,
             excount_date,
             carecount,
             carecount_date,
             clearCnt,
             today_clearCnt,
             clearCnt_date,
             localOccCnt_data,
             overFlowCnt_data,
             detail_defcnt_total_tv,
             detail_defcnt_tv,
             detail_overflow_tv,
             detail_overflow_Quarantine_tv,
             ddtt_name,
             ddt_name,
             doct_name;

//------------------------------------------------------------------------------------------------------------------

//[UI 화면 TextView 변수]---------------------------------------------------------------------------------------------------

    Switch local_switch_btn;
    Boolean local_switch_btn_boolean;

//------------------------------------------------------------------------------------------------------------------

//[색상 정의]---------------------------------------------------------------------------------------------------

    String Color_Lignt_Red = "#F15F5F";
    String Color_Lignt_Blue = "#6799FF";
    String Color_White = "#FFFFFF";

//------------------------------------------------------------------------------------------------------------------


//[XML코드 데이터 변수]---------------------------------------------------------------------------------------------------

    String today_data = "";

    String localocc_data = "";
    String overflow_data = "";
    String detail_defcnt_total_data = "";
    String detail_defcnt_data = "";
    String detail_defcnt_overflow_data = "";
    long overflow_cnt_data = 0;

    String total_today_data = "";

    String examcnt_data = "";

    String today_examcnt_data = "";

    long today_examcnt_data_long = 0;

    String carecnt_data = "";

    String clearCnt_data = "";

    long today_ClearCnt_data_long = 0;

    String today_ClearCnt_data = "";

    String update_time_data_local = "";
    String update_time_data_total = "";

    String update_time_refresh = device_time("yyyy-MM-dd HH:mm:ss");

    String update_save_time_local = "0000-00-00-00-00";
    String update_save_time_total = "0000-00-00-00-00";

    String detail_total_data = "";
    String detail_last_data = "";
    String detail_overflow_data = "";
    String detail_overflow_Quarantine_data = "";

    StringBuilder detail_total_str = new StringBuilder("");
    StringBuilder detail_today_str = new StringBuilder("");
    StringBuilder detail_today_overflow_str = new StringBuilder("");

//------------------------------------------------------------------------------------------------------------------

//[URL요청 데이터]---------------------------------------------------------------------------------------------------

    String servicekey = "서비스키가 입력되는 곳입니다.";
    int pageNo = 1;
    int numOfRows = 10;

//------------------------------------------------------------------------------------------------------------------

//[날짜정보 변수]-----------------------------------------------------------------------------------------------------

    String start_date_1 = "";
    String end_date_1 = "";

    String start_date_2 = "";
    String end_date_2 = "";

//-----------------------------------------------------------------------------------------------------------------

//[업데이트 되지 않음 항목 알림 변수]-------------------------------------------------------------------------------
    //기본값은 99
    int not_update_alart_value = 99;

//-----------------------------------------------------------------------------------------------------------------

//[날짜구하기]-------------------------------------------------------------------------------

    //디바이스 시간 확인 변수
    double time = 0;

    private String device_time(String data_type) {

        Date check_time = new Date();
        SimpleDateFormat check_time_date = new SimpleDateFormat(data_type);
        String now_time = check_time_date.format(check_time);

        return now_time;
    }

    private String reequest_time_data(String data_type, int request_date) {

        Calendar request_time = Calendar.getInstance();
        request_time.add(Calendar.DATE , request_date);
        String request_time_date = new java.text.SimpleDateFormat(data_type).format(request_time.getTime());

        return request_time_date;
    }

//--------------------------------------------------------------------------------------

//[앱 종료-뒤로가기 버튼 활용]---------------------------------------------------------------------------------------------------

    // 마지막으로 뒤로가기 버튼을 눌렀던 시간 저장
    private long backKeyPressedTime = 0;
    // 첫 번째 뒤로가기 버튼을 누를때 표시
    private Toast toast;

    @Override
    public void onBackPressed() {
        // 기존 뒤로가기 버튼의 기능을 막기위해 주석처리 또는 삭제
        // super.onBackPressed();

        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지났으면 Toast Show
        // 2000 milliseconds = 2 seconds
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지나지 않았으면 종료
        // 현재 표시된 Toast 취소
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
            toast.cancel();
        }
    }

//------------------------------------------------------------------------------------------------------------------

//[토스트 메세지 중복 방지]------------------------------------------------------------------------------

    private static Toast sToast;

    public static void showToast(Context context, String message) {
        //안드로이드11에서 Toast메세지가 겹칠시 사라지고 다시 출력이 되지 않는 버그가 발생
        //해당 버그의 대안으로 Toast메세지가 출력중일 때 메세지를 취소하고 다시 출력하는 방식으로 코드 변경
        if (sToast != null) {
            sToast.cancel();
        }
        sToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        sToast.show();
    }

//--------------------------------------------------------------------------------------

//[앱 사용 동의 페이지 이동 여부 확인]------------------------------------------------------------------------------

    public void first_execute_check(){
        SharedPreferences pref = getSharedPreferences("app_state", MainActivity.MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();

        boolean first = pref.getBoolean("isFirst", false);

        if(first==false){
            Log.d("최초 실행?", "최초실행됨");
            Intent agree_page = new Intent(MainActivity.this,agree_page.class);
            startActivity(agree_page);
            finish();
        }else{
            Log.d("최초실행?", "최초실행 아님");
            //Toast.makeText(getApplicationContext(), "처음 아님!", Toast.LENGTH_LONG).show();
        }
    }
//--------------------------------------------------------------------------------------

//[OnCreate 시작]------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//[TextView 연결]------------------------------------------------------------------------------

        dc_tv = findViewById(R.id.dctv);
        dc_tv_date = findViewById(R.id.dctv_date);
        today_dc_tv = findViewById(R.id.today_dctv);
        today_dc_tv_date = findViewById(R.id.today_dctv_date);
        excount = findViewById(R.id.examCnt_tv);
        today_excount = findViewById(R.id.today_examCnt_tv);
        excount_date = findViewById(R.id.examCnt_date);
        carecount = findViewById(R.id.CARE_CNT_tv);
        carecount_date = findViewById(R.id.CARE_CNT_date);
        clearCnt = findViewById(R.id.clearCnt);
        today_clearCnt= findViewById(R.id.today_clearCnt);
        clearCnt_date = findViewById(R.id.clearCnt_date);
        localOccCnt_data = findViewById(R.id.localOccCnt_tv);
        overFlowCnt_data = findViewById(R.id.overFlowCnt_tv);
        detail_defcnt_total_tv = findViewById(R.id.detail_def_total_cnt_tv);
        detail_defcnt_tv = findViewById(R.id.detail_def_cnt_tv);
        detail_overflow_tv = findViewById(R.id.detail_overflow_cnt_tv);
        detail_overflow_Quarantine_tv = findViewById(R.id.detail_overflow_Quarantine_tv);
        ddtt_name = findViewById(R.id.detail_def_total_cnt_tv_name);
        ddt_name = findViewById(R.id.detail_def_cnt_tv_name);
        doct_name = findViewById(R.id.detail_overflow_cnt_tv_name);

        local_switch_btn = findViewById(R.id.local_show_switch);

//-------------------------------------------------------------------------------------------------------

//[날짜 저장]--------------------------------------------------------------------------------------------

        start_date_1 = reequest_time_data("yyyyMMdd", -1);

        end_date_1 = device_time("yyyyMMdd");

        start_date_2 = reequest_time_data("yyyyMMdd", -2);

        end_date_2 = reequest_time_data("yyyyMMdd", -1);

//-------------------------------------------------------------------------------------------------------

//[동의여부 확인]--------------------------------------------------------------------------------------------
        first_execute_check();
//-------------------------------------------------------------------------------------------------------

//[디바이스 시간 확인 및 데이터 요청]--------------------------------------------------------------------------------------------

        data_load();

//-------------------------------------------------------------------------------------------------------
        App_Switch_state_load();

        if (local_switch_btn_boolean == true) {
            ddtt_name.setVisibility(View.VISIBLE);
            ddt_name.setVisibility(View.VISIBLE);
            doct_name.setVisibility(View.VISIBLE);
            detail_defcnt_total_tv.setVisibility(View.VISIBLE);
            detail_defcnt_tv.setVisibility(View.VISIBLE);
            detail_overflow_tv.setVisibility(View.VISIBLE);
            detail_overflow_Quarantine_tv.setVisibility(View.VISIBLE);
        }else if(local_switch_btn_boolean == false) {
            ddtt_name.setVisibility(View.GONE);
            ddt_name.setVisibility(View.GONE);
            doct_name.setVisibility(View.GONE);
            detail_defcnt_total_tv.setVisibility(View.GONE);
            detail_defcnt_tv.setVisibility(View.GONE);
            detail_overflow_tv.setVisibility(View.GONE);
            detail_overflow_Quarantine_tv.setVisibility(View.GONE);
        }

        local_switch_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ddtt_name.setVisibility(View.VISIBLE);
                    ddt_name.setVisibility(View.VISIBLE);
                    doct_name.setVisibility(View.VISIBLE);
                    detail_defcnt_total_tv.setVisibility(View.VISIBLE);
                    detail_defcnt_tv.setVisibility(View.VISIBLE);
                    detail_overflow_tv.setVisibility(View.VISIBLE);
                    detail_overflow_Quarantine_tv.setVisibility(View.VISIBLE);

                    local_switch_btn.setText("접기");
                    App_Switch_state_save("local_switch_btn", isChecked, "접기");
                }else{
                    ddtt_name.setVisibility(View.GONE);
                    ddt_name.setVisibility(View.GONE);
                    doct_name.setVisibility(View.GONE);
                    detail_defcnt_total_tv.setVisibility(View.GONE);
                    detail_defcnt_tv.setVisibility(View.GONE);
                    detail_overflow_tv.setVisibility(View.GONE);
                    detail_overflow_Quarantine_tv.setVisibility(View.GONE);

                    local_switch_btn.setText("펼치기");
                    App_Switch_state_save("local_switch_btn", isChecked, "펼치기");
                }
            }
        });



//-------------------------------------------------------------------------------------------------------


//[안내창 팝업]----------------------------------------------------------------------------------------

        Button button_info = findViewById(R.id.info_btn);
        button_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info_pop dialog = new info_pop(MainActivity.this);
                WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                params.copyFrom(dialog.getWindow().getAttributes());
                params.width = (int) (getApplicationContext().getResources().getDisplayMetrics().widthPixels * 0.7f);
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                dialog.show();

            }

        });
//-------------------------------------------------------------------------------------------------------

//[재조회 버튼]----------------------------------------------------------------------------------------

        Button button_start = findViewById(R.id.refresh_btn);
        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                data_load();


                update_time_refresh = device_time("yyyy-MM-dd HH:mm:ss");

                showToast(getApplicationContext(), "조회 완료!");
            }

        });
//-------------------------------------------------------------------------------------------------------

//[정보요청 - Volley]----------------------------------------------------------------------------------------

        if(AppHelper.requestQueue != null) {
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
        } //RequestQueue 생성

    }//OnCreate 끝

//[OnCreate 끝]----------------------------------------------------------------------------------------

//[스위치값 저장 및 불러오기]----------------------------------------------------------------------------------------

    public void App_Switch_state_save(String Key_value, Boolean value_data, String text_value){
        SharedPreferences pref = getSharedPreferences("app_state", MainActivity.MODE_PRIVATE);

        final SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean(Key_value, value_data);
        editor.commit();
        editor.putString(Key_value + "_str", text_value);
        editor.commit();
    }

    public void App_Switch_state_load(){
        SharedPreferences pref = getSharedPreferences("app_state", MainActivity.MODE_PRIVATE);

        if(pref==null) return;

        local_switch_btn.setText(pref.getString("local_switch_btn_str", "접기"));
        local_switch_btn.setChecked(pref.getBoolean("local_switch_btn", true));
        local_switch_btn_boolean = pref.getBoolean("local_switch_btn", true);

    }

//-------------------------------------------------------------------------------------------------------

    public long parse_long_error_preventtion(String check_data) {

        long long_data = 0;

        if (check_data.equals("")){
            long_data = 0;
        }else if (check_data.equals(null)){
            long_data = 0;
        }else {
            long_data = Long.parseLong(check_data);
        }

        return long_data;
    }

    public void local_update_textview(int reset_value, String cnt_value_str){

        DecimalFormat textViewFormat = new DecimalFormat("###,###");

        String up_down_arrow = "";
        String cnt_value_com = "";

        long cnt_value = 0;

        if (reset_value == 999) {
            detail_today_str.setLength(0);
            detail_today_str.append("");
        }
        if (!cnt_value_str.equals("")) cnt_value = Long.parseLong(cnt_value_str);

        if (cnt_value > 0) {
            up_down_arrow = "▲ ";

        }else if (cnt_value < 0) {
            cnt_value = cnt_value * -1;
            up_down_arrow = "▼ ";

        }else if (cnt_value == 0) {
            up_down_arrow = "- ";

        }

        cnt_value_com = textViewFormat.format(cnt_value);

        String cnt_value_string = up_down_arrow + cnt_value_com + "\n";
        detail_today_str.append(cnt_value_string);

    }

    public void overflow_update_textview(int reset_value, String cnt_value_str){

        DecimalFormat textViewFormat = new DecimalFormat("###,###");

        String up_down_arrow = "";
        String cnt_value_com = "";

        long cnt_value = 0;

        if (reset_value == 999) {
            detail_today_overflow_str.setLength(0);
            detail_today_overflow_str.append("");
        }
        if (!cnt_value_str.equals("")) cnt_value = Long.parseLong(cnt_value_str);

        if (cnt_value > 0) {
            up_down_arrow = "▲ ";

        }else if (cnt_value < 0) {
            cnt_value = cnt_value * -1;
            up_down_arrow = "▼ ";

        }else if (cnt_value == 0) {
            up_down_arrow = "- ";

        }

        cnt_value_com = textViewFormat.format(cnt_value);

        String cnt_value_string = up_down_arrow + cnt_value_com + "\n";
        detail_today_overflow_str.append(cnt_value_string);

    }


//[sendRequest 시작]----------------------------------------------------------------------------------------

    //오늘 정보
    public void sendRequest(final String request_type, String serkey, int pageno, int numofrows, String startCreateDt, String endCreateDt) {

        String url = "";

        //시,도별 정보
        if (request_type.equals("local_data")) {
            url = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson" +
                    "?ServiceKey=" + serkey +
                    "&pageNo=" + pageno +
                    "&numOfRows=" + numofrows +
                    "&startCreateDt=" + startCreateDt +
                    "&endCreateDt=" + endCreateDt;
        }
        //종합 정보
        if (request_type.equals("total_data")) {
            url = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson" +
                    "?ServiceKey=" + serkey +
                    "&pageNo=" + pageno +
                    "&numOfRows=" + numofrows +
                    "&startCreateDt=" + startCreateDt +
                    "&endCreateDt=" + endCreateDt;
        }

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() { //응답을 잘 받았을 때 이 메소드가 자동으로 호출
                    @Override
                    public void onResponse(String response) {
                        //println("응답 -> " + response);
                        if (request_type.equals("local_data")) println_local(response);
                        if (request_type.equals("total_data")) println_total(response);
                    }
                },
                new Response.ErrorListener() { //에러 발생시 호출될 리스너 객체
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (request_type.equals("local_data")) println_local("에러 -> " + error.getMessage());
                        if (request_type.equals("total_data")) println_total("에러 -> " + error.getMessage());

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();

                return params;
            }
        };
        request.setShouldCache(false); //이전 결과 있어도 새로 요청하여 응답을 보여준다.
        AppHelper.requestQueue = Volley.newRequestQueue(this); // requestQueue 초기화 필수
        AppHelper.requestQueue.add(request);
        //println("요청 보냄.");

    }

    public void println_local(String data) {

        //[응답값 저장]------------------------------------------------------------------
        String xmlc_data = data;
        //-----------------------------------------------------------------------------

        //[해외유입 수]------------------------------------------------------------------
        long detail_overflow_cnt = 0;
        //-----------------------------------------------------------------------------

        //[파싱 날짜]-----------------------------------------------------------------
        String request_date_today = null;
        String request_date_yesterday = null;

        if (time < 1000 && time >= 0000) {
            request_date_today = reequest_time_data("yyyy-MM-dd", -1);
            request_date_yesterday = reequest_time_data("yyyy-MM-dd", -2);
        }else {
            request_date_today = device_time("yyyy-MM-dd");
            request_date_yesterday = reequest_time_data("yyyy-MM-dd", -1);
        }
        //-----------------------------------------------------------------------------

        //[어제 확진자 수]------------------------------------------------------------------
        //전체
        today_data = getAllXML("Total","incDec", request_date_today, xmlc_data);
        today_data = null_value_check(today_data);
        if(!today_data.equals("")) app_state_string_commit("today_data", today_data);
        //today_dc_tv.setText(today_data);

        //국내 발생
        localocc_data = getAllXML("Total","localOccCnt", request_date_today, xmlc_data);
        localocc_data = null_value_check(localocc_data);
        if(!localocc_data.equals("")) app_state_string_commit("localOccCnt", localocc_data);
        //localOccCnt_data.setText(localocc_data);

        //해외 유입
        overflow_data = getAllXML("Total","overFlowCnt", request_date_today, xmlc_data);
        if (!overflow_data.equals("")){
            detail_overflow_cnt = Long.parseLong(overflow_data);
        }
        overflow_data = null_value_check(overflow_data);
        if(!overflow_data.equals("")) app_state_string_commit("overFlowCnt", overflow_data);
        //overFlowCnt_data.setText(overflow_data);

        //세부 사항
        if(!today_data.equals("")) {
            detail_total_str.setLength(0);
            local_defcnt_detail_data(xmlc_data, detail_overflow_cnt, "Seoul", "서울", 999);
            local_defcnt_detail_data(xmlc_data, detail_overflow_cnt, "Gyeonggi-do", "경기", 0);
            local_defcnt_detail_data(xmlc_data, detail_overflow_cnt, "Incheon", "인천", 0);
            local_defcnt_detail_data(xmlc_data, detail_overflow_cnt, "Gangwon-do", "강원", 0);
            local_defcnt_detail_data(xmlc_data, detail_overflow_cnt, "Sejong", "세종", 0);
            local_defcnt_detail_data(xmlc_data, detail_overflow_cnt, "Daegu", "대구", 0);
            local_defcnt_detail_data(xmlc_data, detail_overflow_cnt, "Daejeon", "대전", 0);
            local_defcnt_detail_data(xmlc_data, detail_overflow_cnt, "Gwangju", "광주", 0);
            local_defcnt_detail_data(xmlc_data, detail_overflow_cnt, "Chungcheongbuk-do", "충북", 0);
            local_defcnt_detail_data(xmlc_data, detail_overflow_cnt, "Chungcheongnam-do", "충남", 0);
            local_defcnt_detail_data(xmlc_data, detail_overflow_cnt, "Jeollabuk-do", "전북", 0);
            local_defcnt_detail_data(xmlc_data, detail_overflow_cnt, "Jeollanam-do", "전남", 0);
            local_defcnt_detail_data(xmlc_data, detail_overflow_cnt, "Gyeongsangbuk-do", "경북", 0);
            local_defcnt_detail_data(xmlc_data, detail_overflow_cnt, "Gyeongsangnam-do", "전남", 0);
            local_defcnt_detail_data(xmlc_data, detail_overflow_cnt, "Ulsan", "울산", 0);
            local_defcnt_detail_data(xmlc_data, detail_overflow_cnt, "Busan", "부산", 0);
            local_defcnt_detail_data(xmlc_data, detail_overflow_cnt, "Jeju", "제주", 0);

        }
        //-----------------------------------------------------------------------------

        //[총 확진자 수]------------------------------------------------------------------
        total_today_data = getAllXML("Total","defCnt", request_date_today, xmlc_data);
        total_today_data = null_value_check(total_today_data);
        if(!total_today_data.equals("")) app_state_string_commit("total_today_data", total_today_data);
        //dc_tv.setText(total_today_data);
        //-----------------------------------------------------------------------------

        //[격리 중]------------------------------------------------------------------
        carecnt_data = getAllXML("Total","isolIngCnt", request_date_today, xmlc_data);
        carecnt_data = null_value_check(carecnt_data);
        if(!carecnt_data.equals("")) app_state_string_commit("carecnt_data", carecnt_data);
        //carecount.setText(carecnt_data);
        //-----------------------------------------------------------------------------

        //[격리해제]------------------------------------------------------------------
        clearCnt_data = getAllXML("Total","isolClearCnt", request_date_today, xmlc_data);
        long clearCnt_data_today_long = 0;
        if (!clearCnt_data.equals("")) clearCnt_data_today_long = Long.parseLong(clearCnt_data);
        clearCnt_data = null_value_check(clearCnt_data);
        if(!clearCnt_data.equals("")) app_state_string_commit("clearCnt_data", clearCnt_data);
        //clearCnt.setText(clearCnt_data);

        today_clearCnt.setText("");
        today_ClearCnt_data = getAllXML("Total","isolClearCnt", request_date_yesterday, xmlc_data);
        long today_ClearCnt_data_yesterday_long = 0;
        if (!today_ClearCnt_data.equals("")) {
            today_ClearCnt_data_yesterday_long = Long.parseLong(today_ClearCnt_data);
            today_ClearCnt_data_yesterday_long = clearCnt_data_today_long - today_ClearCnt_data_yesterday_long;
        }
        if(clearCnt_data.equals("") || today_ClearCnt_data.equals("")) {
            today_clearCnt.append("N/A");
        }else {
            app_state_long_commit("today_ClearCnt_data_long", today_ClearCnt_data_yesterday_long);
            today_ClearCnt_textview(today_ClearCnt_data_yesterday_long);
        }
        //-----------------------------------------------------------------------------

        //[업데이트 시간]------------------------------------------------------------------
        update_time_data_local = getAllXML("total_data","createDt",  request_date_today, xmlc_data);

        String update_time_data_sub_local = "";
        if(!update_time_data_local.equals("")) {
            update_time_data_sub_local = update_time_data_local.substring(0, 19);
            app_state_string_commit("update_time_data_sub_local", update_time_data_sub_local);
        }
/*
        today_dc_tv_date.setText("업데이트 : " + update_time_data_sub_local + "\n");
        dc_tv_date.setText("업데이트 : " + update_time_data_sub_local + "\n");
        carecount_date.setText("업데이트 : " + update_time_data_sub_local + "\n");
        clearCnt_date.setText("업데이트 : " + update_time_data_sub_local + "\n");
 */
    //-----------------------------------------------------------------------------

        app_state_get_data();
    }

    public void local_defcnt_detail_data(String data, long overflow_cnt, String data_en, String data_kr, int reset_value) {

        //[응답값 저장]------------------------------------------------------------------
        String xmlc_data = data;

        if(reset_value == 999) overflow_cnt_data = overflow_cnt;
        //-----------------------------------------------------------------------------

        //[파싱 날짜]-----------------------------------------------------------------
        String request_date_today = null;
        String request_date_yesterday = null;

        if (time < 1000 && time >= 0000) {
            request_date_today = reequest_time_data("yyyy-MM-dd", -1);
            request_date_yesterday = reequest_time_data("yyyy-MM-dd", -2);
        } else {
            request_date_today = device_time("yyyy-MM-dd");
            request_date_yesterday = reequest_time_data("yyyy-MM-dd", -1);
        }
        //-----------------------------------------------------------------------------

        //[세부정보 파싱]-----------------------------------------------------------------------------

        detail_defcnt_total_data = getAllXML(data_en,"defCnt", request_date_today, xmlc_data);
        detail_defcnt_total_data = null_value_check(detail_defcnt_total_data);
        detail_total_str.append(data_kr +" : " + detail_defcnt_total_data + "\n");

        detail_defcnt_data = getAllXML(data_en,"localOccCnt", request_date_today, xmlc_data);
        local_update_textview(reset_value, detail_defcnt_data);

        detail_defcnt_overflow_data = getAllXML(data_en,"overFlowCnt", request_date_today, xmlc_data);
        if (!detail_defcnt_overflow_data.equals("")) overflow_cnt_data = overflow_cnt_data - Long.parseLong(detail_defcnt_overflow_data);
        overflow_update_textview(reset_value, detail_defcnt_overflow_data);

        //-----------------------------------------------------------------------------

        //[세부정보 저장 및 표시]-----------------------------------------------------------------------------
        //마지막 파싱 데이터인 Jeju값이 전달되었을 때
        if (data_en.equals("Jeju")) {
            app_state_string_commit("detail_total", detail_total_str.toString());
            detail_defcnt_total_tv.setText(detail_total_str);

            String detail_today = detail_today_str.toString();
            app_state_string_commit("detail_last", detail_today);
            detail_defcnt_tv.setText(detail_today);

            String detail_today_overflow = detail_today_overflow_str.toString();
            app_state_string_commit("detail_overflow", detail_today_overflow);
            detail_overflow_tv.setText(detail_today_overflow);

            String detail_today_overflow_Quarantine = "* 검역에서는 총 " + overflow_cnt_data + "명의 확진자가 발생하였습니다.\n";
            app_state_string_commit("detail_overflow_Quarantine", detail_today_overflow_Quarantine);
            detail_overflow_Quarantine_tv.setText(detail_today_overflow_Quarantine);
            //-----------------------------------------------------------------------------
        }
    }
        //[println_today]----------------------------------------------------------------------------------------
    public void println_total(String data) {

        //[응답값 저장]------------------------------------------------------------------
        String xmlc_data = data;
        //-----------------------------------------------------------------------------

        //[파싱 날짜]-----------------------------------------------------------------
        String request_date_today = null;
        String request_date_yesterday = null;

        if (time < 1000 && time >= 0000) {
            request_date_today = reequest_time_data("yyyy-MM-dd", -1);
            request_date_yesterday = reequest_time_data("yyyy-MM-dd", -2);
        }else {
            request_date_today = device_time("yyyy-MM-dd");
            request_date_yesterday = reequest_time_data("yyyy-MM-dd", -1);
        }
        //-----------------------------------------------------------------------------

        //[검사 중]------------------------------------------------------------------
        examcnt_data = getAllXML("total_data","examCnt", request_date_today, xmlc_data);
        long examcnt_data_today_long = 0;
        if (!examcnt_data.equals("")) examcnt_data_today_long = Long.parseLong(examcnt_data);
        examcnt_data = null_value_check(examcnt_data);
        if(!examcnt_data.equals("")) app_state_string_commit("examcnt_data", examcnt_data);
        //excount.setText(examcnt_data);

        today_excount.setText("");
        today_examcnt_data = getAllXML("total_data","examCnt", request_date_yesterday, xmlc_data);
        long today_examcnt_data_yesterday_long = 0;
        if (!today_examcnt_data.equals("")) {
            today_examcnt_data_yesterday_long = Long.parseLong(today_examcnt_data);
            today_examcnt_data_yesterday_long = examcnt_data_today_long - today_examcnt_data_yesterday_long;
        }
        if(examcnt_data.equals("") || today_examcnt_data.equals("")) {
            today_excount.append("N/A");
        }else{
            app_state_long_commit("today_examcnt_data_long", today_examcnt_data_yesterday_long);
            today_examcnt_textview(today_examcnt_data_yesterday_long);
        }
        //-----------------------------------------------------------------------------

        //[업데이트 시간]------------------------------------------------------------------
        update_time_data_total = getAllXML("total_data","createDt",  request_date_today, xmlc_data);

        String update_time_data_sub_total = "";

        if(!update_time_data_total.equals("")){
            update_time_data_sub_total = update_time_data_total.substring(0, 19);
            app_state_string_commit("update_time_data_sub_total", update_time_data_sub_total);
        }

        //excount_date.setText("업데이트 : " + update_time_data_sub_total  + "\n");
        //-----------------------------------------------------------------------------

        app_state_get_data();
    }

//----------------------------------------------------------------------------------------

    public String null_value_check(String check_data) {

        DecimalFormat textViewFormat = new DecimalFormat("###,###");

        long long_data = 0;
        String result_long_data = "";

        if (check_data.equals("")){
            long_data = 0;
            result_long_data = textViewFormat.format(long_data);
        }else if (check_data.equals(null)){
            long_data = 0;
            result_long_data = textViewFormat.format(long_data);
        }else {
            long_data = Long.parseLong(check_data);
            result_long_data = textViewFormat.format(long_data);
        }

        return result_long_data;
    }

//[XML파싱 - XMLPullParser]----------------------------------------------------------------------------------------

    public String getAllXML(String request_loc_data, String request_data, String request_data_date_time, String xmlc){

        Activity activity = this;
        String str = "";

        int i = 0;

        //[시,도별 테그 정보 변수]------------------------------------------------------------------
        Boolean increateDt = false, //createDt테그
                ingubunEn = false, //gubunEn테그
                inincDec = false, //incDec테그
                inisolIngCnt = false, //isolIngCnt테그
                inisolClearCnt = false, //isolClearCnt테그
                indefCnt = false, //defCnt테그
                inlocalOccCnt = false, //localOccCnt테그
                inoverFlowCnt = false, //overFlowCnt테그
                initem = false; //item테그

        String createDt = null, //생성(최초등록)날짜
                gubunEn = null, //지역명(영문)
                incDec = null, //전일대비 증가 수
                isolIngCnt = null, //격리자 수
                isolClearCnt = null, //격리해제 수
                defCnt = null, //감염자 수
                localOccCnt = null, //지역 감염 수
                overFlowCnt = null; //해외 유입 수
        //-----------------------------------------------------------------------------

        //[총합 테그 정보 변수]------------------------------------------------------------------
        Boolean inaccDefRate = false, //accDefRate테그
                inaccExamCnt = false, //accExamCnt테그
                inaccExamCompCnt = false, //accExamCompCnt테그
                incareCnt = false, //careCnt테그
                inclearCnt = false, //clearCnt테그
                increateDt_total = false, //createDt테그
                indecideCnt = false, //decideCnt테그
                inexamCnt = false, //examCnt테그
                inresutlNegCnt = false; //resutlNegCnt테그

        String accDefRate = null, //확진율
                accExamCnt = null, //누적검사 수
                accExamCompCnt = null, //누적 검사 완료 수
                careCnt = null, //치료자 수
                clearCnt = null, //격리해제 수
                createDt_total = null, //등록일자
                decideCnt = null, //감염자 수
                examCnt = null, //검사중 수
                resutlNegCnt = null; //결과음성 수

        //-----------------------------------------------------------------------------

        //For file source
        //Resources res = activity.getResources();
        //XmlResourceParser xpp = res.getXml(R.xml.test);


        try {
            //For String source
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(xmlc));

            xpp.next();
            int eventType = xpp.getEventType();

            //String value_text = "";
/*
            while (xpp.getEventType()!=XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType()==XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("isolClearCnt")) {
                        str = xpp.nextText();
                        if (str.equals(request_data)) i++;
                        if (i == 1) {
                            str = xpp.nextText();
                        }
                    }
                    xpp.next();
                    if (xpp.getEventType()==XmlPullParser.START_TAG) {
                        if (xpp.getName().equals("isolClearCnt")) {
                            if (i == 1) {
                                str = xpp.nextText();
                                i++;
                            }
                        }
                    }
                }
                xpp.next();
            }
*/


            //전체 정보
            if (request_loc_data.equals("total_data")){
                while (xpp.getEventType()!=XmlPullParser.END_DOCUMENT) {
                    switch (xpp.getEventType()) {
                        case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                            if (xpp.getName().equals("accDefRate")) { //accDefRate 만나면 내용을 받을수 있게 하자
                                inaccDefRate = true;
                            }
                            if (xpp.getName().equals("accExamCnt")) { //accExamCnt 만나면 내용을 받을수 있게 하자
                                inaccExamCnt = true;
                            }
                            if (xpp.getName().equals("accExamCompCnt")) { //accExamCompCnt 만나면 내용을 받을수 있게 하자
                                inaccExamCompCnt = true;
                            }
                            if (xpp.getName().equals("careCnt")) { //careCnt 만나면 내용을 받을수 있게 하자
                                incareCnt = true;
                            }
                            if (xpp.getName().equals("clearCnt")) { //clearCnt 만나면 내용을 받을수 있게 하자
                                inclearCnt = true;
                            }
                            if (xpp.getName().equals("createDt")) { //createDt 만나면 내용을 받을수 있게 하자
                                increateDt_total = true;
                            }
                            if (xpp.getName().equals("decideCnt")) { //decideCnt 만나면 내용을 받을수 있게 하자
                                indecideCnt = true;
                            }
                            if (xpp.getName().equals("examCnt")) { //examCnt 만나면 내용을 받을수 있게 하자
                                inexamCnt = true;
                            }
                            if (xpp.getName().equals("resutlNegCnt")) { //resutlNegCnt 만나면 내용을 받을수 있게 하자
                                inresutlNegCnt = true;
                            }
                            break;
                        case XmlPullParser.TEXT://parser가 내용에 접근했을때
                            if (inaccDefRate) { //inaccDefRate true일 때 태그의 내용을 저장.
                                accDefRate = xpp.getText();
                                inaccDefRate = false;
                            }
                            if (inaccExamCnt) { //inaccExamCnt true일 때 태그의 내용을 저장.
                                accExamCnt = xpp.getText();
                                inaccExamCnt = false;
                            }
                            if (inaccExamCompCnt) { //inaccExamCompCnt true일 때 태그의 내용을 저장.
                                accExamCompCnt = xpp.getText();
                                inaccExamCompCnt = false;
                            }
                            if (incareCnt) { //incareCnt true일 때 태그의 내용을 저장.
                                careCnt = xpp.getText();
                                incareCnt = false;
                            }
                            if (inclearCnt) { //inclearCnt true일 때 태그의 내용을 저장.
                                clearCnt = xpp.getText();
                                inclearCnt = false;
                            }
                            if (increateDt_total) { //increateDt true일 때 태그의 내용을 저장.
                                createDt_total = xpp.getText();
                                increateDt_total = false;
                            }
                            if (indecideCnt) { //indecideCnt true일 때 태그의 내용을 저장.
                                decideCnt = xpp.getText();
                                indecideCnt = false;
                            }
                            if (inexamCnt) { //inexamCnt true일 때 태그의 내용을 저장.
                                examCnt = xpp.getText();
                                inexamCnt = false;
                            }
                            if (inresutlNegCnt) { //inresutlNegCnt true일 때 태그의 내용을 저장.
                                resutlNegCnt = xpp.getText();
                                inresutlNegCnt = false;
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if (xpp.getName().equals("item")) {
                                if (createDt_total.contains(request_data_date_time)){
                                    if (request_data.equals("accDefRate")) str = accDefRate; //확진율
                                    if (request_data.equals("accExamCnt")) str = accExamCnt; //누적 검사 수
                                    if (request_data.equals("accExamCompCnt")) str = accExamCompCnt; //누적 검사 완료 수
                                    if (request_data.equals("careCnt")) str = careCnt; //치료자 수
                                    if (request_data.equals("clearCnt")) str = clearCnt; //격리해제 수
                                    if (request_data.equals("createDt")) str = createDt_total; //등록일
                                    if (request_data.equals("decideCnt")) str = decideCnt; //감염자 수
                                    if (request_data.equals("examCnt")) str = examCnt; //검사중 수
                                    if (request_data.equals("resutlNegCnt")) str = resutlNegCnt; //결과 음성 수
                                }
                            }
                            break;
                    }
                    xpp.next();
                }
            }
            //시,도별 정보
            else {
                while (xpp.getEventType()!=XmlPullParser.END_DOCUMENT) {
                    switch (xpp.getEventType()) {
                        case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                            //[시,도별 관련 테그]------------------------------------------------------------------
                            if (xpp.getName().equals("createDt")) { //createDt 만나면 내용을 받을수 있게 하자
                                increateDt = true;
                            }
                            if (xpp.getName().equals("gubunEn")) { //gubunEn 만나면 내용을 받을수 있게 하자
                                ingubunEn = true;
                            }
                            if (xpp.getName().equals("incDec")) { //incDec 만나면 내용을 받을수 있게 하자
                                inincDec = true;
                            }
                            if (xpp.getName().equals("defCnt")) { //defCnt 만나면 내용을 받을수 있게 하자
                                indefCnt= true;
                            }
                            if (xpp.getName().equals("isolClearCnt")) { //isolClearCnt 만나면 내용을 받을수 있게 하자
                                inisolClearCnt= true;
                            }
                            if (xpp.getName().equals("isolIngCnt")) { //isolIngCnt 만나면 내용을 받을수 있게 하자
                                inisolIngCnt= true;
                            }
                            if (xpp.getName().equals("localOccCnt")) { //localOccCnt 만나면 내용을 받을수 있게 하자
                                inlocalOccCnt= true;
                            }
                            if (xpp.getName().equals("overFlowCnt")) { //overFlowCnt 만나면 내용을 받을수 있게 하자
                                inoverFlowCnt = true;
                            }
                            //-----------------------------------------------------------------------------
                            break;
                        case XmlPullParser.TEXT://parser가 내용에 접근했을때
                            //[시,도별 관련 테그]------------------------------------------------------------------
                            if (increateDt) { //increateDt true일 때 태그의 내용을 저장.
                                createDt = xpp.getText();
                                increateDt = false;
                            }
                            if (ingubunEn) { //ingubunEn true일 때 태그의 내용을 저장.
                                gubunEn = xpp.getText();
                                ingubunEn = false;
                            }
                            if (inincDec) { //inincDec true일 때 태그의 내용을 저장.
                                incDec = xpp.getText();
                                inincDec = false;
                            }
                            if (indefCnt) { //indefCnt true일 때 태그의 내용을 저장.
                                defCnt = xpp.getText();
                                indefCnt = false;
                            }
                            if (inisolClearCnt) { //inisolClearCnt true일 때 태그의 내용을 저장.
                                isolClearCnt = xpp.getText();
                                inisolClearCnt = false;
                            }
                            if (inisolIngCnt) { //inisolIngCnt true일 때 태그의 내용을 저장.
                                isolIngCnt = xpp.getText();
                                inisolIngCnt = false;
                            }
                            if (inlocalOccCnt) { //inlocalOccCnt true일 때 태그의 내용을 저장.
                                localOccCnt = xpp.getText();
                                inlocalOccCnt = false;
                            }
                            if (inoverFlowCnt) { //inoverFlowCnt true일 때 태그의 내용을 저장.
                                overFlowCnt = xpp.getText();
                                inoverFlowCnt = false;
                            }
                            //-----------------------------------------------------------------------------
                            break;
                        case XmlPullParser.END_TAG:
                            if (xpp.getName().equals("item")) {
                                if (createDt.contains(request_data_date_time)){
                                    if (gubunEn.equals(request_loc_data)) {
                                        if (request_data.equals("createDt")) str = createDt; //등록일
                                        if (request_data.equals("incDec")) str = incDec; //전일대비 증가수
                                        if (request_data.equals("defCnt")) str = defCnt; //총 감염자 수
                                        if (request_data.equals("isolIngCnt")) str = isolIngCnt; //격리 중
                                        if (request_data.equals("isolClearCnt")) str = isolClearCnt; //격리해제 수
                                        if (request_data.equals("localOccCnt")) str = localOccCnt; //지역 감염자 수
                                        if (request_data.equals("overFlowCnt")) str = overFlowCnt; //해외유입 수
                                    }
                                    //파싱 테스트
                                    //if (gubunEn.equals(request_loc_data)) str = createDt + request_data_kr + "확진자 수 : " + defCnt +" 격리해제 수 : " + isolClearCnt;
                                }
                            }
                            break;
                    }
                    xpp.next();
                }
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return str;
    }
//----------------------------------------------------------------------------------------

//[데이터 구하기]----------------------------------------------------------------------------------------

    public void Request_Data_List() {

        if (time < 1000 && time >= 0000) {
            //Toast.makeText(getApplicationContext(), now_time + "실행되어야 함", Toast.LENGTH_LONG).show();

            //Toast.makeText(getApplicationContext(), yesterDate, Toast.LENGTH_LONG).show();
            sendRequest("local_data", servicekey, pageNo, numOfRows, start_date_2, end_date_2);
            sendRequest("total_data", servicekey, pageNo, numOfRows, start_date_2, end_date_2);

        }else {
            //Toast.makeText(getApplicationContext(), now_time + "실행되면 안됨", Toast.LENGTH_LONG).show();
            sendRequest("local_data", servicekey, pageNo, numOfRows, start_date_1, end_date_1);
            sendRequest("total_data", servicekey, pageNo, numOfRows, start_date_1, end_date_1);

        }
        //업데이트 되지 않은 항목이 있을 경우 Toast 출력
        if (not_update_alart_value == 0) showToast(getApplicationContext(),"일부 정보가 업데이트 되지 않았어요!");
    }

//----------------------------------------------------------------------------------------

    public void today_examcnt_textview(long cnt_value) {

        today_excount.setText("");

        DecimalFormat textViewFormat = new DecimalFormat("###,###");

        String up_down_arrow = "";
        String cnt_value_com = "";

        today_excount.setTextColor(Color.parseColor(Color_White));

        if (cnt_value > 0) {
            today_excount.setTextColor(Color.parseColor(Color_Lignt_Red));
            up_down_arrow = "▲ ";
        }else if (cnt_value < 0) {
            today_excount.setTextColor(Color.parseColor(Color_Lignt_Blue));
            cnt_value = cnt_value * -1;
            up_down_arrow = "▼ ";
        }else if (cnt_value == 0) {
            up_down_arrow = "- ";
        }

        cnt_value_com = textViewFormat.format(cnt_value);

        String cnt_value_string = up_down_arrow + cnt_value_com;
        SpannableStringBuilder text_size_set = new SpannableStringBuilder(cnt_value_string);
        text_size_set.setSpan(new AbsoluteSizeSpan(20, true),0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        today_excount.append(text_size_set);

    }

    public void today_ClearCnt_textview(long cnt_value){

        today_clearCnt.setText("");

        DecimalFormat textViewFormat = new DecimalFormat("###,###");

        String up_down_arrow = "";
        String cnt_value_com = "";

        today_clearCnt.setTextColor(Color.parseColor(Color_White));

        if (cnt_value > 0) {
            today_clearCnt.setTextColor(Color.parseColor(Color_Lignt_Red));
            up_down_arrow = "▲ ";
        }else if (cnt_value < 0) {
            today_clearCnt.setTextColor(Color.parseColor(Color_Lignt_Blue));
            cnt_value = cnt_value * -1;
            up_down_arrow = "▼ ";
        }else if (cnt_value == 0) {
            up_down_arrow = "- ";
        }

        cnt_value_com = textViewFormat.format(cnt_value);

        String cnt_value_string = up_down_arrow + cnt_value_com;
        SpannableStringBuilder text_size_set = new SpannableStringBuilder(cnt_value_string);
        text_size_set.setSpan(new AbsoluteSizeSpan(20, true),0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        today_clearCnt.append(text_size_set);

    }


    public void app_state_string_commit (String key_value, String value_data) {
        SharedPreferences pref = getSharedPreferences("app_state", MainActivity.MODE_PRIVATE);

        final SharedPreferences.Editor editor = pref.edit();

        editor.putString(key_value, value_data);
        editor.commit();
    }
    public void app_state_long_commit (String key_value, long value_data) {
        SharedPreferences pref = getSharedPreferences("app_state", MainActivity.MODE_PRIVATE);

        final SharedPreferences.Editor editor = pref.edit();

        editor.putLong(key_value, value_data);
        editor.commit();
    }
    public void app_state_get_data () {
        SharedPreferences pref = getSharedPreferences("app_state", MainActivity.MODE_PRIVATE);

        if(pref==null) return;

        today_data = pref.getString("today_data", "0");
        today_dc_tv.setText(today_data);

        localocc_data = pref.getString("localOccCnt", "0");
        localOccCnt_data.setText(localocc_data);

        overflow_data = pref.getString("overFlowCnt", "0");
        overFlowCnt_data.setText(overflow_data);

        total_today_data = pref.getString("total_today_data", "0");
        dc_tv.setText(total_today_data);

        examcnt_data = pref.getString("examcnt_data", "0");
        excount.setText(examcnt_data);

        today_examcnt_data_long = pref.getLong("today_examcnt_data_long", 0);
        today_examcnt_textview(today_examcnt_data_long);

        carecnt_data = pref.getString("carecnt_data", "0");
        carecount.setText(carecnt_data);

        clearCnt_data = pref.getString("clearCnt_data", "0");
        clearCnt.setText(clearCnt_data);

        today_ClearCnt_data_long = pref.getLong("today_ClearCnt_data_long", 0);
        today_ClearCnt_textview(today_ClearCnt_data_long);

        update_save_time_local = pref.getString("update_time_data_sub_local", "0000-00-00-00-00");
        update_save_time_total = pref.getString("update_time_data_sub_total", "0000-00-00-00-00");

        detail_total_data = pref.getString("detail_total", "서울 : \n경기 : \n인천 : \n강원 : \n세종 : \n대구 : \n대전 : \n광주 : \n충북 : \n충남 : \n전북 : \n전남 : \n전북 : \n경남 : \n울산 : \n부산 : \n제주 : \n");
        detail_defcnt_total_tv.setText(detail_total_data);

        detail_last_data = pref.getString("detail_last", "-0\n-0\n-0\n-0\n-0\n-0\n-0\n-0\n-0\n-0\n-0\n-0\n-0\n-0\n-0\n-0\n-0\n");
        detail_defcnt_tv.setText(detail_last_data);

        detail_overflow_data = pref.getString("detail_overflow", "-0\n-0\n-0\n-0\n-0\n-0\n-0\n-0\n-0\n-0\n-0\n-0\n-0\n-0\n-0\n-0\n-0\n");
        detail_overflow_tv.setText(detail_overflow_data);

        detail_overflow_Quarantine_data = pref.getString("detail_overflow_Quarantine", "");
        detail_overflow_Quarantine_tv.setText(detail_overflow_Quarantine_data);

        today_dc_tv_date.setText("업데이트 : " + update_save_time_local + "\n");
        dc_tv_date.setText("업데이트 : " + update_save_time_local + "\n");
        excount_date.setText("업데이트 : " + update_save_time_total + "\n");
        carecount_date.setText("업데이트 : " + update_save_time_local + "\n");
        clearCnt_date.setText("업데이트 : " + update_save_time_local + "\n");

    }

    public void data_load() {

        SharedPreferences pref = getSharedPreferences("app_state", MainActivity.MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();

        boolean first = pref.getBoolean("isFirst", false);

        time = Integer.parseInt(device_time("HHmm"));

        app_state_get_data();

        String update_check_time = device_time("yyyy-MM-dd");

        String update_check_time_yesterday = reequest_time_data("yyyy-MM-dd", -1);

        String update_save_time_sub_total = update_save_time_total.substring(0, 10);
        String update_save_time_sub_local = update_save_time_local.substring(0, 10);

        //이용약관 동의를 하지 않으면 정보 조회 불가
        if (first == true) {
            if (time >= 1000) {
                if (!update_save_time_sub_total.equals(update_check_time) || !update_save_time_sub_local.equals(update_check_time)) {
                    Request_Data_List();
                    showToast(getApplicationContext(), "정보가 업데이트 되었습니다!");
                }
                if (update_save_time_sub_total.equals(update_check_time) || update_save_time_sub_local.equals(update_check_time)) {
                    showToast(getApplicationContext(), "모든 정보가 이미 업데이트가 되었어요!");
                }
                if (today_data.equals(null)) {
                    Request_Data_List();
                    showToast(getApplicationContext(), "오류가 발생하여 재조회 하였어요!\n에러 내용 : java.lang.NullPointerException");
                }
                if (today_data.equals("")) {
                    Request_Data_List();
                    showToast(getApplicationContext(), "오류가 발생하여 재조회 하였어요!\n에러 내용 : [StringValue=(\"\")]");
                }
            } else if (time < 1000 && time >= 0000) {
                if (!update_save_time_sub_total.equals(update_check_time_yesterday) || !update_save_time_sub_local.equals(update_check_time)) {
                    Request_Data_List();
                    showToast(getApplicationContext(), "정보가 업데이트 되었습니다!");
                }
                if (update_save_time_sub_total.equals(update_check_time_yesterday) || update_save_time_sub_local.equals(update_check_time)) {
                    showToast(getApplicationContext(), "모든 정보가 이미 업데이트가 되었어요!");
                }
                if (today_data.equals(null)) {
                    Request_Data_List();
                    showToast(getApplicationContext(), "오류가 발생하여 재조회 하였어요!\n에러 내용 : java.lang.NullPointerException");
                }
                if (today_data.equals("")) {
                    Request_Data_List();
                    showToast(getApplicationContext(), "오류가 발생하여 재조회 하였어요!\n에러 내용 : [StringValue=(\"\")]");
                }
            }
        }

    }

}
