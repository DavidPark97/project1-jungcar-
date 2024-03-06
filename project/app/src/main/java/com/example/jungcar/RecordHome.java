package com.example.jungcar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
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

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class RecordHome extends AppCompatActivity {
    ImageView imgLogo, imgCar,imgMy;
    TextView tvBill, tvStatics, tvSerSch, tvPartSell, tvEffi, tvOil, tvDist, tvMaintain, tvCrash, tvBook, tvSerChk;
    EditText edtPlnumber;
    Button btnAdjust, btnFind,btnSerChk;

    ImageButton btnCancel, btnBack;
    LinearLayout llCrash, llMaintain, llDist, llOil ;
    String car_idx;
    String json;
    DecimalFormat decimalFormat = new DecimalFormat("###,###");

    @Override
    protected void onResume() {
        super.onResume();
        if(!car_idx.equals("")){
            btnAdjust.setBackgroundColor(Color.GRAY);
            btnAdjust.setClickable(false);
            btnCancel.setVisibility(View.VISIBLE);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setCarInfo();
                    setRecord();
                }
            },1000);

            edtPlnumber.setClickable(false);

        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),MainSell.class);
        startActivity(intent);
    }


    String strLogo, strCar;
    static StringRequest request;
    static RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recordhome);
        if (requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }

        imgLogo = (ImageView) findViewById(R.id.imgLogo);
        imgCar = (ImageView) findViewById(R.id.imgCar);
        tvBill = (TextView) findViewById(R.id.tvBill);
        tvStatics = (TextView) findViewById(R.id.tvStatics);
        tvSerSch = (TextView) findViewById(R.id.tvSerSch);
        tvPartSell = (TextView) findViewById(R.id.tvPartSell);
        tvEffi = (TextView) findViewById(R.id.tvEffi);
        tvOil = (TextView) findViewById(R.id.tvOil);
        tvDist = (TextView) findViewById(R.id.tvDist);
        tvMaintain = (TextView) findViewById(R.id.tvMaintain);
        tvCrash = (TextView) findViewById(R.id.tvCrash);
        tvBook = (TextView) findViewById(R.id.tvBook);
        tvSerChk = (TextView) findViewById(R.id.tvSerChk);
        edtPlnumber = (EditText) findViewById(R.id.edtPlnumber);
        btnAdjust = (Button) findViewById(R.id.btnAdjust);
        btnFind = (Button)findViewById(R.id.btnFind);
        btnCancel = (ImageButton) findViewById(R.id.btnCancel);
        llCrash = (LinearLayout)findViewById(R.id.llCrash);
        llMaintain = (LinearLayout)findViewById(R.id.llMaintain);
        llDist = (LinearLayout)findViewById(R.id.llDist);
        llOil = (LinearLayout)findViewById(R.id.llOil);
        btnSerChk = (Button) findViewById(R.id.btnSerChk);
        imgMy = (ImageView)findViewById(R.id.imgMy);
        btnBack = (ImageButton)findViewById(R.id.btnBack);
        car_idx= shared_preferences.get_user_car(this);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainPage.class);
                startActivity(intent);
            }
        });

        imgMy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shared_preferences.get_user_email(RecordHome.this).isEmpty()==true) {
                    new AlertDialog.Builder(RecordHome.this)
                            .setTitle("로그인이 필요한 서비스입니다.\n로그인 하시겠습니까?")
                            .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int j) {

                                    Intent intent = new Intent(getApplicationContext(), login.class);
                                    startActivity(intent);

                                }
                            })
                            .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int j) {

                                }
                            })
                            .show();
                }
                else{
                    Intent intent = new Intent(getApplicationContext(),MyPage.class);
                    startActivity(intent);
                }

            }
        });



        if(!car_idx.equals("")){
            btnAdjust.setBackgroundColor(Color.GRAY);
            btnAdjust.setClickable(false);
            btnCancel.setVisibility(View.VISIBLE);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setCarInfo();
                    setRecord();
                }
            },1000);

            edtPlnumber.setClickable(false);

        }

            btnAdjust.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ConnectMycar();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), RecordHome.class);
                            startActivity(intent);
                        }
                    },1000);

                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shared_preferences.clear_car(RecordHome.this);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), RecordHome.class);
                            startActivity(intent);
                        }
                    },1000);

                }
            });

            llDist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), AddDist.class);
                    startActivity(intent);
                }
            });

        llOil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddOil.class);
                intent.putExtra("place","");
                intent.putExtra("charge","");
                intent.putExtra("date","");
                startActivity(intent);
            }
        });

        llMaintain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SelectManage.class);
                startActivity(intent);
            }
        });

        llCrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddCrash.class);
                startActivity(intent);
            }
        });

        tvBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CarRecord.class);
                startActivity(intent);
            }
        });

        tvStatics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShStatics.class);
                startActivity(intent);
            }
        });

        tvSerSch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShSerSch.class);
                intent.putExtra("strDist",tvDist.getText().toString());
                startActivity(intent);
            }
        });

        btnSerChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SelectReserve.class);
                intent.putExtra("strDist",tvDist.getText().toString());
                startActivity(intent);
            }
        });

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shared_preferences.get_user_email(RecordHome.this).isEmpty()==true) {
                    new AlertDialog.Builder(RecordHome.this)
                            .setTitle("로그인이 필요한 서비스입니다.\n로그인 하시겠습니까?")
                            .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int j) {

                                    Intent intent = new Intent(getApplicationContext(), login.class);
                                    startActivity(intent);

                                }
                            })
                            .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int j) {

                                }
                            })
                            .show();
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), FindCenter.class);
                    startActivity(intent);
                }

            }
        });
    }
    public void ConnectMycar() {

        //php url 입력
        String URL = "http://172.26.126.172:443/ConnectMycar.php";


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
                        parsing.setParsing(json);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    shared_preferences.set_user_car(RecordHome.this,parsing.idx);
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

                param.put("plnumber",edtPlnumber.getText().toString());
                param.put("user_idx",shared_preferences.get_user_email(RecordHome.this));

                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);




    }


    public void setCarInfo() {

        //php url 입력
        String URL = "http://172.26.126.172:443/setCarInfo.php";


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
                        parsing.setCarInfoParsing(json);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    strLogo = "http://172.26.126.172:443/img/" + parsing.name +".png";
                    Glide.with(RecordHome.this).load(strLogo).into(imgLogo);

                    strCar = "http://172.26.126.172:443/img/" + parsing.model + ".png";
                    Glide.with(RecordHome.this).load(strCar).into(imgCar);

                    edtPlnumber.setText(parsing.number);


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

                param.put("car_idx",shared_preferences.get_user_car(RecordHome.this));

                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }

    public void setRecord() {

        //php url 입력
        String URL = "http://172.26.126.172:443/setRecord.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {

                    jsonObject = new JSONObject(response);

                    Parsing parsing = new Parsing();
                    try {
                        int index=0;
                        resItem mi;
                        while (true) {

                            parsing.numparsing(response, index);
                            if (parsing.count == "") {

                                break;
                            }

                            if(index==0){
                                tvOil.setText(decimalFormat.format(Integer.parseInt(parsing.count))+" 원");
                            }else if(index==1){
                                tvDist.setText(decimalFormat.format(Integer.parseInt(parsing.count))+" km");
                            }else if(index==2){
                                tvMaintain.setText(decimalFormat.format(Integer.parseInt(parsing.count))+" 원");
                            }else if(index==3){
                                tvCrash.setText(parsing.count+"건");
                            }else if(index==4){
                                tvEffi.setText(parsing.count);
                            }else if (index==5){
                                tvSerChk.setText(parsing.count+"건");
                            }


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

                param.put("car_idx",shared_preferences.get_user_car(RecordHome.this));

                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }
}