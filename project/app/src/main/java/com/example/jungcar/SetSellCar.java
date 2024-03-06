package com.example.jungcar;

import static org.opencv.core.Core.BORDER_CONSTANT;
import static org.opencv.core.Core.copyMakeBorder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetSellCar extends AppCompatActivity {
    static StringRequest request;
    static RequestQueue requestQueue;
    String encodeImageString;
    String json;
    Button  btnEnroll,btnSearch,btnImgSearch;
    ImageButton btnBack, btnBrand, btnModel, btnGrade, btnMilesage, btnPrice, btnCountry, rstBrand, rstModel, rstGrade, rstMilesage, rstPrice, rstCountry,btnOption,rstOption;
    TextView tvBrand, tvModel, tvGrade, tvMilesage, tvPrice,tvCountry,tvOption, tvPhone;
    CheckBox chkOwn,chkAgree,chkInput;
    EditText edtPlnumber, edtDetail;
    ProgressBar progress;
    ImageView imageView;
    Bitmap bitmap, temp;

    Bitmap result;
    DecimalFormat decimalFormat = new DecimalFormat("###,###");
    int CAMERA_CODE = 100;

    int SELECT_CODE = 100;
    String strBrand,strPlnumber, strCountry, strModel, strGrade, strMilesage, strPrice, isChk,strOption, isAgree="n",connection_idx,sell_idx,car_idx;

    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),MainSell.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setsellcar);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnEnroll=(Button) findViewById(R.id.btnEnroll);
        btnSearch=(Button)findViewById(R.id.btnSearch) ;
        edtPlnumber=(EditText)findViewById(R.id.edtPlnumber);
        progress=(ProgressBar)findViewById(R.id.progress);
        edtDetail=(EditText)findViewById(R.id.edtDetail);
        btnImgSearch=(Button)findViewById(R.id.btnImgSearch);
        chkInput = (CheckBox)findViewById(R.id.chkInput);
        btnBrand = (ImageButton) findViewById(R.id.btnBrand);
        btnModel = (ImageButton) findViewById(R.id.btnModel);
        btnGrade = (ImageButton) findViewById(R.id.btnGrade);
        btnMilesage = (ImageButton) findViewById(R.id.btnMilesage);
        btnPrice = (ImageButton) findViewById(R.id.btnPrice);
        btnCountry = (ImageButton) findViewById(R.id.btnCountry);
        rstBrand = (ImageButton) findViewById(R.id.rstBrand);
        rstModel = (ImageButton) findViewById(R.id.rstModel);
        rstGrade = (ImageButton) findViewById(R.id.rstGrade);
        rstMilesage = (ImageButton) findViewById(R.id.rstMilesage);
        rstPrice = (ImageButton) findViewById(R.id.rstPrice);
        rstCountry = (ImageButton) findViewById(R.id.rstCountry);
        tvBrand = (TextView) findViewById(R.id.tvBrand);
        tvModel = (TextView) findViewById(R.id.tvModel);
        tvGrade = (TextView) findViewById(R.id.tvGrade);
        tvMilesage = (TextView) findViewById(R.id.tvMilesage);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvPhone = (TextView)findViewById(R.id.tvPhone);
        tvCountry = (TextView)findViewById(R.id.tvCountry);
        chkOwn = (CheckBox) findViewById(R.id.chkOwn);
        chkAgree=(CheckBox) findViewById(R.id.chkAgree);
        tvOption=(TextView) findViewById(R.id.tvOption);
        btnOption=(ImageButton) findViewById(R.id.btnOption);
        rstOption=(ImageButton) findViewById(R.id.rstOption);

        if (requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }
        edtPlnumber.setInputType(InputType.TYPE_NULL);

        Intent intent = getIntent();
        strPlnumber = intent.getExtras().getString("strPlnumber","");
        strBrand = intent.getExtras().getString("strBrand","");
        strCountry= intent.getExtras().getString("strCountry","");
       strMilesage = intent.getExtras().getString("strMilesage","");
        strModel = intent.getExtras().getString("strModel","");
        strGrade = intent.getExtras().getString("strGrade","");
        strPrice = intent.getExtras().getString("strPrice","");
        strOption = intent.getExtras().getString("strOption","");

        isChk = intent.getExtras().getString("isChk","n");

        setPhone();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(getApplicationContext(),MainSell.class);
                    startActivity(intent);

            }
        });



        if(!strBrand.equals("")) {
            tvBrand.setText(strBrand);
        }

        if(!strModel.equals("")) {
            tvModel.setText(strModel);
        }

        if(!strGrade.equals("")){
            tvGrade.setText(strGrade);
        }

        if(!strPlnumber.equals("")){
            chkInput.setChecked(true);
            edtPlnumber.setText(strPlnumber);

        }

        if(!strOption.equals("")){
            tvOption.setText(strOption);
        }

        if(!strMilesage.equals("")) {

            tvMilesage.setText(decimalFormat.format(Integer.parseInt(strMilesage)) + "km");
        }

        if(!strPrice.equals("")) {
            tvPrice.setText(decimalFormat.format(Integer.parseInt(strPrice)) + "만원");
        }


        if(!strCountry.equals("")) {
            tvCountry.setText(strCountry);
        }

        if(isChk.equals("y")){
            chkOwn.setChecked(true);
        }

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchPlnumber();
            }
        });

        chkInput.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    btnSearch.setClickable(true);
                    edtPlnumber.setInputType(InputType.TYPE_CLASS_TEXT);
                }else{
                    btnSearch.setClickable(false);
                    edtPlnumber.setInputType(InputType.TYPE_NULL);
                }
            }
        });


        chkOwn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(chkOwn.isChecked()==false){
                    isChk = "n";

                }else{
                    isChk = "y";
                }

            }
        });

        chkAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(chkAgree.isChecked()==false){
                    isAgree = "n";

                }else{
                    isAgree = "y";
                }
            }
        });

        rstCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvCountry.setText(" ");
                strCountry = "";

            }
        });
        rstPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvPrice.setText(" ");
                strPrice="";

            }
        });

        rstMilesage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvMilesage.setText(" ");
                strMilesage = "";


            }
        });
        rstOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvOption.setText(" ");
                strOption="";
            }
        });

        rstGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvGrade.setText(" ");
                strGrade = "";


            }
        });

        rstModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvModel.setText(" ");
                strModel= "";
                tvGrade.setText(" ");
                strGrade = "";
                tvOption.setText(" ");
                strOption="";

            }
        });
        rstBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvBrand.setText(" ");
                strBrand = "";
                tvModel.setText(" ");
                strModel= "";
                tvGrade.setText(" ");
                strGrade = "";
                tvOption.setText(" ");
                strOption="";
            }
        });

        btnBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SellCondition.class);
                intent.putExtra("sub",1);
                intent.putExtra("strPlnumber",strPlnumber);
                intent.putExtra("strBrand",strBrand);
                intent.putExtra("strModel",strModel);
                intent.putExtra("strGrade",strGrade);
                intent.putExtra("strMilesage",strMilesage);
                intent.putExtra("strPrice",strPrice);
                intent.putExtra("strCountry",strCountry);
                intent.putExtra("strOption",strOption);
                intent.putExtra("isChk",isChk);

                startActivity(intent);
            }
        });

        btnModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SellCondition.class);
                intent.putExtra("sub",2);
                intent.putExtra("strPlnumber",strPlnumber);
                intent.putExtra("strBrand",strBrand);
                intent.putExtra("strModel",strModel);
                intent.putExtra("strGrade",strGrade);
                intent.putExtra("strMilesage",strMilesage);
                intent.putExtra("strPrice",strPrice);
                intent.putExtra("strCountry",strCountry);
                intent.putExtra("strOption",strOption);
                intent.putExtra("isChk",isChk);

                startActivity(intent);


            }
        });

        btnGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SellCondition.class);
                intent.putExtra("sub",3);
                intent.putExtra("strPlnumber",strPlnumber);
                intent.putExtra("strBrand",strBrand);
                intent.putExtra("strModel",strModel);
                intent.putExtra("strGrade",strGrade);
                intent.putExtra("strMilesage",strMilesage);
                intent.putExtra("strPrice",strPrice);
                intent.putExtra("strCountry",strCountry);
                intent.putExtra("strOption",strOption);
                intent.putExtra("isChk",isChk);

                startActivity(intent);




            }
        });

        btnOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SellCondition.class);
                intent.putExtra("sub",4);
                intent.putExtra("strPlnumber",strPlnumber);
                intent.putExtra("strBrand",strBrand);
                intent.putExtra("strModel",strModel);
                intent.putExtra("strGrade",strGrade);
                intent.putExtra("strMilesage",strMilesage);
                intent.putExtra("strPrice",strPrice);
                intent.putExtra("strCountry",strCountry);
                intent.putExtra("strOption",strOption);
                intent.putExtra("isChk",isChk);

                startActivity(intent);

                startActivity(intent);
            }
        });

        btnCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), SetCountry.class);
                intent.putExtra("strBrand",strBrand);
                intent.putExtra("strPlnumber",strPlnumber);
                intent.putExtra("strModel",strModel);
                intent.putExtra("strGrade",strGrade);
                intent.putExtra("strMilesage",strMilesage);
                intent.putExtra("strPrice",strPrice);
                intent.putExtra("strCountry",strCountry);
                intent.putExtra("strOption",strOption);
                intent.putExtra("isChk",isChk);

                startActivity(intent);

            }
        });

        btnPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), SellNumeric.class);
                intent.putExtra("sub",1);
                intent.putExtra("strBrand",strBrand);
                intent.putExtra("strModel",strModel);
                intent.putExtra("strGrade",strGrade);
                intent.putExtra("strPlnumber",strPlnumber);
                intent.putExtra("strMilesage",strMilesage);
                intent.putExtra("strPrice",strPrice);
                intent.putExtra("strCountry",strCountry);
                intent.putExtra("strOption",strOption);
                intent.putExtra("isChk",isChk);

                startActivity(intent);

            }
        });

        btnMilesage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), SellNumeric.class);
                intent.putExtra("sub",2);
                intent.putExtra("strBrand",strBrand);
                intent.putExtra("strPlnumber",strPlnumber);
                intent.putExtra("strModel",strModel);
                intent.putExtra("strGrade",strGrade);
                intent.putExtra("strMilesage",strMilesage);
                intent.putExtra("strPrice",strPrice);
                intent.putExtra("strCountry",strCountry);
                intent.putExtra("strOption",strOption);
                intent.putExtra("isChk",isChk);

                startActivity(intent);

            }
        });


        btnImgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,SELECT_CODE);
            }
        });




       btnEnroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!strPlnumber.isEmpty() && !strBrand.isEmpty() && !strCountry.isEmpty() && !strOption.isEmpty() && !strGrade.isEmpty() && !strMilesage.isEmpty() && !strPrice.isEmpty() && !strModel.isEmpty()) {
                    EnrollSell();
                } else {
                    Toast.makeText(getApplicationContext(), "입력하지 않은 항목 존재", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    protected  int getContourCount(Mat matSubContour, Rect rcComp) {
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(matSubContour, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
        int nCount = 0;
        float fHeight, fWidth;
        float fCompHeight = rcComp.height;
        for(int idx = 0; idx < contours.size(); idx++) {
            MatOfPoint matOfPoint = contours.get(idx);
            Rect rect = Imgproc.boundingRect(matOfPoint);

            fHeight = rect.height;
            fWidth = rect.width;
            if (rect.width < rect.height && fHeight / fWidth > 1.2 && fHeight / fWidth < 3.0 && fCompHeight / fHeight < 2.1 && fCompHeight / fHeight > 1.2) {
                nCount++;
            }
        }
        return nCount;
    }

    void getPermission() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 101);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_CODE && data != null) {
            //bitmap = (Bitmap) data.getExtras().get("data");
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),data.getData());


                OpenCVLoader.initDebug();

                result = bitmap;

                float ratio;

                Mat matBase = new Mat();
                Utils.bitmapToMat(bitmap ,matBase);
                Mat matGray = new Mat();
                Scalar color = new Scalar( 255, 255, 255 );
                Mat matBorder = new Mat();
                Mat matGau = new Mat();
                Mat matThresh = new Mat();
                Mat matErode = new Mat();
                Mat matDilate = new Mat();

                Imgproc.cvtColor(matBase, matGray, Imgproc.COLOR_BGR2GRAY); // GrayScale

                Imgproc.GaussianBlur(matGray, matGau, new org.opencv.core.Size(5,5), 0); //가우시안 블러로 노이즈 제거

                Imgproc.adaptiveThreshold(matGau, matThresh, 255.0, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY_INV, 19, 9);

                List<MatOfPoint> contours = new ArrayList<>();
                Mat hierarchy = new Mat();
                Imgproc.findContours(matThresh, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE); //컨투어 생성

                float MIN_RATIO = (float) 4.0, MAX_RATIO = (float) 5.1, MIN_AREA = (float) 250.0, MAX_AREA = (float) 5000.0, MIN_NUM = (float) 1.1, MAX_NUM = (float) 1.4;

                int count = 0;

                for(int idx = 0; idx >= 0; idx = (int) hierarchy.get(0, idx)[0]) {
                    MatOfPoint matOfPoint = contours.get(idx);
                    Rect rect = Imgproc.boundingRect(matOfPoint);

                    ratio = (float)rect.width / (float)rect.height;

                    if(ratio < MAX_RATIO && ratio > MIN_RATIO && MIN_AREA < Imgproc.contourArea(matOfPoint)) {
                        Imgproc.rectangle(matGray, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 0, 255), 2);
                        Mat matRoi = matThresh.submat(rect);

                        //번호판이 맞는지 확인
                        count = getContourCount(matRoi, rect);
                        if(count >= 6) {//인식된 글자갯수 체크
                            result = Bitmap.createBitmap(bitmap, (int) rect.tl().x, (int) rect.tl().y, rect.width, rect.height);
                        }
                    }
                    /*if(Imgproc.contourArea(matOfPoint) > MIN_AREA && rect.width > MIN_WIDTH && rect.height > MIN_HEIGHT && MIN_RATIO < ratio && ratio < MAX_RATIO) {
                    possible_contours.add(matOfPoint);
                    count++;
                    Imgproc.rectangle(matGray, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 0, 255), 2);
                    }*/


                }
                Utils.bitmapToMat(result ,matBase);

                Imgproc.cvtColor(matBase, matGray, Imgproc.COLOR_BGR2GRAY); // GrayScale

                Imgproc.GaussianBlur(matGray, matGau, new org.opencv.core.Size(5,5), 0); //가우시안 블러로 노이즈 제거

                Imgproc.adaptiveThreshold(matGau, matThresh, 255.0, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY_INV, 19, 9);

                Point anchor = new Point(-1,-1);

                Imgproc.erode(matThresh, matErode, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2,2)), anchor ,3);

                Imgproc.dilate(matErode, matDilate, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(13,13)));

                List<MatOfPoint> pContours = new ArrayList<>();
                List<MatOfPoint> letter = new ArrayList<>();

                float numHeight = 0;

                Mat pHierarchy = new Mat();
                Imgproc.findContours(matDilate, pContours, pHierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE); //컨투어 생성
                for(int idx = 0; idx >= 0; idx = (int) pHierarchy.get(0, idx)[0]) {
                    MatOfPoint matOfPoint = pContours.get(idx);
                    Rect rect = Imgproc.boundingRect(matOfPoint);

                    ratio = (float)rect.width / (float)rect.height;
                    Log.d("height", "height : " + matDilate.height());
                    Log.d("height", "rect.height : " + rect.height);
                    Log.d("height", "ratio : " + ratio);
                    Log.d("height", "rectAndMat : " + ((float)rect.height / (float)matDilate.height()));



                    if(ratio < 0.8 && ratio > 0.3 && (float)rect.height / (float)matDilate.height() < 0.80 && (float)rect.height / (float)matDilate.height() > 0.5){
                        if(numHeight == 0){
                            letter.add(pContours.get(idx));
                        }
                        else if(numHeight != 0 && numHeight / (float)rect.height > 0.9 && numHeight / (float)rect.height < 1.1){
                            letter.add(pContours.get(idx));
                        }
                        numHeight = (float)rect.height;
                    }
                }

                //컨투러스를 x 순서로 정렬
                Collections.sort(letter, new Comparator<MatOfPoint>() {
                    public int compare(MatOfPoint o1, MatOfPoint o2) {
                        Rect rect1 = Imgproc.boundingRect(o1);
                        Rect rect2 = Imgproc.boundingRect(o2);



                        if (rect1.x > rect2.x){
                            return 1;
                        }
                        else if (rect1.x < rect2.x){
                            return -1;
                        }
                        else
                            return 0;
                    }
                });

                ArrayList<String> items = new ArrayList<String>();

                for(int idx = 0; idx < (int)letter.size(); idx++ ) {
                    MatOfPoint matOfPoint = letter.get(idx);
                    Rect rect = Imgproc.boundingRect(matOfPoint);
                    temp = Bitmap.createBitmap(result, (int) rect.tl().x, (int) rect.tl().y, rect.width, rect.height);
                    Utils.bitmapToMat(temp, matBorder);
                    Utils.matToBitmap(matBorder, temp);
                    copyMakeBorder(matBorder, matBorder,50, 50, 50 ,50, BORDER_CONSTANT, color);
                    String tmp = encodeBitmapImage(Bitmap.createBitmap(temp));
                    items.add(tmp);
                    if(idx == 1 || idx == 2){
                        if((Imgproc.boundingRect(letter.get(idx)).x + (float)Imgproc.boundingRect(letter.get(idx)).height * 0.6 * 2) < Imgproc.boundingRect(letter.get(idx + 1)).x) {
                            tmp = encodeBitmapImage(Bitmap.createBitmap(result, (int) (rect.tl().x + rect.width), (int)rect.tl().y, rect.width*2, rect.height));
                            items.add(tmp);
                        }
                    }

                }


                for(int i=0;i<items.size();i++){
                    insImg(items.get(i),i);
                }
                progress.setVisibility(View.VISIBLE);
                setPlnumber(Integer.toString(items.size()));

                Utils.matToBitmap(matDilate, result);


                //Imgproc.dilate(matThresh, matDilate, matKernel); //엣지 테두리 더 굵게 처리

                /*List<MatOfPoint> contours = new ArrayList<>();
                Mat hierarchy = new Mat();
                Mat matContour = new Mat();
                Imgproc.cvtColor(matDilate, matContour, Imgproc.COLOR_GRAY2BGR);
                Imgproc.findContours(matDilate, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

                Imgproc.adaptiveThreshold(matGray, matGray, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 15, 20);
                imgRoi =  Bitmap.createBitmap(matDilate.cols(), matDilate.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(matDilate, imgRoi);

                int nCount;

                //번호판 크기와 같은지 확인
                for(int idx = 0; idx >= 0; idx = (int) hierarchy.get(0, idx)[0]) {
                    MatOfPoint matOfPoint = contours.get(idx);

                    if(Imgproc.contourArea(matOfPoint) < 1000.0)
                        continue;

                    Rect rect = Imgproc.boundingRect(matOfPoint);

                    ratio = (float)rect.width / (float)rect.height;
                    if (rect.width < 30 || rect.height < 30 || rect.width <= rect.height || ratio < 1.7 || ratio > 5.0)
                        continue;

                    Mat matRoi = matThresh.submat(rect);

                    //번호판이 맞는지 확인
                    Imgproc.rectangle(matContour, rect.tl(), rect.br(), new Scalar(0, 255,0), 2);
                    nCount = getContourCount(matContour, matRoi, rect);
                    if(nCount < 6 || nCount > 9) //인식된 글자갯수 체크
                        continue;
                    //최종선택 번호판 유형 표시
                    Imgproc.rectangle(matContour, rect.tl(), rect.br(), new Scalar(255, 0,0), 2);
                    roi = Bitmap.createBitmap( imgRoi, (int)rect.tl().x, (int)rect.tl().y, rect.width, rect.height);

                    imageView.setImageBitmap(roi);
                }

                 */
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101 && grantResults.length > 0) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                getPermission();
            }
        }
    }

    private String encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] bytesOfImage = byteArrayOutputStream.toByteArray();


        encodeImageString = android.util.Base64.encodeToString(bytesOfImage, Base64.DEFAULT);
        return encodeImageString;
    }

    public void EnrollSell() {

        //php url 입력
        String URL = "http://172.26.126.172:443/EnrollSell.php";


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


                        parsing.connectionParsing(json);;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    connection_idx=parsing.idx;
                    sell_idx=parsing.name;
                    car_idx = parsing.car;
                    Intent intent = new Intent(getApplicationContext(), SetOption.class);
                    intent.putExtra("connection_idx",connection_idx);
                    intent.putExtra("sell_idx",sell_idx);
                    intent.putExtra("car_idx",car_idx);
                    startActivity(intent);
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


                param.put("plnumber",strPlnumber);
                param.put("brand",strBrand);
                param.put("model",strModel);
                param.put("fuel_name",strGrade);
                param.put("opt_grade_name",strOption);
                param.put("mileage",strMilesage);
                param.put("price",strPrice);
                param.put("country",strCountry);
                param.put("own",isChk);
                param.put("agree",isAgree);
                param.put("detail",edtDetail.getText().toString());
                param.put("user_idx",shared_preferences.get_user_email(SetSellCar.this));


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



                param.put("user_idx",shared_preferences.get_user_email(SetSellCar.this));


                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }

    public void insImg(String encode,int flag) {

        //php url 입력
        String URL = "http://172.26.126.172:443/insImg2.php";


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
                param.put("user_idx",shared_preferences.get_user_email(SetSellCar.this));



                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }

    public void setPlnumber(String size) {

        //php url 입력
        String URL = "http://172.26.126.172:443/plnumber.php";


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
                        parsing.plnumberParsing(json);
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                    progress.setVisibility(View.INVISIBLE);
                    strBrand = parsing.brand;
                    strModel = parsing.model;
                    strGrade = parsing.fuel;
                    strPlnumber=parsing.plnumber;
                    edtPlnumber.setText(strPlnumber);
                    tvBrand.setText(strBrand);
                    tvModel.setText(strModel);
                    tvGrade.setText(strGrade);

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
                param.put("user_idx",shared_preferences.get_user_email(SetSellCar.this));
                param.put("size",size);
                return param;
            }
        };


        request.setRetryPolicy(new com.android.volley.DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);
        requestQueue.add(request);


    }
    public void SearchPlnumber() {

        //php url 입력
        String URL = "http://172.26.126.172:443/SearchPlnumber.php";


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
                        parsing.plnumberParsing(json);
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }

                    progress.setVisibility(View.INVISIBLE);
                    strBrand = parsing.brand;
                    strModel = parsing.model;
                    strGrade = parsing.fuel;
                    strPlnumber=parsing.plnumber;
                    edtPlnumber.setText(strPlnumber);
                    tvBrand.setText(strBrand);
                    tvModel.setText(strModel);
                    tvGrade.setText(strGrade);

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
                param.put("plnumber",edtPlnumber.getText().toString());

                return param;
            }
        };


        request.setShouldCache(false);
        requestQueue.add(request);


    }
}
