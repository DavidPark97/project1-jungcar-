package com.example.jungcar;

import android.app.Activity;
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

public class QnA extends Activity {

    Button btnClose;
    ListView qnaList;
    ArrayList<qnaItem> qnaItems;
    static StringRequest request;
    static RequestQueue requestQueue;
    String json;
    qnaAdpt qnaadpt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.qna);
        btnClose = (Button) findViewById(R.id.btnClose);
        qnaList = (ListView) findViewById(R.id.qnaList);

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        setQnaList();

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, R.anim.slideout);
            }
        });

        qnaList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),shQna.class);
                intent.putExtra("strImg",qnaItems.get(i).img);
                startActivity(intent);
            }
        });
    }

    public void setQnaList() {

        //php url 입력
        String URL = "http://172.26.126.172:443/setQnaList.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {

                    qnaItems = new ArrayList<com.example.jungcar.qnaItem>();

                    jsonObject = new JSONObject(response);

                    Parsing parsing = new Parsing();
                    try {
                        int index = 0;
                        qnaItem mi;
                        while (true) {

                            parsing.qnaParsing(response, index);
                            if (parsing.name == "") {

                                break;
                            }


                            String subject = parsing.name;
                            String img = parsing.img;



                            index++;

                            mi = new qnaItem(subject, img);
                            qnaItems.add(mi);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                qnaadpt = new com.example.jungcar.qnaAdpt(QnA.this, R.layout.qnalist, qnaItems);
                qnaList.setAdapter(qnaadpt);

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
class qnaItem {
    String subject;
    String img;

    qnaItem(String subject, String img) {
    this.subject=subject;
    this.img=img;
    }
}
class qnaAdpt extends BaseAdapter {
    @Override
    public long getItemId(int i) {
        return 0;
    }


    Context maincon;
    LayoutInflater Inflater;
    ArrayList<qnaItem> arsrc;
    int layout;


    public qnaAdpt(Context context, int alayout, ArrayList<qnaItem> aarSrc) {
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

    public void updateReceiptsList(ArrayList<qnaItem> arsrc) {
        arsrc = this.arsrc;
        int size = arsrc.size();
        this.notifyDataSetChanged();
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        if (convertView == null) {
            convertView = Inflater.inflate(layout, parent, false);
        }
        TextView subject = (TextView) convertView.findViewById(R.id.subject);
        subject.setText(arsrc.get(position).subject);


        return convertView;


    }
}



