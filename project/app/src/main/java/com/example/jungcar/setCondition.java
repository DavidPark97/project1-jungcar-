package com.example.jungcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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

public class setCondition extends AppCompatActivity {
    static StringRequest request;
    static RequestQueue requestQueue;

    String json;
    ArrayList<condItem> condItems;
    condAdpt condadpt;
    ListView condList;
    ImageButton btnBack;
    TextView tvCondName;
    Button btnOk;

    int sub, pageflag;
    int flag = 1;
    String strBrand, strCountry, strModel, strGrade, minMilesage, maxMilesage, minPrice, maxPrice, isChk, strCond;
    String strTmp, strName,strTotal;

    @Override
    public void onBackPressed(){
        if(sub==1){
            strBrand = strTmp;

        }else if(sub==2){
            strModel = strTmp;

        }else if(sub==3) {
            strGrade = strTmp;
        }else if(sub==4){
            strCountry = strTmp;

        }

        if(pageflag==1) {
            Intent intent = new Intent(getApplicationContext(), BuyOption.class);
            intent.putExtra("strName",strName);
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
        }else{
            Intent intent = new Intent(getApplicationContext(), ShResult.class);
            intent.putExtra("strName",strName);
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
    }



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
        isChk = intent.getExtras().getString("isChk", "n");
        strName = intent.getExtras().getString("strName","");
        strTotal = intent.getExtras().getString("strTotal","");
        sub = intent.getExtras().getInt("sub");
        pageflag = intent.getExtras().getInt("pageflag");


        if(sub==1){
            tvCondName.setText("브랜드");
            strCond = "brand";
            strTmp = strBrand;
            strBrand="";

        }else if(sub==2){
            tvCondName.setText("모델");
            strCond="year";
            strTmp=strModel;
            strModel="";
        }else if(sub==3) {
            tvCondName.setText("등급");
            strCond="fuel_name";
            strTmp=strGrade;
            strGrade = "";
        }else if(sub==4){
            tvCondName.setText("지역");
            strCond="country";
            strTmp=strCountry;
            strCountry="";

        }


        if (requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }

        condList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(sub==1){
                    strBrand = condItems.get(i).wname;

                }else if(sub==2){
                    strModel = condItems.get(i).wname;

                }else if(sub==3) {
                        strGrade = condItems.get(i).wname;
                }else if(sub==4){
                        strCountry = condItems.get(i).wname;


                }
                flag=0;
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(sub==1){
                    strBrand = strTmp;

                }else if(sub==2){
                    strModel = strTmp;

                }else if(sub==3) {
                    strGrade = strTmp;
                }else if(sub==4){
                    strCountry = strTmp;

                }

                if(pageflag==1) {
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
                }else{
                    Intent intent = new Intent(getApplicationContext(), ShResult.class);
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
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==1){
                    Toast.makeText(getApplicationContext(),"아이템을 선택하세요",Toast.LENGTH_SHORT).show();
                }else {

                    if(pageflag==1) {
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
                    }else{
                        Intent intent = new Intent(getApplicationContext(), ShResult.class);
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

                }
            }
        });
        shCount();
    }



    public void shCount() {

        //php url 입력
        String URL = "http://172.26.126.172:443/shCount.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {
                    condItems = new ArrayList<condItem>();

                    jsonObject = new JSONObject(response);

                    Parsing parsing = new Parsing();
                    try {
                        condItem mi;
                        int index=0;
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
                condadpt = new condAdpt(setCondition.this,R.layout.conditionlist,condItems);
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
                param.put("brand",strBrand);
                param.put("model",strModel);
                param.put("grade",strGrade);
                param.put("minmilesage",minMilesage);
                param.put("maxmilesage",maxMilesage);
                param.put("minprice",minPrice);
                param.put("maxprice",maxPrice);
                param.put("country",strCountry);
                param.put("own",isChk);
                param.put("cond",strCond);

                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }
}
    class condItem {
        String wcnt;
        String wname;

        condItem(String name, String cnt) {
            wname = name;
            wcnt = cnt;


        }
        condItem(String name) {
            wname = name;

        }
}
    class condAdpt extends BaseAdapter {
        @Override
        public long getItemId(int i) {
            return 0;
        }

        Context maincon;
        LayoutInflater Inflater;
        ArrayList<condItem> arsrc;
        int layout;

        public condAdpt(Context context, int alayout, ArrayList<condItem> aarSrc) {
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
            return arsrc.get(position).wname;
        }



        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            if(convertView == null) {
                convertView = Inflater.inflate(layout,parent,false);
            }
            TextView name = (TextView)convertView.findViewById(R.id.tvCondName);
            name.setText(arsrc.get(position).wname);
            TextView count = (TextView)convertView.findViewById(R.id.tvCount);
            count.setText(arsrc.get(position).wcnt);

            return convertView;


        }

}




