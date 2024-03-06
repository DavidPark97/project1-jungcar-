package com.example.jungcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyPage extends AppCompatActivity {

    ImageButton btnBack,btnDel;
    TextView tvBuy, tvSell, tvCommunity, tvPay,tvBookmark,tvRecent,tvTotal,tvSelect;
    CheckBox chkAll;
    int index=0;
    ListView myList;
    String strUser;
    TextView tvLogout;
    myAdpt myadpt;
    String strTable = "bookmark";
    static StringRequest request;
    static RequestQueue requestQueue;
    String json;
    ArrayList<resItem> resItems;
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),MainPage.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);
        btnBack=(ImageButton) findViewById(R.id.btnBack);
        btnDel=(ImageButton) findViewById(R.id.btnDel);
        tvBuy=(TextView) findViewById(R.id.tvBuy);
        tvSell=(TextView) findViewById(R.id.tvSell);
        tvCommunity=(TextView) findViewById(R.id.tvCommunity);
        tvPay=(TextView) findViewById(R.id.tvPay);
        tvBookmark=(TextView) findViewById(R.id.tvBookmark);
        tvRecent=(TextView) findViewById(R.id.tvRecent);
        tvTotal=(TextView) findViewById(R.id.tvTotal);
        tvSelect=(TextView) findViewById(R.id.tvSelect);
        tvLogout=(TextView) findViewById(R.id.tvLogout);

        if (requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }

        chkAll=(CheckBox) findViewById(R.id.chkAll);
        myList=(ListView) findViewById(R.id.myList);
        strUser = shared_preferences.get_user_email(this);
        Intent intent = getIntent();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),MainPage.class);
                startActivity(intent);
            }
        });

        setMyList("bookmark");

        tvRecent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvBookmark.setTextColor(Color.GRAY);
                tvRecent.setTextColor(Color.BLACK);
                setMyList("sh_history");
                strTable="sh_history";
            }
        });

        tvBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvBookmark.setTextColor(Color.BLACK);
                tvRecent.setTextColor(Color.GRAY);
                setMyList("bookmark");
                strTable="bookmark";
            }
        });

        tvBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyPage.class);
                startActivity(intent);

            }
        });

        tvSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyPageSell.class);
                startActivity(intent);
            }
        });

        tvCommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MyPageComm.class);
                startActivity(intent);
            }
        });

        tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),MyPagePayment.class);
                startActivity(intent);
            }
        });

        chkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    myadpt.setAllChecked(true);
                    tvSelect.setText("전체해제");
                }else{
                    myadpt.setAllChecked(false);
                    tvSelect.setText("전체선택");
                }
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=myadpt.getCount()-1;i>=0;i--){
                    if(myadpt.arsrc.get(i).chk==true){

                        myadpt.delete(myadpt.arsrc.get(i).sell_idx,myadpt.arsrc.get(i).table,i);
                    }
                }

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setMyList(strTable);
                        myadpt.setAllChecked(false);
                        tvSelect.setText("전체선택");
                        chkAll.setChecked(false);
                    }
                },1000);

            }

        });

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ShItem.class);
                intent.putExtra("pageflag","2");
                intent.putExtra("sell_idx",resItems.get(i).sell_idx);
                intent.putExtra("name","");
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
                startActivity(intent);
            }
        });
        tvLogout.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shared_preferences.clear_user(MyPage.this);
                shared_preferences.clear_car(MyPage.this);
                Intent intent = new Intent(getApplicationContext(),MainPage.class);
                startActivity(intent);
            }
        });

    }


    public void setMyList(String table) {

        //php url 입력
        String URL = "http://172.26.126.172:443/setMyList.php";


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
                        resItem mi;
                        while (true) {

                            parsing.resParsing(response, index);
                            if (parsing.name == "") {

                                break;
                            }


                            String subject = parsing.name;
                            String detail = parsing.date + " " + parsing.mileage+"km " + parsing.country;
                            String price = parsing.price+"만원";
                            String label = parsing.label;
                            String img = parsing.img;
                            String user = parsing.user;
                            String sell_idx = parsing.idx;

                            index++;

                            mi = new resItem(subject,detail,price,img,label,user,sell_idx,table);
                            resItems.add(mi);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                myadpt = new myAdpt(MyPage.this,R.layout.mypagelist,resItems);
                myadpt.setOnItemClickListener(new myAdpt.OnItemClickListener() {
                    @Override
                    public void onItemClick(int pos) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                tvTotal.setText(myList.getAdapter().getCount()+"");
                            }
                        },1000);
                    }
                });
                myList.setAdapter(myadpt);
                index=0;
                tvTotal.setText(resItems.size()+"");
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

                param.put("user",shared_preferences.get_user_email(MyPage.this));
                param.put("table",table);
                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);



    }
}
class myAdpt extends BaseAdapter {
    @Override
    public long getItemId(int i) {
        return 0;
    }

    RequestQueue requestQueue;
    Context maincon;
    LayoutInflater Inflater;
    ArrayList<resItem> arsrc;

    int layout;

    public RequestQueue getRequestQueue() {
        requestQueue = Volley.newRequestQueue(maincon.getApplicationContext());
        return requestQueue;
    }


    public myAdpt(Context context, int alayout, ArrayList<resItem> aarSrc) {
        maincon = context;
        Inflater= (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        arsrc = aarSrc;
        layout = alayout;

    }
    public int getCount() {
        return arsrc.size();
    }

    public void setAllChecked(final boolean ischeked) {
        final int tempSize = getCount();
        if(ischeked == true){
            for (int ia = 0; ia < tempSize; ia++) {
                arsrc.get(ia).chk = true;
                notifyDataSetChanged();
            }
        } else {
            for (int a = 0; a < tempSize; a++) {
                arsrc.get(a).chk = false;
                notifyDataSetChanged();
            }
        }
    }


    public String getItem(int position) {
        return arsrc.get(position).subject;
    }

    public void updateReceiptsList(ArrayList<resItem> arsrc) {
        arsrc = this.arsrc;
        int size = arsrc.size();
        this.notifyDataSetChanged();
    }

    public void delete(String sell_idx, String tb,int pos){
        requestQueue = getRequestQueue();
        arsrc.remove(arsrc.get(pos));
        updateReceiptsList(arsrc);
        //php url 입력
        String URL = "http://172.26.126.172:443/delete.php";


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
                param.put("sell_idx",sell_idx);
                param.put("user",shared_preferences.get_user_email(maincon));
                param.put("table",tb);

                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);

    }

    public boolean ischekced(int position){
        return arsrc.get(position).chk;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        if(convertView == null) {
            convertView = Inflater.inflate(layout,parent,false);
        }
        TextView subject = (TextView)convertView.findViewById(R.id.subject);
        subject.setText(arsrc.get(position).subject);
        TextView detail = (TextView)convertView.findViewById(R.id.detail);
        detail.setText(arsrc.get(position).detail);
        TextView price = (TextView)convertView.findViewById(R.id.price);
        price.setText(arsrc.get(position).price);
        ImageView img = (ImageView)convertView.findViewById(R.id.img);
        String imgurl = "http://172.26.126.172:443/"+arsrc.get(position).img;
        Glide.with(convertView).load(imgurl).into(img);
        CheckBox chk = (CheckBox)convertView.findViewById(R.id.item);
        TextView label = (TextView) convertView.findViewById(R.id.label);
        if(!arsrc.get(position).label.equals("null")){
            label.setVisibility(View.VISIBLE);
            label.setText(arsrc.get(position).label);
         }else{
            label.setVisibility(View.INVISIBLE);
        }

        chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean state = !arsrc.get(position).chk;
                arsrc.get(position).chk=state;
            }
        });

        chk.setChecked(ischekced(position));
        chk.setFocusable(false);




        ImageView delete = (ImageView)convertView.findViewById(R.id.delete);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position != ListView.INVALID_POSITION) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position);
                        delete(arsrc.get(position).sell_idx, arsrc.get(position).table, position);

                    }
                }
            }


        });



        return convertView;


    }

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    private myAdpt.OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(myAdpt.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


}
