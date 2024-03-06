package com.example.jungcar;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board extends AppCompatActivity {
    TextView tvBuy, tvSell, tvPart, tvRecord;
    TextView tvCommunity, tvRecent, tvNotice, tvBest;
    Button btnFir,btnSec,btnThi,btnFour,btnFifth, btnNext,btnWrite;
    ImageButton btnFind;
    EditText edtQuery;
    Spinner spinBrdType,spinCond;
    String [] strType = new String[9];
    String [] strTypeidx = new String[9];
    LinearLayout llType;
    ListView boardList;
    ImageView imgMy;
    static StringRequest request;
    static RequestQueue requestQueue;
    ArrayList<boardItem> boardItems;
    boardAdpt boardadpt;

    String strSelected;
    String strBrand = "", strCountry = "", strModel = "", strGrade = "", minMilesage = "", maxMilesage = "", minPrice = "", maxPrice = "", isChk = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board);
        tvSell = (TextView) findViewById(R.id.tvSell);
        tvBuy = (TextView) findViewById(R.id.tvBuy);
        tvCommunity = (TextView) findViewById(R.id.tvCommunity);
        tvPart = (TextView) findViewById(R.id.tvPart);
        tvRecord = (TextView) findViewById(R.id.tvRecord);
        tvRecent = (TextView)findViewById(R.id.tvRecent);
        tvNotice = (TextView)findViewById(R.id.tvNotice);
        tvBest = (TextView)findViewById(R.id.tvBest);
        spinBrdType = (Spinner) findViewById(R.id.spinBrdType);
        llType = (LinearLayout)findViewById(R.id.llType);
        btnFir = (Button)findViewById(R.id.btnFir);
        btnSec = (Button)findViewById(R.id.btnSec);
        btnThi = (Button)findViewById(R.id.btnThi);
        btnFour = (Button)findViewById(R.id.btnFour);
        btnFifth = (Button)findViewById(R.id.btnFifth);
        btnNext = (Button)findViewById(R.id.btnNext);
        btnWrite = (Button)findViewById(R.id.btnWrite);
        spinCond = (Spinner)findViewById(R.id.spinCond);
        boardList = (ListView)findViewById(R.id.boardList);
        btnFind = (ImageButton)findViewById(R.id.btnFind);
        edtQuery = (EditText)findViewById(R.id.edtQuery);
        imgMy = (ImageView)findViewById(R.id.imgMy);


        imgMy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shared_preferences.get_user_email(Board.this).isEmpty()==true) {
                    new AlertDialog.Builder(Board.this)
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
        spinBrdType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                btnWrite.setTag(strTypeidx[i]);
                strSelected = strTypeidx[i];
                btnFir.setText("1");
                btnSec.setText("2");
                btnThi.setText("3");
                btnFour.setText("4");
                btnFifth.setText("5");
                btnFir.performClick();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),BoardWrite.class);
                intent.putExtra("board_type",btnWrite.getTag().toString());
                startActivity(intent);
            }
        });


        Intent intent = getIntent();
        strSelected= intent.getExtras().getString("board_type");

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        List<String> pro = Arrays.asList(getResources().getStringArray(R.array.board));
        ArrayAdapter adp = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_dropdown_item,pro);
        spinCond.setAdapter(adp);

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

        tvRecent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvRecent.setBackgroundColor(Color.parseColor("#ffffff"));
                tvRecent.setTextColor(Color.parseColor("#0000ff"));
                tvBest.setBackgroundResource(R.drawable.grayedge);
                tvBest.setTextColor(Color.parseColor("#666666"));
                tvNotice.setBackgroundResource(R.drawable.grayedge);
                tvNotice.setTextColor(Color.parseColor("#666666"));
                llType.setVisibility(View.VISIBLE);
            }
        });

        tvNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvNotice.setBackgroundColor(Color.parseColor("#ffffff"));
                tvNotice.setTextColor(Color.parseColor("#0000ff"));
                tvBest.setBackgroundResource(R.drawable.grayedge);
                tvBest.setTextColor(Color.parseColor("#666666"));
                tvRecent.setBackgroundResource(R.drawable.grayedge);
                tvRecent.setTextColor(Color.parseColor("#666666"));
                llType.setVisibility(View.GONE);
            }
        });

        tvBest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvBest.setBackgroundColor(Color.parseColor("#ffffff"));
                tvBest.setTextColor(Color.parseColor("#0000ff"));
                tvRecent.setBackgroundResource(R.drawable.grayedge);
                tvRecent.setTextColor(Color.parseColor("#666666"));
                tvNotice.setBackgroundResource(R.drawable.grayedge);
                tvNotice.setTextColor(Color.parseColor("#666666"));
                llType.setVisibility(View.GONE);
            }
        });

        btnFir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnFir.setBackgroundColor(Color.parseColor("#0088ee"));
                btnFir.setTextColor(Color.parseColor("#ffffff"));
                btnSec.setBackgroundResource(R.drawable.tablesquare);
                btnSec.setTextColor(Color.parseColor("#000000"));
                btnThi.setBackgroundResource(R.drawable.tablesquare);
                btnThi.setTextColor(Color.parseColor("#000000"));
                btnFour.setBackgroundResource(R.drawable.tablesquare);
                btnFour.setTextColor(Color.parseColor("#000000"));
                btnFifth.setBackgroundResource(R.drawable.tablesquare);
                btnFifth.setTextColor(Color.parseColor("#000000"));

                setBoardList(strSelected,btnFir.getText().toString());
            }
        });

        btnSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSec.setBackgroundColor(Color.parseColor("#0088ee"));
                btnSec.setTextColor(Color.parseColor("#ffffff"));
                btnFir.setBackgroundResource(R.drawable.tablesquare);
                btnFir.setTextColor(Color.parseColor("#000000"));
                btnThi.setBackgroundResource(R.drawable.tablesquare);
                btnThi.setTextColor(Color.parseColor("#000000"));
                btnFour.setBackgroundResource(R.drawable.tablesquare);
                btnFour.setTextColor(Color.parseColor("#000000"));
                btnFifth.setBackgroundResource(R.drawable.tablesquare);
                btnFifth.setTextColor(Color.parseColor("#000000"));
                setBoardList(strSelected,btnSec.getText().toString());
            }
        });

        btnThi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnThi.setBackgroundColor(Color.parseColor("#0088ee"));
                btnThi.setTextColor(Color.parseColor("#ffffff"));
                btnSec.setBackgroundResource(R.drawable.tablesquare);
                btnSec.setTextColor(Color.parseColor("#000000"));
                btnFir.setBackgroundResource(R.drawable.tablesquare);
                btnFir.setTextColor(Color.parseColor("#000000"));
                btnFour.setBackgroundResource(R.drawable.tablesquare);
                btnFour.setTextColor(Color.parseColor("#000000"));
                btnFifth.setBackgroundResource(R.drawable.tablesquare);
                btnFifth.setTextColor(Color.parseColor("#000000"));
                setBoardList(strSelected,btnThi.getText().toString());
            }
        });

        btnFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnFour.setBackgroundColor(Color.parseColor("#0088ee"));
                btnFour.setTextColor(Color.parseColor("#ffffff"));
                btnSec.setBackgroundResource(R.drawable.tablesquare);
                btnSec.setTextColor(Color.parseColor("#000000"));
                btnThi.setBackgroundResource(R.drawable.tablesquare);
                btnThi.setTextColor(Color.parseColor("#000000"));
                btnFir.setBackgroundResource(R.drawable.tablesquare);
                btnFir.setTextColor(Color.parseColor("#000000"));
                btnFifth.setBackgroundResource(R.drawable.tablesquare);
                btnFifth.setTextColor(Color.parseColor("#000000"));

                setBoardList(strSelected,btnFour.getText().toString());
            }
        });

        btnFifth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnFifth.setBackgroundColor(Color.parseColor("#0088ee"));
                btnFifth.setTextColor(Color.parseColor("#ffffff"));
                btnSec.setBackgroundResource(R.drawable.tablesquare);
                btnSec.setTextColor(Color.parseColor("#000000"));
                btnThi.setBackgroundResource(R.drawable.tablesquare);
                btnThi.setTextColor(Color.parseColor("#000000"));
                btnFour.setBackgroundResource(R.drawable.tablesquare);
                btnFour.setTextColor(Color.parseColor("#000000"));
                btnFir.setBackgroundResource(R.drawable.tablesquare);
                btnFir.setTextColor(Color.parseColor("#000000"));
                setBoardList(strSelected,btnFifth.getText().toString());
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnFir.setText(Integer.toString(Integer.parseInt(btnFir.getText().toString())+5));
                btnSec.setText(Integer.toString(Integer.parseInt(btnSec.getText().toString())+5));
                btnThi.setText(Integer.toString(Integer.parseInt(btnThi.getText().toString())+5));
                btnFour.setText(Integer.toString(Integer.parseInt(btnFour.getText().toString())+5));
                btnFifth.setText(Integer.toString(Integer.parseInt(btnFifth.getText().toString())+5));
                btnFir.performClick();
            }
        });
        setBoardType();
        setBoardList(strSelected,"1");

        boardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),BoardRead.class);
                intent.putExtra("board_idx",boardItems.get(i).idx);
                intent.putExtra("type",spinBrdType.getSelectedItem().toString());
                startActivity(intent);
            }
        });

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cond;
                if(spinCond.getSelectedItemPosition()==0){
                    cond = "subject";
                }else if(spinCond.getSelectedItemPosition()==1){
                    cond = "id";
                }else{
                    cond = "content";
                }

                Intent intent = new Intent(getApplicationContext(),BoardSearch.class);
                intent.putExtra("board_type",spinBrdType.getSelectedItem().toString());
                intent.putExtra("condition",cond);
                intent.putExtra("query",edtQuery.getText().toString());
                startActivity(intent);
            }
        });

    }

    public void setBoardList(String board_type, String page) {

        //php url 입력
        String URL = "http://172.26.126.172:443/setBoardList.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {

                    boardItems = new ArrayList<boardItem>();

                    jsonObject = new JSONObject(response);

                    Parsing parsing = new Parsing();
                    try {
                        int index=0;
                        boardItem mi;
                        while (true) {

                            parsing.boardParsing(response, index);
                            if (parsing.idx == "") {
                                break;
                            }


                            String subject = parsing.name;
                            String user = parsing.user;
                            String cmtcnt = parsing.count;
                            String shnum = parsing.number;
                            String idx = parsing.idx;
                            String date =parsing.date;
                            index++;

                            mi = new boardItem(subject,user,cmtcnt,shnum,idx,date);
                            boardItems.add(mi);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                boardadpt = new boardAdpt(Board.this,R.layout.boardlist,boardItems);
                boardList.setAdapter(boardadpt);

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
                param.put("board_type",board_type);
                param.put("page",Integer.toString(((Integer.parseInt(page))-1)*10));

                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }

    public void setBoardType() {

        //php url 입력
        String URL = "http://172.26.126.172:443/setBoardType.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {

                    int tmp =0;
                    jsonObject = new JSONObject(response);

                    Parsing parsing = new Parsing();
                    try {

                        int index = 0;
                        partboardItem mi;
                        while (true) {

                            parsing.BoardTypeParsing(response, index);
                            if (parsing.name == "") {
                                break;
                            }
                            strType[index]=parsing.name;
                            strTypeidx[index]=parsing.idx;

                            if(strSelected.equals(parsing.idx)){
                                tmp = index;

                            }


                            index++;


                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_spinner_dropdown_item,strType);
                    spinBrdType.setAdapter(adapter);
                    spinBrdType.setSelection(tmp);

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
                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }

}
class boardItem {
    String subject;
    String user;
    String cmtcnt;
    String shnum;
    String idx;
    String date;

    boardItem(String subject, String user, String cmtcnt,String shnum, String idx, String date) {
            this.subject = subject;
            this.user = user;
            this.cmtcnt = cmtcnt;
            this.shnum = shnum;
            this.idx = idx;
            this.date = date;
    }

}

class boardAdpt extends BaseAdapter {
    @Override
    public long getItemId(int i) {
        return 0;
    }

    RequestQueue requestQueue;
    Context maincon;
    LayoutInflater Inflater;
    ArrayList<boardItem> arsrc;

    int layout;

    public RequestQueue getRequestQueue() {
        requestQueue = Volley.newRequestQueue(maincon.getApplicationContext());
        return requestQueue;
    }


    public boardAdpt(Context context, int alayout, ArrayList<boardItem> aarSrc) {
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

    public void updateReceiptsList(ArrayList<boardItem> arsrc) {
        arsrc = this.arsrc;
        int size = arsrc.size();
        this.notifyDataSetChanged();
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        if (convertView == null) {
            convertView = Inflater.inflate(layout, parent, false);
        }
        TextView subject = (TextView)convertView.findViewById(R.id.subject);
        subject.setText(arsrc.get(position).subject);
        TextView user = (TextView)convertView.findViewById(R.id.user);
        user.setText(arsrc.get(position).user);
        TextView date = (TextView)convertView.findViewById(R.id.date);
        date.setText(arsrc.get(position).date);
        TextView cmtcnt = (TextView)convertView.findViewById(R.id.cmtcnt);
        cmtcnt.setText(arsrc.get(position).cmtcnt);
        TextView shnum = (TextView)convertView.findViewById(R.id.shnum);
        shnum.setText(arsrc.get(position).shnum);



        return convertView;


    }


}