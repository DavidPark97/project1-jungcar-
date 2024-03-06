package com.example.jungcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class BuyOption extends AppCompatActivity {
    static StringRequest request;
    static RequestQueue requestQueue;
    String json;
    Button  btnReset, btnShList, btnSearch;
    ImageButton btnBack, btnBrand, btnModel, btnGrade, btnMilesage, btnPrice, btnCountry, rstBrand, rstModel, rstGrade, rstMilesage, rstPrice, rstCountry;
    TextView tvBrand, tvModel, tvGrade, tvMilesage, tvPrice,tvCountry;
    EditText edtSearch;
    CheckBox chkOwn;
    String strTotal;
    String strBrand, strCountry, strModel, strGrade, minMilesage,maxMilesage, minPrice, maxPrice, isChk, strName;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),MainPage.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyoption);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnReset=(Button) findViewById(R.id.btnReset);
        btnShList=(Button) findViewById(R.id.btnShList);
        btnBrand = (ImageButton) findViewById(R.id.btnBrand);
        btnModel = (ImageButton) findViewById(R.id.btnModel);
        btnGrade = (ImageButton) findViewById(R.id.btnGrade);
        btnMilesage = (ImageButton) findViewById(R.id.btnMilesage);
        btnPrice = (ImageButton) findViewById(R.id.btnPrice);
        btnCountry = (ImageButton) findViewById(R.id.btnCountry);
        rstBrand = (ImageButton) findViewById(R.id.rstBrand);
        rstModel = (ImageButton) findViewById(R.id.rstModel);
        rstGrade = (ImageButton) findViewById(R.id.rstGrade);
        rstMilesage = (ImageButton) findViewById(R.id.rstMilesage);
        rstPrice = (ImageButton) findViewById(R.id.rstPrice);
        rstCountry = (ImageButton) findViewById(R.id.rstCountry);
        btnSearch = (Button)findViewById(R.id.btnSearch);
        tvBrand = (TextView) findViewById(R.id.tvBrand);
         tvModel = (TextView) findViewById(R.id.tvModel);
        tvGrade = (TextView) findViewById(R.id.tvGrade);
        tvMilesage = (TextView) findViewById(R.id.tvMilesage);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvCountry = (TextView)findViewById(R.id.tvCountry);
        edtSearch = (EditText) findViewById(R.id.edtSearch);
        chkOwn = (CheckBox) findViewById(R.id.chkOwn);

        if (requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }


        Intent intent = getIntent();
        strName = intent.getExtras().getString("strName","");
        strTotal = intent.getExtras().getString("strTotal","");
        strBrand = intent.getExtras().getString("strBrand","");
        strCountry= intent.getExtras().getString("strCountry","");
        minMilesage = intent.getExtras().getString("minMilesage","");
        maxMilesage = intent.getExtras().getString("maxMilesage","");
        strModel = intent.getExtras().getString("strModel","");
        strGrade = intent.getExtras().getString("strGrade","");
        minPrice = intent.getExtras().getString("minPrice","");
        maxPrice = intent.getExtras().getString("maxPrice","");
        isChk = intent.getExtras().getString("isChk","");

        if(!strBrand.equals("")) {
            tvBrand.setText(strBrand);
        }

        if(!strModel.equals("")) {
            tvModel.setText(strModel);
        }

        if(!strGrade.equals("")){
            tvGrade.setText(strGrade);
        }
        if(!minMilesage.equals("")) {
            tvMilesage.setText(minMilesage + "km ~ " + maxMilesage + "km");
        }

        if(!minPrice.equals("")) {
            tvPrice.setText(minPrice + "만원 ~ " + maxPrice + "만원");
        }

        if(!strCountry.equals("")) {
            tvCountry.setText(strCountry);
        }

        if(isChk.equals("y")){
            chkOwn.setChecked(true);
        }

        chkOwn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(chkOwn.isChecked()==false){
                    isChk = "n";

                }else{
                    isChk = "y";
                }
                setCount();
            }
        });

        rstCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvCountry.setText(" ");
                strCountry = "";
                setCount();
            }
        });
        rstPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvPrice.setText(" ");
                minPrice = "";
                maxPrice = "";
                setCount();
            }
        });

        rstMilesage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvMilesage.setText(" ");
                minMilesage = "";
                maxMilesage = "";
                setCount();

            }
        });

        rstGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvGrade.setText(" ");
                strGrade = "";
                setCount();
            }
        });

        rstModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvModel.setText(" ");
                strModel= "";
                setCount();

            }
        });
        rstBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvBrand.setText(" ");
                strBrand = "";
                setCount();
            }
        });

        btnBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), setCondition.class);
                intent.putExtra("sub",1);
                intent.putExtra("pageflag",1);
                intent.putExtra("strBrand",strBrand);
                intent.putExtra("strModel",strModel);
                intent.putExtra("strGrade",strGrade);
                intent.putExtra("minMilesage",minMilesage);
                intent.putExtra("maxMilesage",maxMilesage);
                intent.putExtra("minPrice",minPrice);
                intent.putExtra("maxPrice",maxPrice);
                intent.putExtra("strCountry",strCountry);
                intent.putExtra("isChk",isChk);
                intent.putExtra("strName","");
                intent.putExtra("strTotal",strTotal);

                startActivity(intent);
            }
        });

        btnModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), setCondition.class);
                intent.putExtra("sub",2);
                intent.putExtra("pageflag",1);
                intent.putExtra("strBrand",strBrand);
                intent.putExtra("strModel",strModel);
                intent.putExtra("strGrade",strGrade);
                intent.putExtra("minMilesage",minMilesage);
                intent.putExtra("maxMilesage",maxMilesage);
                intent.putExtra("minPrice",minPrice);
                intent.putExtra("maxPrice",maxPrice);
                intent.putExtra("strCountry",strCountry);
                intent.putExtra("isChk",isChk);
                intent.putExtra("strName","");
                intent.putExtra("strTotal",strTotal);

                startActivity(intent);
            }
        });

        btnGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), setCondition.class);
                intent.putExtra("sub",3);
                intent.putExtra("pageflag",1);
                intent.putExtra("strBrand",strBrand);
                intent.putExtra("strModel",strModel);
                intent.putExtra("strGrade",strGrade);
                intent.putExtra("minMilesage",minMilesage);
                intent.putExtra("maxMilesage",maxMilesage);
                intent.putExtra("minPrice",minPrice);
                intent.putExtra("maxPrice",maxPrice);
                intent.putExtra("strCountry",strCountry);
                intent.putExtra("isChk",isChk);
                intent.putExtra("strName","");
                intent.putExtra("strTotal",strTotal);


                startActivity(intent);


            }
        });

        btnCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        Intent intent = new Intent(getApplicationContext(), setCondition.class);
                        intent.putExtra("sub",4);
                         intent.putExtra("pageflag",1);
                intent.putExtra("strBrand",strBrand);
                intent.putExtra("strModel",strModel);
                intent.putExtra("strGrade",strGrade);
                intent.putExtra("minMilesage",minMilesage);
                intent.putExtra("maxMilesage",maxMilesage);
                intent.putExtra("minPrice",minPrice);
                intent.putExtra("maxPrice",maxPrice);
                intent.putExtra("strCountry",strCountry);
                intent.putExtra("isChk",isChk);
                intent.putExtra("strName","");
                intent.putExtra("strTotal",strTotal);


                startActivity(intent);
            }
        });

        btnPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        Intent intent = new Intent(getApplicationContext(), SetNumeric.class);
                        intent.putExtra("sub",1);
                        intent.putExtra("pageflag",1);
                intent.putExtra("strBrand",strBrand);
                intent.putExtra("strModel",strModel);
                intent.putExtra("strGrade",strGrade);
                intent.putExtra("minMilesage",minMilesage);
                intent.putExtra("maxMilesage",maxMilesage);
                intent.putExtra("minPrice",minPrice);
                intent.putExtra("maxPrice",maxPrice);
                intent.putExtra("strCountry",strCountry);
                intent.putExtra("isChk",isChk);
                intent.putExtra("strName","");
                intent.putExtra("strTotal",strTotal);


                startActivity(intent);
                    }
                });

        btnMilesage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        Intent intent = new Intent(getApplicationContext(), SetNumeric.class);
                        intent.putExtra("sub",2);
                        intent.putExtra("pageflag",1);
                 intent.putExtra("strBrand",strBrand);
                 intent.putExtra("strModel",strModel);
                intent.putExtra("strGrade",strGrade);
                intent.putExtra("minMilesage",minMilesage);
                intent.putExtra("maxMilesage",maxMilesage);
                intent.putExtra("minPrice",minPrice);
                intent.putExtra("maxPrice",maxPrice);
                intent.putExtra("strCountry",strCountry);
                intent.putExtra("isChk",isChk);
                intent.putExtra("strName","");
                intent.putExtra("strTotal",strTotal);

                        startActivity(intent);
                    }
                });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvBrand.setText(" ");
                tvModel.setText(" ");
                tvGrade.setText(" ");
                tvMilesage.setText(" ");
                tvPrice.setText(" ");
                tvCountry.setText(" ");
                chkOwn.setChecked(false);

                strBrand="";
                strModel="";
                strGrade="";
                minMilesage="";
                maxMilesage="";
                minPrice="";
                maxPrice="";
                strCountry="";
                isChk="";
                chkOwn.setChecked(false);
            }
        });


        setCount();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainPage.class);
                startActivity(intent);
            }
        });


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NameCond.class);
                intent.putExtra("strName",edtSearch.getText().toString());
                intent.putExtra("strBrand",strBrand);
                intent.putExtra("strModel",strModel);
                intent.putExtra("strGrade",strGrade);
                intent.putExtra("minMilesage",minMilesage);
                intent.putExtra("maxMilesage",maxMilesage);
                intent.putExtra("minPrice",minPrice);
                intent.putExtra("maxPrice",maxPrice);
                intent.putExtra("strCountry",strCountry);
                intent.putExtra("isChk",isChk);
                intent.putExtra("strTotal",strTotal);


                startActivity(intent);
            }
        });

        btnShList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShResult.class);
                intent.putExtra("strName",edtSearch.getText().toString());
                intent.putExtra("strBrand",strBrand);
                intent.putExtra("strModel",strModel);
                intent.putExtra("strGrade",strGrade);
                intent.putExtra("minMilesage",minMilesage);
                intent.putExtra("maxMilesage",maxMilesage);
                intent.putExtra("minPrice",minPrice);
                intent.putExtra("maxPrice",maxPrice);
                intent.putExtra("strCountry",strCountry);
                intent.putExtra("isChk",isChk);
                intent.putExtra("strTotal",strTotal);

                startActivity(intent);
            }
        });
    }
    public void setCount (){

        //php url 입력
        String URL = "http://172.26.126.172:443/setCount.php";
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
                        parsing.setCountParsing(json);
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                    btnShList.setText("매물보기 "+ parsing.count +"대");
                    strTotal = parsing.count;

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

                param.put("brand",strBrand);
                param.put("model",strModel);
                param.put("grade",strGrade);
                param.put("minmilesage",minMilesage);
                param.put("maxmilesage",maxMilesage);
                param.put("minprice",minPrice);
                param.put("maxprice",maxPrice);
                param.put("country",strCountry);
                param.put("own",isChk);

                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);
    }

}