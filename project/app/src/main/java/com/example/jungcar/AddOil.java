package com.example.jungcar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
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

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddOil extends AppCompatActivity {
    TextView tvCancel, tvDate, tvAdd;
    EditText edtMemo, edtDist,edtPlace,edtCharge,edtUnit;
    DatePicker picker;
    ImageButton btnMore;
    static StringRequest request;
    static RequestQueue requestQueue;
    String json;
    String strtgDate;
    Date now = new Date();
    DecimalFormat decimalFormat = new DecimalFormat("###,###");
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat format2 = new SimpleDateFormat("yyyy.MM.dd(E)", Locale.KOREAN);
    String strTotal;
    String strPlace, strCharge,strDate;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addoil);
        if (requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }
        Intent intent = getIntent();
        strCharge = intent.getExtras().getString("charge","");
        strPlace = intent.getExtras().getString("place", "");
        strDate = intent.getExtras().getString("date", "");

        tvCancel = (TextView) findViewById(R.id.tvCancel);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvAdd = (TextView) findViewById(R.id.tvAdd);
        edtMemo = (EditText) findViewById(R.id.edtMemo);
        edtDist = (EditText) findViewById(R.id.edtDist);
        edtCharge = (EditText) findViewById(R.id.edtCharge);
        edtUnit = (EditText) findViewById(R.id.edtUnit);
        edtPlace = (EditText) findViewById(R.id.edtPlace);
        picker= (DatePicker) findViewById(R.id.picker);
        btnMore = (ImageButton) findViewById(R.id.btnMore);

        if(!strCharge.equals("")){
            edtCharge.setText(decimalFormat.format(Integer.parseInt(strCharge)));
            edtPlace.setText(strPlace);
            try {
                Date dd = format.parse(strDate);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dd);
                strtgDate = format.format(calendar.getTime());
                tvDate.setText(format2.format(calendar.getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }else{
            strtgDate = format.format(now);
            tvDate.setText(format2.format(now));
        }

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picker.setVisibility(View.VISIBLE);
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edtCharge.getText().toString().length()==0||edtUnit.getText().toString().length()==0){
                    Toast.makeText(getApplicationContext(), "값을 입력하세요.", Toast.LENGTH_SHORT).show();
                }else {

                    if (Integer.parseInt(edtDist.getText().toString().replace(",","")) > Integer.parseInt(strTotal)) {
                        addDist();
                        addOil();
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        },1000);

                    } else if (Integer.parseInt(edtDist.getText().toString().replace(",","")) == Integer.parseInt(strTotal)) {
                        addOil();
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        },1000);
                    } else {
                        Toast.makeText(getApplicationContext(), "누적 거리가 작아졌습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



        edtDist.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                edtDist.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = edtDist.getText().length();

                    String v = editable.toString().replace(String.valueOf(decimalFormat.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = decimalFormat.parse(v);
                    int cp = edtDist.getSelectionStart();

                    edtDist.setText(decimalFormat.format(n));

                    endlen = edtDist.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= edtDist.getText().length()) {
                        edtDist.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        edtDist.setSelection(edtDist.getText().length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException e) {
                    // do nothing?
                }

                edtDist.addTextChangedListener(this);
            }
        });

        edtCharge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                edtCharge.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = edtCharge.getText().length();

                    String v = editable.toString().replace(String.valueOf(decimalFormat.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = decimalFormat.parse(v);
                    int cp = edtCharge.getSelectionStart();

                    edtCharge.setText(decimalFormat.format(n));

                    endlen = edtCharge.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= edtCharge.getText().length()) {
                        edtCharge.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        edtCharge.setSelection(edtCharge.getText().length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException e) {
                    // do nothing?
                }

                edtCharge.addTextChangedListener(this);
            }
        });

        edtUnit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                edtUnit.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = edtUnit.getText().length();

                    String v = editable.toString().replace(String.valueOf(decimalFormat.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = decimalFormat.parse(v);
                    int cp = edtUnit.getSelectionStart();

                    edtUnit.setText(decimalFormat.format(n));

                    endlen = edtUnit.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= edtUnit.getText().length()) {
                        edtUnit.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        edtUnit.setSelection(edtUnit.getText().length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException e) {
                    // do nothing?
                }

                edtUnit.addTextChangedListener(this);
            }
        });


        setDist();


    }

    public void setDist() {

        //php url 입력
        String URL = "http://172.26.126.172:443/setDist.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {

                    jsonObject = new JSONObject(response);
                    json = response;
                    Parsing parsing = new Parsing();

                    try {
                        parsing.setDistParsing(json);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    strTotal = parsing.count;
                    edtDist.setText(strTotal);
                    picker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                        @Override
                        public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {

                            strtgDate = i+"-"+(i1+1)+"-"+i2;
                            try {
                                Date dd = format.parse(strtgDate);
                                tvDate.setText(format2.format(dd.getTime()));
                                picker.setVisibility(View.INVISIBLE);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }



                        }
                    });

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
                param.put("car_idx",shared_preferences.get_user_car(AddOil.this));


                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }

    public void addDist() {

        //php url 입력
        String URL = "http://172.26.126.172:443/addDist.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @RequiresApi(api = Build.VERSION_CODES.O)
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
                param.put("car_idx",shared_preferences.get_user_car(AddOil.this));
                param.put("date",strtgDate);

                param.put("distance",edtDist.getText().toString().replace(",",""));

                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }

    public void addOil() {

        //php url 입력
        String URL = "http://172.26.126.172:443/addOil.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @RequiresApi(api = Build.VERSION_CODES.O)
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
                param.put("car_idx",shared_preferences.get_user_car(AddOil.this));
                param.put("date",strtgDate);
                param.put("memo",edtMemo.getText().toString());
                param.put("charge",edtCharge.getText().toString().replace(",",""));
                param.put("unit",edtUnit.getText().toString().replace(",",""));
                param.put("place",edtPlace.getText().toString());

                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }
}