package com.example.jungcar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class SelectManage extends AppCompatActivity {
    ImageButton btnBack;
    TextView tvSelected,tvOk;
    ListView manageList;

    String strSelect = "";
    int count = 0;
    DecimalFormat decimalFormat = new DecimalFormat("###,###");
    static StringRequest request;
    static RequestQueue requestQueue;
    String json;
    manageAdpt manageadpt;
    ArrayList<manageItem> manageItems;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectmanage);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        tvSelected = (TextView) findViewById(R.id.tvSelected);
        tvOk = (TextView) findViewById(R.id.tvOk);
        manageList = (ListView) findViewById(R.id.manageList);


        if (requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }

        selectManage();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cnt = manageList.getAdapter().getCount();
                String tmp = "";
                if (count==0) {
                Toast.makeText(getApplicationContext(),"선택된 항목이 없습니다",Toast.LENGTH_SHORT).show();
                }else{
                    for (int i = 0; i < cnt; i++) {
                        if (manageadpt.arsrc.get(i).chk == true) {
                            tmp += manageadpt.arsrc.get(i).type + ",";
                        }

                    }
                    tmp = tmp.substring(0, tmp.length() - 1);
                    Intent intent = new Intent(getApplicationContext(), AddMaintain.class);
                    intent.putExtra("selected", tmp);
                    startActivity(intent);
                }
            }
        });
    }



    public void selectManage() {

        //php url 입력
        String URL = "http://172.26.126.172:443/selectManage.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {
                    manageItems = new ArrayList<manageItem>();
                    int index=0;
                    jsonObject = new JSONObject(response);

                    Parsing parsing = new Parsing();
                    try {
                        manageItem mi;
                        while (true) {

                            parsing.manageParsing(response, index);
                            if (parsing.name == "") {

                                break;
                            }


                            String name = parsing.name;
                            String duration = parsing.date;
                            String dist = decimalFormat.format(Integer.parseInt(parsing.distance));
                            String disting = parsing.detail;
                            String img = parsing.img;
                            String type = parsing.idx;
                            String detail;

                            if(duration.equals("0")){
                                if(name.equals("타이어")) {
                                    detail = dist + "km부터 " + disting;
                                }else {
                                    detail = dist + "km 마다" + disting;
                                }
                            }else{
                                detail = dist+"km | " + duration+"개월 마다 "+disting;

                            }
                            index++;

                            mi = new manageItem(name,detail,type,img);
                            manageItems.add(mi);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                manageadpt = new manageAdpt(SelectManage.this,R.layout.selectmanagelist,manageItems);
                manageadpt.setOnItemClickListener(new manageAdpt.OnItemClickListener() {
                    @Override
                    public void onItemClick(int pos) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                count = 0;
                                for (int i = 0; i < manageItems.size(); i++) {
                                    if (manageItems.get(i).chk) {
                                        count++;
                                    }
                                }
                                tvSelected.setText(count+"개 선택");
                            }


                        }, 100);



                    }
                });

                manageList.setAdapter(manageadpt);

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
                param.put("selected",strSelect);
                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);



    }
}
class manageItem {

    String img;
    String type;
    String name;
    String detail;
    boolean chk = false;

    manageItem(String name, String detail, String type, String img) {
        this.name = name;
        this.detail=detail;
        this.type=type;
        this.img=img;
        chk = false;

    }

}
class manageAdpt extends BaseAdapter {
    @Override
    public long getItemId(int i) {
        return 0;
    }


    Context maincon;
    LayoutInflater Inflater;
    ArrayList<manageItem> arsrc;
    int layout;


    public manageAdpt(Context context, int alayout, ArrayList<manageItem> aarSrc) {
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
        return arsrc.get(position).name;
    }

    public void updateReceiptsList(ArrayList<manageItem> arsrc) {
        arsrc = this.arsrc;
        int size = arsrc.size();
        this.notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    private manageAdpt.OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(manageAdpt.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    public boolean ischekced(int position){
        return arsrc.get(position).chk;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        if(convertView == null) {
            convertView = Inflater.inflate(layout,parent,false);
        }
        TextView subject = (TextView)convertView.findViewById(R.id.name);
        subject.setText(arsrc.get(position).name);
        subject.setTag(arsrc.get(position).type);
        TextView detail = (TextView)convertView.findViewById(R.id.detail);
        detail.setText(arsrc.get(position).detail);
        ImageView img = (ImageView)convertView.findViewById(R.id.img);
        String imgurl = "http://172.26.126.172:443/maintain/"+arsrc.get(position).img;
        Glide.with(convertView).load(imgurl).into(img);
        CheckBox chk = (CheckBox)convertView.findViewById(R.id.chk);


        chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position != ListView.INVALID_POSITION) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position);
                        boolean state = !arsrc.get(position).chk;
                        arsrc.get(position).chk=state;
                    }
                        notifyDataSetChanged();
                    }
                }

        });

        chk.setChecked(ischekced(position));
        chk.setFocusable(false);




        return convertView;


    }


}