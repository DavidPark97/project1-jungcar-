package com.example.jungcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class setImg extends AppCompatActivity {
    static StringRequest request;
    static RequestQueue requestQueue;
    String json;
    imgAdpt imgadpt;
    ArrayList<String> items = new ArrayList<String>();
    Button btnOther, btnBest, btnEnroll;
    ImageView imgMain;
    Bitmap bitmap;
    ListView imgList;
    String encodeImageString;
    ArrayList<imgItem> imgItems;
    String car_idx;

    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),MainSell.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setimg);
        Intent intent = getIntent();

        car_idx= intent.getExtras().getString("car_idx","");

        btnOther = (Button) findViewById(R.id.btnOther);
        btnBest = (Button) findViewById(R.id.btnBest);
        btnEnroll = (Button) findViewById(R.id.btnEnroll);
        imgMain = (ImageView) findViewById(R.id.imgMain);
        imgList = (ListView) findViewById(R.id.imgList);
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        btnBest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                ;
                startActivityForResult(intent, 2);
            }

        });

        btnOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });

        btnEnroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insImg(imgMain.getTag().toString(),0);
                for(int i=0;i<imgList.getAdapter().getCount();i++){
                        insImg(imgList.getAdapter().getItem(i).toString(),i+1);
                }

                Intent intent = new Intent(getApplicationContext(), MainSell.class);
                startActivity(intent);
            }
        });
    }

    public void insImg(String encode,int flag) {

        //php url 입력
        String URL = "http://172.26.126.172:443/insImg.php";


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
                param.put("car_idx",car_idx);




                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


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
                imgadpt = new imgAdpt(setImg.this, R.layout.imglist, imgItems);
                imgList.setAdapter(imgadpt);
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();


                    try {
                        InputStream inputStream = getContentResolver().openInputStream(uri);
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        imgMain.setImageBitmap(bitmap);
                        String tmp = encodeBitmapImage(bitmap);
                        imgMain.setTag(tmp);
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
}

    class imgItem {
        String encode;
        Bitmap bitmap;

        imgItem(String encode, Bitmap bitmap) {
            this.bitmap = bitmap;
            this.encode = encode;
        }
    }

    class imgAdpt extends BaseAdapter {
        @Override
        public long getItemId(int i) {
            return 0;
        }

        RequestQueue requestQueue;
        Context maincon;
        LayoutInflater Inflater;
        ArrayList<imgItem> arsrc;

        int layout;

        public RequestQueue getRequestQueue() {
            requestQueue = Volley.newRequestQueue(maincon.getApplicationContext());
            return requestQueue;
        }


        public imgAdpt(Context context, int alayout, ArrayList<imgItem> aarSrc) {
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
            return arsrc.get(position).encode;
        }

        public void updateReceiptsList(ArrayList<imgItem> arsrc) {
            arsrc = this.arsrc;
            int size = arsrc.size();
            this.notifyDataSetChanged();
        }


        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            if (convertView == null) {
                convertView = Inflater.inflate(layout, parent, false);
            }

            ImageView img = (ImageView) convertView.findViewById(R.id.img);
            img.setImageBitmap(arsrc.get(position).bitmap);

            ImageView delete = (ImageView) convertView.findViewById(R.id.delete);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    arsrc.remove(arsrc.get(pos));
                    notifyDataSetChanged();



                }


            });


            return convertView;


        }


}

