package com.example.jungcar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class login extends AppCompatActivity {
        EditText edtId, edtPasswd, edtNewId, edtNewPasswd, edtConfirm, edtPhone, edtEmail;
        Button btnLogin, btnNew;
        LinearLayout llNew, llLogin;
        TextView tvNew, tvFindId,tvFindPasswd;
         static StringRequest request;
         static RequestQueue requestQueue;

       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
           edtId= (EditText) findViewById(R.id.edtId);
           edtPasswd = (EditText) findViewById(R.id.edtPasswd);
           edtNewId = (EditText) findViewById(R.id.edtNewId);
           edtNewPasswd = (EditText) findViewById(R.id.edtNewPasswd);
           edtConfirm = (EditText) findViewById(R.id.edtConfirm);
           edtPhone = (EditText) findViewById(R.id.edtPhone);
           edtEmail = (EditText) findViewById(R.id.edtEmail);
           btnLogin = (Button) findViewById(R.id.btnLogin);
           btnNew = (Button) findViewById(R.id.btnNew);
           llNew = (LinearLayout) findViewById(R.id.llNew);
           llLogin = (LinearLayout)findViewById(R.id.llLogin);
           tvNew = (TextView) findViewById(R.id.tvNew);
           tvFindId = (TextView) findViewById(R.id.tvFindId);
           tvFindPasswd = (TextView) findViewById(R.id.tvFindPasswd);

           if (requestQueue == null) {
               requestQueue = Volley.newRequestQueue(getApplicationContext());
           }
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(edtId.length()!=0&&edtPasswd.length()!=0) {
                        login();

                    }else{

                        Toast.makeText(getApplicationContext(),"정보를 입력해주세요",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            tvNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    llLogin.setVisibility(View.GONE);
                    llNew.setVisibility(View.VISIBLE);
                }
            });

            tvFindPasswd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), passwdPopup.class);
                    startActivityForResult(intent, 1);
                }
            });

            tvFindId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), Idpopup.class);
                    startActivityForResult(intent, 1);
                }
            });

            btnNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(edtNewId.length()!=0&&edtNewPasswd.length()!=0&&edtConfirm.length()!=0&&edtPhone.length()!=0&&edtEmail.length()!=0) {
                        if(edtNewPasswd.getText().toString().equals(edtConfirm.getText().toString())) {
                            newAccount();
                        }else{
                            Toast.makeText(getApplicationContext(),"비밀번호 불일치",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"정보를 입력해주세요",Toast.LENGTH_SHORT).show();
                    }
                }
            });




       }
    public void login() {

        //php url 입력
        String URL = "http://172.26.126.172:443/login.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {



                    jsonObject = new JSONObject(response);

                    Parsing parsing = new Parsing();
                    try {


                        parsing.loginParsing(response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String idx = parsing.idx;

                    if(idx.equals("null")||idx.equals("")){
                        Toast.makeText(getApplicationContext(),"해당 정보의 유저가 없습니다",Toast.LENGTH_SHORT).show();
                    }else{
                        shared_preferences.set_user_email(login.this,parsing.idx);
                        Intent intent =  new Intent(getApplicationContext(),MainPage.class);
                        startActivity(intent);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //에러나면 error로 나옴


            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                //php로 설정값을 보낼 수 있음
                param.put("id",edtId.getText().toString());
                param.put("passwd",edtPasswd.getText().toString());


                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);

    }

    public void newAccount() {

        //php url 입력
        String URL = "http://172.26.126.172:443/newAccount.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {



                    jsonObject = new JSONObject(response);

                    Parsing parsing = new Parsing();
                    try {


                        parsing.loginParsing(response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String idx =parsing.idx;
                    if(idx.equals("null")||idx.equals("")){
                        Toast.makeText(getApplicationContext(),"아이디 중복",Toast.LENGTH_SHORT).show();
                    }else{
                        shared_preferences.set_user_email(login.this,parsing.idx);
                        Intent intent =  new Intent(getApplicationContext(),MainPage.class);
                        startActivity(intent);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //에러나면 error로 나옴


            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                //php로 설정값을 보낼 수 있음
                param.put("id",edtNewId.getText().toString());
                param.put("passwd",edtNewPasswd.getText().toString());
                param.put("phone",edtPhone.getText().toString());
                param.put("email",edtEmail.getText().toString());
                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case 1:
                if (resultCode == RESULT_OK) {
                    edtId.setText(data.getStringExtra("id"));
                }
                break;

        }

    }

    public void sendSMS(String smsNumber, String smsText){
        PendingIntent sentIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_SENT_ACTION"), 0);
        PendingIntent deliveredIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_DELIVERED_ACTION"), 0);

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch(getResultCode()){
                    case Activity.RESULT_OK:
                        Toast.makeText(getApplicationContext(), "전송 완료", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getApplicationContext(), "전송 실패", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getApplicationContext(), "서비스 지역이 아닙니다", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getApplicationContext(), "무선(Radio)가 꺼져있습니다", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getApplicationContext(), "PDU Null", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter("SMS_SENT_ACTION"));


        SmsManager mSmsManager = SmsManager.getDefault();
        mSmsManager.sendTextMessage(smsNumber, null, smsText, sentIntent, deliveredIntent);
    }

    private String auth(){
        Random random = new Random();
        String Key="";
        for(int i=0;i<6;i++){
            int tmp = random.nextInt(9);
            Key += Integer.toString(tmp);
        }

        return Key;
    }
}



