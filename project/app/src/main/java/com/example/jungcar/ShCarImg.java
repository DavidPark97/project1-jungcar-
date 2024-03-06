package com.example.jungcar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ShCarImg extends AppCompatActivity {

    ImageView imgClose, imgCar;
    Button btnBefore, btnAfter;
    static StringRequest request;
    static RequestQueue requestQueue;
    String sell_idx;
    int count = 0;
    int idx = 1;

    String strImg;
    String json;
    String strBrand, strCountry, strModel, strGrade, minMilesage, maxMilesage, minPrice, maxPrice, isChk, strName, strTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.shcarimg);
        imgClose = (ImageView) findViewById(R.id.imgClose);
        imgCar = (ImageView) findViewById(R.id.imgCar);
        btnBefore = (Button) findViewById(R.id.btnBefore);
        btnAfter = (Button) findViewById(R.id.btnAfter);
        Intent intent = getIntent();

        sell_idx = intent.getExtras().getString("sell_idx");
        strBrand = intent.getExtras().getString("strBrand", "");
        strCountry = intent.getExtras().getString("strCountry", "");
        minMilesage = intent.getExtras().getString("minMilesage", "");
        maxMilesage = intent.getExtras().getString("maxMilesage", "");
        strModel = intent.getExtras().getString("strModel", "");
        strGrade = intent.getExtras().getString("strGrade", "");
        minPrice = intent.getExtras().getString("minPrice", "");
        maxPrice = intent.getExtras().getString("maxPrice", "");
        isChk = intent.getExtras().getString("isChk", "");
        strName = intent.getExtras().getString("strName", "");
        strTotal = intent.getExtras().getString("strTotal", "");


        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        shCarImg(idx);

        btnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idx == 1) {
                    Toast.makeText(getApplicationContext(), "처음입니다", Toast.LENGTH_SHORT).show();
                } else {
                    idx--;
                    shCarImg(idx);

                }
            }
        });

        btnAfter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idx == count) {
                    Toast.makeText(getApplicationContext(), "마지막입니다", Toast.LENGTH_SHORT).show();
                } else {
                    idx++;
                    shCarImg(idx);
                }
            }
        });

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShItem.class);
                intent.putExtra("sell_idx", sell_idx);
                intent.putExtra("strName", strName);
                intent.putExtra("strBrand", strBrand);
                intent.putExtra("strModel", strModel);
                intent.putExtra("strGrade", strGrade);
                intent.putExtra("minMilesage", minMilesage);
                intent.putExtra("maxMilesage", maxMilesage);
                intent.putExtra("minPrice", minPrice);
                intent.putExtra("maxPrice", maxPrice);
                intent.putExtra("strCountry", strCountry);
                intent.putExtra("isChk", isChk);
                intent.putExtra("strTotal", strTotal);
                startActivity(intent);
            }
        });

    }

    public void shCarImg(int odr) {

        //php url 입력
        String URL = "http://172.26.126.172:443/shCarImg.php";
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
                        parsing.imgParsing(json);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    strImg = "http://172.26.126.172:443/" + parsing.name;
                    Glide.with(ShCarImg.this).load(strImg).into(imgCar);

                    count = Integer.parseInt(parsing.idx);
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
                param.put("sell_idx", sell_idx);
                param.put("orders", Integer.toString(odr));
                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);
    }
}
