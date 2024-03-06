package com.example.jungcar;

import android.app.Activity ;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import kr.co.bootpay.Bootpay;
import kr.co.bootpay.BootpayAnalytics;
import kr.co.bootpay.enums.Method;
import kr.co.bootpay.enums.PG;
import kr.co.bootpay.enums.UX;
import kr.co.bootpay.listener.CancelListener;
import kr.co.bootpay.listener.CloseListener;
import kr.co.bootpay.listener.ConfirmListener;
import kr.co.bootpay.listener.DoneListener;
import kr.co.bootpay.listener.ErrorListener;
import kr.co.bootpay.listener.ReadyListener;
import kr.co.bootpay.model.BootExtra;
import kr.co.bootpay.model.BootUser;

public class DealerPopup extends Activity {


    static StringRequest request;
    static RequestQueue requestQueue;
    String json;
    TextView tvName, tvDist, tvPrice;
    Button btnCancel, btnEnroll;
    DecimalFormat decimalFormat = new DecimalFormat("###,###");
    String strPlnumber, strDist,strPrice,strStart,strEnd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dealerpopup);
        tvName = (TextView) findViewById(R.id.tvName);
        tvDist = (TextView) findViewById(R.id.tvDist);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnEnroll = (Button) findViewById(R.id.btnEnroll);

        Intent intent = getIntent();
        strPlnumber = intent.getExtras().getString("strPlnumber");
        strDist = intent.getExtras().getString("strDist");
        strStart = intent.getExtras().getString("strStart");
        strEnd = intent.getExtras().getString("strEnd");
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        setPrice();

        btnEnroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SetSellCar.class);
                intent.putExtra("strBrand","");
                intent.putExtra("strCountry","");
                intent.putExtra("strMilesage",strDist);
                intent.putExtra("strGrade","");
                intent.putExtra("strPrice",strPrice);
                intent.putExtra("strModel","");
                intent.putExtra("strOption","");
                intent.putExtra("strPlnumber",strPlnumber);
                intent.putExtra("isChk","n");
                startActivity(intent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    public void setPrice() {

        //php url 입력
        String URL = "http://172.26.126.172:443/setPrice.php";


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
                        parsing.priceParsing(json);
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }

                    tvName.setText(parsing.name);
                    tvPrice.setText(decimalFormat.format(Integer.parseInt(parsing.price)));
                    tvDist.setText(decimalFormat.format(Integer.parseInt(strDist)));
                    strPrice = parsing.price;



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
                param.put("start",strStart);
                param.put("end",strEnd);
                param.put("vhrno",strPlnumber);
                param.put("distance",strDist);
                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }

}
