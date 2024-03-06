package com.example.jungcar;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class editCrash extends AppCompatActivity {
    TextView tvCancel, tvDate, tvAdd,tvPlace,tvDelLoc;
    LinearLayout llEditLoc,llAddImg;
    EditText edtMemo;
    ImageView imgTmp;
    imgAdpt imgadpt;
    DatePicker picker;
    ImageButton btnMore;
    Bitmap bitmap;
    ArrayList<imgItem> imgItems = new ArrayList<imgItem>();
    static StringRequest request;
    static RequestQueue requestQueue;
    String json;
    String encodeImageString;
    String strtgDate;
    String crash_idx;
    ArrayList<String> urls = new ArrayList<String>();
    ListView imgList;

    Date now = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat format2 = new SimpleDateFormat("yyyy.MM.dd(E)", Locale.KOREAN);

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editcrash);
        if (requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }

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
        imgList = (ListView)findViewById(R.id.imgList);
        imgTmp = (ImageView)findViewById(R.id.imgTmp);

        Intent intent = getIntent();
        crash_idx = intent.getExtras().getString("crash_idx");

        setCrash();
        setImg();
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
                 updateCrash();

                for(int i=0;i<imgItems.size();i++){

                    updateCrashImg(imgItems.get(i).encode, i, crash_idx);

                }
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                },2000);

            }
        });



    }
    public void updateCrashImg(String encode,int flag,String crash_idx) {

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

    public void updateCrash() {

        //php url 입력
        String URL = "http://172.26.126.172:443/updateCrash.php";


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


                        parsing.crashParsing(json);

                    } catch (JSONException e) {
                        e.printStackTrace();
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
                param.put("crash_idx",crash_idx);
                param.put("date",strtgDate);
                param.put("memo",edtMemo.getText().toString());
                param.put("location",tvPlace.getText().toString());

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

                imgItem mi;
                for (int i = 0; i < clip.getItemCount(); i++) {
                    Uri uri = clip.getItemAt(i).getUri();

                    InputStream inputStream = null;
                    try {
                        inputStream = getContentResolver().openInputStream(uri);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    bitmap = BitmapFactory.decodeStream(inputStream);
                        String tmp = encodeBitmapImage(bitmap);
                        mi = new imgItem(tmp, bitmap);
                        imgItems.add(mi);
                }
                imgadpt = new imgAdpt(editCrash.this,R.layout.imglist,imgItems);
                imgList.setAdapter(imgadpt);

                break;
        }

    }


    public void setCrash() {

        //php url 입력
        String URL = "http://172.26.126.172:443/setCrash.php";


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
                        parsing.editCrashParsing(json);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Date dd = format.parse(parsing.date);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dd);
                    strtgDate = format.format(calendar.getTime());
                    tvDate.setText(format2.format(calendar.getTime()));
                    tvPlace.setText(parsing.place);
                    edtMemo.setText(parsing.memo);

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

                } catch (JSONException | ParseException e) {
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
                param.put("crash_idx",crash_idx);


                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }

    public void setImg() {

        //php url 입력
        String URL = "http://172.26.126.172:443/setCrashImg.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {

                    jsonObject = new JSONObject(response);

                    Parsing parsing = new Parsing();
                    try {
                        int index = 0;

                        while (true) {

                            parsing.crashImgParsing(response, index);
                            if (parsing.img == "") {

                                break;
                            }
                            String img = parsing.img;
                            String imgurl = "http://172.26.126.172:443/" + img;
                            urls.add(imgurl);


                            index++;

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                for(int i=0;i< urls.size();i++) {

                                    imgItem mi;
                                    Bitmap bitmap;
                                    bitmap = getBitmap(urls.get(i));
                                    String tmp = encodeBitmapImage(bitmap);
                                    mi = new imgItem(tmp, bitmap);
                                    imgItems.add(mi);
                                }
                            }catch(Exception e) {

                            }
                            finally {
                                    runOnUiThread(new Runnable() {
                                        @SuppressLint("NewApi")
                                        public void run() {
                                            imgadpt = new imgAdpt(editCrash.this,R.layout.imglist,imgItems);
                                            imgList.setAdapter(imgadpt);
                                        }
                                    });
                            }
                        }
                    }).start();


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

                param.put("crash_idx",crash_idx);
                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }


    private Bitmap getBitmap(String url) {
        URL imgUrl = null;
        HttpURLConnection connection = null;
        InputStream is = null;

        Bitmap retBitmap = null;

        try{
            imgUrl = new URL(url);
            connection = (HttpURLConnection) imgUrl.openConnection();
            connection.setDoInput(true); //url로 input받는 flag 허용
            connection.connect(); //연결
            is = connection.getInputStream(); // get inputstream
            retBitmap = BitmapFactory.decodeStream(is);
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            if(connection!=null) {
                connection.disconnect();
            }
            return retBitmap;
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