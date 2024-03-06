package com.example.jungcar;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddCrash extends AppCompatActivity {
    TextView tvCancel, tvDate, tvAdd,tvPlace,tvDelLoc;
    LinearLayout llEditLoc,llAddImg;
    EditText edtMemo;
    TableLayout tlTable;
    DatePicker picker;
    ImageButton btnMore;
    Bitmap bitmap;
    ArrayList<imgItem> imgItems;
    static StringRequest request;
    static RequestQueue requestQueue;
    String json;
    String encodeImageString;
    String strtgDate;

    Date now = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat format2 = new SimpleDateFormat("yyyy.MM.dd(E)", Locale.KOREAN);

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcrash);
        if (requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }

        tlTable = (TableLayout)findViewById(R.id.tlTable);
        llEditLoc = (LinearLayout)findViewById(R.id.llEditLoc);
        llAddImg = (LinearLayout)findViewById(R.id.llAddImg);
        tvDelLoc = (TextView)findViewById(R.id.tvDelLoc);
        edtMemo = (EditText) findViewById(R.id.edtMemo);
        tvCancel = (TextView) findViewById(R.id.tvCancel);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvAdd = (TextView) findViewById(R.id.tvAdd);
        tvPlace = (TextView) findViewById(R.id.tvPlace);
        picker= (DatePicker) findViewById(R.id.picker);
        btnMore = (ImageButton) findViewById(R.id.btnMore);



        strtgDate = format.format(now);
        tvDate.setText(format2.format(now));

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


        tvDelLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvPlace.setText("");
            }
        });

        llEditLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CrashLocation.class);
                startActivityForResult(intent, 1);
            }
        });
        llAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            }
        });

        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insCrash();
                Intent intent = new Intent(getApplicationContext(), RecordHome.class);
                startActivity(intent);
            }
        });



    }
    public void insCrashImg(String encode,int flag,String crash_idx) {

        //php url 입력
        String URL = "http://172.26.126.172:443/insCrashImg.php";


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
                param.put("img",encode);
                param.put("flag",Integer.toString(flag));
                param.put("crash_idx",crash_idx);



                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }

    public void insCrash() {

        //php url 입력
        String URL = "http://172.26.126.172:443/insCrash.php";


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


                        parsing.crashParsing(json);;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String crash_idx = parsing.idx;
                    for(int i=0;i<imgItems.size();i++){
                            insCrashImg(imgItems.get(i).encode, i, crash_idx);

                    }
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
                param.put("car_idx",shared_preferences.get_user_car(AddCrash.this));
                param.put("date",strtgDate);
                param.put("memo",edtMemo.getText().toString());
                param.put("place",tvPlace.getText().toString());

                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case 1:
            if (resultCode == RESULT_OK) {
                tvPlace.setText(data.getStringExtra("address"));
            }
            break;
            case 2:
                ClipData clip = data.getClipData();
                imgItems = new ArrayList<imgItem>();
                imgItem mi;
                TableRow trow = null;
                for (int i = 0; i < clip.getItemCount(); i++) {
                    Uri uri = clip.getItemAt(i).getUri();
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(uri);
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        String tmp = encodeBitmapImage(bitmap);
                        mi = new imgItem(tmp, bitmap);
                        imgItems.add(mi);
                        if(i%3==0) {
                            trow = new TableRow(AddCrash.this);
                            TableLayout.LayoutParams lp = new TableLayout.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT);
                            lp.weight=1;
                            lp.setMargins(1, 1, 1, 1);
                            trow.setLayoutParams(lp);
                        }
                        TableRow.LayoutParams tp = new TableRow.LayoutParams
                                (ViewGroup.LayoutParams.WRAP_CONTENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT);
                        tp.setMargins(1,1,1,1);
                        tp.weight = 1;
                        ImageView im = new ImageView(this);
                        im.setLayoutParams(tp);
                        im.setImageBitmap(bitmap);
                        trow.addView(im);

                        if(i%3==2||i+1==clip.getItemCount()) {
                            tlTable.addView(trow);
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                llAddImg.setVisibility(View.INVISIBLE);
                break;
        }

    }
    private String encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] bytesOfImage = byteArrayOutputStream.toByteArray();


        encodeImageString = android.util.Base64.encodeToString(bytesOfImage, Base64.DEFAULT);
        return encodeImageString;
    }
}