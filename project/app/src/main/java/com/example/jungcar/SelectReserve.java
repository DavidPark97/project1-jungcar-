package com.example.jungcar;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SelectReserve extends AppCompatActivity {

    ImageButton btnBack;
    Button btnRecord, btnCenter;
    TextView tvDate, tvDist;
    ListView reserveList;
    static StringRequest request;
    static RequestQueue requestQueue;
    String json;
    reserveAdpt reserveadpt;
    ArrayList<serschItem> serschItems;

    DecimalFormat decimalFormat = new DecimalFormat("###,###");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectreserve);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        tvDate= (TextView) findViewById(R.id.tvDate);
        tvDist = (TextView) findViewById(R.id.tvDist);
        reserveList = (ListView) findViewById(R.id.reserveList);
        btnRecord = (Button)findViewById(R.id.btnRecord);
        btnCenter = (Button)findViewById(R.id.btnCenter);
        SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월");
        Intent intent = getIntent();
        String strDist = intent.getStringExtra("strDist");
        tvDist.setText(strDist);
        Date today = Calendar.getInstance().getTime();

        tvDate.setText(format.format(today));

        if (requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }
        setSerList();

        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cnt = reserveList.getAdapter().getCount();
                String tmp="";
                for(int i=0;i<cnt;i++){
                    if(reserveadpt.arsrc.get(i).chk==true){
                        tmp+=reserveadpt.arsrc.get(i).type+",";
                    }

                }
                tmp = tmp.substring(0,tmp.length()-1);
                Intent intent = new Intent(getApplicationContext(), AddMaintain.class);
                intent.putExtra("selected",tmp);
                startActivity(intent);
            }
        });

        btnCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),FindCenter.class);
                startActivity(intent);
            }
        });

    }

    public void setSerList() {

        //php url 입력
        String URL = "http://172.26.126.172:443/setSerList.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {
                    serschItems = new ArrayList<serschItem>();
                    int index=0;
                    jsonObject = new JSONObject(response);

                    Parsing parsing = new Parsing();
                    try {
                        serschItem mi;
                        while (true) {

                            parsing.serSchParsing(response, index);
                            if (parsing.name == "") {

                                break;
                            }


                            String name = parsing.name;
                            String stdmth = parsing.date;
                            String stdkm = decimalFormat.format(Integer.parseInt(parsing.distance));
                            String img = parsing.img;
                            String type = parsing.idx;
                            String detail = parsing.detail;
                            String diff = parsing.diff;
                            String dist = parsing.dist;

                            index++;

                            mi = new serschItem(name, type,diff,dist,stdkm,stdmth,img,detail);
                            serschItems.add(mi);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                reserveadpt = new reserveAdpt(SelectReserve.this,R.layout.reservelist,serschItems);
                reserveList.setAdapter(reserveadpt);

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
                param.put("car_idx",shared_preferences.get_user_car(SelectReserve.this));
                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);



    }



}

class reserveAdpt extends BaseAdapter {
    @Override
    public long getItemId(int i) {
        return 0;
    }


    Context maincon;
    LayoutInflater Inflater;
    ArrayList<serschItem> arsrc;
    int layout;


    public reserveAdpt(Context context, int alayout, ArrayList<serschItem> aarSrc) {
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
        return arsrc.get(position).name;
    }

    public void updateReceiptsList(ArrayList<serschItem> arsrc) {
        arsrc = this.arsrc;
        int size = arsrc.size();
        this.notifyDataSetChanged();
    }

    public boolean ischekced(int position){
        return arsrc.get(position).chk;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        if(convertView == null) {
            convertView = Inflater.inflate(layout,parent,false);
        }
        TextView tvName = (TextView)convertView.findViewById(R.id.tvName);
        TextView tvMth = (TextView) convertView.findViewById(R.id.tvMth);
        TextView tvKm = (TextView) convertView.findViewById(R.id.tvKm);
        ProgressBar pro = (ProgressBar) convertView.findViewById(R.id.progress);
        ImageView img = (ImageView)convertView.findViewById(R.id.img);

        tvName.setText(arsrc.get(position).name);
        tvName.setTag(arsrc.get(position).type);

        if(arsrc.get(position).zero){
            tvMth.setVisibility(View.GONE);
            if(arsrc.get(position).name.equals("타이어")) {
                tvKm.setText(arsrc.get(position).stdkm + "km부터" + arsrc.get(position).detail);
            }else{
                tvKm.setText(arsrc.get(position).stdkm + "km 마다" + arsrc.get(position).detail);
            }

        }else{

            tvMth.setVisibility(View.VISIBLE);
            tvKm.setText(arsrc.get(position).stdkm + "km");
            tvMth.setText(arsrc.get(position).stdmth+ "개월 마다 " + arsrc.get(position).detail);
        }

        if(arsrc.get(position).stdmth.equals("0")){

            tvMth.setVisibility(View.GONE);
            arsrc.get(position).zero=true;
            if(arsrc.get(position).name.equals("타이어")) {
                tvKm.setText(arsrc.get(position).stdkm + "km부터 " + arsrc.get(position).detail);
            }else{
                tvKm.setText(arsrc.get(position).stdkm + "km 마다" + arsrc.get(position).detail);
            }
            notifyDataSetChanged();
        }else{

            tvMth.setVisibility(View.VISIBLE);
            tvKm.setText(arsrc.get(position).stdkm + "km");
            tvMth.setText(arsrc.get(position).stdmth+ "개월 마다 " + arsrc.get(position).detail);
            arsrc.get(position).zero=false;
            notifyDataSetChanged();
        }

        float dist,diff;
        dist = Float.parseFloat(arsrc.get(position).dist);
        diff = Float.parseFloat(arsrc.get(position).diff);

        if(dist>1||diff>1){

            pro.setProgress(100);

            pro.setProgressTintList(ColorStateList.valueOf(Color.rgb(255,0,0)));
          
        }else{
            if(dist >= diff){
                int tmp = (int) (dist*100);
                pro.setProgress(tmp);
                pro.setProgressTintList(ColorStateList.valueOf(Color.rgb(30,187,215)));
         
            }else{
                int tmp = (int) (diff*100);
                pro.setProgress(tmp);
                pro.setProgressTintList(ColorStateList.valueOf(Color.rgb(30,187,215)));
                
            }
        }

        String imgurl = "http://172.26.126.172:443/maintain/"+arsrc.get(position).img;
        Glide.with(convertView).load(imgurl).into(img);

        CheckBox chk = (CheckBox)convertView.findViewById(R.id.chk);

        chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean state = !arsrc.get(position).chk;
                arsrc.get(position).chk=state;
            }
        });

        chk.setChecked(ischekced(position));
        chk.setFocusable(false);
        return convertView;


    }


}