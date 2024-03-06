package com.example.jungcar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PartSelect extends AppCompatActivity {
    TextView tvBuy, tvSell, tvRecord, tvCommunity, tvPartBuy, tvPartSell,tvPart,tvType;
    ImageButton btnToTop;
    Button btnWrite;
    ImageView imgMy;
    static StringRequest request;
    static RequestQueue requestQueue;
    String json;
    ArrayList<partboardItem> partboardItems;
    SearchView serFindPart;
    partboardAdpt partboardadpt;
    ListView boardList;
    String strType, strQuery;
    String strCond, strTotal;
    String strBrand = "", strCountry = "", strModel = "", strGrade = "", minMilesage = "", maxMilesage = "", minPrice = "", maxPrice = "", isChk = "";

    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),MainPart.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partselect);
        tvSell = (TextView) findViewById(R.id.tvSell);

        tvBuy = (TextView) findViewById(R.id.tvBuy);
        tvCommunity = (TextView) findViewById(R.id.tvCommunity);
        tvRecord = (TextView) findViewById(R.id.tvRecord);
        tvPartBuy = (TextView) findViewById(R.id.tvPartBuy);
        tvPartSell = (TextView) findViewById(R.id.tvPartSell);
        serFindPart = (SearchView) findViewById(R.id.serFindPart);
        imgMy = (ImageView)findViewById(R.id.imgMy);
        tvPart = (TextView) findViewById(R.id.tvPart);
        tvType = (TextView) findViewById(R.id.tvType);
        boardList = (ListView)findViewById(R.id.boardList);
        btnToTop = (ImageButton)findViewById(R.id.btnToTop);
        btnWrite = (Button)findViewById(R.id.btnWrite);
        Intent intent = getIntent();
        strType = intent.getExtras().getString("part_type");
        strQuery = intent.getExtras().getString("query","");
        if (requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }


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

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getApplicationContext(),SetSellPart.class);
                intent.putExtra("part_type",strType);
                startActivity(intent);
            }
        });

        serFindPart.setSubmitButtonEnabled(true);

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

        tvPartBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainPart.class);

                startActivity(intent);
            }
        });


        tvPartSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SetSellPart.class);
                intent.putExtra("part_type","");
                startActivity(intent);
            }
        });


        serFindPart.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(shared_preferences.get_user_email(PartSelect.this).isEmpty()==true) {
                    new AlertDialog.Builder(PartSelect.this)
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
                            SearchPartBoard(serFindPart.getQuery().toString());
                }
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        imgMy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shared_preferences.get_user_email(PartSelect.this).isEmpty()==true) {
                    new AlertDialog.Builder(PartSelect.this)
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


        if(strType.equals("0")){
            SearchPartBoard(strQuery);
        }else {
            setPartBoard();
        }
        boardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String idx = boardList.getAdapter().getItem(i).toString();
                Intent intent = new Intent(getApplicationContext(), ShPartItem.class);
                intent.putExtra("market_idx",idx);
                startActivity(intent);
            }
        });

        btnToTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boardList.smoothScrollToPosition(0);
            }
        });
    }

    public void setPartBoard() {

        //php url 입력
        String URL = "http://172.26.126.172:443/setPartBoard.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {
                    partboardItems = new ArrayList<partboardItem>();

                    jsonObject = new JSONObject(response);
                    int index=0;
                    Parsing parsing = new Parsing();
                    try {
                        partboardItem mi;
                        while (true) {

                            parsing.partboardparsing(response, index);
                            if (parsing.name == "") {

                                break;
                            }

                            String subject = parsing.name;
                            String id = parsing.user;
                            String market_idx = parsing.idx;
                            String date = parsing.date;
                            String img = parsing.img;
                            String count = parsing.count;
                            String number = parsing.number;
                            String state = parsing.state;
                            strCond = parsing.cond;
                            strTotal = parsing.total;

                            index++;

                            mi = new partboardItem(subject,state,id,date,img,number,market_idx,count);
                            partboardItems.add(mi);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                partboardadpt = new partboardAdpt(PartSelect.this,R.layout.partboardlist,partboardItems);
                boardList.setAdapter(partboardadpt);

                tvType.setText(strCond);
                serFindPart.setQueryHint("총 "+strTotal+"개의 판매부품 검색");

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
                    param.put("part_type",strType);
                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }

    public void SearchPartBoard(String query) {

        //php url 입력
        String URL = "http://172.26.126.172:443/SearchPartBoard.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {
                    partboardItems = new ArrayList<partboardItem>();

                    jsonObject = new JSONObject(response);
                    int index=0;
                    Parsing parsing = new Parsing();
                    try {
                        partboardItem mi;
                        while (true) {

                            parsing.partSearchparsing(response, index);
                            if (parsing.name == "") {

                                break;
                            }

                            String subject = parsing.name;
                            String id = parsing.user;
                            String market_idx = parsing.idx;
                            String date = parsing.date;
                            String img = parsing.img;
                            String count = parsing.count;
                            String number = parsing.number;
                            String state = parsing.state;

                            index++;

                            mi = new partboardItem(subject,state,id,date,img,number,market_idx,count);
                            partboardItems.add(mi);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                partboardadpt = new partboardAdpt(PartSelect.this,R.layout.partboardlist,partboardItems);
                boardList.setAdapter(partboardadpt);

                tvType.setText("검색결과");
                serFindPart.setVisibility(View.GONE);

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
                param.put("part_type",strType);
                param.put("query",query);
                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }
}
class partboardItem {
    String subject;
    String state;
    String id;
    String date;
    String img;
    String number;
    String market_idx;
    String cnt;

   partboardItem(String subject, String state,String id, String date, String img,String number,String market_idx,String cnt) {
        this.subject=subject;
        this.state=state;
        this.id=id;
        this.date=date;
        this.img = img;
        this.number=number;
        this.market_idx=market_idx;
        this.cnt=cnt;
    }

}
class partboardAdpt extends BaseAdapter {
    @Override
    public long getItemId(int i) {
        return 0;
    }


    Context maincon;
    LayoutInflater Inflater;
    ArrayList<partboardItem> arsrc;
    int layout;


    public partboardAdpt(Context context, int alayout, ArrayList<partboardItem> aarSrc) {
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
        return arsrc.get(position).market_idx;
    }

    public void updateReceiptsList(ArrayList<partboardItem> arsrc) {
        arsrc = this.arsrc;
        int size = arsrc.size();
        this.notifyDataSetChanged();
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        if(convertView == null) {
            convertView = Inflater.inflate(layout,parent,false);
        }
        TextView subject = (TextView)convertView.findViewById(R.id.subject);
        subject.setText(arsrc.get(position).subject);
        subject.setTag(arsrc.get(position).market_idx);



        TextView state = (TextView)convertView.findViewById(R.id.state);
        if(arsrc.get(position).state.equals("null")){
            state.setText("판매");
        }else{
            state.setTextColor(Color.rgb(99,77,00));
            state.setText("완료");
        }

        TextView uid = (TextView)convertView.findViewById(R.id.uid);
        uid.setText(arsrc.get(position).id);

        TextView cnt = (TextView)convertView.findViewById(R.id.cnt);
        cnt.setText(arsrc.get(position).cnt);

        TextView number = (TextView)convertView.findViewById(R.id.number);
        number.setText(arsrc.get(position).number);

        TextView date = (TextView)convertView.findViewById(R.id.date);
        date.setText(arsrc.get(position).date);

        ImageView img = (ImageView)convertView.findViewById(R.id.img);
        String imgurl = "http://172.26.126.172:443/"+arsrc.get(position).img;
        Glide.with(convertView).load(imgurl).into(img);











        return convertView;


    }


}
