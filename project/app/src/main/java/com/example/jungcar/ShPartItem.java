package com.example.jungcar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class ShPartItem extends AppCompatActivity {
    ImageView imgMy, img;
    LinearLayout llSell;
    TextView tvSubject, tvState, tvPrice, tvType, tvDate, tvNumber, tvContent,tvDelivery,tvCondition,tvCmtCnt;
    ImageButton btnMore, btnToTop;
    ListView imgList, cmtList;
    EditText edtComm;
    Button btnEnroll;
    DecimalFormat decimalFormat = new DecimalFormat("###,###원");
    ArrayList<shImgItem> shImgItems;
    shImgAdpt shImgadpt;
    static StringRequest request;
    static RequestQueue requestQueue;
    String json,market_idx,board_idx;
    ArrayList<commentItem> commentItems;
    commentAdpt commentadpt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shpartitem);

        Intent intent = getIntent();
        market_idx = intent.getExtras().getString("market_idx");

        imgMy = (ImageView) findViewById(R.id.imgMy);
        img = (ImageView) findViewById(R.id.img);
        llSell = (LinearLayout) findViewById(R.id.llSell);
        tvSubject = (TextView) findViewById(R.id.tvSubject);
        tvState = (TextView) findViewById(R.id.tvState);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvType = (TextView) findViewById(R.id.tvType);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvNumber = (TextView) findViewById(R.id.tvNumber);
        tvContent = (TextView) findViewById(R.id.tvContent);
        tvDelivery=(TextView)findViewById(R.id.tvDelivery);
        tvCondition=(TextView)findViewById(R.id.tvCondition);
        tvCmtCnt=(TextView)findViewById(R.id.tvCmtCnt);
        btnMore = (ImageButton) findViewById(R.id.btnMore);
        btnToTop = (ImageButton) findViewById(R.id.btnToTop);
        imgList = (ListView) findViewById(R.id.imgList);
        cmtList = (ListView) findViewById(R.id.cmtList);
        edtComm = (EditText) findViewById(R.id.edtComm);
        btnEnroll = (Button) findViewById(R.id.btnEnroll);

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        shPartImg();
        setPartItem();
        ShPart();

        btnToTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               imgList.smoothScrollToPosition(0);
            }
        });


        imgMy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shared_preferences.get_user_email(ShPartItem.this).isEmpty()==true) {
                    new AlertDialog.Builder(ShPartItem.this)
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
                else{
                    Intent intent = new Intent(getApplicationContext(),MyPage.class);
                    startActivity(intent);
                }

            }
        });

        btnEnroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insComment(board_idx);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        setCommentList(board_idx);
                        shCmtCnt(board_idx);
                        edtComm.setText("");
                    }
                },1000);

            }
        });

    }
    public void ShPart() {

        //php url 입력
        String URL = "http://172.26.126.172:443/ShPart.php";


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
                param.put("user",shared_preferences.get_user_email(ShPartItem.this).toString());
                param.put("market_idx",market_idx);


                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }

    public void insComment(String board_idx){

        //php url 입력
        String URL = "http://172.26.126.172:443/insComment.php";

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

                param.put("board_idx",board_idx);
                param.put("content",edtComm.getText().toString());
                param.put("user_idx",shared_preferences.get_user_email(ShPartItem.this));

                return param;
            }

        };
        request.setShouldCache(false);
        requestQueue.add(request);



    }

    public void shCmtCnt(String board_idx) {

        //php url 입력
        String URL = "http://172.26.126.172:443/shCmtCnt.php";


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
                param.put("board_idx",board_idx);

                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);
    }


    public void setCommentList(String board_idx) {

        //php url 입력
        String URL = "http://172.26.126.172:443/setCommentList.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {

                    commentItems = new ArrayList<commentItem>();

                    jsonObject = new JSONObject(response);

                    Parsing parsing = new Parsing();
                    try {
                        int index=0;
                        commentItem mi;
                        while (true) {

                            parsing.commentParsing(response, index);
                            if (parsing.comment == "") {
                                break;
                            }

                            String idx = parsing.idx;
                            String comment = parsing.comment;
                            String user_idx = parsing.user_idx;
                            String date = parsing.date;
                            String user = parsing.user;
                            String recomment_idx = parsing.recomment_idx;
                            String ranking = parsing.ranking;
                            String state = parsing.state;
                            String reco = parsing.reco;
                            index++;

                            mi = new commentItem(idx, user, user_idx, comment, date, reco, state, recomment_idx, ranking);
                            commentItems.add(mi);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                commentadpt = new commentAdpt(ShPartItem.this,R.layout.commentlist,commentItems);
                commentadpt.setOnItemClickListener(new commentAdpt.OnItemClickListener() {
                    @Override
                    public void onItemClick(int pos) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setCommentList(board_idx);
                                shCmtCnt(board_idx);
                            }
                        },1000);
                    }
                });
                cmtList.setAdapter(commentadpt);

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
                param.put("board_idx",board_idx);
                param.put("user_idx",shared_preferences.get_user_email(ShPartItem.this));

                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }


    public void shPartImg() {

            //php url 입력
            String URL = "http://172.26.126.172:443/shPartImg.php";


            StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

                JSONObject jsonObject;

                @Override
                public void onResponse(String response) {
                    //응답이 되었을때 response로 값이 들어옴
                    try {
                        shImgItems = new ArrayList<shImgItem>();

                        jsonObject = new JSONObject(response);

                        Parsing parsing = new Parsing();
                        try {
                            int index=0;
                            shImgItem mi;
                            while (true) {

                                parsing.imgparsing(response, index);
                                if (parsing.img == "") {

                                    break;
                                }
                                String img = parsing.img;

                                index++;
                                mi = new shImgItem(img);
                                shImgItems.add(mi);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    shImgadpt = new shImgAdpt(ShPartItem.this,R.layout.shimglist,shImgItems);
                    imgList.setAdapter(shImgadpt);
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


    public void setPartItem() {

        //php url 입력
        String URL = "http://172.26.126.172:443/setPartItem.php";


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


                        parsing.shpartitemParsing(json);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    tvSubject.setText(parsing.name);
                    if(parsing.idx.equals("null")){
                        tvState.setText("판매");
                    }else{
                        tvState.setText("완료");
                        llSell.setVisibility(View.VISIBLE);
                    }
                    tvPrice.setText(decimalFormat.format(Integer.parseInt(parsing.price))+"원");
                    tvType.setText(parsing.cond);
                    tvDate.setText(parsing.date);
                    tvContent.setText(parsing.detail);
                    tvDelivery.setText("배송: "+parsing.delivery);
                    tvCondition.setText("상태: "+parsing.state);
                    board_idx=parsing.board_idx;

                    if(parsing.user.equals(shared_preferences.get_user_email(ShPartItem.this))){
                        btnMore.setVisibility(View.VISIBLE);
                    }else{
                        btnMore.setVisibility(View.INVISIBLE);
                    }
                    if(parsing.number.equals("null")){
                        tvNumber.setText("조회 0");
                    }else {
                        tvNumber.setText("조회 " + parsing.number);
                    }


                    String imgurl = "http://172.26.126.172:443/"+parsing.img;
                    Glide.with(ShPartItem.this).load(imgurl).into(img);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setCommentList(board_idx);
                shCmtCnt(board_idx);
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
class shImgItem {

    String img;
    shImgItem(String img){

        this.img = img;

    }
}

class shImgAdpt extends BaseAdapter {
    @Override
    public long getItemId(int i) {
        return 0;
    }

    RequestQueue requestQueue;
    Context maincon;
    LayoutInflater Inflater;
    ArrayList<shImgItem> arsrc;

    int layout;

    public RequestQueue getRequestQueue() {
        requestQueue = Volley.newRequestQueue(maincon.getApplicationContext());
        return requestQueue;
    }


    public shImgAdpt(Context context, int alayout, ArrayList<shImgItem> aarSrc) {
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
        return arsrc.get(position).img;
    }

    public void updateReceiptsList(ArrayList<shImgItem> arsrc) {
        arsrc = this.arsrc;
        int size = arsrc.size();
        this.notifyDataSetChanged();
    }



    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        if(convertView == null) {
            convertView = Inflater.inflate(layout,parent,false);
        }

        ImageView img = (ImageView)convertView.findViewById(R.id.img);
        String imgurl = "http://172.26.126.172:443/"+arsrc.get(position).img;
        Glide.with(convertView).load(imgurl).into(img);


        return convertView;


    }


}