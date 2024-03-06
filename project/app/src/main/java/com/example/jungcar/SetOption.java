package com.example.jungcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SetOption extends AppCompatActivity {

    int index=0;
    Button btnEnroll;
    optionAdpt optionadpt;
    String connection_idx,sell_idx,car_idx;
    static StringRequest request;
    static RequestQueue requestQueue;
    String json;
    ListView optionList;
    ArrayList<optionItem> optionItems;
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),MainSell.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setoption);
        Intent intent = getIntent();
        connection_idx = intent.getExtras().getString("connection_idx","");
        sell_idx= intent.getExtras().getString("sell_idx","");
        car_idx= intent.getExtras().getString("car_idx","");

        if (requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }
        optionList = (ListView) findViewById(R.id.optionList);
        btnEnroll = (Button)findViewById(R.id.btnEnroll);
        setSellOption();
        delay();
        delay2();


        btnEnroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionadpt.check(sell_idx);
                Intent intent = new Intent(getApplicationContext(), setImg.class);
                intent.putExtra("car_idx",car_idx);
                startActivity(intent);
            }
        });

    }

    public void delay(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                optionList.smoothScrollToPosition(3);

            }
        },1000);
    }
    public void delay2(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                optionList.smoothScrollToPosition(0);

            }
        },3000);
    }


    public void setSellOption() {

        //php url 입력
        String URL = "http://172.26.126.172:443/setSellOption.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {
                    optionItems = new ArrayList<optionItem>();

                    jsonObject = new JSONObject(response);

                    Parsing parsing = new Parsing();
                    try {
                        optionItem mi;
                        while (true) {

                            parsing.optionparsing(response, index);
                            if (parsing.name == "") {

                                break;
                            }


                            String name = parsing.name;

                            index++;

                            mi = new optionItem(name,parsing.idx,connection_idx);
                            optionItems.add(mi);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                optionadpt = new optionAdpt(SetOption.this,R.layout.inneroption,optionItems);
                optionList.setAdapter(optionadpt);
                index=0;
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

class optionItem {
    String name;
    String idx;
    String connection_idx;

    int chk;
    optionItem(String name,String idx,String connection_idx) {
        this.name=name;
        this.idx=idx;
        this.connection_idx=connection_idx;

    }
    optionItem(String name,String idx,int chk) {
        this.name=name;
        this.idx=idx;
        this.chk=chk;

    }

}



class optionAdpt extends BaseAdapter {
    @Override
    public long getItemId(int i) {
        return 0;
    }
    innerAdpt inneradpt[] = new innerAdpt[6];
    ArrayList<optionItem>[] optionItems = new ArrayList[6];

    RequestQueue requestQueue;
    Context maincon;
    LayoutInflater Inflater;
    ArrayList<optionItem> arsrc;
    int index=0;
    int layout;

    public RequestQueue getRequestQueue() {
        requestQueue = Volley.newRequestQueue(maincon.getApplicationContext());
        return requestQueue;
    }


    public optionAdpt(Context context, int alayout, ArrayList<optionItem> aarSrc) {
        maincon = context;
        Inflater= (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        arsrc = aarSrc;
        layout = alayout;

    }
    public int getCount() {
        return arsrc.size();
    }

    public void check(String sell_idx){
        int tmp = getCount();
        for(int i=0;i<tmp;i++){
            inneradpt[i].insert(sell_idx);
        }
    }

    public String getItem(int position) {
        return arsrc.get(position).name;
    }

    private class ViewHolder {
        TextView name;
        ListView list;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        ViewHolder holder;

        if(convertView == null) {
            convertView = Inflater.inflate(layout,parent,false);
            holder = new ViewHolder();

            holder.name = convertView.findViewById(R.id.category);
            holder.list = convertView.findViewById(R.id.innerlist);
            for(int j=1;j<=5;j++) {
                setInner(holder.list, parent,Integer.toString(j));
            }


            convertView.setTag(holder);

            holder.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(maincon.getApplicationContext(),holder.list.getAdapter().getItem(i).toString(),Toast.LENGTH_SHORT).show();
                }
            });
        }else {

            holder = (ViewHolder) convertView.getTag();
            holder.list.setAdapter(inneradpt[Integer.parseInt(arsrc.get(position).idx)-1]);
        }
        holder.list.setAdapter(inneradpt[Integer.parseInt(arsrc.get(position).idx)-1]);
        holder.name.setText(arsrc.get(position).name);

        return convertView;


    }



    public void setInner(ListView lvlist, ViewGroup parent, String idx){
        requestQueue = getRequestQueue();

        //php url 입력
        String URL = "http://172.26.126.172:443/setInner.php";
        index=0;
        int pos ;
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {
                    optionItems[Integer.parseInt(idx)-1] = new ArrayList<optionItem>();

                    jsonObject = new JSONObject(response);

                    Parsing parsing = new Parsing();
                    try {
                        optionItem mi;
                        while (true) {

                            parsing.optionparsing(response, index);
                            if (parsing.name == "") {

                                break;
                            }


                            String name = parsing.name;
                            String indx = parsing.idx;

                            index++;

                            mi = new optionItem(name,indx,0);
                            optionItems[Integer.parseInt(idx)-1].add(mi);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                inneradpt[Integer.parseInt(idx)-1] = new innerAdpt(parent.getContext(),R.layout.innerlist,optionItems[Integer.parseInt(idx)-1]);

                index=0;
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
                param.put("connection_idx",arsrc.get(1).connection_idx);
                param.put("option_type_idx",idx);
                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);



    }



}

class innerAdpt extends BaseAdapter {
    @Override
    public long getItemId(int i) {
        return 0;
    }
    Context maincon;
    LayoutInflater Inflater;
    ArrayList<optionItem> arsrc;
    RequestQueue requestQueue;
    int index=0;
    int layout;



    public innerAdpt(Context context, int alayout, ArrayList<optionItem> aarSrc) {
        maincon = context;
        Inflater= (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        arsrc = aarSrc;
        layout = alayout;


    }
    public int getCount() {
        return arsrc.size();
    }

    public void getchk(int chk, CheckBox chkopt){
        if (chk==1){
            chkopt.setChecked(true);
        }else{
            chkopt.setChecked(false);
        }
    }


    public String getItem(int position) {
        return arsrc.get(position).name;
    }

    private class ViewHolder {
        TextView opt;
        CheckBox chkopt;
        int isChk;
    }


    public RequestQueue getRequestQueue() {
        requestQueue = Volley.newRequestQueue(maincon.getApplicationContext());
        return requestQueue;
    }

    public void insert(String sell_idx){
        int tmp = getCount();
        for (int i=0;i<tmp;i++){
            if(arsrc.get(i).chk==1){
                insOption(arsrc.get(i).idx,sell_idx);

            }
        }
    }
    public void insOption(String option_idx, String sell_idx){

        requestQueue = getRequestQueue();

        //php url 입력
        String URL = "http://172.26.126.172:443/insOption.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {


                    jsonObject = new JSONObject(response);



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
                param.put("option_idx",option_idx);
                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);

    }



    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        ViewHolder holder;

        if(convertView == null) {
            convertView = Inflater.inflate(layout,parent,false);
            holder = new ViewHolder();
            holder.opt = convertView.findViewById(R.id.opt);
            holder.chkopt = convertView.findViewById(R.id.chkopt);
            holder.isChk = 0;
            holder.chkopt.setChecked(((ListView)parent).isItemChecked(position));
            holder.chkopt.setFocusable(false);

            convertView.setTag(holder);



        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.opt.setText(arsrc.get(position).name);
        getchk(arsrc.get(position).chk, holder.chkopt);

        if(arsrc.get(position).chk==1){
            holder.chkopt.setChecked(true);
        }else{
            holder.chkopt.setChecked(false);
        }

        holder.chkopt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    arsrc.get(position).chk=1;
                    holder.chkopt.setChecked(true);
                    notifyDataSetChanged();
                }else{
                    arsrc.get(position).chk=0;
                    holder.chkopt.setChecked(false);
                    notifyDataSetChanged();
                }

            }

        });
        return convertView;
    }
}