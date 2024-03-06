package com.example.jungcar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddMaintain extends AppCompatActivity {
    TextView tvCancel, tvDate, tvAdd,tvPlace;
    EditText  edtDist;
    DatePicker picker;
    ImageButton btnMore;
    Button btnSelf,btnCenter;
    ListView manageList;
    selectedAdpt selectedadpt;
    ArrayList<selectedItem> selectedItems;
    static StringRequest request;
    static RequestQueue requestQueue;
    String json;
    String center_idx="0";
  
    String strtgDate;
    String strSelect;
    Date now = new Date();

    DecimalFormat decimalFormat = new DecimalFormat("###,###");
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat format2 = new SimpleDateFormat("yyyy.MM.dd(E)", Locale.KOREAN);
    String strTotal;


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),RecordHome.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addmaintain);
        if (requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }
        Intent intent = getIntent();
        strSelect = intent.getExtras().getString("selected","");

        btnSelf=(Button)findViewById(R.id.btnSelf);
        btnCenter=(Button)findViewById(R.id.btnCenter);
        manageList=(ListView)findViewById(R.id.manageList);
        tvCancel = (TextView) findViewById(R.id.tvCancel);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvAdd = (TextView) findViewById(R.id.tvAdd);
        edtDist = (EditText) findViewById(R.id.edtDist);
        tvPlace = (TextView) findViewById(R.id.tvPlace);
        picker= (DatePicker) findViewById(R.id.picker);
        btnMore = (ImageButton) findViewById(R.id.btnMore);


            strtgDate = format.format(now);
            tvDate.setText(format2.format(now));


        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picker.setVisibility(View.VISIBLE);
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSelf.setBackgroundColor(Color.parseColor("#00fefe"));
                btnCenter.setBackgroundColor(Color.parseColor("#aaaaaa"));
                tvPlace.setClickable(false);
                tvPlace.setText("자가진단");
                center_idx="99999";
            }
        });

        btnCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnCenter.setBackgroundColor(Color.parseColor("#00fefe"));
                btnSelf.setBackgroundColor(Color.parseColor("#aaaaaa"));
                tvPlace.setClickable(true);
                tvPlace.setText("장소를 입력해주세요");
                center_idx="0";
            }
        });

        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if (Integer.parseInt(edtDist.getText().toString().replace(",","")) >= Integer.parseInt(strTotal)) {
                        if(Integer.parseInt(edtDist.getText().toString().replace(",",""))>Integer.parseInt(strTotal)){
                            addDist();
                        }
                        edtDist.requestFocus();
                        int flag = 0;
                        if (!center_idx.equals("0")) {
                            for (int i = 0; i < selectedItems.size(); i++) {
                                if (selectedItems.get(i).pr.replace(",","").equals("0") || selectedItems.get(i).pr.replace(",","").equals("")) {
                                    flag = 1;
                                    break;
                                }
                            }
                            if (flag == 1) {
                                Toast.makeText(getApplicationContext(), "값을 모두 입력해주세요", Toast.LENGTH_SHORT).show();
                            } else {
                                for (int i = 0; i < selectedItems.size(); i++) {
                                    addMaintain(i);
                                }
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(getApplicationContext(), RecordHome.class);
                                        startActivity(intent);
                                    }
                                },500);

                            }
                        }else{
                            Toast.makeText(getApplicationContext(),"정비소를 입력해주세요",Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "누적 거리가 작아졌습니다.", Toast.LENGTH_SHORT).show();
                    }
                }

        });


        edtDist.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                edtDist.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = edtDist.getText().length();

                    String v = editable.toString().replace(String.valueOf(decimalFormat.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = decimalFormat.parse(v);
                    int cp = edtDist.getSelectionStart();

                    edtDist.setText(decimalFormat.format(n));

                    endlen = edtDist.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= edtDist.getText().length()) {
                        edtDist.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        edtDist.setSelection(edtDist.getText().length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException e) {
                    // do nothing?
                }

                edtDist.addTextChangedListener(this);
            }
        });




        selectManage();

        setDist();

        tvPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), getCenter.class);
                startActivityForResult(intent, 1);
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
                    selectedItems = new ArrayList<selectedItem>();
                    int index=0;
                    jsonObject = new JSONObject(response);

                    Parsing parsing = new Parsing();
                    try {
                        selectedItem mi;
                        while (true) {

                            parsing.manageParsing2(response, index);
                            if (parsing.name == "") {

                                break;
                            }


                            String name = parsing.name;

                            String img = parsing.img;
                            String type = parsing.idx;



                            index++;

                            mi = new selectedItem(name,type,img,Integer.toString(index));
                            selectedItems.add(mi);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                selectedadpt = new selectedAdpt(AddMaintain.this,R.layout.addmaintainlist,selectedItems);
                manageList.setAdapter(selectedadpt);
           
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

    public void setDist() {

        //php url 입력
        String URL = "http://172.26.126.172:443/setDist.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {

                    jsonObject = new JSONObject(response);
                    json = response;
                    Parsing parsing = new Parsing();

                    try {
                        parsing.setDistParsing(json);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    strTotal = parsing.count;
                    edtDist.setText(strTotal);
                    picker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                        @Override
                        public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {

                            strtgDate = i+"-"+(i1+1)+"-"+i2;
                            try {
                                Date dd = format.parse(strtgDate);
                                tvDate.setText(format2.format(dd.getTime()));
                                picker.setVisibility(View.INVISIBLE);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }



                        }
                    });

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
                param.put("car_idx",shared_preferences.get_user_car(AddMaintain.this));


                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }

    public void addMaintain(int order) {

        //php url 입력
        String URL = "http://172.26.126.172:443/addMaintain.php";


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
                };
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

                param.put("car_idx",shared_preferences.get_user_car(AddMaintain.this));
                param.put("date",strtgDate);
                param.put("center_idx",center_idx);
                param.put("main_type",selectedItems.get(order).type);
                param.put("charge",selectedItems.get(order).pr.replace(",",""));
                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }

    public void addDist() {

        //php url 입력
        String URL = "http://172.26.126.172:443/addDist.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @RequiresApi(api = Build.VERSION_CODES.O)
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
                param.put("car_idx",shared_preferences.get_user_car(AddMaintain.this));
                param.put("date",strtgDate);
                param.put("distance",edtDist.getText().toString().replace(",",""));

                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            center_idx = data.getStringExtra("idx");
            tvPlace.setText(data.getStringExtra("name"));
        }
    }
}
class selectedItem {


    String type;
    String name;
    String img;
    String order;
    String pr;


    selectedItem(String name,String type, String img,String order) {
        this.name = name;
        this.type=type;
        this.img=img;
        this.order=order;
        pr = "0";

    }

}
class selectedAdpt extends BaseAdapter {
    @Override
    public long getItemId(int i) {
        return 0;
    }


    Context maincon;
    LayoutInflater Inflater;
    ArrayList<selectedItem> arsrc;
    int layout;
    DecimalFormat decimalFormat = new DecimalFormat("###,###");

    public selectedAdpt(Context context, int alayout, ArrayList<selectedItem> aarSrc) {
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

    public void updateReceiptsList(ArrayList<selectedItem> arsrc) {
        arsrc = this.arsrc;
        int size = arsrc.size();
        this.notifyDataSetChanged();
    }




    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        if(convertView == null) {
            convertView = Inflater.inflate(layout,parent,false);
        }
        TextView subject = (TextView)convertView.findViewById(R.id.name);
        subject.setText(arsrc.get(position).name);
        subject.setTag(arsrc.get(position).type);

        ImageView img = (ImageView)convertView.findViewById(R.id.img);
        String imgurl = "http://172.26.126.172:443/maintain/"+arsrc.get(position).img;
        Glide.with(convertView).load(imgurl).into(img);

        TextView order = (TextView)convertView.findViewById(R.id.order);
        order.setText(arsrc.get(position).order);

        EditText price = (EditText)convertView.findViewById(R.id.price);
        price.setHint("지출금액");
        price.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){

                }else{
                    arsrc.get(position).pr=price.getText().toString();
                }
            }
        });

        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

               price.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = price.getText().length();

                    String v = editable.toString().replace(String.valueOf(decimalFormat.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = decimalFormat.parse(v);
                    int cp = price.getSelectionStart();

                    price.setText(decimalFormat.format(n));

                    endlen = price.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= price.getText().length()) {
                        price.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        price.setSelection(price.getText().length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException e) {
                    // do nothing?
                }

                price.addTextChangedListener(this);
            }
        });


        return convertView;


    }


}