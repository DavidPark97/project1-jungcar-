package com.example.jungcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyPageSell extends AppCompatActivity {
    TextView tvBuy, tvSell, tvCommunity, tvPay;
    static StringRequest request;
    static RequestQueue requestQueue;
    String json;
    TextView tvLogout;
    ArrayList<carsellItem> carsellItems;
    ArrayList<partsellItem> partsellItems;
    carsellAdpt carselladpt;
    partsellAdpt partselladpt;
    
    ImageButton btnBack;
    ListView carsellLIst,partsellList;
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),MainPage.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypagesell);
        btnBack=(ImageButton) findViewById(R.id.btnBack);
        tvBuy=(TextView) findViewById(R.id.tvBuy);
        tvSell=(TextView) findViewById(R.id.tvSell);
        tvCommunity=(TextView) findViewById(R.id.tvCommunity);
        tvPay=(TextView) findViewById(R.id.tvPay);
        carsellLIst = (ListView)findViewById(R.id.carsellList);
        partsellList=(ListView)findViewById(R.id.partsellList);
        tvLogout=(TextView) findViewById(R.id.tvLogout);

        Intent intent = getIntent();

        if (requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainSell.class);
                startActivity(intent);
            }
        });

        sellCarList();
        sellPartList();

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


        tvLogout.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shared_preferences.clear_user(MyPageSell.this);
                shared_preferences.clear_car(MyPageSell.this);
                Intent intent = new Intent(getApplicationContext(),MainPage.class);
                startActivity(intent);
            }
        });


    }

    public void sellCarList() {

        //php url 입력
        String URL = "http://172.26.126.172:443/sellCarList.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {
                    carsellItems = new ArrayList<carsellItem>();

                    jsonObject = new JSONObject(response);

                    Parsing parsing = new Parsing();
                    try {
                        int index = 0;
                        carsellItem mi;
                        while (true) {

                            parsing.sellitemParsing(response, index);
                            if (parsing.name == "") {

                                break;
                            }


                            String subject = parsing.name;
                            String detail = parsing.date + " " + parsing.mileage+"km " + parsing.country;
                            String price = parsing.price+"만원";
                            String label = parsing.label;
                            String img = parsing.img;
                            String cnt = "댓글 " + parsing.count;
                            String sell_idx = parsing.idx;

                            index++;

                            mi = new carsellItem(subject,detail,price,img,label,cnt,sell_idx);
                            carsellItems.add(mi);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                carselladpt = new carsellAdpt(MyPageSell.this,R.layout.carselllist,carsellItems);
                carsellLIst.setAdapter(carselladpt);

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

                param.put("user",shared_preferences.get_user_email(MyPageSell.this));

                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);




    }

    public void sellPartList() {

        //php url 입력
        String URL = "http://172.26.126.172:443/sellPartList.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {
                    partsellItems = new ArrayList<partsellItem>();

                    jsonObject = new JSONObject(response);

                    Parsing parsing = new Parsing();
                    try {
                        int index = 0;
                        partsellItem mi;
                        while (true) {

                            parsing.sellpartParsing(response, index);
                            if (parsing.name == "") {

                                break;
                            }


                            String subject = parsing.name;
                            String detail = parsing.date;
                            String price = parsing.price+"원";
                            String img = parsing.img;
                            String cnt = "댓글 " + parsing.count;
                            String market_idx = parsing.idx;

                            index++;

                            mi = new partsellItem(subject,detail,price,img,cnt,market_idx);
                            partsellItems.add(mi);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                partselladpt = new partsellAdpt(MyPageSell.this,R.layout.partselllist,partsellItems);
                partsellList.setAdapter(partselladpt);
     
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

                param.put("user",shared_preferences.get_user_email(MyPageSell.this));

                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);



    }



}

class carsellItem {
    String subject;
    String detail;
    String price;
    String img;
    String label;
    String sell_idx;
    String cmtcnt;


    carsellItem(String subject, String detail,String price, String img, String label,String cmtcnt,String sell_idx) {
        this.subject=subject;
        this.detail=detail;
        this.price=price;
        this.img=img;
        this.label=label;
        this.cmtcnt=cmtcnt;
        this.sell_idx=sell_idx;
    }

}
class carsellAdpt extends BaseAdapter  {
    @Override
    public long getItemId(int i) {
        return 0;
    }
    RequestQueue requestQueue;
    Context maincon;
    LayoutInflater Inflater;
    ArrayList<carsellItem> arsrc;
    int layout;


    public RequestQueue getRequestQueue() {
        requestQueue = Volley.newRequestQueue(maincon.getApplicationContext());
        return requestQueue;
    }


    public carsellAdpt(Context context, int alayout, ArrayList<carsellItem> aarSrc) {
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




    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        if(convertView == null) {
            convertView = Inflater.inflate(layout,parent,false);
        }
        TextView subject = (TextView)convertView.findViewById(R.id.subject);
        subject.setText(arsrc.get(position).subject);
        subject.setTag(arsrc.get(position).sell_idx);
        TextView detail = (TextView)convertView.findViewById(R.id.detail);
        detail.setText(arsrc.get(position).detail);
        TextView price = (TextView)convertView.findViewById(R.id.price);
        price.setText(arsrc.get(position).price);
        ImageView img = (ImageView)convertView.findViewById(R.id.img);
        String imgurl = "http://172.26.126.172:443/"+arsrc.get(position).img;
        Glide.with(convertView).load(imgurl).into(img);
        TextView cmtcnt = (TextView) convertView.findViewById(R.id.cmtcnt);
        ImageView more = (ImageView) convertView.findViewById(R.id.more);
        cmtcnt.setText(arsrc.get(position).cmtcnt);
        TextView label = (TextView) convertView.findViewById(R.id.label);
        if(!arsrc.get(position).label.equals("null")){
            label.setVisibility(View.VISIBLE);
            label.setText(arsrc.get(position).label);
        }else{
            label.setVisibility(View.INVISIBLE);
        }






        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final PopupMenu popupMenu = new PopupMenu(maincon.getApplicationContext(),view);
                popupMenu.getMenuInflater().inflate(R.menu.sellpopup,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.sell_menu1){
                            Intent intent = new Intent(maincon.getApplicationContext(), CarEdit.class);
                            intent.putExtra("sell_idx",arsrc.get(position).sell_idx);
                            maincon.startActivity(intent);
                        }else if (menuItem.getItemId() == R.id.sell_menu2){
                            delCar(arsrc.get(position).sell_idx);
                            arsrc.remove(arsrc.get(position));
                            notifyDataSetChanged();
                        }else if (menuItem.getItemId() == R.id.sell_menu3){
                            Intent intent = new Intent(maincon.getApplicationContext(), setCarTrade.class);
                            intent.putExtra("sell_idx",arsrc.get(position).sell_idx);
                            intent.putExtra("table", "trade");
                            maincon.startActivity(intent);
                        }else if (menuItem.getItemId() == R.id.sell_menu4){
                            Intent intent = new Intent(maincon.getApplicationContext(), BuyLabel.class);
                            intent.putExtra("sell_idx",arsrc.get(position).sell_idx);
                            intent.putExtra("strImg",arsrc.get(position).img);
                            maincon.startActivity(intent);
                        }

                        return false;
                    }
                });
                popupMenu.show();
            }
        });





        return convertView;


    }

    public void delCar(String sell_idx) {
        requestQueue = getRequestQueue();
        //php url 입력
        String URL = "http://172.26.126.172:443/delCar.php";


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


                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }


}


class partsellItem {
    String subject;
    String detail;
    String price;
    String img;
    String market_idx;
    String cmtcnt;


    partsellItem(String subject, String detail,String price, String img,String cmtcnt,String market_idx) {
        this.subject=subject;
        this.detail=detail;
        this.price=price;
        this.img=img;
        this.cmtcnt=cmtcnt;
        this.market_idx=market_idx;
    }

}
class partsellAdpt extends BaseAdapter {
    @Override
    public long getItemId(int i) {
        return 0;
    }

    RequestQueue requestQueue;
    Context maincon;
    LayoutInflater Inflater;
    ArrayList<partsellItem> arsrc;
    int layout;


    public RequestQueue getRequestQueue() {
        requestQueue = Volley.newRequestQueue(maincon.getApplicationContext());
        return requestQueue;
    }


    public partsellAdpt(Context context, int alayout, ArrayList<partsellItem> aarSrc) {
        maincon = context;
        Inflater = (LayoutInflater) context.getSystemService(
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


    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        if (convertView == null) {
            convertView = Inflater.inflate(layout, parent, false);
        }
        TextView subject = (TextView) convertView.findViewById(R.id.subject);
        subject.setText(arsrc.get(position).subject);
        subject.setTag(arsrc.get(position).market_idx);
        TextView detail = (TextView) convertView.findViewById(R.id.detail);
        detail.setText(arsrc.get(position).detail);
        TextView price = (TextView) convertView.findViewById(R.id.price);
        price.setText(arsrc.get(position).price);
        ImageView img = (ImageView) convertView.findViewById(R.id.img);
        String imgurl = "http://172.26.126.172:443/" + arsrc.get(position).img;
        Glide.with(convertView).load(imgurl).into(img);
        TextView cmtcnt = (TextView) convertView.findViewById(R.id.cmtcnt);
        ImageView more = (ImageView) convertView.findViewById(R.id.more);
        cmtcnt.setText(arsrc.get(position).cmtcnt);


        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final PopupMenu popupMenu = new PopupMenu(maincon.getApplicationContext(), view);
                popupMenu.getMenuInflater().inflate(R.menu.partsellpopup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.sell_menu1) {
                            delPart(arsrc.get(position).market_idx);
                            arsrc.remove(arsrc.get(position));
                            notifyDataSetChanged();

                        } else if (menuItem.getItemId() == R.id.sell_menu2) {
                            Intent intent = new Intent(maincon.getApplicationContext(), setCarTrade.class);
                            intent.putExtra("sell_idx", arsrc.get(position).market_idx);
                            intent.putExtra("table", "part_trade");

                            maincon.startActivity(intent);
                        }

                        return false;
                    }
                });
                popupMenu.show();
            }
        });


        return convertView;


    }

    public void delPart(String market_idx) {
        requestQueue = getRequestQueue();
        //php url 입력
        String URL = "http://172.26.126.172:443/delPart.php";


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
                param.put("market_idx",market_idx);


                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }
}