package com.example.jungcar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class shQna extends AppCompatActivity {
    Button btnClose;
    ImageView imgQna;
    String strImg;
    @Override
    protected void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.shqna);
        btnClose = (Button) findViewById(R.id.btnClose);
        imgQna = (ImageView) findViewById(R.id.imgQna);

        Intent intent = getIntent();
        strImg = intent.getExtras().getString("strImg");
        String imgurl = "http://172.26.126.172:443/qna/"+strImg;
        Glide.with(shQna.this).load(imgurl).into(imgQna);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }
}
