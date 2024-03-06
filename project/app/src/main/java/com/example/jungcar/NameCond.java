package com.example.jungcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NameCond extends AppCompatActivity {
    static StringRequest request;
    static RequestQueue requestQueue;

    String json;
    ArrayList<condItem> condItems;
    condAdpt condadpt;
    ListView condList;

    ImageButton btnBack;
    TextView tvCondName;
    Button btnOk;
    int flag = 1;
    String strTotal;
    String strBrand, strCountry, strModel, strGrade, minMilesage, maxMilesage, minPrice, maxPrice, isChk, strcond, strName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sellcondition);
        condList = (ListView)findViewById(R.id.condList);
        btnBack = (ImageButton)findViewById(R.id.btnBack);
        tvCondName =(TextView)findViewById(R.id.tvCondName);
        btnOk = (Button)findViewById(R.id.btnOk);
        Intent intent = getIntent();
        strBrand = intent.getExtras().getString("strBrand", "");
        strCountry = intent.getExtras().getString("strCountry", "");
        minMilesage = intent.getExtras().getString("minMilesage", "");
        maxMilesage = intent.getExtras().getString("maxMilesage", "");
        strModel = intent.getExtras().getString("strModel", "");
        strGrade = intent.getExtras().getString("strGrade", "");
        minPrice = intent.getExtras().getString("minPrice", "");
        maxPrice = intent.getExtras().getString("maxPrice", "");
        strName = intent.getExtras().getString("strName","");
        isChk = intent.getExtras().getString("isChk", "n");





        if (requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }

        condList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                flag=0;
                strcond = condItems.get(i).wname;
                strTotal = condItems.get(i).wcnt;
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), BuyOption.class);
                intent.putExtra("strBrand", strBrand);
                intent.putExtra("strModel", strModel);
                intent.putExtra("strGrade", strGrade);
                intent.putExtra("minMilesage", minMilesage);
                intent.putExtra("maxMilesage", maxMilesage);
                intent.putExtra("minPrice", minPrice);
                intent.putExtra("maxPrice", maxPrice);
                intent.putExtra("strCountry", strCountry);
                intent.putExtra("isChk", isChk);

                startActivity(intent);
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==1){
                    Toast.makeText(getApplicationContext(),"아이템을 선택하세요",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(getApplicationContext(), ShResult.class);
                    intent.putExtra("strBrand", "");
                    intent.putExtra("strModel", "");
                    intent.putExtra("strGrade", "");
                    intent.putExtra("minMilesage", "");
                    intent.putExtra("maxMilesage", "");
                    intent.putExtra("minPrice", "");
                    intent.putExtra("maxPrice", "");
                    intent.putExtra("strCountry", "");
                    intent.putExtra("isChk", "");
                    intent.putExtra("strName",strcond);
                    intent.putExtra("strTotal",strTotal);
                    startActivity(intent);
                }
            }
        });
        SearchName();
    }
    public void SearchName() {

        //php url 입력
        String URL = "http://172.26.126.172:443/SearchName.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {
                    condItems = new ArrayList<condItem>();
                    int index=0;
                    jsonObject = new JSONObject(response);

                    Parsing parsing = new Parsing();
                    try {
                        condItem mi;
                        while (true) {

                            parsing.countparsing(response, index);
                            if (parsing.name == "") {

                                break;
                            }


                            String name = parsing.name;
                            String count = parsing.count +"대";
                            index++;
                            mi = new condItem(name,count);
                            condItems.add(mi);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                condadpt = new condAdpt(NameCond.this,R.layout.conditionlist,condItems);
                condList.setAdapter(condadpt);
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
                param.put("name",strName);


                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }
}







