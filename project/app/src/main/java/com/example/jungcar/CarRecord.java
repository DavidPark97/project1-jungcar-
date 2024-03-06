package com.example.jungcar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CarRecord extends AppCompatActivity {
    TextView tvYear,tvMonth;
    TextView tvOil,tvMaintain,tvDrive,tvCrash;
    View vOil,vMaintain,vDrive,vCrash;
    TextView tgYear,tgMonth,yrEffi,yrDist,yrCharge,yrAmount,mtEffi,mtDist,mtAmount,mtCharge,tvFold,tvSpread;
    ImageButton btnBack,btnShMore;
    static StringRequest request;
    static RequestQueue requestQueue;
    String json;
    LinearLayout llBoard;
    int strYear,strMonth;
    Calendar cal = Calendar.getInstance();
    ArrayList<oilItem> oilItems;
    oilAdpt oiladpt;
    ArrayList<maintainItem> maintainItems;
    maintainAdpt maintainadpt;
    ArrayList<driveItem> driveItems;
    driveAdpt driveadpt;
    ArrayList<crashItem> crashItems;
    DecimalFormat decimalFormat = new DecimalFormat("###,###");
    DecimalFormat decimalFormat2 = new DecimalFormat("###,###.####");
    crashAdpt crashadpt;
    ListView recList;
    int listflag = 1;

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            strYear = i;
            strMonth = i1;
            tvYear.setText(strYear+"년");
            tvMonth.setText(strMonth+"월");
            tgYear.setText(strYear+"년");
            tgMonth.setText(strMonth+"월");
            setCarRecord();
            if (listflag==1) {
                setOilList();
            }else if(listflag==2){
                setMaintainList();
            }else if(listflag==3){
                setDriveList();
            }else if(listflag==4){
                setCrashList();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        setCarRecord();
        if (listflag==1) {
            setOilList();
        }else if(listflag==2){
            setMaintainList();
        }else if(listflag==3){
            setDriveList();
        }else if(listflag==4){
            setCrashList();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carrecord);
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        tvYear = (TextView) findViewById(R.id.tvYear);
        tvMonth = (TextView) findViewById(R.id.tvMonth);
        tgYear = (TextView) findViewById(R.id.tgYear);
        tgMonth = (TextView) findViewById(R.id.tgMonth);
        yrEffi = (TextView) findViewById(R.id.yrEffi);
        yrDist = (TextView) findViewById(R.id.yrDist);
        yrCharge = (TextView) findViewById(R.id.yrCharge);
        yrAmount = (TextView) findViewById(R.id.yrAmount);
        mtEffi = (TextView) findViewById(R.id.mtEffi);
        mtDist = (TextView) findViewById(R.id.mtDist);
        mtCharge = (TextView) findViewById(R.id.mtCharge);
        mtAmount = (TextView) findViewById(R.id.mtAmount);
        tvOil = (TextView) findViewById(R.id.tvOil);
        tvCrash = (TextView) findViewById(R.id.tvCrash);
        tvMaintain = (TextView) findViewById(R.id.tvMaintain);
        tvDrive = (TextView) findViewById(R.id.tvDrive);
        vOil = (View)findViewById(R.id.vOil);
        vMaintain = (View)findViewById(R.id.vMaintain);
        vDrive = (View)findViewById(R.id.vDrive);
        vCrash = (View)findViewById(R.id.vCrash);

        tvFold = (TextView) findViewById(R.id.tvFold);
        tvSpread = (TextView) findViewById(R.id.tvSpread);
        llBoard = (LinearLayout) findViewById(R.id.llBoard);
        btnShMore = (ImageButton) findViewById(R.id.btnShMore);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        recList = (ListView)findViewById(R.id.recList);
        strMonth = cal.get(Calendar.MONTH) + 1;
        strYear = cal.get(Calendar.YEAR);
        tvYear.setText(strYear + "년");
        tvMonth.setText(strMonth + "월");
        tgYear.setText(strYear + "년");
        tgMonth.setText(strMonth + "월");
        setCarRecord();
        setOilList();
        btnShMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monthPicker mp = new monthPicker();
                mp.setListener(d, strYear, strMonth);
                mp.show(getSupportFragmentManager(), "SelectMonth");
            }
        });

        tvFold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.height = 0;
                lp.weight = 0;
                llBoard.setLayoutParams(lp);
                tvSpread.setVisibility(View.VISIBLE);

            }
        });

        tvSpread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.height = 0;
                lp.weight = 2;
                llBoard.setLayoutParams(lp);
                tvSpread.setVisibility(View.INVISIBLE);
            }
        });

        tvOil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listflag = 1;
                tvOil.setTextColor(Color.parseColor("#ffffff"));;
                vOil.setVisibility(View.VISIBLE);
                vCrash.setVisibility(View.INVISIBLE);
                vMaintain.setVisibility(View.INVISIBLE);
                vDrive.setVisibility(View.INVISIBLE);
                tvCrash.setTextColor(Color.parseColor("#aaaaaa"));
                tvMaintain.setTextColor(Color.parseColor("#aaaaaa"));
                tvDrive.setTextColor(Color.parseColor("#aaaaaa"));
                setOilList();
            }
        });

        tvDrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listflag = 3;
                tvDrive.setTextColor(Color.parseColor("#ffffff"));;
                vDrive.setVisibility(View.VISIBLE);
                vCrash.setVisibility(View.INVISIBLE);
                vMaintain.setVisibility(View.INVISIBLE);
                vOil.setVisibility(View.INVISIBLE);
                tvCrash.setTextColor(Color.parseColor("#aaaaaa"));
                tvOil.setTextColor(Color.parseColor("#aaaaaa"));
                tvMaintain.setTextColor(Color.parseColor("#aaaaaa"));
                setDriveList();
            }
        });

        tvMaintain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listflag = 2;
                tvMaintain.setTextColor(Color.parseColor("#ffffff"));;
                vMaintain.setVisibility(View.VISIBLE);
                vCrash.setVisibility(View.INVISIBLE);
                vOil.setVisibility(View.INVISIBLE);
                vDrive.setVisibility(View.INVISIBLE);
                tvCrash.setTextColor(Color.parseColor("#aaaaaa"));
                tvOil.setTextColor(Color.parseColor("#aaaaaa"));
                tvDrive.setTextColor(Color.parseColor("#aaaaaa"));
                setMaintainList();
            }
        });

        tvCrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listflag = 4;
                tvCrash.setTextColor(Color.parseColor("#ffffff"));;
                vCrash.setVisibility(View.VISIBLE);
                vMaintain.setVisibility(View.INVISIBLE);
                vOil.setVisibility(View.INVISIBLE);
                vDrive.setVisibility(View.INVISIBLE);
                tvMaintain.setTextColor(Color.parseColor("#aaaaaa"));
                tvOil.setTextColor(Color.parseColor("#aaaaaa"));
                tvDrive.setTextColor(Color.parseColor("#aaaaaa"));
                setCrashList();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void setCrashList() {

        //php url 입력
        String URL = "http://172.26.126.172:443/setCrashList.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            int index=0;
            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {
                    crashItems = new ArrayList<crashItem>();

                    jsonObject = new JSONObject(response);

                    Parsing parsing = new Parsing();
                    try {
                        crashItem mi;
                        while (true) {

                            parsing.crashitemParsing(response, index);
                            if (parsing.idx == "") {
                                break;
                            }
                            String location = parsing.address;
                            String memo = parsing.memo;
                            String date = parsing.date;
                            String crash_idx=parsing.idx;

                            index++;
                            mi = new crashItem(location,date,memo,crash_idx);
                            crashItems.add(mi);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                crashadpt = new crashAdpt(CarRecord.this,R.layout.reccrashlist,crashItems);
                crashadpt.setOnItemClickListener(new crashAdpt.OnItemClickListener() {
                    @Override
                    public void onItemClick(int pos) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                              setCrashList();
                              setCarRecord();
                            }
                        },100);
                    }
                });
                recList.setAdapter(crashadpt);
                index=0;
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

                param.put("car_idx",shared_preferences.get_user_car(CarRecord.this));
                param.put("year",Integer.toString(strYear));
                param.put("month",Integer.toString(strMonth));

                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);



    }

    public void setDriveList() {

        //php url 입력
        String URL = "http://172.26.126.172:443/setDriveList.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            int index=0;
            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {
                    driveItems = new ArrayList<driveItem>();

                    jsonObject = new JSONObject(response);

                    Parsing parsing = new Parsing();
                    try {
                        driveItem mi;
                        while (true) {

                            parsing.driveitemParsing(response, index);
                            if (parsing.total == "") {
                                break;
                            }


                            String total = parsing.total;
                            String memo = parsing.memo;
                            String distance= parsing.distance;
                            String date = parsing.date;
                            String drive_idx=parsing.idx;

                            index++;
                            mi = new driveItem(total,date,memo,distance,drive_idx);
                            driveItems.add(mi);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                driveadpt = new driveAdpt(CarRecord.this,R.layout.recdrivelist,driveItems);
                driveadpt.setOnItemClickListener(new driveAdpt.OnItemClickListener() {
                    @Override
                    public void onItemClick(int pos) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setDriveList();
                                setCarRecord();
                            }
                        },100);
                    }
                });
                recList.setAdapter(driveadpt);
                index=0;
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

                param.put("car_idx",shared_preferences.get_user_car(CarRecord.this));
                param.put("year",Integer.toString(strYear));
                param.put("month",Integer.toString(strMonth));

                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);



    }


    public void setMaintainList() {

        //php url 입력
        String URL = "http://172.26.126.172:443/setMaintainList.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            int index=0;
            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {
                    maintainItems = new ArrayList<maintainItem>();

                    jsonObject = new JSONObject(response);

                    Parsing parsing = new Parsing();
                    try {
                        maintainItem mi;
                        while (true) {

                            parsing.maintainitemParsing(response, index);
                            if (parsing.center == "") {
                                break;
                            }


                            String center = parsing.center;
                            String type_name = parsing.name;
                            String charge= parsing.charge;
                            String date = parsing.date;
                            String main_idx=parsing.idx;

                            index++;
                            mi = new maintainItem(center,date,type_name,charge,main_idx);
                            maintainItems.add(mi);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                maintainadpt = new maintainAdpt(CarRecord.this,R.layout.recmainlist,maintainItems);
                maintainadpt.setOnItemClickListener(new maintainAdpt.OnItemClickListener() {
                    @Override
                    public void onItemClick(int pos) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setMaintainList();
                                setCarRecord();
                            }
                        },100);
                    }
                });
                recList.setAdapter(maintainadpt);
                index=0;
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

                param.put("car_idx",shared_preferences.get_user_car(CarRecord.this));
                param.put("year",Integer.toString(strYear));
                param.put("month",Integer.toString(strMonth));

                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);



    }



    public void setOilList() {

            //php url 입력
            String URL = "http://172.26.126.172:443/setOilList.php";


            StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            int index=0;
                JSONObject jsonObject;

                @Override
                public void onResponse(String response) {
                    //응답이 되었을때 response로 값이 들어옴
                    try {
                        oilItems = new ArrayList<oilItem>();

                        jsonObject = new JSONObject(response);

                        Parsing parsing = new Parsing();
                        try {
                            oilItem mi;
                            while (true) {

                                parsing.oilitemParsing(response, index);
                                if (parsing.total == "") {
                                    break;
                                }


                                String total = parsing.total;
                                String date = parsing.date;
                                String memo = parsing.memo;
                                String charge= parsing.charge;
                                String amount= parsing.amount;
                                String unit=parsing.unit;
                                String oil_idx=parsing.idx;

                                index++;

                                mi = new oilItem(total,date,memo,charge,amount,unit,oil_idx);
                                oilItems.add(mi);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    oiladpt = new oilAdpt(CarRecord.this,R.layout.recoillist,oilItems);
                    oiladpt.setOnItemClickListener(new oilAdpt.OnItemClickListener() {
                        @Override
                        public void onItemClick(int pos) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    setOilList();
                                    setCarRecord();
                                }
                            },100);
                        }
                    });
                    recList.setAdapter(oiladpt);
                    index=0;
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

                    param.put("car_idx",shared_preferences.get_user_car(CarRecord.this));
                    param.put("year",Integer.toString(strYear));
                    param.put("month",Integer.toString(strMonth));

                    return param;
                }
            };
            request.setShouldCache(false);
            requestQueue.add(request);



        }


    public void setCarRecord() {

        //php url 입력
        String URL = "http://172.26.126.172:443/setCarRecord.php";


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
                    Parsing parsing2 = new Parsing();
                    try {
                        parsing.recyearParsing(json);
                        parsing2.recmthParsing(json);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    yrCharge.setText(decimalFormat.format(Float.parseFloat(parsing.charge))+"₩");
                    yrDist.setText(decimalFormat.format(Float.parseFloat(parsing.distance))+"km");
                    yrEffi.setText(decimalFormat2.format(Float.parseFloat(parsing.effi))+"km/L");
                    yrAmount.setText(decimalFormat2.format(Float.parseFloat(parsing.amount))+"L");
                    mtCharge.setText(decimalFormat.format(Float.parseFloat(parsing2.charge))+"₩");
                    mtDist.setText(decimalFormat.format(Float.parseFloat(parsing2.distance))+"km");
                    mtEffi.setText(decimalFormat2.format(Float.parseFloat(parsing2.effi))+"km/L");
                    mtAmount.setText(decimalFormat2.format(Float.parseFloat(parsing2.amount))+"L");


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
                param.put("car_idx",shared_preferences.get_user_car(CarRecord.this));
                param.put("year",Integer.toString(strYear));
                param.put("month",Integer.toString(strMonth));

                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }


}
class oilItem {
    String total;
    String date;
    String memo;
    String charge;
    String amount;
    String unit;
    String oil_idx;


    oilItem(String total, String date, String memo, String charge, String amount, String unit, String oil_idx) {
        this.total=total;
        this.date=date;
        this.memo=memo;
        this.charge=charge;
        this.amount=amount;
        this.unit=unit;
        this.oil_idx=oil_idx;
    }
}
class oilAdpt extends BaseAdapter {
    @Override
    public long getItemId(int i) {
        return 0;
    }

    DecimalFormat decimalFormat = new DecimalFormat("###,###");
    DecimalFormat decimalFormat2 = new DecimalFormat("###,###.####");
    Context maincon;
    LayoutInflater Inflater;
    ArrayList<oilItem> arsrc;
    int layout;
    RequestQueue requestQueue;
    public RequestQueue getRequestQueue() {
        requestQueue = Volley.newRequestQueue(maincon.getApplicationContext());
        return requestQueue;
    }




    public oilAdpt(Context context, int alayout, ArrayList<oilItem> aarSrc) {
        maincon = context;
        Inflater= (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        arsrc = aarSrc;
        layout = alayout;

    }
    public int getCount() {
        return arsrc.size();
    }

    public String getItem(int position) {
        return arsrc.get(position).oil_idx;
    }

    public void updateReceiptsList(ArrayList<oilItem> arsrc) {
        arsrc = this.arsrc;
        int size = arsrc.size();
        this.notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    private oilAdpt.OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(oilAdpt.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        if(convertView == null) {
            convertView = Inflater.inflate(layout,parent,false);
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2 = new SimpleDateFormat("MM.dd");
        TextView total = (TextView)convertView.findViewById(R.id.total);
        TextView date = (TextView)convertView.findViewById(R.id.date);
        TextView memo = (TextView)convertView.findViewById(R.id.memo);
        TextView charge = (TextView)convertView.findViewById(R.id.charge);
        TextView amount = (TextView)convertView.findViewById(R.id.amount);
        TextView unit = (TextView)convertView.findViewById(R.id.unit);
        total.setText(decimalFormat.format(Integer.parseInt(arsrc.get(position).total)) +" km");
        total.setTag(arsrc.get(position).oil_idx);
        try {
            Date dd = format.parse(arsrc.get(position).date);
            date.setText(format2.format(dd.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        memo.setText(arsrc.get(position).memo);
        charge.setText("₩" + decimalFormat.format(Integer.parseInt(arsrc.get(position).charge)));
        amount.setText("주유 " + arsrc.get(position).amount + "L");
        unit.setText(decimalFormat.format(Integer.parseInt(arsrc.get(position).unit)) + "₩/L");

        ImageButton shmore = (ImageButton) convertView.findViewById(R.id.shmore);


        shmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        final PopupMenu popupMenu = new PopupMenu(maincon.getApplicationContext(), view);
                        popupMenu.getMenuInflater().inflate(R.menu.recordmenu, popupMenu.getMenu());
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                if (menuItem.getItemId() == R.id.sell_menu1) {

                                    if (position != ListView.INVALID_POSITION) {
                                        if (onItemClickListener != null) {
                                            onItemClickListener.onItemClick(position);

                                            Intent intent = new Intent(maincon.getApplicationContext(), editOil.class);
                                            intent.putExtra("oil_idx", arsrc.get(position).oil_idx);

                                            maincon.startActivity(intent);
                                        }
                                    }
                                } else if (menuItem.getItemId() == R.id.sell_menu2) {
                                    if (position != ListView.INVALID_POSITION) {
                                        if (onItemClickListener != null) {
                                            onItemClickListener.onItemClick(position);

                                            delOil(arsrc.get(position).oil_idx);
                                        }
                                    }

                                }

                                return false;
                            }
                        });
                        popupMenu.show();
                    }


        });




        return convertView;


    }

    public void delOil(String oil_idx){
        requestQueue = getRequestQueue();

        //php url 입력
        String URL = "http://172.26.126.172:443/delOil.php";

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {
                    jsonObject = new JSONObject(response);



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
                return param;
            }

        };
        request.setShouldCache(false);
        requestQueue.add(request);



    }


}

class maintainItem {
    String center_name;
    String type_name;
    String date;
    String charge;
    String main_idx;


    maintainItem(String center_name, String date, String type_name, String charge, String main_idx) {
        this.center_name=center_name;
        this.date=date;
        this.charge=charge;
        this.type_name=type_name;
        this.main_idx=main_idx;
    }
}
class maintainAdpt extends BaseAdapter {
    @Override
    public long getItemId(int i) {
        return 0;
    }

    DecimalFormat decimalFormat = new DecimalFormat("###,###");
    DecimalFormat decimalFormat2 = new DecimalFormat("###,###.####");
    Context maincon;
    LayoutInflater Inflater;
    ArrayList<maintainItem> arsrc;
    int layout;

    RequestQueue requestQueue;
    public RequestQueue getRequestQueue() {
        requestQueue = Volley.newRequestQueue(maincon.getApplicationContext());
        return requestQueue;
    }
    public maintainAdpt(Context context, int alayout, ArrayList<maintainItem> aarSrc) {
        maincon = context;
        Inflater= (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        arsrc = aarSrc;
        layout = alayout;

    }
    public int getCount() {
        return arsrc.size();
    }

    public String getItem(int position) {
        return arsrc.get(position).main_idx;
    }

    public void updateReceiptsList(ArrayList<maintainItem> arsrc) {
        arsrc = this.arsrc;
        int size = arsrc.size();
        this.notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    private maintainAdpt.OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(maintainAdpt.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        if(convertView == null) {
            convertView = Inflater.inflate(layout,parent,false);
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2 = new SimpleDateFormat("MM.dd");
        TextView type_name = (TextView)convertView.findViewById(R.id.type_name);
        TextView date = (TextView)convertView.findViewById(R.id.date);
        TextView charge = (TextView)convertView.findViewById(R.id.charge);
        TextView center_name = (TextView)convertView.findViewById(R.id.center_name);

        type_name.setText(arsrc.get(position).type_name);
        type_name.setTag(arsrc.get(position).main_idx);
        try {
            Date dd = format.parse(arsrc.get(position).date);
            date.setText(format2.format(dd.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        charge.setText("₩" + decimalFormat.format(Integer.parseInt(arsrc.get(position).charge)));
        center_name.setText(arsrc.get(position).center_name);


        ImageButton shmore = (ImageButton) convertView.findViewById(R.id.shmore);


        shmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        final PopupMenu popupMenu = new PopupMenu(maincon.getApplicationContext(), view);
                        popupMenu.getMenuInflater().inflate(R.menu.recordmenu, popupMenu.getMenu());
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                if (menuItem.getItemId() == R.id.sell_menu1) {
                                    if (position != ListView.INVALID_POSITION) {
                                        if (onItemClickListener != null) {
                                            onItemClickListener.onItemClick(position);
                                            Intent intent = new Intent(maincon.getApplicationContext(), editMaintain.class);
                                            intent.putExtra("main_idx", arsrc.get(position).main_idx);
                                            maincon.startActivity(intent);
                                        }
                                    }
                                } else if (menuItem.getItemId() == R.id.sell_menu2) {

                                    if (position != ListView.INVALID_POSITION) {
                                        if (onItemClickListener != null) {
                                            onItemClickListener.onItemClick(position);
                                            delMaintain(arsrc.get(position).main_idx);
                                        }
                                    }
                                }

                                return false;
                            }
                        });
                        popupMenu.show();
                    }

        });



        return convertView;


    }

    public void delMaintain(String main_idx){
        requestQueue = getRequestQueue();

        //php url 입력
        String URL = "http://172.26.126.172:443/delMaintain.php";

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {
                    jsonObject = new JSONObject(response);



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

                param.put("main_idx",main_idx);

                return param;
            }

        };
        request.setShouldCache(false);
        requestQueue.add(request);



    }

}

class driveItem {
    String total;
    String date;
    String memo;
    String distance;
    String drive_idx;


    driveItem(String total, String date, String memo, String distance, String drive_idx) {
        this.total=total;
        this.date=date;
        this.memo=memo;
        this.distance=distance;
        this.drive_idx=drive_idx;
    }
}
class driveAdpt extends BaseAdapter {
    @Override
    public long getItemId(int i) {
        return 0;
    }

    DecimalFormat decimalFormat = new DecimalFormat("###,###");
    DecimalFormat decimalFormat2 = new DecimalFormat("###,###.####");
    Context maincon;
    LayoutInflater Inflater;
    ArrayList<driveItem> arsrc;
    int layout;
    RequestQueue requestQueue;
    public RequestQueue getRequestQueue() {
        requestQueue = Volley.newRequestQueue(maincon.getApplicationContext());
        return requestQueue;
    }


    public driveAdpt(Context context, int alayout, ArrayList<driveItem> aarSrc) {
        maincon = context;
        Inflater= (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        arsrc = aarSrc;
        layout = alayout;

    }
    public int getCount() {
        return arsrc.size();
    }

    public String getItem(int position) {
        return arsrc.get(position).drive_idx;
    }

    public void updateReceiptsList(ArrayList<driveItem> arsrc) {
        arsrc = this.arsrc;
        int size = arsrc.size();
        this.notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    private driveAdpt.OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(driveAdpt.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        if(convertView == null) {
            convertView = Inflater.inflate(layout,parent,false);
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2 = new SimpleDateFormat("MM.dd");
        TextView total = (TextView)convertView.findViewById(R.id.total);
        TextView date = (TextView)convertView.findViewById(R.id.date);
        TextView memo = (TextView)convertView.findViewById(R.id.memo);
        TextView distance = (TextView)convertView.findViewById(R.id.distance);
        total.setText(decimalFormat.format(Integer.parseInt(arsrc.get(position).total)) +" km");
        total.setTag(arsrc.get(position).drive_idx);
        try {
            Date dd = format.parse(arsrc.get(position).date);
            date.setText(format2.format(dd.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        memo.setText(arsrc.get(position).memo);
        distance.setText(decimalFormat.format(Integer.parseInt(arsrc.get(position).distance)) + "km");

        ImageButton shmore = (ImageButton) convertView.findViewById(R.id.shmore);


        shmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                        final PopupMenu popupMenu = new PopupMenu(maincon.getApplicationContext(), view);
                        popupMenu.getMenuInflater().inflate(R.menu.recordmenu, popupMenu.getMenu());
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                if (menuItem.getItemId() == R.id.sell_menu1) {
                                    if (position != ListView.INVALID_POSITION) {
                                        if (onItemClickListener != null) {
                                            onItemClickListener.onItemClick(position);
                                            Intent intent = new Intent(maincon.getApplicationContext(), editDist.class);
                                            intent.putExtra("drive_idx", arsrc.get(position).drive_idx);
                                            maincon.startActivity(intent);
                                        }
                                    }
                                } else if (menuItem.getItemId() == R.id.sell_menu2) {

                                    if (position != ListView.INVALID_POSITION) {
                                        if (onItemClickListener != null) {
                                            onItemClickListener.onItemClick(position);
                                            delDrvie(arsrc.get(position).drive_idx);
                                        }
                                    }

                                }

                                return false;
                            }

                        });

                        popupMenu.show();

                    }

        });



        return convertView;


    }
    public void delDrvie(String drive_idx){
        requestQueue = getRequestQueue();

        //php url 입력
        String URL = "http://172.26.126.172:443/delDrive.php";

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {
                    jsonObject = new JSONObject(response);



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

                param.put("drive_idx",drive_idx);

                return param;
            }

        };
        request.setShouldCache(false);
        requestQueue.add(request);



    }


}

class crashItem {
    String location;
    String date;
    String memo;
    String crash_idx;


    crashItem(String location, String date, String memo,String crash_idx) {
        this.location=location;
        this.date=date;
        this.memo=memo;
        this.crash_idx=crash_idx;
    }
}
class crashAdpt extends BaseAdapter {
    @Override
    public long getItemId(int i) {
        return 0;
    }


    Context maincon;
    LayoutInflater Inflater;
    ArrayList<crashItem> arsrc;
    int layout;
    RequestQueue requestQueue;
    public RequestQueue getRequestQueue() {
        requestQueue = Volley.newRequestQueue(maincon.getApplicationContext());
        return requestQueue;
    }

    public crashAdpt(Context context, int alayout, ArrayList<crashItem> aarSrc) {
        maincon = context;
        Inflater= (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        arsrc = aarSrc;
        layout = alayout;

    }
    public int getCount() {
        return arsrc.size();
    }

    public String getItem(int position) {
        return arsrc.get(position).crash_idx;
    }

    public void updateReceiptsList(ArrayList<crashItem> arsrc) {
        arsrc = this.arsrc;
        int size = arsrc.size();
        this.notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    private crashAdpt.OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(crashAdpt.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        if(convertView == null) {
            convertView = Inflater.inflate(layout,parent,false);
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2 = new SimpleDateFormat("MM.dd");
        TextView location = (TextView)convertView.findViewById(R.id.location);
        TextView date = (TextView)convertView.findViewById(R.id.date);
        TextView memo = (TextView)convertView.findViewById(R.id.memo);

        location.setText("사고장소: "+arsrc.get(position).location);
        location.setTag(arsrc.get(position).crash_idx);
        try {
            Date dd = format.parse(arsrc.get(position).date);
            date.setText(format2.format(dd.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        memo.setText(arsrc.get(position).memo);


        ImageButton shmore = (ImageButton) convertView.findViewById(R.id.shmore);


        shmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                        final PopupMenu popupMenu = new PopupMenu(maincon.getApplicationContext(), view);
                        popupMenu.getMenuInflater().inflate(R.menu.recordmenu, popupMenu.getMenu());
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                if (menuItem.getItemId() == R.id.sell_menu1) {
                                    if (position != ListView.INVALID_POSITION) {
                                        if (onItemClickListener != null) {
                                            onItemClickListener.onItemClick(position);
                                            Intent intent = new Intent(maincon.getApplicationContext(), editCrash.class);
                                            intent.putExtra("crash_idx", arsrc.get(position).crash_idx);
                                            maincon.startActivity(intent);
                                        }
                                    }
                                } else if (menuItem.getItemId() == R.id.sell_menu2) {
                                    if (position != ListView.INVALID_POSITION) {
                                        if (onItemClickListener != null) {
                                            onItemClickListener.onItemClick(position);

                                            delCrash(arsrc.get(position).crash_idx);
                                        }
                                    }

                                }

                                return false;
                            }

                        });
                        popupMenu.show();
                    }


        });




        return convertView;


    }

    public void delCrash(String crash_idx){
        requestQueue = getRequestQueue();

        //php url 입력
        String URL = "http://172.26.126.172:443/delCrash.php";

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {
                    jsonObject = new JSONObject(response);


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

                param.put("crash_idx",crash_idx);

                return param;
            }

        };
        request.setShouldCache(false);
        requestQueue.add(request);



    }

}

