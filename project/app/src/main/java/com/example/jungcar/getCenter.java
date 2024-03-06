package com.example.jungcar;

import android.app.Activity ;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class getCenter extends Activity {
    static StringRequest request;
    static RequestQueue requestQueue;
    String json;
    ListView centerList;
    Button btnFind;
    EditText edtName;
     String center_idx,strName;
    Button btnCancel, btnSelect;
    ArrayList<adressItem> adressItems;
    adressAdpt adressadpt;
    int flag=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.getcenter);
        centerList = (ListView) findViewById(R.id.centerList);
        btnFind = (Button) findViewById(R.id.btnFind);
        edtName = (EditText) findViewById(R.id.edtName);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnSelect = (Button) findViewById(R.id.btnSelect);

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCenterList();
            }
        });

        centerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                strName=adressItems.get(i).name;
                center_idx=adressItems.get(i).idx;
                flag=0;
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==1){
                    Toast.makeText(getApplicationContext(),"아이템을 선택하세요",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent();
                    intent.putExtra("name",strName);
                    intent.putExtra("idx",center_idx);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }

    public void getCenterList() {
        String URL = "http://172.26.126.172:443/getCenterList.php";

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {


            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {
                    adressItems = new ArrayList<adressItem>();

                    jsonObject = new JSONObject(response);

                    Parsing parsing = new Parsing();
                    try {
                        int index=0;
                        adressItem mi;
                        while (true) {

                            parsing.getCenterListParsing(response, index);
                            if (parsing.name == "") {

                                break;
                            }

                            String idx = parsing.idx;
                            String name = parsing.name;

                            String address = parsing.address;

                            index++;
                            mi = new adressItem(name,address,idx);
                            adressItems.add(mi);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adressadpt = new adressAdpt(getCenter.this,R.layout.getcenterlist,adressItems);
                centerList.setAdapter(adressadpt);





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
                param.put("name",edtName.getText().toString());

                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);

    }

}

class adressItem {
    String name;
    String address;
    String idx;
    adressItem(String name,String address, String idx) {
        this.name=name;

        this.address=address;

        this.idx=idx;


    }
}

class adressAdpt extends BaseAdapter {
    @Override
    public long getItemId(int i) {
        return 0;
    }

    Context maincon;
    LayoutInflater Inflater;
    ArrayList<adressItem> arsrc;
    int layout;

    public adressAdpt(Context context, int alayout, ArrayList<adressItem> aarSrc) {
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
        return arsrc.get(position).name;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        if (convertView == null) {
            convertView = Inflater.inflate(layout, parent, false);
        }

        TextView name = (TextView)convertView.findViewById(R.id.name);
        name.setText(arsrc.get(position).name);
        name.setTag(arsrc.get(position).idx);

        TextView address = (TextView)convertView.findViewById(R.id.address);
        address.setText(arsrc.get(position).address);

        return convertView;


    }

}

