package com.example.jungcar;

import android.app.Activity ;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
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

import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import kr.co.bootpay.Bootpay;
import kr.co.bootpay.BootpayAnalytics;
import kr.co.bootpay.enums.Method;
import kr.co.bootpay.enums.PG;
import kr.co.bootpay.enums.UX;
import kr.co.bootpay.listener.CancelListener;
import kr.co.bootpay.listener.CloseListener;
import kr.co.bootpay.listener.ConfirmListener;
import kr.co.bootpay.listener.DoneListener;
import kr.co.bootpay.listener.ErrorListener;
import kr.co.bootpay.listener.ReadyListener;
import kr.co.bootpay.model.BootExtra;
import kr.co.bootpay.model.BootUser;

public class BuyLabel extends Activity {

    CheckBox chkUp, chkHot;
    LinearLayout llWhole;
    TextView tvHot, tvHotMore,tvPrice, tvUpMore, tvInfo;
    String sell_idx,strImg;
    Button btnCancel, btnBuy;
    ListView hotList;
    int upflag =0,hotflag=0;

    static StringRequest request;
    static RequestQueue requestQueue;
    String json;
    ArrayList<hotItem> hotItems;
    hotAdpt hotadpt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.buylabel);
        BootpayAnalytics.init(this, "6477f54a755e27001a38d90a");
        if (requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }


        hotList = (ListView)findViewById(R.id.hotList);
        chkUp = (CheckBox)findViewById(R.id.chkUp);
        chkHot = (CheckBox)findViewById(R.id.chkHot);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnBuy = (Button) findViewById(R.id.btnBuy);
        llWhole = (LinearLayout)findViewById(R.id.llWhole);
        tvHot = (TextView)findViewById(R.id.tvHot);
        tvUpMore = (TextView)findViewById(R.id.tvUpMore);
        tvHotMore = (TextView)findViewById(R.id.tvHotMore);
        tvPrice = (TextView)findViewById(R.id.tvPrice);
        tvInfo = (TextView)findViewById(R.id.tvInfo);
        Intent intent = getIntent();
        sell_idx = intent.getExtras().getString("sell_idx");
        strImg = intent.getExtras().getString("strImg");
        setHotList();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        tvHotMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hotflag==0){
                    int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,350 , getResources().getDisplayMetrics());
                    int height = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 500, getResources().getDisplayMetrics());
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width,height);
                    llWhole.setLayoutParams(lp);
                    hotList.setVisibility(View.VISIBLE);
                    tvHotMore.setText("접기▲");
                    hotflag=1;
                }else if(hotflag==1){
                    hotflag=0;
                    tvHotMore.setText("더보기▼");
                    int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 350, getResources().getDisplayMetrics());
                    int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 220, getResources().getDisplayMetrics());
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height);
                    llWhole.setLayoutParams(lp);
                    hotList.setVisibility(View.GONE);
                }

            }
        });

        tvUpMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(upflag==0) {
                    upflag=1;
                    tvUpMore.setText("접기▲");
                    int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 350, getResources().getDisplayMetrics());
                    int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 400, getResources().getDisplayMetrics());
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height);
                    llWhole.setLayoutParams(lp);
                    tvInfo.setVisibility(View.VISIBLE);
                }else if(upflag==1){
                    upflag=0;
                    tvUpMore.setText("더보기▼");
                    int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 350, getResources().getDisplayMetrics());
                    int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 220, getResources().getDisplayMetrics());
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height);
                    llWhole.setLayoutParams(lp);
                    tvInfo.setVisibility(View.GONE);
                }
            }
        });

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cnt = 0;

                ArrayList<String> item = new ArrayList<String>();
                if(chkUp.isChecked()){
                    cnt++;
                    item.add("999");
                }

                if(chkHot.isChecked()){
                    for(int i=0;i<hotItems.size();i++){
                        if(hotItems.get(i).boolChk){
                        cnt++;
                        item.add(hotItems.get(i).idx);
                        }
                    }
                }
                BootUser bootUser = new BootUser().setPhone("010-4079-6760");
                BootExtra bootExtra = new BootExtra().setQuotas(new int[] {0,2,3});

                Bootpay.init(getFragmentManager())
                        .setApplicationId("6477f54a755e27001a38d90a") // 해당 프로젝트(안드로이드)의 application id 값
                .setPG(PG.INICIS) // 결제할 PG 사
                        .setMethod(Method.PHONE) // 결제수단
                        .setContext(BuyLabel.this)
                        .setBootUser(bootUser)
                        .setBootExtra(bootExtra)
                        .setUX(UX.PG_DIALOG)
//                .setUserPhone("010-1234-5678") // 구매자 전화번호
                        .setName("라벨 결제") // 결제할 상품명
                        .setOrderId("1234") // 결제 고유번호expire_month
                        .setPrice(cnt*1000) // 결제할 금액
                        .addItem("라벨", 1, "Label", cnt*1000) // 주문정보에 담길 상품정보, 통계를 위해 사용
                        .onConfirm(new ConfirmListener() { // 결제가 진행되기 바로 직전 호출되는 함수로, 주로 재고처리 등의 로직이 수행
                            @Override
                            public void onConfirm(@Nullable String message) {

                                Bootpay.confirm(message); // 재고가 있을 경우.

                            }
                        })
                        .onDone(new DoneListener() { // 결제완료시 호출, 아이템 지급 등 데이터 동기화 로직을 수행합니다
                            @Override
                            public void onDone(@Nullable String message) {
                                for(int i=0;i<item.size();i++){
                                    insLabel(item.get(i));
                                }

                                insPayment(item.size()*1000);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(getApplicationContext(),MyPageSell.class);
                                        startActivity(intent);
                                    }
                                },1000);

                            }
                        })
                        .onReady(new ReadyListener() { // 가상계좌 입금 계좌번호가 발급되면 호출되는 함수입니다.
                            @Override
                            public void onReady(@Nullable String message) {
                                Log.d("ready", message);
                            }
                        })
                        .onCancel(new CancelListener() { // 결제 취소시 호출
                            @Override
                            public void onCancel(@Nullable String message) {

                                Toast.makeText(getApplicationContext(),"결제가 취소되었습니다",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onError(new ErrorListener() { // 에러가 났을때 호출되는 부분
                            @Override
                            public void onError(@Nullable String message) {
                                Log.d("error", message);
                            }
                        })
                        .onClose(
                                new CloseListener() { //결제창이 닫힐때 실행되는 부분
                                    @Override
                                    public void onClose(String message) {

                                    }
                                })
                        .request();
            }
        });
    }
    public void setHotList() {

        //php url 입력
        String URL = "http://172.26.126.172:443/setHotList.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {

                    hotItems = new ArrayList<hotItem>();

                    jsonObject = new JSONObject(response);

                    Parsing parsing = new Parsing();
                    try {
                        int index=0;
                        hotItem mi;
                        while (true) {

                            parsing.hotParsing(response, index);
                            if (parsing.label == "") {

                                break;
                            }


                            String idx = parsing.idx;
                            String label = parsing.label;
                            String state = parsing.state;

                            index++;

                            mi = new hotItem(idx,label,strImg,state);
                            hotItems.add(mi);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                hotadpt = new hotAdpt(BuyLabel.this,R.layout.hotlist,hotItems);
                hotadpt.setOnItemClickListener(new hotAdpt.OnItemClickListener() {
                    @Override
                    public void onItemClick(int pos) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                chkHot.setChecked(false);
                                int cnt=0;
                                for(int i=0;i<hotItems.size();i++){
                                    if(hotItems.get(i).boolChk){
                                        cnt++;
                                    }
                                }
                                if(cnt!=0){
                                    chkHot.setChecked(true);
                                    tvPrice.setText(cnt*1000 + "원");
                                }

                                if(cnt>=2){
                                    Toast.makeText(getApplicationContext(),"다중 구매시에도 라벨은 하나만 나타납니다.",Toast.LENGTH_SHORT).show();
                                }
                            }
                        },100);
                    }
                });
                hotList.setAdapter(hotadpt);


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

    public void insPayment(int payment) {

        //php url 입력
        String URL = "http://172.26.126.172:443/insPayment.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

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
                param.put("payment",Integer.toString(payment));
                param.put("user_idx",shared_preferences.get_user_email(BuyLabel.this));


                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }


    public void insLabel(String idx) {

        //php url 입력
        String URL = "http://172.26.126.172:443/insLabel.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

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
                param.put("idx",idx);
                param.put("sell_idx",sell_idx);


                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }


}
class hotItem {

    String idx;
    String label;
    String img;
    Boolean boolChk;
    Boolean boolHot;
    String state;

    hotItem(String idx,String label,String img,String state){

        this.idx= idx;
        this.label=label;
        this.img=img;
        this.state=state;
        boolChk= false;
        boolHot= false;
    }
}

class hotAdpt extends BaseAdapter {
    @Override
    public long getItemId(int i) {
        return 0;
    }

    RequestQueue requestQueue;
    Context maincon;
    LayoutInflater Inflater;
    ArrayList<hotItem> arsrc;

    int layout;

    public RequestQueue getRequestQueue() {
        requestQueue = Volley.newRequestQueue(maincon.getApplicationContext());
        return requestQueue;
    }


    public hotAdpt(Context context, int alayout, ArrayList<hotItem> aarSrc) {
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
        return arsrc.get(position).label;
    }

    public void updateReceiptsList(ArrayList<hotItem> arsrc) {
        arsrc = this.arsrc;
        int size = arsrc.size();
        this.notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    private hotAdpt.OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(hotAdpt.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }



    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        if(convertView == null) {
            convertView = Inflater.inflate(layout,parent,false);
        }
        TextView label = (TextView)convertView.findViewById(R.id.label);
        TextView onlabel = (TextView)convertView.findViewById(R.id.onlabel);
        CheckBox chk = (CheckBox)convertView.findViewById(R.id.chk);
        ImageView img = (ImageView)convertView.findViewById(R.id.img);
        String imgurl = "http://172.26.126.172:443/"+arsrc.get(position).img;
        Glide.with(convertView).load(imgurl).into(img);
        label.setText(arsrc.get(position).label);
        onlabel.setText(arsrc.get(position).label);

        if(!arsrc.get(position).state.equals("0")){
          arsrc.get(position).boolHot=true;
          notifyDataSetChanged();
        }

        if(arsrc.get(position).boolHot){
            chk.setClickable(false);
            chk.setFocusable(false);
        }else{
            chk.setClickable(true);
            chk.setFocusable(true);
            chk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position != ListView.INVALID_POSITION) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(position);
                            if(arsrc.get(position).boolChk) {
                                arsrc.get(position).boolChk = false;
                            }else{
                                arsrc.get(position).boolChk = true;
                            }
                            notifyDataSetChanged();
                        }
                    }
                }

            });
        }



        return convertView;


    }



}
