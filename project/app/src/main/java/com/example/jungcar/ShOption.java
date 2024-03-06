package com.example.jungcar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

public class ShOption extends AppCompatActivity {
    TableLayout tlTable;
    String json;
    String sell_idx;
    String strBrand, strCountry, strModel, strGrade, minMilesage, maxMilesage, minPrice, maxPrice, isChk, strName, strTotal;
    ImageView imgClose;

    static StringRequest request;
    static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoption);

        Intent intent = getIntent();
        imgClose = (ImageView)findViewById(R.id.imgClose);
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

        tlTable = (TableLayout) findViewById(R.id.tlTable);


        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        shOption();

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

    public void shOption() {

        //php url 입력
        String URL = "http://172.26.126.172:443/shOption.php";

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {

                    int index=0;
                    jsonObject = new JSONObject(response);

                    Parsing parsing = new Parsing();
                    try {

                        while (true) {

                            parsing.optableParsing(response, index);
                            if (parsing.name == "") {

                                break;
                            }

                            TableRow trow = new TableRow(ShOption.this);
                            TableLayout.LayoutParams lp = new TableLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT);
                            lp.setMargins(1,1,1,1);
                            trow.setLayoutParams(lp);

                            TableRow.LayoutParams tp = new TableRow.LayoutParams
                                    (ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT);
                            tp.setMargins(1,1,1,1);
                            tp.weight = 1;

                            TextView txt1 = new TextView(ShOption.this);
                            TextView txt2 = new TextView(ShOption.this);
                            txt1.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                            txt1.setGravity(Gravity.CENTER);
                            txt1.setBackgroundColor(Color.WHITE);
                            txt1.setLayoutParams(tp);
                            txt1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            txt1.setTextSize(25);
                            txt1.setText(parsing.name);


                            txt2.setGravity(Gravity.CENTER);
                            txt2.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                            txt2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            txt2.setLayoutParams(tp);
                            txt2.setBackgroundColor(Color.WHITE);
                            txt2.setTextSize(20);
                            txt2.setText(parsing.detail);
                            trow.addView(txt1);
                            trow.addView(txt2);
                            tlTable.addView(trow);

                            index++;



                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
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
                param.put("sell_idx", sell_idx);


                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }
}