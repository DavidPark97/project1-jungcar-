package com.example.jungcar;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okio.BufferedSink;
import okio.Okio;

public class BoardWrite extends AppCompatActivity {
    TextView tvBuy, tvSell, tvPart, tvRecord;
    TextView tvCommunity;
    EditText edtSubject, edtContent;
    Button btnImg, btnVideo,btnEnroll;
    Spinner spinBrdType;
    Bitmap bitmap;
    String [] strType = new String[9];
    String [] strTypeidx = new String[9];
    Uri uriVideo;
    String encodeImageString;
    ArrayList<imgItem> imgItems;
    static StringRequest request;
    static RequestQueue requestQueue;
    String json;
    int videoflag=0, imgflag=0;
    String strSelected;
    String strBrand = "", strCountry = "", strModel = "", strGrade = "", minMilesage = "", maxMilesage = "", minPrice = "", maxPrice = "", isChk = "";
    ImageView imgMy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boardwrite);
        tvSell = (TextView) findViewById(R.id.tvSell);
        tvBuy = (TextView) findViewById(R.id.tvBuy);
        tvCommunity = (TextView) findViewById(R.id.tvCommunity);
        tvPart = (TextView) findViewById(R.id.tvPart);
        tvRecord = (TextView) findViewById(R.id.tvRecord);
        spinBrdType = (Spinner) findViewById(R.id.spinBrdType);
        btnImg = (Button)findViewById(R.id.btnImg);
        btnVideo = (Button)findViewById(R.id.btnVideo);
        btnEnroll = (Button)findViewById(R.id.btnEnroll);
        edtSubject = (EditText)findViewById(R.id.edtSubject);
        edtContent = (EditText)findViewById(R.id.edtContent);
        imgMy = (ImageView)findViewById(R.id.imgMy);
        Intent intent = getIntent();
        strSelected= intent.getExtras().getString("board_type");

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }


        imgMy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shared_preferences.get_user_email(BoardWrite.this).isEmpty()==true) {
                    new AlertDialog.Builder(BoardWrite.this)
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
        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            }
        });

        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uriVideo = Uri.parse("content://media/external/images/media");
                Intent intent = new Intent(Intent.ACTION_VIEW, uriVideo);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("video/*");
                startActivityForResult(intent, 1);
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

        btnEnroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtContent.length()==0||edtSubject.length()==0){
                    Toast.makeText(getApplicationContext(),"내용을 입력하세요",Toast.LENGTH_SHORT).show();
                }else{
                    insContent();
                }
            }
        });

        spinBrdType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strSelected = spinBrdType.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(),strSelected,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        setBoardType();



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {
            case 1:
                uriVideo = data.getData();
                videoflag = 1;
                btnVideo.setText("비디오 선택됨");
                break;
            case 2:
                imgflag=1;
                ClipData clip = data.getClipData();
                imgItems = new ArrayList<imgItem>();
                imgItem mi;
                btnImg.setText(clip.getItemCount()+"개 선택됨");
                for (int i = 0; i < clip.getItemCount(); i++) {
                    Uri uri = clip.getItemAt(i).getUri();
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(uri);
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        String tmp = encodeBitmapImage(bitmap);
                        mi = new imgItem(tmp, bitmap);
                        imgItems.add(mi);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                break;
            default:
                Toast.makeText(getApplicationContext(), "선택된것이 없습니다", Toast.LENGTH_SHORT).show();
        }

    }

    public void insContent() {

        //php url 입력
        String URL = "http://172.26.126.172:443/insContent.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {

                    jsonObject = new JSONObject(response);
                    json = response;
                    Parsing parsing = new Parsing();

                    try {


                        parsing.boardIdxParsing(json);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String board_idx = parsing.idx;
                    if (videoflag==1){
                        insBoardVideo(board_idx);
                    }

                    if(imgflag==1){
                        for(int i=0;i<imgItems.size();i++){
                            insBoardImage(imgItems.get(i).encode, i, board_idx);
                        }
                    }
                   finish();

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

                param.put("content",edtContent.getText().toString());
                param.put("subject",edtSubject.getText().toString());
                param.put("board_type",strSelected);
                param.put("user_idx",shared_preferences.get_user_email(BoardWrite.this));


                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }


    public void setBoardType() {

        //php url 입력
        String URL = "http://172.26.126.172:443/setBoardType.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {

                    int tmp =0;
                    jsonObject = new JSONObject(response);

                    Parsing parsing = new Parsing();
                    try {

                        int index = 0;
                        partboardItem mi;
                        while (true) {

                            parsing.BoardTypeParsing(response, index);
                            if (parsing.name == "") {
                                break;
                            }
                            strType[index]=parsing.name;
                            strTypeidx[index]=parsing.idx;

                            if(strSelected.equals(parsing.idx)){
                                tmp = index;

                            }


                            index++;


                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_spinner_dropdown_item,strType);
                    spinBrdType.setAdapter(adapter);
                    spinBrdType.setSelection(tmp);

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
                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }
    private String encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] bytesOfImage = byteArrayOutputStream.toByteArray();


        encodeImageString = android.util.Base64.encodeToString(bytesOfImage, Base64.DEFAULT);
        return encodeImageString;
    }

    public void insBoardImage(String encode,int order,String board_idx) {

        //php url 입력
        String URL = "http://172.26.126.172:443/insBoardImage.php";


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
                param.put("order",Integer.toString(order));
                param.put("board_idx",board_idx);
                param.put("type","2");



                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }

    public void insBoardVideo(String board_idx) {


        class sendDataToHttp extends AsyncTask<Void, Void, String> {
            String serverUrl = "http://172.26.126.172:443/insBoardVideo.php";
            OkHttpClient client = new OkHttpClient();

            Context context;

            public sendDataToHttp(Context context) {
                this.context = context;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... voids) {
                ContentResolver contentResolver = context.getContentResolver();
                final String contentType = contentResolver.getType(uriVideo);
                final AssetFileDescriptor fd;
                try {
                    fd = contentResolver.openAssetFileDescriptor(uriVideo, "r");

                    if (fd == null) {
                        throw new FileNotFoundException("could not open file descriptor");
                    }


                    RequestBody videoFile = new RequestBody() {
                        @Override
                        public long contentLength() {
                            return fd.getDeclaredLength();
                        }

                        @Override
                        public MediaType contentType() {
                            return MediaType.parse(contentType);
                        }

                        @Override
                        public void writeTo(BufferedSink sink) throws IOException {
                            try (InputStream is = fd.createInputStream()) {
                                sink.writeAll(Okio.buffer(Okio.source(is)));
                            }
                        }
                    };

                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("board_idx",board_idx)
                            .addFormDataPart("type", "1")
                            .addFormDataPart("file", "fname", videoFile)
                            .build();

                    okhttp3.Request request = new okhttp3.Request.Builder()
                            .url(serverUrl)
                            .post(requestBody)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            try {
                                fd.close();
                            } catch (IOException ex) {
                                e.addSuppressed(ex);
                            }
                            Log.d("실패", "failed", e);
                        }

                        @Override
                        public void onResponse(Call call, okhttp3.Response response) throws IOException {
                            String result = response.body().string();
                            Log.d("결과",""+result);
                            fd.close();
                        }
                    });

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }

        }

        sendDataToHttp sendData = new sendDataToHttp(this);
        sendData.execute();
    }

}