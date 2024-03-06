package com.example.jungcar;

import android.app.Activity ;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class passwdPopup extends Activity {


    Button btnSend, btnAuth;
    LinearLayout llAuth,llPasswd;
    Button btnFind, btnCancel;
    String strAuth, strId;
    EditText edtPasswd, edtConfirm;
    String json;
    static StringRequest request;
    static RequestQueue requestQueue;
    int flag=0;
    EditText edtMail, edtAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.passwdpopup);

        btnCancel =(Button)findViewById(R.id.btnCancel);
        btnFind = (Button)findViewById(R.id.btnFind);
        edtAuth = (EditText)findViewById(R.id.edtAuth);
        edtMail = (EditText)findViewById(R.id.edtMail);
        llAuth = (LinearLayout)findViewById(R.id.llAuth);
        llPasswd = (LinearLayout)findViewById(R.id.llPasswd);
        btnAuth = (Button) findViewById(R.id.btnAuth);
        btnSend = (Button) findViewById(R.id.btnSend);
        edtPasswd = (EditText) findViewById(R.id.edtPasswd);
        edtConfirm = (EditText) findViewById(R.id.edtConfirm);
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getApplicationContext(), login.class);

                startActivity(intent);
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strAuth = auth();
                SendAuth(strAuth);
            }
        });

        btnAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(strAuth.equals(edtAuth.getText().toString())){
                    Toast.makeText(getApplicationContext(),"인증되었습니다. 확인버튼을 눌러주세요",Toast.LENGTH_SHORT).show();
                    llPasswd.setVisibility(View.VISIBLE);
                    flag=1;
                }else{
                    Toast.makeText(getApplicationContext(),"인증번호가 불일치합니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==1) {
                    if(edtPasswd.getText().toString().equals(edtConfirm.getText().toString())&&edtPasswd.length()>=8) {
                        UpdatePasswd();
                        Intent intent = new Intent();
                        intent.putExtra("id", strId);
                        setResult(RESULT_OK, intent);
                        finish();
                    }else{
                        if(edtPasswd.length()<8){
                                    Toast.makeText(getApplicationContext(),"비밀번호는 8자 이상입니다.",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"비밀번호가 불일치합니다.",Toast.LENGTH_SHORT).show();

                        }
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"인증을 먼저 해주세요.",Toast.LENGTH_SHORT).show();
                }
            }
        });


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

    public void SendAuth(String key) {

        //php url 입력
        String URL = "http://172.26.126.172:443/SendAuth.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {


                    jsonObject = new JSONObject(response);
                    json = response;

                    Parsing parsing = new Parsing();
                    try {


                        parsing.mailParsing(response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String user =parsing.user;
                    if(user.equals("null")||user.equals("")){
                        Toast.makeText(getApplicationContext(),"해당 메일로 등록된 아이디가 없습니다." + edtMail.getText().toString(),Toast.LENGTH_SHORT).show();
                    }else{
                        llAuth.setVisibility(View.VISIBLE);
                        strId = parsing.user;
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

                param.put("key",key);
                param.put("email",edtMail.getText().toString());

                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }

    public void UpdatePasswd() {

        //php url 입력
        String URL = "http://172.26.126.172:443/UpdatePasswd.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {


                    jsonObject = new JSONObject(response);
                    json = response;



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
                param.put("id",strId);
                param.put("passwd",edtPasswd.getText().toString());


                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }


}