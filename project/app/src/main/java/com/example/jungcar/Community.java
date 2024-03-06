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
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;
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

public class Community extends AppCompatActivity {
    TextView tvBuy, tvSell, tvPart, tvRecord;
    TextView tvCommunity;
    TextView tvYear, tvMonth, tvWeek;
    SearchView serFindQuery;
    ListView bestList;
    ArrayList<bestItem> bestItems;
    bestAdpt bestadpt;
    ImageView imgMy;
    View vWeek,vMonth,vYear;


    Button btnFree, btnQuery, btnBlack, btnImg, btnNews, btnAccident, btnTune,btnIntro,btnSujestion;
    static StringRequest request;
    static RequestQueue requestQueue;

    String strBrand = "", strCountry = "", strModel = "", strGrade = "", minMilesage = "", maxMilesage = "", minPrice = "", maxPrice = "", isChk = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community);
        tvSell = (TextView) findViewById(R.id.tvSell);
        tvBuy = (TextView) findViewById(R.id.tvBuy);
        tvCommunity = (TextView) findViewById(R.id.tvCommunity);
        tvPart = (TextView) findViewById(R.id.tvPart);
        tvRecord = (TextView) findViewById(R.id.tvRecord);
        tvYear = (TextView) findViewById(R.id.tvYear);
        tvMonth = (TextView) findViewById(R.id.tvMonth);
        tvWeek = (TextView) findViewById(R.id.tvWeek);
        serFindQuery = (SearchView) findViewById(R.id.serFindQuery);
        btnFree = (Button) findViewById(R.id.btnFree);
        btnQuery = (Button)findViewById(R.id.btnQuery);
        btnBlack = (Button) findViewById(R.id.btnBlack);
        btnImg = (Button) findViewById(R.id.btnImg);
        btnNews =(Button) findViewById(R.id.btnNews);
        btnAccident = (Button) findViewById(R.id.btnAccident);
        btnTune = (Button) findViewById(R.id.btnTune);
        btnIntro = (Button) findViewById(R.id.btnIntro);
        btnSujestion = (Button) findViewById(R.id.btnSujestion);
        bestList = (ListView) findViewById(R.id.bestList);
        imgMy = (ImageView) findViewById(R.id.imgMy);
        vWeek = (View)findViewById(R.id.vweek);
        vMonth = (View)findViewById(R.id.vMonth);
        vYear = (View)findViewById(R.id.vYear);
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        imgMy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shared_preferences.get_user_email(Community.this).isEmpty()==true) {
                    new AlertDialog.Builder(Community.this)
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


        btnFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Board.class);
                intent.putExtra("board_type","2");
                startActivity(intent);
            }
        });
        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Board.class);
                intent.putExtra("board_type","3");
                startActivity(intent);
            }
        });
        btnBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Board.class);
                intent.putExtra("board_type","4");
                startActivity(intent);
            }

        }); btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Board.class);
                intent.putExtra("board_type","5");
                startActivity(intent);
            }
        });
        btnNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Board.class);
                intent.putExtra("board_type","6");
                startActivity(intent);
            }
        });
        btnAccident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Board.class);
                intent.putExtra("board_type","7");
                startActivity(intent);
            }
        });
        btnTune.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Board.class);
                intent.putExtra("board_type","8");
                startActivity(intent);
            }
        });
        btnIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Board.class);
                intent.putExtra("board_type","9");
                startActivity(intent);
            }
        });
        btnSujestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Board.class);
                intent.putExtra("board_type","10");
                startActivity(intent);
            }
        });

        setBestList("week");
        tvWeek.setBackgroundColor(Color.parseColor("#3fef77"));

        tvWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvMonth.setBackgroundColor(Color.parseColor("#ffffff"));
                tvWeek.setBackgroundColor(Color.parseColor("#3fef77"));
                tvYear.setBackgroundColor(Color.parseColor("#ffffff"));
                vWeek.setVisibility(View.VISIBLE);
                vMonth.setVisibility(View.INVISIBLE);
                vYear.setVisibility(View.INVISIBLE);

                setBestList("week");
            }
        });

        tvMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvWeek.setBackgroundColor(Color.parseColor("#ffffff"));
                tvMonth.setBackgroundColor(Color.parseColor("#3fef77"));
                tvYear.setBackgroundColor(Color.parseColor("#ffffff"));
                vWeek.setVisibility(View.INVISIBLE);
                vMonth.setVisibility(View.VISIBLE);
                vYear.setVisibility(View.INVISIBLE);


                setBestList("month");
            }
        });

        tvYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvWeek.setBackgroundColor(Color.parseColor("#ffffff"));
                tvYear.setBackgroundColor(Color.parseColor("#3fef77"));
                tvMonth.setBackgroundColor(Color.parseColor("#ffffff"));
                vWeek.setVisibility(View.INVISIBLE);
                vMonth.setVisibility(View.INVISIBLE);
                vYear.setVisibility(View.VISIBLE);


                setBestList("year");
            }
        });
        bestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),BoardRead.class);
                intent.putExtra("board_idx",bestItems.get(i).idx);
                intent.putExtra("type",bestItems.get(i).name);
                startActivity(intent);
            }
        });
        serFindQuery.setSubmitButtonEnabled(true);
        serFindQuery.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getApplicationContext(),BoardSearch.class);
                intent.putExtra("board_type","0");
                intent.putExtra("condition","subject");
                intent.putExtra("query",serFindQuery.getQuery().toString());
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void setBestList(String type) {

        //php url 입력
        String URL = "http://172.26.126.172:443/setBestList.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {

                    bestItems = new ArrayList<bestItem>();

                    jsonObject = new JSONObject(response);

                    Parsing parsing = new Parsing();
                    try {
                        int index=0;
                        bestItem mi;
                        while (true) {

                            parsing.bestParsing(response, index);
                            if (parsing.name == "") {
                                break;
                            }

                            String idx = parsing.idx;
                            String subject = parsing.name;
                            String name = parsing.type;
                            String count = parsing.count;
                            String ranking = parsing.ranking;
                            index++;

                            mi = new bestItem(idx,subject,name,count,ranking);
                            bestItems.add(mi);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                bestadpt = new bestAdpt(Community.this,R.layout.bestlist,bestItems);
                bestList.setAdapter(bestadpt);

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
                param.put("type",type);

                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }


}
class bestItem {
    String idx;
    String subject;
    String name;
    String count;
    String ranking;

    bestItem(String idx,String subject, String name, String count,String ranking) {
        this.idx=idx;
        this.subject = subject;
        this.name = name;
        this.count = count;
        this.ranking = ranking;
    }

}

class bestAdpt extends BaseAdapter {
    @Override
    public long getItemId(int i) {
        return 0;
    }

    RequestQueue requestQueue;
    Context maincon;
    LayoutInflater Inflater;
    ArrayList<bestItem> arsrc;

    int layout;

    public RequestQueue getRequestQueue() {
        requestQueue = Volley.newRequestQueue(maincon.getApplicationContext());
        return requestQueue;
    }


    public bestAdpt(Context context, int alayout, ArrayList<bestItem> aarSrc) {
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

    public void updateReceiptsList(ArrayList<bestItem> arsrc) {
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

        TextView rank = (TextView)convertView.findViewById(R.id.rank);
        rank.setText(arsrc.get(position).ranking);

        TextView count = (TextView)convertView.findViewById(R.id.count);
        count.setText(arsrc.get(position).count);




        return convertView;


    }


}