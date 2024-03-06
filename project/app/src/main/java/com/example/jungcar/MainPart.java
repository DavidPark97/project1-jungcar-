package com.example.jungcar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

public class MainPart extends AppCompatActivity {
    TextView tvBuy, tvSell, tvRecord, tvCommunity, tvPartBuy, tvPartSell;
    static StringRequest request;
    static RequestQueue requestQueue;
    ImageView imgMy;
    String json;
    ArrayList<partItem> partItems;
    SearchView serFindPart;
    Button btnWheel, btnBlack, btnWash, btnInner, btnOuter, btnTune, btnMirror, btnOther;
    RecyclerView partList;
    String strBrand = "", strCountry = "", strModel = "", strGrade = "", minMilesage = "", maxMilesage = "", minPrice = "", maxPrice = "", isChk = "";
    String strTotal;

    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),MainPage.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpart);

        tvSell = (TextView) findViewById(R.id.tvSell);
        tvBuy = (TextView) findViewById(R.id.tvBuy);
        tvCommunity = (TextView) findViewById(R.id.tvCommunity);
        tvRecord = (TextView) findViewById(R.id.tvRecord);
        tvPartBuy = (TextView) findViewById(R.id.tvPartBuy);
        tvPartSell = (TextView) findViewById(R.id.tvPartSell);
        serFindPart = (SearchView) findViewById(R.id.serFindPart);
        btnWheel = (Button) findViewById(R.id.btnWheel);
        btnBlack = (Button) findViewById(R.id.btnBlack);
        btnWash = (Button) findViewById(R.id.btnWash);
        imgMy = (ImageView)findViewById(R.id.imgMy);
        btnInner = (Button) findViewById(R.id.btnInner);
        btnOuter = (Button) findViewById(R.id.btnOuter);
        btnTune = (Button) findViewById(R.id.btnTune);
        btnMirror = (Button) findViewById(R.id.btnMirror);
        btnOther = (Button) findViewById(R.id.btnOther);
        partList = (RecyclerView) findViewById(R.id.partList);
        if (requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }
        partList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        imgMy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shared_preferences.get_user_email(MainPart.this).isEmpty()==true) {
                    new AlertDialog.Builder(MainPart.this)
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


        tvCommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), Community.class);
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

        tvPartSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SetSellPart.class);
                intent.putExtra("part_type","");
                startActivity(intent);
            }
        });


        setPart();
        serFindPart.setSubmitButtonEnabled(true);
        btnWheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PartSelect.class);
                intent.putExtra("part_type","1");
                startActivity(intent);
            }
        });

        btnBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PartSelect.class);
                intent.putExtra("part_type","2");
                startActivity(intent);
            }
        });

        btnWash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PartSelect.class);
                intent.putExtra("part_type","3");
                startActivity(intent);
            }
        });

        btnInner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PartSelect.class);
                intent.putExtra("part_type","4");
                startActivity(intent);
            }
        });

        btnOuter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PartSelect.class);
                intent.putExtra("part_type","5");
                startActivity(intent);
            }
        });

        btnTune.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PartSelect.class);
                intent.putExtra("part_type","6");
                startActivity(intent);
            }
        });

        btnMirror.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PartSelect.class);
                intent.putExtra("part_type","7");
                startActivity(intent);
            }
        });

        btnOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PartSelect.class);
                intent.putExtra("part_type","8");
                startActivity(intent);
            }
        });

        serFindPart.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(shared_preferences.get_user_email(MainPart.this).isEmpty()==true) {
                    new AlertDialog.Builder(MainPart.this)
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
                    Intent intent = new Intent(getApplicationContext(), PartSelect.class);
                    intent.putExtra("part_type","0");
                    intent.putExtra("query",serFindPart.getQuery().toString());
                    startActivity(intent);

                    startActivity(intent);
                }
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


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
                    int index=0;
                    jsonObject = new JSONObject(response);

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
                            mi = new partItem(idx,name,img,price);
                            partItems.add(mi);
                            strTotal = parsing.total;
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                serFindPart.setQueryHint("총 "+strTotal+"개의 판매부품 검색");
                partAdpt myAdapter= new partAdpt(MainPart.this,partItems);
                partList.setAdapter(myAdapter);
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
    class partItem {
        String idx;
        String name;
        String img;
        String price;
        partItem(String idx, String name, String img, String price){
            this.name = name;
            this.idx = idx;
            this.img = img;
            this.price = price;
        }
    }
    class partAdpt extends RecyclerView.Adapter<partAdpt.ViewHolder> {

        ArrayList<partItem> itemList = new ArrayList<>();
        Context context;
        DecimalFormat decimalFormat = new DecimalFormat("###,###원");

        partAdpt(Context context,ArrayList<partItem> itemList) {
            this.itemList = itemList;
            this.context=context;
        }


        @Override
        public partAdpt.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            // context 와 parent.getContext() 는 같다.
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.partlist, parent, false);
            context = parent.getContext();

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(partAdpt.ViewHolder holder, int position) {
            partItem item = itemList.get(position);
            holder.price.setText(decimalFormat.format(Integer.parseInt(item.price)));
            holder.type.setText(item.name);
            holder.type.setTag(item.idx);
            String imgurl = "http://172.26.126.172:443/"+item.img;
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
                    }
                });
            }
        }
    }