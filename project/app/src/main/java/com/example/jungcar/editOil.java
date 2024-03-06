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

public class editOil extends AppCompatActivity {
    TextView tvCancel, tvDate, tvAdd;
    EditText edtMemo, edtPlace,edtCharge,edtUnit;
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
    String oil_idx;
    String strPlace, strCharge,strDate;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editoil);
        if (requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }
        Intent intent = getIntent();
        oil_idx = intent.getExtras().getString("oil_idx");

        tvCancel = (TextView) findViewById(R.id.tvCancel);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvAdd = (TextView) findViewById(R.id.tvAdd);
        edtMemo = (EditText) findViewById(R.id.edtMemo);

        edtCharge = (EditText) findViewById(R.id.edtCharge);
        edtUnit = (EditText) findViewById(R.id.edtUnit);
        edtPlace = (EditText) findViewById(R.id.edtPlace);
        picker= (DatePicker) findViewById(R.id.picker);
        btnMore = (ImageButton) findViewById(R.id.btnMore);


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


        setOil();

        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edtCharge.getText().toString().length()==0||edtUnit.getText().toString().length()==0){
                    Toast.makeText(getApplicationContext(), "값을 입력하세요.", Toast.LENGTH_SHORT).show();
                }else {

                  updateOil();
                  Handler handler = new Handler();
                  handler.postDelayed(new Runnable() {
                      @Override
                      public void run() {
                          finish();
                      }
                  },1000);
                }
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





    }
    public void updateOil() {

        //php url 입력
        String URL = "http://172.26.126.172:443/updateOil.php";


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
                param.put("oil_idx",oil_idx);
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

    public void setOil() {

        //php url 입력
        String URL = "http://172.26.126.172:443/setOil.php";


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
                        parsing.oilParsing(json);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Date dd = format.parse(parsing.date);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dd);
                    strtgDate = format.format(calendar.getTime());
                    tvDate.setText(format2.format(calendar.getTime()));

                    edtCharge.setText(parsing.charge);
                    edtUnit.setText(parsing.unit);
                    edtPlace.setText(parsing.place);
                    edtMemo.setText(parsing.memo);

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

                } catch (JSONException | ParseException e) {
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
                param.put("oil_idx",oil_idx);


                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }

}