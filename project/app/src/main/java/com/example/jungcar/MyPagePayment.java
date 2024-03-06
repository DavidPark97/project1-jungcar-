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

public class MyPagePayment extends AppCompatActivity {
    TextView tvBuy, tvSell, tvCommunity, tvPay, tvCnt;
    static StringRequest request;
    static RequestQueue requestQueue;
    String json;
   TextView tvLogout;
    ListView paymentList;

    ImageButton btnBack;
    ArrayList<paymentItem> paymentItems;
    paymentAdpt paymentadpt;
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),MainPage.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypagepayment);
        btnBack=(ImageButton) findViewById(R.id.btnBack);
        tvBuy=(TextView) findViewById(R.id.tvBuy);
        tvSell=(TextView) findViewById(R.id.tvSell);
        tvCommunity=(TextView) findViewById(R.id.tvCommunity);
        tvPay=(TextView) findViewById(R.id.tvPay);
        tvCnt=(TextView)findViewById(R.id.tvCnt);
        paymentList=(ListView)findViewById(R.id.paymentList);
        tvLogout=(TextView) findViewById(R.id.tvLogout);
        Intent intent = getIntent();

        if (requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainPage.class);
                startActivity(intent);
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



        tvLogout.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shared_preferences.clear_user(MyPagePayment.this);
                shared_preferences.clear_car(MyPagePayment.this);
                Intent intent = new Intent(getApplicationContext(),MainPage.class);
                startActivity(intent);
            }
        });
        MyPaymentList();
    }

    public void MyPaymentList() {

        //php url 입력
        String URL = "http://172.26.126.172:443/MyPaymentList.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {

                    paymentItems = new ArrayList<paymentItem>();

                    jsonObject = new JSONObject(response);

                    Parsing parsing = new Parsing();
                    try {
                        int index=0;
                        paymentItem mi;
                        while (true) {

                            parsing.paymentParsing(response, index);
                            if (parsing.payment == "") {
                                break;
                            }


                            String payment = parsing.payment;
                            String date =parsing.date;
                            index++;

                            mi = new paymentItem(date,payment);
                            paymentItems.add(mi);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                paymentadpt = new paymentAdpt(MyPagePayment.this,R.layout.paymentlist,paymentItems);
                paymentList.setAdapter(paymentadpt);
                tvCnt.setText(paymentItems.size()+"개");
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
                param.put("user_idx",shared_preferences.get_user_email(MyPagePayment.this));


                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }


}

class paymentItem {
    String date;
    String payment;

    paymentItem(String date, String payment) {
        this.payment = payment;

        this.date = date;
    }

}

class paymentAdpt extends BaseAdapter {
    @Override
    public long getItemId(int i) {
        return 0;
    }

    RequestQueue requestQueue;
    Context maincon;
    LayoutInflater Inflater;
    ArrayList<paymentItem> arsrc;

    int layout;

    public RequestQueue getRequestQueue() {
        requestQueue = Volley.newRequestQueue(maincon.getApplicationContext());
        return requestQueue;
    }


    public paymentAdpt(Context context, int alayout, ArrayList<paymentItem> aarSrc) {
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
        return arsrc.get(position).payment;
    }

    public void updateReceiptsList(ArrayList<paymentItem> arsrc) {
        arsrc = this.arsrc;
        int size = arsrc.size();
        this.notifyDataSetChanged();
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        if (convertView == null) {
            convertView = Inflater.inflate(layout, parent, false);
        }
        TextView date = (TextView)convertView.findViewById(R.id.date);
        date.setText(arsrc.get(position).date);
        TextView payment = (TextView)convertView.findViewById(R.id.payment);
        payment.setText(arsrc.get(position).payment);




        return convertView;


    }


}