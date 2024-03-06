package com.example.jungcar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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

public class MainPage extends AppCompatActivity {
    TextView tvBuy, tvSell, tvPart, tvRecord;
    TextView tvCommunity;
    static StringRequest request;
    static RequestQueue requestQueue;
    ArrayList<partItem> partItems;
    RecyclerView partList;
    ImageView imgMy;
   SearchView serFindCar;
    TextView tvYear, tvMonth, tvWeek;
    ListView bestList;
    LinearLayout llSearch, llAnaly, llEnroll;
    ArrayList<bestItem> bestItems;
    bestAdpt bestadpt;
    View vWeek,vMonth,vYear;


    String strBrand = "", strCountry = "", strModel = "", strGrade = "", minMilesage = "", maxMilesage = "", minPrice = "", maxPrice = "", isChk = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        tvSell = (TextView) findViewById(R.id.tvSell);
        tvBuy = (TextView) findViewById(R.id.tvBuy);
        tvCommunity = (TextView) findViewById(R.id.tvCommunity);
        tvPart = (TextView) findViewById(R.id.tvPart);
        tvYear = (TextView) findViewById(R.id.tvYear);
        tvMonth = (TextView) findViewById(R.id.tvMonth);
        tvWeek = (TextView) findViewById(R.id.tvWeek);
        partList = (RecyclerView) findViewById(R.id.partList);
        tvRecord = (TextView) findViewById(R.id.tvRecord);
        imgMy = (ImageView)findViewById(R.id.imgMy);
        bestList = (ListView) findViewById(R.id.bestList);
        serFindCar = (SearchView)findViewById(R.id.serFindCar);
        llSearch = (LinearLayout)findViewById(R.id.llSearch);
        llAnaly = (LinearLayout)findViewById(R.id.llAnaly);
        llEnroll = (LinearLayout)findViewById(R.id.llEnroll);
        vWeek = (View)findViewById(R.id.vweek);
        vMonth = (View)findViewById(R.id.vMonth);
        vYear = (View)findViewById(R.id.vYear);

        imgMy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shared_preferences.get_user_email(MainPage.this).isEmpty()==true) {
                    new AlertDialog.Builder(MainPage.this)
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
        requirePerms();
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        partList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        tvCommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), Community.class);
                startActivity(intent);

            }
        });

        llAnaly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Dealer.class);
                startActivity(intent);
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

        serFindCar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(shared_preferences.get_user_email(MainPage.this).isEmpty()==true) {
                    new AlertDialog.Builder(MainPage.this)
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
                    Intent intent = new Intent(getApplicationContext(), NameCond.class);
                    intent.putExtra("strName", serFindCar.getQuery().toString());
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
                    return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        llEnroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shared_preferences.get_user_email(MainPage.this).isEmpty()==true) {
                    new AlertDialog.Builder(MainPage.this)
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
                    Intent intent = new Intent(getApplicationContext(),SetSellCar.class);
                    intent.putExtra("strBrand","");
                    intent.putExtra("strCountry","");
                    intent.putExtra("strMilesage","");
                    intent.putExtra("strGrade","");
                    intent.putExtra("strPrice","");
                    intent.putExtra("strModel","");
                    intent.putExtra("strOption","");
                    intent.putExtra("isChk","n");

                    startActivity(intent);
                }

            }
        });
        setPart();

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
        llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CarSearch.class);
                startActivity(intent);
            }
        });

        bestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(shared_preferences.get_user_email(MainPage.this).isEmpty()==true) {
                    new AlertDialog.Builder(MainPage.this)
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
                    Intent intent = new Intent(getApplicationContext(), BoardRead.class);
                    intent.putExtra("board_idx", bestItems.get(i).idx);
                    intent.putExtra("type", bestItems.get(i).name);
                    startActivity(intent);
                }
            }
        });




    }

    private void requirePerms() {
        String[] permissions = {Manifest.permission.RECEIVE_SMS,
                Manifest.permission.SEND_SMS,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION};
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, permissions, 1);
        }


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
                bestadpt = new bestAdpt(MainPage.this,R.layout.bestlist,bestItems);
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


    public void setPart() {

        //php url 입력
        String URL = "http://172.26.126.172:443/setPart.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {
                    partItems = new ArrayList<partItem>();

                    jsonObject = new JSONObject(response);
                    int index = 0;
                    Parsing parsing = new Parsing();
                    try {
                        partItem mi;
                        while (true) {

                            parsing.mpPartParsing(response, index);
                            if (parsing.name == "") {

                                break;
                            }
                            String name = parsing.name;
                            String idx = parsing.idx;
                            String img = parsing.img;
                            String price = parsing.price;
                            index++;
                            mi = new partItem(idx, name, img, price);
                            partItems.add(mi);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mpPartAdpt mppartadpt = new mpPartAdpt(MainPage.this, partItems);
                partList.setAdapter(mppartadpt);
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
    class mpPartAdpt extends RecyclerView.Adapter<com.example.jungcar.mpPartAdpt.ViewHolder> {

        ArrayList<partItem> itemList = new ArrayList<>();
        Context context;
        DecimalFormat decimalFormat = new DecimalFormat("###,###원");

        mpPartAdpt(Context context, ArrayList<partItem> itemList) {
            this.itemList = itemList;
            this.context = context;
        }


        @Override
        public com.example.jungcar.mpPartAdpt.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            // context 와 parent.getContext() 는 같다.
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.partlist2, parent, false);
            context = parent.getContext();
            return new com.example.jungcar.mpPartAdpt.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(com.example.jungcar.mpPartAdpt.ViewHolder holder, int position) {
            partItem item = itemList.get(position);
            holder.price.setText(decimalFormat.format(Integer.parseInt(item.price)));
            holder.type.setText(item.name);
            holder.type.setTag(item.idx);
            String imgurl = "http://172.26.126.172:443/" + item.img;
            Glide.with(holder.itemView.getContext()).load(imgurl).into(holder.showimg);
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }

        public void setItems(ArrayList<partItem> itemList) {
            this.itemList = itemList;
        }


        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView type, price;
            ImageView showimg;

            public ViewHolder(View itemView) {
                super(itemView);
                type = itemView.findViewById(R.id.type);
                price = itemView.findViewById(R.id.price);
                showimg = itemView.findViewById(R.id.showimg);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context.getApplicationContext(),ShPartItem.class);
                        intent.putExtra("market_idx",type.getTag().toString());
                        context.startActivity(intent);
                        //  Intent intent = new Intent(view.getContext(),MainActivity.class);

                    }
                });
            }
        }
}
