package com.example.jungcar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShResult extends AppCompatActivity  {
    TextView tvTotal, tvDist, tvPrice,tvCountry,tvFuel,tvFilter;
    static StringRequest request;
    static RequestQueue requestQueue;
    String json;
    ArrayList<resItem> resItems;
    resAdpt resadpt;

    ListView resList;
    ImageButton btnToMy,btnToTop,btnPrev;
    String strBrand, strCountry, strModel, strGrade, minMilesage, maxMilesage, minPrice, maxPrice, isChk, strName,strTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shresult);

        if (requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }




            Intent intent = getIntent();
            strBrand = intent.getExtras().getString("strBrand", "");
            strCountry = intent.getExtras().getString("strCountry", "");
            minMilesage = intent.getExtras().getString("minMilesage", "");
            maxMilesage = intent.getExtras().getString("maxMilesage", "");
            strModel = intent.getExtras().getString("strModel", "");
            strGrade = intent.getExtras().getString("strGrade", "");
            minPrice = intent.getExtras().getString("minPrice", "");
            maxPrice = intent.getExtras().getString("maxPrice", "");
                isChk = intent.getExtras().getString("isChk", "");
            strName = intent.getExtras().getString("strName", "");
            strTotal = intent.getExtras().getString("strTotal", "");

        tvTotal=(TextView)findViewById(R.id.tvTotal);
        tvDist=(TextView) findViewById(R.id.tvDist);
        tvPrice=(TextView) findViewById(R.id.tvPrice);
        tvCountry=(TextView) findViewById(R.id.tvCountry);
        tvFuel=(TextView) findViewById(R.id.tvFuel);
        tvFilter=(TextView) findViewById(R.id.tvFilter);

        resList=(ListView) findViewById(R.id.resList);
        btnToMy=(ImageButton) findViewById(R.id.btnToMy);
        btnToTop=(ImageButton) findViewById(R.id.btnToTop);
        btnPrev=(ImageButton) findViewById(R.id.btnPrev);

        setResList();



        tvDist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SetNumeric.class);
                intent.putExtra("sub",2);
                intent.putExtra("pageflag",2);
                intent.putExtra("strBrand",strBrand);
                intent.putExtra("strModel",strModel);
                intent.putExtra("strGrade",strGrade);
                intent.putExtra("minMilesage",minMilesage);
                intent.putExtra("maxMilesage",maxMilesage);
                intent.putExtra("minPrice",minPrice);
                intent.putExtra("maxPrice",maxPrice);
                intent.putExtra("strCountry",strCountry);
                intent.putExtra("isChk",isChk);
                intent.putExtra("strName",strName);
                intent.putExtra("strTotal",strTotal);


                startActivity(intent);
            }
        });

        tvPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SetNumeric.class);
                intent.putExtra("sub",1);
                intent.putExtra("pageflag",2);
                intent.putExtra("strBrand",strBrand);
                intent.putExtra("strModel",strModel);
                intent.putExtra("strGrade",strGrade);
                intent.putExtra("minMilesage",minMilesage);
                intent.putExtra("maxMilesage",maxMilesage);
                intent.putExtra("minPrice",minPrice);
                intent.putExtra("maxPrice",maxPrice);
                intent.putExtra("strCountry",strCountry);
                intent.putExtra("isChk",isChk);
                intent.putExtra("strName",strName);
                intent.putExtra("strTotal",strTotal);


                startActivity(intent);
            }
        });

        tvCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), setCondition.class);
                intent.putExtra("sub",4);
                intent.putExtra("pageflag",2);
                intent.putExtra("strBrand",strBrand);
                intent.putExtra("strModel",strModel);
                intent.putExtra("strGrade",strGrade);
                intent.putExtra("minMilesage",minMilesage);
                intent.putExtra("maxMilesage",maxMilesage);
                intent.putExtra("minPrice",minPrice);
                intent.putExtra("maxPrice",maxPrice);
                intent.putExtra("strCountry",strCountry);
                intent.putExtra("isChk",isChk);
                intent.putExtra("strName",strName);
                intent.putExtra("strTotal",strTotal);


                startActivity(intent);
            }
        });

        tvFuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), setCondition.class);
                intent.putExtra("sub",3);
                intent.putExtra("pageflag",2);
                intent.putExtra("strBrand",strBrand);
                intent.putExtra("strModel",strModel);
                intent.putExtra("strGrade",strGrade);
                intent.putExtra("minMilesage",minMilesage);
                intent.putExtra("maxMilesage",maxMilesage);
                intent.putExtra("minPrice",minPrice);
                intent.putExtra("maxPrice",maxPrice);
                intent.putExtra("strCountry",strCountry);
                intent.putExtra("isChk",isChk);
                intent.putExtra("strName",strName);
                intent.putExtra("strTotal",strTotal);

                startActivity(intent);
            }
        });

        tvFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BuyOption.class);

                intent.putExtra("strBrand",strBrand);
                intent.putExtra("strModel",strModel);
                intent.putExtra("strGrade",strGrade);
                intent.putExtra("minMilesage",minMilesage);
                intent.putExtra("maxMilesage",maxMilesage);
                intent.putExtra("minPrice",minPrice);
                intent.putExtra("maxPrice",maxPrice);
                intent.putExtra("strCountry",strCountry);
                intent.putExtra("isChk",isChk);
                startActivity(intent);
            }
        });



        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BuyOption.class);

                intent.putExtra("strBrand","");
                intent.putExtra("strModel","");
                intent.putExtra("strGrade","");
                intent.putExtra("minMilesage","");
                intent.putExtra("maxMilesage","");
                intent.putExtra("minPrice","");
                intent.putExtra("maxPrice","");
                intent.putExtra("strCountry","");
                intent.putExtra("isChk","");
                intent.putExtra("strTotal","");
                intent.putExtra("strName","");


                startActivity(intent);

            }
        });


        btnToMy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shared_preferences.get_user_email(ShResult.this).isEmpty()==true) {
                    new AlertDialog.Builder(ShResult.this)
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
                    Intent intent = new Intent(getApplicationContext(), MyPage.class);
                    startActivity(intent);
                }
            }
        });

        btnToTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resList.smoothScrollToPosition(0);
            }
        });

        resList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ShItem.class);
                intent.putExtra("sell_idx",resItems.get(i).sell_idx);
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
        });
    }

    @Override
    protected void onPause(){
        super.onPause();

        String user = shared_preferences.get_user_email(this);


        for(int i=0;i<resList.getAdapter().getCount();i++){
            if(resItems.get(i).chg==1){
                delBookmark(user,resItems.get(i).sell_idx);
            }else if(resItems.get(i).chg==2){
                insBookmark(user,resItems.get(i).sell_idx);
            }
        }
    }




    public void setResList() {

        //php url 입력
        String URL = "http://172.26.126.172:443/setResList.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {

                    resItems = new ArrayList<resItem>();

                    jsonObject = new JSONObject(response);

                    Parsing parsing = new Parsing();
                    try {
                        int index=0;
                        resItem mi;
                        while (true) {

                            parsing.resParsing(response, index);
                            if (parsing.name == "") {

                                break;
                            }


                            String subject = parsing.name + " " + parsing.detail;
                            String detail = parsing.date + " " + parsing.mileage+"km " + parsing.country;
                            String price = parsing.price+"만원";
                            String label = parsing.label;
                            String img = parsing.img;
                            String user = parsing.user;
                            String sell_idx = parsing.idx;

                            index++;

                            mi = new resItem(subject,detail,price,img,label,user,sell_idx);
                            resItems.add(mi);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                resadpt = new resAdpt(ShResult.this,R.layout.resultlist,resItems);
                resList.setAdapter(resadpt);

                tvTotal.setText(resList.getAdapter().getCount()+"대");
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
                param.put("name",strName);
                param.put("user",shared_preferences.get_user_email(ShResult.this));

                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }
    public void insBookmark(String user, String sell_idx) {

        //php url 입력
        String URL = "http://172.26.126.172:443/insBookmark.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {
                    resItems = new ArrayList<resItem>();

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
                param.put("user",user);
                param.put("sell_idx",sell_idx);


                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }

    public void delBookmark(String user, String sell_idx) {

        //php url 입력
        String URL = "http://172.26.126.172:443/delBookmark.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {
                    resItems = new ArrayList<resItem>();

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
                param.put("user",user);
                param.put("sell_idx",sell_idx);


                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }
}
class resItem {
    String subject;
    String detail;
    String price;
    String img;
    String label;
    String user;
    String sell_idx;
    String table;
    int chg=0;
    boolean chk=false;

    resItem(String subject, String detail,String price, String img, String label,String user,String sell_idx) {
        this.subject=subject;
        this.detail=detail;
        this.price=price;
        this.img=img;
        this.label=label;
        this.user=user;
        this.sell_idx=sell_idx;
    }
    resItem(String subject, String detail,String price, String img, String label,String user,String sell_idx,String table) {
        this.subject=subject;
        this.detail=detail;
        this.price=price;
        this.img=img;
        this.label=label;
        this.user=user;
        this.sell_idx=sell_idx;
        this.table=table;
    }
}
class resAdpt extends BaseAdapter {
    @Override
    public long getItemId(int i) {
        return 0;
    }

    DecimalFormat decimalFormat = new DecimalFormat("###,###원");
    Context maincon;
    LayoutInflater Inflater;
    ArrayList<resItem> arsrc;
    int layout;


    public resAdpt(Context context, int alayout, ArrayList<resItem> aarSrc) {
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
        return arsrc.get(position).subject;
    }

    public void updateReceiptsList(ArrayList<resItem> arsrc) {
        arsrc = this.arsrc;
        int size = arsrc.size();
        this.notifyDataSetChanged();
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        if(convertView == null) {
            convertView = Inflater.inflate(layout,parent,false);
        }
        TextView tvSub = (TextView)convertView.findViewById(R.id.tvSub);
        tvSub.setText(arsrc.get(position).subject);
        tvSub.setTag(arsrc.get(position).sell_idx);
        TextView tvDetail = (TextView)convertView.findViewById(R.id.tvDetail);
        tvDetail.setText(arsrc.get(position).detail);
        TextView tvPrice = (TextView)convertView.findViewById(R.id.tvPrice);
        tvPrice.setText(arsrc.get(position).price);
        ImageView img = (ImageView)convertView.findViewById(R.id.img);
        String imgurl = "http://172.26.126.172:443/"+arsrc.get(position).img;
        Glide.with(convertView).load(imgurl).into(img);

        TextView label = (TextView) convertView.findViewById(R.id.label);
        if(!arsrc.get(position).label.equals("null")){
            label.setVisibility(View.VISIBLE);
            label.setText(arsrc.get(position).label);
        }else{
            label.setVisibility(View.INVISIBLE);
        }

        ImageView imgPop = (ImageView)convertView.findViewById(R.id.imgPop);


        if(arsrc.get(position).chg==0) {
            if (!arsrc.get(position).user.equals("null")) {

                arsrc.get(position).chg = 2;

            } else {

                arsrc.get(position).chg = 1;
            }
        }

        if(arsrc.get(position).chg==1){
            imgPop.setImageResource(R.drawable.pop);
        }else{
            imgPop.setImageResource(R.drawable.popon);
        }




        imgPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shared_preferences.get_user_email(maincon.getApplicationContext()).isEmpty() == true) {
                        Toast.makeText(maincon.getApplicationContext(),"로그인시 이용가능합니다",Toast.LENGTH_SHORT).show();
                } else {

                    if (arsrc.get(position).chg == 2) {
                        imgPop.setImageResource(R.drawable.pop);
                        arsrc.get(position).chg = 1;

                        //db에서삭제 추가
                    } else {
                        imgPop.setImageResource(R.drawable.popon);
                        arsrc.get(position).chg = 2;
                        //db에추가

                    }

                }
            }


        });

        return convertView;


    }


}
