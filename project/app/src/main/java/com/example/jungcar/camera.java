package com.example.jungcar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class camera extends AppCompatActivity {
    static {
        System.loadLibrary("jungcar");
    }

    Button camera;
    Button select;
    ImageView imageView;
    Bitmap bitmap;
    Bitmap result;

    int CAMERA_CODE = 100;

    int SELECT_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (OpenCVLoader.initDebug()) Log.d("LOADED", "success");
        else Log.d("LOADED", "error");

        getPermission();

        camera = findViewById(R.id.camera);
        select = findViewById(R.id.select);
        imageView = findViewById(R.id.imageView);


        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_CODE);
            }
        });
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,SELECT_CODE);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_CODE && data != null) {
            //bitmap = (Bitmap) data.getExtras().get("data");
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),data.getData());
                imageView.setImageBitmap(bitmap);

                OpenCVLoader.initDebug();

                result = bitmap;

                float ratio;

                boolean carPlate = new Boolean(false);

                Mat matBase = new Mat();
                Utils.bitmapToMat(bitmap ,matBase);
                Mat matGray = new Mat();
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
                            carPlate = true;
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

                for(int idx = 0; idx < (int)letter.size(); idx++ ) {
                    MatOfPoint matOfPoint = letter.get(idx);
                    Rect rect = Imgproc.boundingRect(matOfPoint);

                    Imgproc.rectangle(matDilate, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(255, 0, 0), 4);
                }
                Utils.matToBitmap(matDilate, result);

                imageView.setImageBitmap(result);

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101 && grantResults.length > 0) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                getPermission();
            }
        }
    }
}
