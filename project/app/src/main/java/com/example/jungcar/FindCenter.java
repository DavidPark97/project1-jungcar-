package com.example.jungcar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
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
import com.journeyapps.barcodescanner.CaptureActivity;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.ScanOptions;
import com.journeyapps.barcodescanner.ScanContract;

public class FindCenter extends AppCompatActivity implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;
    private NaverMap naverMap;
    String json;
    static RequestQueue requestQueue;
    MapView mapView;
    Marker locmarker;
    EditText edtRev;
    Button btnReFind, btnCall,btnReview, btnBack, btnEnroll,btnLabor;
    TextView tvName,tvRate,tvRevCnt,tvDist,tvAddress;
    RatingBar ratBar,ratEnroll;
    InfoWindow infoWindow = new InfoWindow();
    int center_idx;
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    double latitude,longitude;
    ArrayList<mapItem> mapItems;
    ArrayList<Marker> markers;
    mapAdpt mapadpt;
    ArrayList<revItem> revItems;
    revAdpt revadpt;
    LinearLayout llCenterList,llShCenter,llEnrollRev;

    ListView mapList,revList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findcenter);
        mapList = (ListView) findViewById(R.id.mapList);
        btnReFind = (Button) findViewById(R.id.btnReFind);
        llCenterList =(LinearLayout) findViewById(R.id.llCenterList);
        llShCenter =(LinearLayout) findViewById(R.id.llShCenter);
        llEnrollRev =(LinearLayout) findViewById(R.id.llEnrollRev);
        btnCall = (Button)findViewById(R.id.btnCall);
        btnReview = (Button)findViewById(R.id.btnReview);
        btnBack = (Button)findViewById(R.id.btnBack);
        btnEnroll = (Button)findViewById(R.id.btnEnroll);
        btnLabor = (Button)findViewById(R.id.btnLabor);
        edtRev = (EditText)findViewById(R.id.edtRev);
        tvName = (TextView) findViewById(R.id.tvName);
        tvRate = (TextView)findViewById(R.id.tvRate);
        tvRevCnt = (TextView)findViewById(R.id.tvRevCnt);
        tvDist = (TextView)findViewById(R.id.tvDist);
        tvAddress = (TextView)findViewById(R.id.tvAddress);
        ratBar = (RatingBar)findViewById(R.id.ratBar);
        ratEnroll = (RatingBar)findViewById(R.id.ratEnroll);
        revList = (ListView) findViewById(R.id.revList);
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
       if(location!=null) {
           latitude = Math.round(location.getLatitude() * 1000) / 1000.0;
           longitude = Math.round(location.getLongitude() * 1000) / 1000.0;
           CenterList(latitude, longitude);
       }
        btnReFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<markers.size();i++){
                    markers.get(i).setMap(null);
                }

                CenterList(locmarker.getPosition().latitude, locmarker.getPosition().longitude);

            }
        });

        mapList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CameraPosition cameraPosition = new CameraPosition(new LatLng(Double.parseDouble(mapItems.get(i).latitude),Double.parseDouble(mapItems.get(i).longitude)),17);
                infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(getApplicationContext()) {
                    @NonNull
                    @Override
                    public CharSequence getText(@NonNull InfoWindow infoWindow) {
                        return mapItems.get(i).name;
                    }
                });
                infoWindow.open(markers.get(i));
                naverMap.setCameraPosition(cameraPosition);

                llCenterList.setVisibility(View.GONE);
                llShCenter.setVisibility(View.VISIBLE);
                tvName.setText(mapItems.get(i).name);
                tvDist.setText(mapItems.get(i).distance);
                tvRevCnt.setText(mapItems.get(i).cnt);
                tvAddress.setText(mapItems.get(i).address);
                btnCall.setTag(mapItems.get(i).phone);
                tvRate.setText(mapItems.get(i).rate);
                ratBar.setRating((float) ((Float.parseFloat(mapItems.get(i).rate))*0.2));
                btnReFind.setVisibility(View.INVISIBLE);
                btnBack.setVisibility(View.VISIBLE);
                btnReview.setTag(mapItems.get(i).visit);
                CenterRevList(mapItems.get(i).idx);
                center_idx=i;

            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnBack.setVisibility(View.INVISIBLE);
                btnReFind.setVisibility(View.VISIBLE);
                llCenterList.setVisibility(View.VISIBLE);
                llShCenter.setVisibility(View.GONE);
                llEnrollRev.setVisibility(View.GONE);
                infoWindow.close();
                CameraPosition cameraPosition = new CameraPosition(new LatLng(locmarker.getPosition().latitude,locmarker.getPosition().longitude),12);
                naverMap.setCameraPosition(cameraPosition);

                CenterList(locmarker.getPosition().latitude,locmarker.getPosition().longitude);

            }
        });

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnCall.getTag().toString().equals("null")){
                    Toast.makeText(getApplicationContext(),"해당 업체의 전화번호가 없습니다.",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+btnCall.getTag().toString()));
                    startActivity(intent);
                }
            }
        });

        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnReview.getTag().toString().equals("0")){
                    Toast.makeText(getApplicationContext(),"방문기록이 없습니다",Toast.LENGTH_SHORT).show();
                }else{
                    new AlertDialog.Builder(FindCenter.this)
                            .setTitle("영수증 인증이 필요합니다.\nQR코드로 인증하시겠습니까??")
                            .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int j) {

                                    scanCode();


                                }
                            })
                            .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int j) {

                                }
                            })
                            .show();
                }
            }
        });




        btnEnroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtRev.length()!=0) {
                    llEnrollRev.setVisibility(View.GONE);
                    insCenRev();
                    revList.setVisibility(View.VISIBLE);
                    CenterRevList(mapItems.get(center_idx).idx);
                    edtRev.setText("");
                    ratEnroll.setRating(2.5F);
                }else{
                    Toast.makeText(getApplicationContext(),"리뷰 내용을 입력해주세요.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnLabor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LaborCost.class);
                startActivity(intent);
            }
        });


    }

    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("QR 코드를 스캔해 주세요");
        options.setBarcodeImageEnabled(false);
        options.setOrientationLocked(false);

        options.setCaptureActivity(QrCapture.class);
        barLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result ->
    {
        if(result.getContents() !=null){
            String tmp[];
            tmp = result.getContents().split(",");
            chkRecipt(tmp[0],tmp[1],tmp[2]);

        }
    });

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated()) { // 권한 거부됨
                naverMap.setLocationTrackingMode(LocationTrackingMode.None);
                return;
            } else {
                naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
            }
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
    }


    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);
        naverMap.setLocationSource(locationSource);
        ActivityCompat.requestPermissions(this, PERMISSIONS, LOCATION_PERMISSION_REQUEST_CODE);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);


        locmarker = new Marker();
        locmarker.setIcon(OverlayImage.fromResource(R.drawable.mypage));
        naverMap.addOnCameraChangeListener(new NaverMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(int i, boolean b) {

                locmarker.setPosition(new LatLng(naverMap.getCameraPosition().target.latitude,naverMap.getCameraPosition().target.longitude));
            }
        });

        naverMap.addOnCameraIdleListener(new NaverMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                locmarker.setPosition(new LatLng(naverMap.getCameraPosition().target.latitude, naverMap.getCameraPosition().target.longitude));
                locmarker.setMap(naverMap);


            }
        });

    }

    public void insCenRev() {

        //php url 입력
        String URL = "http://172.26.126.172:443/insCenRev.php";


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
                param.put("car_idx",shared_preferences.get_user_car(FindCenter.this));
                param.put("center_idx",mapItems.get(center_idx).idx);
                param.put("star",Float.toString(ratEnroll.getRating()));
                param.put("comment",edtRev.getText().toString());


                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }

    public void chkRecipt(String name,String latitude, String longitdue) {

        //php url 입력
        String URL = "http://172.26.126.172:443/chkRecipt.php";


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
                        parsing.ReciptParsing(json);
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }

                    if(parsing.name.equals(tvName.getText().toString())){
                        revList.setVisibility(View.GONE);
                        llEnrollRev.setVisibility(View.VISIBLE);
                    }else{
                        Toast.makeText(getApplicationContext(),"영수증정보를 확인해주세요",Toast.LENGTH_LONG).show();
                    }




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
                param.put("name",name);
                param.put("latitude",latitude);
                param.put("longitude",longitdue);

                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }




    public void CenterList(Double latitude, Double longitude) {
        String URL = "http://172.26.126.172:443/CenterList.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {


            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                markers = new ArrayList<Marker>();
                //응답이 되었을때 response로 값이 들어옴
                try {
                    mapItems = new ArrayList<mapItem>();

                    jsonObject = new JSONObject(response);

                    Parsing parsing = new Parsing();
                    try {
                        int index = 0;
                        mapItem mi;
                        while (true) {

                            parsing.mapParsing(response, index);
                            if (parsing.name == "") {

                                break;
                            }

                            String idx = parsing.idx;
                            String name = parsing.name;
                            String count = "리뷰 "+parsing.count;
                            String address = parsing.address;
                            String distance = parsing.distance + "km";
                            String latitude = parsing.latitude;
                            String longitude = parsing.longitude;
                            String rate = parsing.rate;
                            String phone = parsing.phone;
                            String visit = parsing.visit;
                            Marker marker = new Marker();
                            marker.setPosition(new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude)));
                            marker.setMap(naverMap);
                            marker.setTag(parsing.name);
                            markers.add(marker);
                            marker.setOnClickListener(new Overlay.OnClickListener() {
                                @Override
                                public boolean onClick(@NonNull Overlay overlay) {
                                    Toast.makeText(getApplicationContext(),marker.getTag().toString(),Toast.LENGTH_SHORT).show();
                                    return false;
                                }
                            });
                            index++;
                            mi = new mapItem(name, distance,address,count,idx,latitude,longitude,rate,phone,visit);
                            mapItems.add(mi);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mapadpt = new mapAdpt(FindCenter.this,R.layout.findcenterlist,mapItems);
                mapList.setAdapter(mapadpt);



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
                param.put("latitude",latitude.toString());
                param.put("longitude",longitude.toString());
                param.put("car_idx",shared_preferences.get_user_car(FindCenter.this));

                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);

    }

    public void CenterRevList(String center_idx) {
        String URL = "http://172.26.126.172:443/CenterRevList.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {


            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {

                //응답이 되었을때 response로 값이 들어옴
                try {
                    revItems = new ArrayList<revItem>();
                    jsonObject = new JSONObject(response);

                    Parsing parsing = new Parsing();
                    try {
                        int index = 0;
                        revItem mi;
                        while (true) {

                            parsing.centerRevParsing(response, index);
                            if (parsing.comment == "") {

                                break;
                            }

                            String comment = parsing.comment;
                            String date = parsing.date;
                            String rate = parsing.rate;
                            String visit = parsing.visit;

                            index++;
                            mi = new revItem(comment,date,rate,visit);
                            revItems.add(mi);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                revadpt = new revAdpt(FindCenter.this,R.layout.revlist,revItems);
                revList.setAdapter(revadpt);




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
                param.put("center_idx",center_idx);


                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);

    }
}

class mapItem {
    String name;
    String distance;
    String address;
    String cnt;
    String idx;
    String latitude;
    String longitude;
    String rate;
    String phone;
    String visit;
    mapItem(String name, String distance,String address, String cnt, String idx,String latitude, String longitude,String rate,String phone, String visit) {
        this.name=name;
        this.distance=distance;
        this.address=address;
        this.cnt=cnt;
        this.idx=idx;
        this.latitude=latitude;
        this.longitude=longitude;
        this.rate=rate;
        this.phone=phone;
        this.visit=visit;


    }
}

class mapAdpt extends BaseAdapter {
    @Override
    public long getItemId(int i) {
        return 0;
    }

    Context maincon;
    LayoutInflater Inflater;
    ArrayList<mapItem> arsrc;
    int layout;

    public mapAdpt(Context context, int alayout, ArrayList<mapItem> aarSrc) {
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
        TextView count = (TextView)convertView.findViewById(R.id.cnt);
        count.setText(arsrc.get(position).cnt);
        TextView address = (TextView)convertView.findViewById(R.id.address);
        address.setText(arsrc.get(position).address);
        TextView distance = (TextView)convertView.findViewById(R.id.distance);
        distance.setText(arsrc.get(position).distance);
        TextView rate = (TextView)convertView.findViewById(R.id.rate);
        rate.setText(arsrc.get(position).rate);
        RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.ratBar);
        ratingBar.setRating((float) ((Float.parseFloat(arsrc.get(position).rate))*0.2));

        return convertView;


    }

}

class revItem {
    String comment;
    String date;
    String rate;
    String visit;
    revItem(String comment, String date,String rate,String visit) {
        this.comment=comment;
        this.date=date;
        this.rate=rate;
        this.visit=visit;


    }
}

class revAdpt extends BaseAdapter {
    @Override
    public long getItemId(int i) {
        return 0;
    }

    Context maincon;
    LayoutInflater Inflater;
    ArrayList<revItem> arsrc;
    int layout;

    public revAdpt(Context context, int alayout, ArrayList<revItem> aarSrc) {
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
        return arsrc.get(position).comment;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        if (convertView == null) {
            convertView = Inflater.inflate(layout, parent, false);
        }

        TextView comment = (TextView)convertView.findViewById(R.id.comment);
        comment.setText(arsrc.get(position).comment);
        TextView date = (TextView)convertView.findViewById(R.id.date);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2 = new SimpleDateFormat("yy.MM.dd.E", Locale.KOREA);
        try {
            Date dd = format.parse(arsrc.get(position).date);

           date.setText(format2.format(dd.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        TextView visit = (TextView)convertView.findViewById(R.id.visit);
        visit.setText(arsrc.get(position).visit+ "번 방문자");

        TextView rate = (TextView)convertView.findViewById(R.id.rate);
        rate.setText(arsrc.get(position).rate);
        RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.ratBar);
        ratingBar.setRating((float) (Float.parseFloat(arsrc.get(position).rate)*0.2));

        return convertView;


    }

}

