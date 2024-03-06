package com.example.jungcar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class ShItem extends AppCompatActivity {
    static StringRequest request;
    static RequestQueue requestQueue;
    String json;
    ImageButton btnBack;
    ImageView img;
    Button btnShAll, btnCrash;
    Button btnComment;
    LinearLayout llCall, llSendMessage;
    TextView tvSubject, tvMore, tvDetail,tvPhone,tvUser,tvCmtCnt,tvPrice,tvShOpt;
    ListView cmtList;
    EditText edtAddCmt;
    String sell_idx;
    ImageView imgPower,imgHandle,imgCruz,imgTier,imgWarning,imgHotSheet,imgVenti,imgAround,imgMemory,imgPark;
    String strBrand, strCountry, strModel, strGrade, minMilesage, maxMilesage, minPrice, maxPrice, isChk, strName,strTotal;
    String pageflag;
    ArrayList<sellCommentItem> sellCommentItems;
    sellCommentAdpt sellcommentadpt;
    @Override
    public void onBackPressed(){
        if(pageflag.equals("2")){
          finish();
        }else {

            Intent intent = new Intent(getApplicationContext(), ShResult.class);
            intent.putExtra("strName", strName);
            intent.putExtra("strBrand", strBrand);
            intent.putExtra("strModel", strModel);
            intent.putExtra("strGrade", strGrade);
            intent.putExtra("minMilesage", minMilesage);
            intent.putExtra("maxMilesage", maxMilesage);
            intent.putExtra("minPrice", minPrice);
            intent.putExtra("maxPrice", maxPrice);
            intent.putExtra("strCountry", strCountry);
            intent.putExtra("isChk", isChk);
            intent.putExtra("strTotal", strTotal);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shitem);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        img = (ImageView) findViewById(R.id.img);
        btnShAll = (Button) findViewById(R.id.btnShAll);
        btnCrash = (Button) findViewById(R.id.btnCrash);
        llCall = (LinearLayout) findViewById(R.id.llCall);
        llSendMessage = (LinearLayout) findViewById(R.id.llSendMessage);
        tvSubject = (TextView) findViewById(R.id.tvSubject);
        tvMore = (TextView) findViewById(R.id.tvMore);
        tvDetail = (TextView) findViewById(R.id.tvDetail);
        tvPhone = (TextView) findViewById(R.id.tvPhone);
        tvPrice = (TextView)findViewById(R.id.tvPrice);
        tvUser = (TextView) findViewById(R.id.tvUser);
        tvCmtCnt = (TextView)findViewById(R.id.tvCmtCnt);
        tvShOpt = (TextView)findViewById(R.id.tvShOpt);
        cmtList = (ListView)findViewById(R.id.cmtList);
        edtAddCmt = (EditText) findViewById(R.id.edtAddCmt);

        imgPower =(ImageView)findViewById(R.id.imgPower);
        imgHandle =(ImageView)findViewById(R.id.imgHandle);
        imgCruz  =(ImageView)findViewById(R.id.imgCruz);
        imgTier  =(ImageView)findViewById(R.id.imgTier);
        imgWarning =(ImageView)findViewById(R.id.imgWarning);
        imgHotSheet =(ImageView)findViewById(R.id.imgHotSheet);
        imgVenti =(ImageView)findViewById(R.id.imgVenti);
        imgAround =(ImageView)findViewById(R.id.imgAround);
        imgMemory =(ImageView)findViewById(R.id.imgMemory);
        imgPark =(ImageView)findViewById(R.id.imgPark);
        btnComment = (Button)findViewById(R.id.btnComment);

        Intent intent = getIntent();
        sell_idx = intent.getExtras().getString("sell_idx");
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
        pageflag=intent.getExtras().getString("pageflag","1");
        if (requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pageflag.equals("2")){
                 finish();
                }else {

                    Intent intent = new Intent(getApplicationContext(), ShResult.class);
                    intent.putExtra("strName", strName);
                    intent.putExtra("strBrand", strBrand);
                    intent.putExtra("strModel", strModel);
                    intent.putExtra("strGrade", strGrade);
                    intent.putExtra("minMilesage", minMilesage);
                    intent.putExtra("maxMilesage", maxMilesage);
                    intent.putExtra("minPrice", minPrice);
                    intent.putExtra("maxPrice", maxPrice);
                    intent.putExtra("strCountry", strCountry);
                    intent.putExtra("isChk", isChk);
                    intent.putExtra("strTotal", strTotal);
                    startActivity(intent);
                }
            }
        });

        btnShAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShCarImg.class);
                intent.putExtra("sell_idx",sell_idx);
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

        tvShOpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShOption.class);
                intent.putExtra("sell_idx",sell_idx);
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

        if(shared_preferences.get_user_email(ShItem.this).isEmpty()==false) {
            insHistory();
        }
       setItem();
       setOption();
        setSellCommentList();
        shSellCmtCnt(sell_idx);

       btnComment.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (shared_preferences.get_user_email(ShItem.this).isEmpty() == true) {
                   new AlertDialog.Builder(ShItem.this)
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
               } else {
                   if (edtAddCmt.length() == 0) {
                    Toast.makeText(getApplicationContext(),"내용을 입력하세요",Toast.LENGTH_SHORT).show();
                   } else {
                       insSellComment();
                       Handler handler = new Handler();
                       handler.postDelayed(new Runnable() {
                           @Override
                           public void run() {
                               setSellCommentList();
                               shSellCmtCnt(sell_idx);
                               edtAddCmt.setText("");
                           }
                       }, 1000);

                   }
               }
           }
       });
    }

    public void setSellCommentList() {

        //php url 입력
        String URL = "http://172.26.126.172:443/setSellCommentList.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {

                    sellCommentItems = new ArrayList<sellCommentItem>();

                    jsonObject = new JSONObject(response);

                    Parsing parsing = new Parsing();
                    try {
                        int index=0;
                        sellCommentItem mi;
                        while (true) {

                            parsing.sellCommentParsing(response, index);
                            if (parsing.comment == "") {
                                break;
                            }

                            String idx = parsing.idx;
                            String comment = parsing.comment;
                            String user_idx = parsing.user_idx;
                            String date = parsing.date;
                            String user = parsing.user;
                            String recomment_idx = parsing.recomment_idx;
                            String comment_idx = parsing.comment_idx;
                            index++;

                            mi = new sellCommentItem(comment_idx,user,user_idx,comment,date,recomment_idx,idx);
                            sellCommentItems.add(mi);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                sellcommentadpt = new sellCommentAdpt(ShItem.this,R.layout.sellcmtlist,sellCommentItems);
                sellcommentadpt.setOnItemClickListener(new sellCommentAdpt.OnItemClickListener() {
                    @Override
                    public void onItemClick(int pos) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setSellCommentList();
                                shSellCmtCnt(sell_idx);
                            }
                        },1000);
                    }
                });
                cmtList.setAdapter(sellcommentadpt);

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

    public void insSellComment(){

        //php url 입력
        String URL = "http://172.26.126.172:443/insSellComment.php";

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {
                    jsonObject = new JSONObject(response);
                    Parsing parsing = new Parsing();


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
                param.put("content",edtAddCmt.getText().toString());
                param.put("user_idx",shared_preferences.get_user_email(ShItem.this));

                return param;
            }

        };
        request.setShouldCache(false);
        requestQueue.add(request);



    }

    public void insHistory() {

        //php url 입력
        String URL = "http://172.26.126.172:443/insHistory.php";


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
                param.put("user",shared_preferences.get_user_email(ShItem.this).toString());
                param.put("sell_idx",sell_idx);


                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }


    public void setItem() {

        //php url 입력
        String URL = "http://172.26.126.172:443/setItem.php";


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
                            parsing.itemParsing(json);
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                          tvSubject.setText(parsing.name);
                        tvMore.setText(parsing.date + " " + parsing.mileage+"km " + parsing.fuel);
                        tvPrice.setText(parsing.price+"만원");
                        String imgurl = "http://172.26.126.172:443/"+parsing.img;
                        Glide.with(ShItem.this).load(imgurl).into(img);
                        tvUser.setText(parsing.user);

                        if(shared_preferences.get_user_email(ShItem.this).isEmpty()==false) {

                                if(parsing.agree.equals("y")) {
                                    tvPhone.setText(parsing.phone);
                                }else{
                                    tvPhone.setText("공개거부");
                                }

                        }else{
                            tvPhone.setText("로그인필요");
                        }
                        tvDetail.setText(parsing.detail);


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
    public void setOption() {

        //php url 입력
        String URL = "http://172.26.126.172:443/setOption.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴



                try {
                    int index=0;

                    jsonObject = new JSONObject(response);

                    Parsing parsing = new Parsing();
                    try {

                        while (true) {

                            parsing.setOptionParsing(response, index);
                            if (parsing.name == "") {

                                break;
                            }

                            if(parsing.name.equals("파워전동 트렁크")){
                                imgPower.setImageResource(R.drawable.trunkon);
                            }else if(parsing.name.equals("열선핸들")){
                                imgHandle.setImageResource(R.drawable.handleon);
                            }else if(parsing.name.equals("크루즈컨트롤 (일반)")||parsing.name.equals("크루즈컨트롤 (어댑티브)")){
                                imgCruz.setImageResource(R.drawable.curzon);
                            }else if(parsing.name.equals("전자식주차브레이크")){
                                imgPark.setImageResource(R.drawable.breakon);
                            }else if(parsing.name.equals("타이어 공기압 센서")){
                                imgTier.setImageResource(R.drawable.tieron);
                            }else if(parsing.name.equals("후측방 경보시스템")){
                                imgWarning.setImageResource(R.drawable.alramon);
                            }else if(parsing.name.equals("어라운드뷰")){
                                imgAround.setImageResource(R.drawable.aroundon);
                            }else if(parsing.name.equals("열선시트")){
                                imgHotSheet.setImageResource(R.drawable.feveron);
                            }else if(parsing.name.equals("메모리시트")){
                                imgMemory.setImageResource(R.drawable.memoryon);
                            }else if(parsing.name.equals("통풍시트")){
                                imgVenti.setImageResource(R.drawable.windon);
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
                param.put("sell_idx",sell_idx);

                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }


    public void shSellCmtCnt(String Sell_idx) {

        //php url 입력
        String URL = "http://172.26.126.172:443/shSellCmtCnt.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {



                    jsonObject = new JSONObject(response);

                    Parsing parsing = new Parsing();
                    try {


                        parsing.countParsing(response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    tvCmtCnt.setText(parsing.count);

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


class sellCommentItem {
    String comment_idx;
    String id;
    String user_idx;
    String comment;
    String date;
    String recomment_idx;
    String idx;
    int flag;
    int focusflag;

    sellCommentItem(String comment_idx,String id,String user_idx,String comment,String date,String recomment_idx,String idx) {
        this.comment_idx=comment_idx;
        this.id=id;
        this.user_idx=user_idx;
        this.comment=comment;
        this.date= date;
        this.recomment_idx=recomment_idx;
        this.idx=idx;
        flag=0;
        focusflag=0;
    }
}

class sellCommentAdpt extends BaseAdapter {
    @Override
    public long getItemId(int i) {
        return 0;
    }

    RequestQueue requestQueue;
    Context maincon;
    LayoutInflater Inflater;
    ArrayList<sellCommentItem> arsrc;

    int layout;

    public RequestQueue getRequestQueue() {
        requestQueue = Volley.newRequestQueue(maincon.getApplicationContext());
        return requestQueue;
    }


    public sellCommentAdpt(Context context, int alayout, ArrayList<sellCommentItem> aarSrc) {
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
        return arsrc.get(position).id;
    }

    public void updateReceiptsList(ArrayList<sellCommentItem> arsrc) {
        arsrc = this.arsrc;
        int size = arsrc.size();
        this.notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    private sellCommentAdpt.OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(sellCommentAdpt.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }



    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        if (convertView == null) {
            convertView = Inflater.inflate(layout, parent, false);
        }
        EditText comment = (EditText) convertView.findViewById(R.id.comment);
        LinearLayout llReply =(LinearLayout)convertView.findViewById(R.id.llReply);
        TextView user =(TextView)convertView.findViewById(R.id.user);
        TextView date =(TextView)convertView.findViewById(R.id.date);
        EditText edtReply = (EditText)convertView.findViewById(R.id.edtReply);
        Button addReply = (Button)convertView.findViewById(R.id.addReply);
        TextView tvReply=(TextView) convertView.findViewById(R.id.tvReply);
        ImageButton more=(ImageButton)convertView.findViewById(R.id.more);
        LinearLayout llDetail = (LinearLayout)convertView.findViewById(R.id.llDetail);
        comment.setText(arsrc.get(position).comment);
        user.setText(arsrc.get(position).id);
        date.setText(arsrc.get(position).date);
        ImageView shreply = (ImageView)convertView.findViewById(R.id.shreply);
        TextView modify =(TextView)convertView.findViewById(R.id.modify) ;
        tvReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shared_preferences.get_user_email(maincon.getApplicationContext()).isEmpty()==false) {
                    arsrc.get(position).flag = 1;
                    notifyDataSetChanged();
                }else{
                    Toast.makeText(maincon.getApplicationContext(),"로그인필요",Toast.LENGTH_SHORT).show();
                }
            }
        });

        if(arsrc.get(position).flag==0){
            llReply.setVisibility(View.GONE);
            llDetail.setVisibility(View.VISIBLE);
        }else if(arsrc.get(position).flag==1){
            llReply.setVisibility(View.VISIBLE);
            llDetail.setVisibility(View.GONE);
        }

        if(arsrc.get(position).idx.equals("null")){
            shreply.setVisibility(View.VISIBLE);
            tvReply.setVisibility(View.INVISIBLE);
        }else{
            shreply.setVisibility(View.GONE);
            tvReply.setVisibility(View.VISIBLE);
        }

        if(arsrc.get(position).focusflag==0){
            modify.setVisibility(View.INVISIBLE);
            comment.setFocusable(false);
        }else{
            modify.setVisibility(View.VISIBLE);
            comment.setSelectAllOnFocus(true);
            comment.setTextIsSelectable(true);
            comment.setFocusable(true);
            comment.requestFocus();
        }

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position != ListView.INVALID_POSITION) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position);
                        more.requestFocus();
                        if(arsrc.get(position).idx.equals("null")){
                            updateSellComment("recomment",arsrc.get(position).recomment_idx,comment.getText().toString());
                        }else{
                            updateSellComment("comment",arsrc.get(position).comment_idx,comment.getText().toString());
                        }
                        arsrc.get(position).focusflag=0;
                        notifyDataSetChanged();
                    }
                }
            }
        });


                more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(shared_preferences.get_user_email(maincon.getApplicationContext()).isEmpty()==false) {
                            if (arsrc.get(position).user_idx.equals(shared_preferences.get_user_email(maincon.getApplicationContext()))) {
                                final PopupMenu popupMenu = new PopupMenu(maincon.getApplicationContext(), view);
                                popupMenu.getMenuInflater().inflate(R.menu.recordmenu, popupMenu.getMenu());
                                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem menuItem) {
                                        if (menuItem.getItemId() == R.id.sell_menu1) {
                                          arsrc.get(position).focusflag=1;
                                          notifyDataSetChanged();


                                        } else if (menuItem.getItemId() == R.id.sell_menu2) {

                                            if (position != ListView.INVALID_POSITION) {
                                                if (onItemClickListener != null) {
                                                    onItemClickListener.onItemClick(position);
                                                    if(arsrc.get(position).idx.equals("null")){
                                                        delSellComment("recomment",arsrc.get(position).recomment_idx);
                                                    }else{
                                                        delSellComment("comment",arsrc.get(position).comment_idx);
                                                    }
                                                }
                                            }

                                        }

                                        return false;
                                    }

                                });
                                popupMenu.show();
                            } else {
                                final PopupMenu popupMenu = new PopupMenu(maincon.getApplicationContext(), view);
                                popupMenu.getMenuInflater().inflate(R.menu.reportmenu, popupMenu.getMenu());
                                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem menuItem) {
                                        if (menuItem.getItemId() == R.id.menu1) {
                                            Toast.makeText(maincon.getApplicationContext(), "신고하기", Toast.LENGTH_SHORT).show();

                                        }
                                        return false;
                                    }
                                });
                                popupMenu.show();
                            }
                        }else{
                            Toast.makeText(maincon.getApplicationContext(),"로그인필요",Toast.LENGTH_SHORT).show();
                        }
                    }
            });

        addReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtReply.length() == 0) {
                    Toast.makeText(maincon.getApplicationContext(),"내용을입력하세요",Toast.LENGTH_SHORT).show();
                } else {
                    if (position != ListView.INVALID_POSITION) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(position);
                            insSellRecomment(edtReply.getText().toString(), arsrc.get(position).comment_idx);
                            arsrc.get(position).flag = 0;
                            notifyDataSetChanged();
                        }
                    }
                }
            }
        });


        return convertView;


    }
    public void insSellRecomment(String recomment,String comment_idx){
        requestQueue = getRequestQueue();

        //php url 입력
        String URL = "http://172.26.126.172:443/insSellRecomment.php";

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {
                    jsonObject = new JSONObject(response);
                    Parsing parsing = new Parsing();


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

                param.put("comment_idx",comment_idx);
                param.put("content",recomment);
                param.put("user_idx",shared_preferences.get_user_email(maincon.getApplicationContext()));

                return param;
            }

        };
        request.setShouldCache(false);
        requestQueue.add(request);



    }

    public void delSellComment(String type, String idx){
        requestQueue = getRequestQueue();

        //php url 입력
        String URL = "http://172.26.126.172:443/delSellComment.php";

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {
                    jsonObject = new JSONObject(response);
                    Parsing parsing = new Parsing();


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
                param.put("type",type);
                return param;
            }

        };
        request.setShouldCache(false);
        requestQueue.add(request);



    }

    public void updateSellComment(String type, String idx,String comment){
        requestQueue = getRequestQueue();

        //php url 입력
        String URL = "http://172.26.126.172:443/updateSellComment.php";

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {
                    jsonObject = new JSONObject(response);
                    Parsing parsing = new Parsing();


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
                param.put("comment",comment);
                param.put("idx",idx);
                param.put("type",type);

                return param;
            }

        };
        request.setShouldCache(false);
        requestQueue.add(request);



    }



}





