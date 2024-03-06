package com.example.jungcar;

import android.app.Activity ;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

public class setCarTrade extends Activity {
    RequestQueue requestQueue;
    String json;
    TextView tvColumn, tvCond;
    String sell_idx,table;
    Button btnCancel, btnModify;
    EditText edtCond;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sellnumeric);
        tvColumn = (TextView) findViewById(R.id.tvColumn);
        tvCond = (TextView)findViewById(R.id.tvCond);

        edtCond = (EditText) findViewById(R.id.edtCond);
        btnCancel =(Button)findViewById(R.id.btnCancel);
        btnModify = (Button)findViewById(R.id.btnModify);
        Intent intent = getIntent();
        sell_idx = intent.getExtras().getString("sell_idx");
        table = intent.getExtras().getString("table");
        tvColumn.setText("구매자 아이디");
        tvCond.setVisibility(View.GONE);
        if (requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }
        edtCond.setInputType(InputType.TYPE_CLASS_TEXT);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


              finish();
            }
        });

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cl;
                trade(sell_idx);

                Intent intent = new Intent(getApplicationContext(), MyPageSell.class);
                startActivity(intent);

            }
        });
    }
    public void trade(String sell_idx) {

        //php url 입력
        String URL = "http://172.26.126.172:443/trade.php";


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
                param.put("table",table);
                param.put("seller",shared_preferences.get_user_email(setCarTrade.this));
                param.put("buyer",edtCond.getText().toString());


                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }

}