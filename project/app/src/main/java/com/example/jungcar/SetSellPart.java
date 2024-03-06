package com.example.jungcar;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SetSellPart extends AppCompatActivity {
    TextView tvBuy, tvSell, tvRecord, tvCommunity, tvPartBuy, tvPartSell, tvPart,tvPhone;
    static StringRequest request;
    static RequestQueue requestQueue;
    String json;
    Spinner spinType;
    ImageButton btnImg;
    EditText edtSubject, edtPrice, edtContent;
    Button btnUnOpen, btnNewly, btnUsed, btnCancel, btnEnroll;
    RadioGroup rgDelivery;
    RadioButton rbDirect, rbDelivery;
    CheckBox chkAgree;
    Bitmap bitmap;
    String encodeImageString;
    ArrayList<imgItem> imgItems;
    String [] strType = new String[8];
    String strState;
    String strPartType;
    String strAgree = "n";
    String strDelivery;
    String strBrand = "", strCountry = "", strModel = "", strGrade = "", minMilesage = "", maxMilesage = "", minPrice = "", maxPrice = "", isChk = "";
    ImageView imgMy;

    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),MainPart.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setsellpart);

        tvSell = (TextView) findViewById(R.id.tvSell);
        tvBuy = (TextView) findViewById(R.id.tvBuy);
        tvPart = (TextView) findViewById(R.id.tvPart);
        tvCommunity = (TextView) findViewById(R.id.tvCommunity);
        tvRecord = (TextView) findViewById(R.id.tvRecord);
        tvPartBuy = (TextView) findViewById(R.id.tvPartBuy);
        tvPartSell = (TextView) findViewById(R.id.tvPartSell);
        imgMy = (ImageView)findViewById(R.id.imgMy);
        tvPhone = (TextView)findViewById(R.id.tvPhone);
        spinType = (Spinner)findViewById(R.id.spinType);
        btnImg = (ImageButton)findViewById(R.id.btnImg);
        edtSubject=(EditText)findViewById(R.id.edtSubject);
        edtPrice=(EditText)findViewById(R.id.edtPrice);
        edtContent=(EditText)findViewById(R.id.edtContent);
        btnUnOpen = (Button)findViewById(R.id.btnUnOpen);
        btnNewly = (Button)findViewById(R.id.btnNewly);
        btnUsed   = (Button)findViewById(R.id.btnUsed);
        btnCancel = (Button)findViewById(R.id.btnCancel);
        btnEnroll = (Button)findViewById(R.id.btnEnroll);
        rgDelivery = (RadioGroup)findViewById(R.id.rgDelivery);
        rbDirect= (RadioButton)findViewById(R.id.rbDirect);
        rbDelivery=(RadioButton)findViewById(R.id.rbDelivery);
        chkAgree=(CheckBox) findViewById(R.id.chkAgree);
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        Intent intent = getIntent();
        strPartType = intent.getExtras().getString("part_type", "");
        setPartType();

        rgDelivery.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rbDirect){
                    strDelivery = rbDirect.getText().toString();
                }else if(i ==R.id.rbDelivery){
                    strDelivery = rbDelivery.getText().toString();
                }
            }
        });

        btnNewly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnNewly.setBackgroundColor(Color.GRAY);
                btnUsed.setBackgroundColor(Color.WHITE);
                btnUnOpen.setBackgroundColor(Color.WHITE);
                strState = btnNewly.getText().toString();

            }
        });
        btnUnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnNewly.setBackgroundColor(Color.WHITE);
                btnUsed.setBackgroundColor(Color.WHITE);
                btnUnOpen.setBackgroundColor(Color.GRAY);
                strState = btnUnOpen.getText().toString();

            }
        });

        btnUsed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnNewly.setBackgroundColor(Color.WHITE);
                btnUsed.setBackgroundColor(Color.GRAY);
                btnUnOpen.setBackgroundColor(Color.WHITE);
                strState = btnUsed.getText().toString();

            }
        });

        chkAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    strAgree="y";
                }else{
                    strAgree="n";
                }
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

        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
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

        tvPartBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainPart.class);

                startActivity(intent);
            }
        });


        btnEnroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnrollPart();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(),MainPart.class);
                        startActivity(intent);
                    }
                },1000);

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



        imgMy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shared_preferences.get_user_email(SetSellPart.this).isEmpty()==true) {
                    new AlertDialog.Builder(SetSellPart.this)
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


        setPhone();



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {
            case 1:
                ClipData clip = data.getClipData();
                imgItems = new ArrayList<imgItem>();
                imgItem mi;
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

    public void EnrollPartImg(String encode,int flag,String market_idx) {

        //php url 입력
        String URL = "http://172.26.126.172:443/EnrollPartImg.php";


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
                param.put("market_idx",market_idx);



                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }

    public void EnrollPart() {

        //php url 입력
        String URL = "http://172.26.126.172:443/EnrollPart.php";


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


                        parsing.marketParsing(json);;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                        String market_idx = parsing.idx;
                    for(int i=0;i<imgItems.size();i++){
                        if(!imgItems.get(i).encode.equals("")) {
                            EnrollPartImg(imgItems.get(i).encode, i, market_idx);
                        }
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
                param.put("user_idx",shared_preferences.get_user_email(SetSellPart.this));
                param.put("type_name", Integer.toString(spinType.getSelectedItemPosition()+1));
                param.put("subject",edtSubject.getText().toString());
                param.put("price",edtPrice.getText().toString());
                param.put("state",strState);
                param.put("delivery",strDelivery);
                param.put("agree",strAgree);
                param.put("content",edtContent.getText().toString());



                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }



    public void setPartType() {

        //php url 입력
        String URL = "http://172.26.126.172:443/setPartType.php";


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
                        partboardItem mi;
                        while (true) {

                            parsing.setOptionParsing(response, index);
                            if (parsing.name == "") {
                                break;
                            }
                            strType[index]=parsing.name;
                            index++;




                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_spinner_dropdown_item,strType);
                    spinType.setAdapter(adapter);
                    if (!strPartType.equals("")) {
                        if(!strPartType.equals("0")) {
                            spinType.setSelection(Integer.parseInt(strPartType) - 1);
                            spinType.setEnabled(false);
                        }
                    }

                    spinType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            ((TextView)adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

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
                param.put("part_type",strPartType);
                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }

    public void setPhone() {

        //php url 입력
        String URL = "http://172.26.126.172:443/setPhone.php";


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


                        parsing.PhoneParsing(json);;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    tvPhone.setText(parsing.phone);
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



                param.put("user_idx",shared_preferences.get_user_email(SetSellPart.this));


                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }
}