package com.example.jungcar;

import android.app.Activity ;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

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

public class CarEdit extends Activity {
    static StringRequest request;
    static RequestQueue requestQueue;
    String json;
    EditText edtContent,edtPrice;
    String sell_idx;
    Button btnCancel, btnModify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.caredit);
        edtPrice = (EditText)findViewById(R.id.edtPrice);
        edtContent = (EditText)findViewById(R.id.edtContent);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnModify = (Button) findViewById(R.id.btnModify);
        Intent intent = getIntent();
        sell_idx = intent.getExtras().getString("sell_idx");

        if (requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCont(sell_idx);
                Intent intent = new Intent(getApplicationContext(), MyPageSell.class);
                startActivity(intent);
            }
        });
        getEditCont();
    }
    public void getEditCont (){

        //php url 입력
        String URL = "http://172.26.126.172:443/getEditCont.php";
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {

                //응답이 되었을때 response로 값이 들어옴
                try {
                    jsonObject = new JSONObject(response);
                    json = response;
                    Parsing par = new Parsing();
                    try {
                        par.editParsing(json);
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }

                    edtContent.setText(par.detail);
                    edtPrice.setText(par.price);


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

                param.put("sell_idx",sell_idx);


                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);
    }

    public void updateCont(String sell_idx) {

        //php url 입력
        String URL = "http://172.26.126.172:443/updateCont.php";


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
                param.put("sell_idx",sell_idx);
                param.put("price",edtPrice.getText().toString());
                param.put("detail",edtContent.getText().toString());


                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }

}
