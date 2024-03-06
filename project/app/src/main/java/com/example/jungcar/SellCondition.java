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

public class  SellCondition extends AppCompatActivity {
    static StringRequest request;
    static RequestQueue requestQueue;
    String json;
    ArrayList<condItem> condItems;
    SellCondAdpt sellcondadpt;
    ListView condList;
    int sub;
    ImageButton btnBack;
    TextView tvcondName;
    Button btnOk;
    int flag = 1;
    String strBrand, strCountry, strModel, strGrade, strMilesage, strPrice, isChk,strCond,strOption,strPlnumber;
    String strTmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sellcondition);
        condList = (ListView)findViewById(R.id.condList);
        btnBack = (ImageButton)findViewById(R.id.btnBack);
        tvcondName =(TextView)findViewById(R.id.tvCondName);
        btnOk = (Button)findViewById(R.id.btnOk);

        Intent intent = getIntent();
        strPlnumber = intent.getExtras().getString("strPlnumber","");
        strBrand = intent.getExtras().getString("strBrand","");
        strCountry= intent.getExtras().getString("strCountry","");
        strMilesage = intent.getExtras().getString("strMilesage","");
        strModel = intent.getExtras().getString("strModel","");
        strGrade = intent.getExtras().getString("strGrade","");
        strPrice = intent.getExtras().getString("strPrice","");
        isChk = intent.getExtras().getString("isChk","");
        strOption = intent.getExtras().getString("strOption","");
        sub = intent.getExtras().getInt("sub");


        if(sub==1){
            tvcondName.setText("브랜드");
            strCond = "brand";
            strTmp = strBrand;
            strBrand="";

        }else if(sub==2){
            tvcondName.setText("모델");
            strCond="year";
            strTmp=strModel;
            strModel="";
        }else if(sub==3) {
            tvcondName.setText("연료등급");
            strCond="fuel_name";
            strTmp=strGrade;
            strGrade = "";
        }else if(sub==4){
            tvcondName.setText("옵션등급");
            strCond="opt_grade_name";
            strTmp=strOption;
            strOption="";

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
                    strOption = condItems.get(i).wname;
                }
                flag = 0;
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
                    strOption = strTmp;

                }
                Intent intent = new Intent(getApplicationContext(), SetSellCar.class);
                intent.putExtra("strBrand", strBrand);
                intent.putExtra("strModel", strModel);
                intent.putExtra("strGrade", strGrade);
                intent.putExtra("strPlnumber",strPlnumber);
                intent.putExtra("strMilesage",strMilesage);
                intent.putExtra("strPrice",strPrice);
                intent.putExtra("strCountry", strCountry);
                intent.putExtra("strOption", strOption);
                intent.putExtra("isChk", isChk);


                startActivity(intent);
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == 1) {
                    Toast.makeText(getApplicationContext(), "아이템을 선택하세요", Toast.LENGTH_SHORT).show();
                } else {

                    Intent intent = new Intent(getApplicationContext(), SetSellCar.class);
                    intent.putExtra("strBrand", strBrand);
                    intent.putExtra("strModel", strModel);
                    intent.putExtra("strPlnumber",strPlnumber);
                    intent.putExtra("strGrade", strGrade);
                    intent.putExtra("strMilesage", strMilesage);
                    intent.putExtra("strPrice", strPrice);
                    intent.putExtra("strCountry", strCountry);
                    intent.putExtra("strOption", strOption);
                    intent.putExtra("isChk", isChk);
                    startActivity(intent);
                }


            }
        });
        ShSelllist();

    }
    public void ShSelllist() {

        //php url 입력
        String URL = "http://172.26.126.172:443/ShSelllist.php";


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
                        int index=0;
                        condItem mi;
                        while (true) {

                            parsing.SellCondParsing(response, index);
                            if (parsing.name == "") {

                                break;
                            }


                            String name = parsing.name;

                            index++;
                            mi = new condItem(name);
                            condItems.add(mi);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                sellcondadpt = new SellCondAdpt(SellCondition.this,R.layout.sellcondlist,condItems);
                condList.setAdapter(sellcondadpt);
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
                param.put("cond",strCond);

                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }
}


class SellCondAdpt extends BaseAdapter {
    @Override
    public long getItemId(int i) {
        return 0;
    }

    Context maincon;
    LayoutInflater Inflater;
    ArrayList<condItem> arsrc;
    int layout;

    public SellCondAdpt(Context context, int alayout, ArrayList<condItem> aarSrc) {
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
        TextView name = (TextView)convertView.findViewById(R.id.cond);
        name.setText(arsrc.get(position).wname);

        return convertView;


    }

}




