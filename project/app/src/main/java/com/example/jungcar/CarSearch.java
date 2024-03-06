package com.example.jungcar;

import android.app.Activity ;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CarSearch extends Activity {
    static StringRequest request;
    static RequestQueue requestQueue;
    String json;
    ImageView img;
    TextView tvName;
    Bitmap bitmap;
    LinearLayout llText;
    ProgressBar progress;
    String encodeImageString;
    Button btnCancel, btnSearch, btnImg;
    String strTotal="",strBrand = "", strCountry = "", strModel = "", strGrade = "", minMilesage = "", maxMilesage = "", minPrice = "", maxPrice = "", isChk = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.carsearch);
        img = (ImageView)findViewById(R.id.img);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnImg = (Button) findViewById(R.id.btnImg);
        tvName = (TextView) findViewById(R.id.tvName);
        progress=(ProgressBar)findViewById(R.id.progress);
        llText=(LinearLayout)findViewById(R.id.llText);
        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llText.setVisibility(View.INVISIBLE);
                btnSearch.setText("매물보기");
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, 2);
            }
        });

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ShResult.class);
                intent.putExtra("strName",tvName.getText().toString());
                intent.putExtra("strBrand",strBrand);
                intent.putExtra("strModel",strModel);
                intent.putExtra("strGrade",strGrade);
                intent.putExtra("minMilesage",minMilesage);
                intent.putExtra("maxMilesage",maxMilesage);
                intent.putExtra("minPrice",minPrice);
                intent.putExtra("maxPrice",maxPrice);
                intent.putExtra("strCountry",strCountry);
                intent.putExtra("isChk",isChk);
                intent.putExtra("strTotal",strTotal);

                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {

            case 2:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();


                    try {
                        InputStream inputStream = getContentResolver().openInputStream(uri);
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        img.setImageBitmap(bitmap);
                        String tmp = encodeBitmapImage(bitmap);
                        img.setTag(tmp);
                        progress.setVisibility(View.VISIBLE);
                        findCar(tmp);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                }

            default:
                Toast.makeText(getApplicationContext(), "선택된 이미지가 없습니다", Toast.LENGTH_SHORT).show();
        }

    }



    private String encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] bytesOfImage = byteArrayOutputStream.toByteArray();


        encodeImageString = android.util.Base64.encodeToString(bytesOfImage, Base64.DEFAULT);
        return encodeImageString;
    }

    public void findCar(String encode) {

        //php url 입력
        String URL = "http://172.26.126.172:443/findCar.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {

                    jsonObject = new JSONObject(response);

                    Parsing parsing = new Parsing();
                    try {


                        parsing.searchParsing(response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    progress.setVisibility(View.INVISIBLE);
                    tvName.setText(parsing.name);
                    btnSearch.setText("매물보기 "+parsing.count+"대");
                    llText.setVisibility(View.VISIBLE);

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
                param.put("user_idx",shared_preferences.get_user_email(CarSearch.this));




                return param;
            }

        };
        request.setRetryPolicy(new com.android.volley.DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);
        requestQueue.add(request);

        request.setShouldCache(false);
        requestQueue.add(request);


    }


}
