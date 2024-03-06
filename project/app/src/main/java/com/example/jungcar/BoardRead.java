package com.example.jungcar;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
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
import android.widget.MediaController;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

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

public class BoardRead extends AppCompatActivity {
    TextView tvBuy, tvSell, tvPart, tvRecord;
    TextView tvCommunity, tvType, tvSubject, tvUser, tvDate, tvNum, tvContent, tvCmtCnt;
    Button btnReport, btnPrev, btnNext, btnList, btnShWrite, btnEnroll,btnReco;
    ListView imgList, cmtList;
    VideoView video;
    EditText edtAddCmt;
    static StringRequest request;
    static RequestQueue requestQueue;
    String json;
    ArrayList<shImgItem> shImgItems;
    shImgAdpt shImgadpt;
    String strSelected;
    String strBrand = "", strCountry = "", strModel = "", strGrade = "", minMilesage = "", maxMilesage = "", minPrice = "", maxPrice = "", isChk = "";
    String board_idx, pre_idx, next_idx;
    ArrayList<commentItem> commentItems;
    commentAdpt commentadpt;
    ImageView imgMy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boardread);
        tvSell = (TextView) findViewById(R.id.tvSell);
        tvBuy = (TextView) findViewById(R.id.tvBuy);
        tvCommunity = (TextView) findViewById(R.id.tvCommunity);
        tvPart = (TextView) findViewById(R.id.tvPart);
        tvRecord = (TextView) findViewById(R.id.tvRecord);
        tvType = (TextView) findViewById(R.id.tvType);
        tvSubject = (TextView) findViewById(R.id.tvSubject);
        tvUser = (TextView) findViewById(R.id.tvUser);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvNum = (TextView) findViewById(R.id.tvNum);
        tvContent = (TextView) findViewById(R.id.tvContent);
        tvCmtCnt = (TextView) findViewById(R.id.tvCmtCnt);
        btnReport = (Button) findViewById(R.id.btnReport);
        btnPrev  = (Button) findViewById(R.id.btnPrev);
        btnNext  = (Button) findViewById(R.id.btnNext);
        btnList  = (Button) findViewById(R.id.btnList);
        btnShWrite  = (Button) findViewById(R.id.btnShWrite);
        btnEnroll  = (Button) findViewById(R.id.btnEnroll);
        btnReco = (Button) findViewById(R.id.btnReco);
        imgList = (ListView) findViewById(R.id.imgList);
        cmtList = (ListView) findViewById(R.id.cmtList);
        video = (VideoView) findViewById(R.id.video);
        edtAddCmt = (EditText) findViewById(R.id.edtAddCmt);
        imgMy = (ImageView) findViewById(R.id.imgMy);
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        Intent intent = getIntent();
        strSelected= intent.getExtras().getString("type");
        board_idx= intent.getExtras().getString("board_idx");

        imgMy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shared_preferences.get_user_email(BoardRead.this).isEmpty()==true) {
                    new AlertDialog.Builder(BoardRead.this)
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

        if(strSelected.equals("0")){
            btnPrev.setVisibility(View.INVISIBLE);
            btnNext.setVisibility(View.INVISIBLE);
            tvType.setVisibility(View.INVISIBLE);
        }else {
            tvType.setText(strSelected);
        }
        shBoard(board_idx);
        setCommentList(board_idx);
        tvRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RecordHome.class);
                startActivity(intent);
            }
        });

        tvBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

            }
        });

        tvSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainSell.class);
                startActivity(intent);
            }
        });

        tvPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainPart.class);
                startActivity(intent);
            }
        });

        btnReco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chgReco(board_idx);
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pre_idx.equals("null")){
                    Toast.makeText(getApplicationContext(),"이전글이 없습니다.",Toast.LENGTH_SHORT).show();
                }else {
                    board_idx = pre_idx;
                    shBoard(pre_idx);
                    setCommentList(pre_idx);
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(next_idx.equals("null")){
                    Toast.makeText(getApplicationContext(),"다음글이 없습니다.",Toast.LENGTH_SHORT).show();
                }else {
                    board_idx=next_idx;
                    shBoard(next_idx);
                    setCommentList(next_idx);

                }
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnEnroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtAddCmt.length()!=0) {
                    insComment(board_idx);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setCommentList(board_idx);
                            shCmtCnt(board_idx);
                            edtAddCmt.setText("");
                        }
                    }, 1000);
                }else{
                    Toast.makeText(getApplicationContext(),"댓글 내용을 입력하세요",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnShWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),BoardSearch.class);
                intent.putExtra("board_type","0");
                intent.putExtra("condition","id");
                intent.putExtra("query",tvUser.getText().toString());
                startActivity(intent);
            }
        });
    }

    public void insBoardHistory(String board_idx) {

        //php url 입력
        String URL = "http://172.26.126.172:443/insBoardHistory.php";


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
                param.put("user_idx",shared_preferences.get_user_email(BoardRead.this).toString());
                param.put("board_idx",board_idx);


                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }

    public void chgBoard(String board_idx) {

        //php url 입력
        String URL = "http://172.26.126.172:443/chgBoard.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {



                    jsonObject = new JSONObject(response);

                    Parsing parsing = new Parsing();
                    try {


                        parsing.chgBoardParsing(response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    next_idx = parsing.next;
                    pre_idx = parsing.pre;




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
                param.put("board_type",strSelected);
                param.put("board_idx",board_idx);


                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);

    }


    public void chgReco(String board_idx) {

        //php url 입력
        String URL = "http://172.26.126.172:443/chgReco.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {



                    jsonObject = new JSONObject(response);

                    Parsing parsing = new Parsing();
                    try {


                        parsing.chgRecoParsing(response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    btnReco.setText("추천 "+parsing.reco);

                    String state = parsing.state;

                    if(state.equals("0")){
                        btnReco.setBackgroundColor(Color.parseColor("#ff0000"));
                    }else{
                        btnReco.setBackgroundColor(Color.parseColor("#00ff00"));
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
                param.put("user_idx",shared_preferences.get_user_email(BoardRead.this));
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

                commentadpt = new commentAdpt(BoardRead.this,R.layout.commentlist,commentItems);
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
                param.put("user_idx",shared_preferences.get_user_email(BoardRead.this));

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
                param.put("content",edtAddCmt.getText().toString());
                param.put("user_idx",shared_preferences.get_user_email(BoardRead.this));

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

    public void shBoard(String board_idx) {

        //php url 입력
        String URL = "http://172.26.126.172:443/shBoard.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {



                    jsonObject = new JSONObject(response);

                    Parsing parsing = new Parsing();
                    try {


                            parsing.shBoardParsing(response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    tvSubject.setText(parsing.name);
                    tvUser.setText(parsing.user);
                    tvCmtCnt.setText(parsing.count);
                    tvNum.setText(parsing.number);
                    btnReco.setText("추천 "+parsing.reco);
                    tvDate.setText(parsing.date);
                    tvContent.setText(parsing.content);
                    String state = parsing.state;

                    if(state.equals("0")){
                        btnReco.setBackgroundColor(Color.parseColor("#ff0000"));
                    }else{
                        btnReco.setBackgroundColor(Color.parseColor("#00ff00"));
                    }

                    shContent(board_idx);
                    insBoardHistory(board_idx);
                    chgBoard(board_idx);

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
                param.put("user_idx",shared_preferences.get_user_email(BoardRead.this));


                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);
    }

    public void shContent(String board_idx) {

        //php url 입력
        String URL = "http://172.26.126.172:443/shContent.php";


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

                            parsing.boardContentParsing(response, index);
                            if (parsing.content == "") {

                                break;
                            }
                            String content = parsing.content;
                            String type = parsing.type;

                            if(type.equals("2")) {
                                imgList.setVisibility(View.VISIBLE);
                                mi = new shImgItem(content);
                                shImgItems.add(mi);
                            }else{
                                MediaController mediaController = new MediaController(BoardRead.this);
                                mediaController.setAnchorView(video);
                                video.setMediaController(mediaController);
                                video.setVisibility(View.VISIBLE);
                                String uri = "http://172.26.126.172:443/"+content;
                                video.setVideoPath(uri);
                            }
                            index++;

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                shImgadpt = new shImgAdpt(BoardRead.this,R.layout.shimglist,shImgItems);
                imgList.setAdapter(shImgadpt);
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //에러나면 error로 나옴


            }

        }) {
            @Override
            protected Map<String, String> getParams()    throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                //php로 설정값을 보낼 수 있음
                param.put("board_idx",board_idx);

                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);
    }
}

class commentItem {
    String comment_idx;
    String id;
    String user_idx;
    String comment;
    String date;
    String reco;
    String state;
    String ranking;
    String recomment_idx;
    int flag;
    int focusflag;

    commentItem(String comment_idx,String id,String user_idx,String comment,String date,String reco, String state,String recomment_idx,String ranking) {
        this.comment_idx=comment_idx;
        this.id=id;
        this.user_idx=user_idx;
        this.comment=comment;
        this.date= date;
        this.reco=reco;
        this.state=state;
        this.recomment_idx=recomment_idx;
        this.ranking=ranking;
        flag=0;
        focusflag=0;
    }
}

class commentAdpt extends BaseAdapter {
    @Override
    public long getItemId(int i) {
        return 0;
    }

    RequestQueue requestQueue;
    Context maincon;
    LayoutInflater Inflater;
    ArrayList<commentItem> arsrc;

    int layout;

    public RequestQueue getRequestQueue() {
        requestQueue = Volley.newRequestQueue(maincon.getApplicationContext());
        return requestQueue;
    }


    public commentAdpt(Context context, int alayout, ArrayList<commentItem> aarSrc) {
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

    public void updateReceiptsList(ArrayList<commentItem> arsrc) {
        arsrc = this.arsrc;
        int size = arsrc.size();
        this.notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    private commentAdpt.OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(commentAdpt.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }



    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        if (convertView == null) {
            convertView = Inflater.inflate(layout, parent, false);
        }
        TextView comment = (EditText)convertView.findViewById(R.id.comment);
        LinearLayout reply =(LinearLayout)convertView.findViewById(R.id.reply);
        TextView user =(TextView)convertView.findViewById(R.id.user);
        TextView date =(TextView)convertView.findViewById(R.id.date);
        TextView report = (TextView)convertView.findViewById(R.id.report);
        TextView reco = (TextView)convertView.findViewById(R.id.reco);
        TextView modify = (TextView)convertView.findViewById(R.id.modify);
        EditText edtReply = (EditText)convertView.findViewById(R.id.edtreply);
        Button addReply = (Button)convertView.findViewById(R.id.addReply);
        Button btnReply=(Button)convertView.findViewById(R.id.btnReply);
        ImageButton more=(ImageButton)convertView.findViewById(R.id.more);
        LinearLayout llReco=(LinearLayout)convertView.findViewById(R.id.llReco);
        LinearLayout llReply=(LinearLayout)convertView.findViewById(R.id.llReply);
        comment.setText(arsrc.get(position).comment);
        ImageView best = (ImageView)convertView.findViewById(R.id.best);
        user.setText(arsrc.get(position).id);
        date.setText(arsrc.get(position).date);
        LinearLayout llBack = (LinearLayout)convertView.findViewById(R.id.llBack);

        reco.setText(arsrc.get(position).reco);

        if(arsrc.get(position).user_idx.equals(shared_preferences.get_user_email(maincon.getApplicationContext()))){
            more.setVisibility(View.VISIBLE);
            report.setVisibility(View.INVISIBLE);
        }else{
            more.setVisibility(View.INVISIBLE);
        }

        if(arsrc.get(position).reco.equals("null")){
            reply.setVisibility(View.VISIBLE);
            btnReply.setVisibility(View.INVISIBLE);
            arsrc.get(position).flag=2;
            int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,400 , maincon.getResources().getDisplayMetrics());
            int height = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, maincon.getResources().getDisplayMetrics());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width,height);
            llBack.setLayoutParams(lp);
            notifyDataSetChanged();
            llReco.setVisibility(View.INVISIBLE);
        }else{
            reply.setVisibility(View.GONE);
            btnReply.setVisibility(View.VISIBLE);
            llReco.setVisibility(View.VISIBLE);
            int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,400 , maincon.getResources().getDisplayMetrics());
            int height = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 90, maincon.getResources().getDisplayMetrics());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width,height);
            llBack.setLayoutParams(lp);
        }

        if(arsrc.get(position).state.equals("1")){
            llReco.setBackgroundColor(Color.RED);
        }else{
            llReco.setBackgroundColor(Color.WHITE);
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
                                            if(arsrc.get(position).reco.equals("null")){
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

        btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arsrc.get(position).flag=1;
                notifyDataSetChanged();
            }
        });

        if(arsrc.get(position).flag==0){
            llReply.setVisibility(View.GONE);
            btnReply.setVisibility(View.VISIBLE);
        }else if(arsrc.get(position).flag==1){
            llReply.setVisibility(View.VISIBLE);
            btnReply.setVisibility(View.GONE);
        }else{
            llReply.setVisibility(View.GONE);
            btnReply.setVisibility(View.GONE);
        }

        llReco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position != ListView.INVALID_POSITION) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position);
                        chgCmtReco(arsrc.get(position).comment_idx, position);
                    }
                }
            }
        });


        if(arsrc.get(position).ranking.equals("1")){
            best.setVisibility(View.VISIBLE);
            best.setImageResource(R.drawable.best);
        }else if(arsrc.get(position).ranking.equals("2")||arsrc.get(position).ranking.equals("3")){
            best.setVisibility(View.VISIBLE);
            best.setImageResource(R.drawable.better);
        }else{
            best.setVisibility(View.GONE);
        }

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position != ListView.INVALID_POSITION) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position);
                        more.requestFocus();
                        if(arsrc.get(position).reco.equals("null")){
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




        addReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (position != ListView.INVALID_POSITION) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position);
                        insRecomment(edtReply.getText().toString(), arsrc.get(position).comment_idx);
                        arsrc.get(position).flag = 0;
                        notifyDataSetChanged();

                    }
                }
            }
        });


        return convertView;


    }
    public void insRecomment(String recomment,String comment_idx){
        requestQueue = getRequestQueue();

        //php url 입력
        String URL = "http://172.26.126.172:443/insRecomment.php";

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

    public void chgCmtReco(String comment_idx,int position) {
        requestQueue = getRequestQueue();

        //php url 입력
        String URL = "http://172.26.126.172:443/chgCmtReco.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {



                    jsonObject = new JSONObject(response);

                    Parsing parsing = new Parsing();
                    try {


                        parsing.chgRecoParsing(response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }




                    String state = parsing.state;
                    String reco = parsing.reco;

                    arsrc.get(position).state = state;
                    arsrc.get(position).reco = reco;
                    notifyDataSetChanged();




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
                param.put("user_idx",shared_preferences.get_user_email(maincon.getApplicationContext()));
                param.put("comment_idx",comment_idx);


                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }

    public void delSellComment(String type, String idx){
        requestQueue = getRequestQueue();

        //php url 입력
        String URL = "http://172.26.126.172:443/delComment.php";

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
        String URL = "http://172.26.126.172:443/updateComment.php";

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