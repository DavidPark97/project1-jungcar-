package com.example.jungcar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class LaborCost extends AppCompatActivity {
    ImageButton btnBack;
    ImageView img;
    Button btnEngine, btnMission,btnBreak, btnAnti,btnPower, btnFilter,btnIgnite,btnAir,btnGene,btnSelf,btnJoint,btnBattery;
    LinearLayout llImg;
    int flag=0;
    @Override
    protected void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.laborcost);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnEngine = (Button) findViewById(R.id.btnEngine);
        btnMission = (Button) findViewById(R.id.btnMission);
        btnBreak = (Button) findViewById(R.id.btnBreak);
        btnAnti = (Button) findViewById(R.id.btnAnti);
        btnBattery = (Button)findViewById(R.id.btnBattery);
        btnPower = (Button) findViewById(R.id.btnPower);
        btnFilter = (Button) findViewById(R.id.btnFilter);
        btnIgnite = (Button) findViewById(R.id.btnIgnite);
        btnAir = (Button) findViewById(R.id.btnAir);
        btnGene = (Button) findViewById(R.id.btnGene);
        btnSelf = (Button) findViewById(R.id.btnSelf);
        btnJoint = (Button) findViewById(R.id.btnJoint);
        img = (ImageView)findViewById(R.id.img);
        llImg = (LinearLayout)findViewById(R.id.llImg);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==0){
                    finish();
                }else if(flag==1){
                    llImg.setVisibility(View.INVISIBLE);
                    flag=0;
                }
            }
        });

        btnEngine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imgurl = "http://172.26.126.172:443/labor/engine.jpg";
                Glide.with(LaborCost.this).load(imgurl).into(img);
                llImg.setVisibility(View.VISIBLE);
                flag=1;
            }
        });

        btnMission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imgurl = "http://172.26.126.172:443/labor/mission.png";
                Glide.with(LaborCost.this).load(imgurl).into(img);
                llImg.setVisibility(View.VISIBLE);
                flag=1;
            }
        });

                btnBreak.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String imgurl = "http://172.26.126.172:443/labor/break.png";
                        Glide.with(LaborCost.this).load(imgurl).into(img);
                        llImg.setVisibility(View.VISIBLE);
                        flag=1;
                    }
                });
                        btnAnti.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String imgurl = "http://172.26.126.172:443/labor/anti.png";
                                Glide.with(LaborCost.this).load(imgurl).into(img);
                                llImg.setVisibility(View.VISIBLE);
                                flag=1;
                            }
                        });
                        btnFilter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String imgurl = "http://172.26.126.172:443/labor/filter.png";
                                Glide.with(LaborCost.this).load(imgurl).into(img);
                                llImg.setVisibility(View.VISIBLE);
                                flag=1;
                            }
                        });
                        btnIgnite.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String imgurl = "http://172.26.126.172:443/labor/ignite.png";
                                Glide.with(LaborCost.this).load(imgurl).into(img);
                                llImg.setVisibility(View.VISIBLE);
                                flag=1;
                            }
                        });
                        btnAir.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String imgurl = "http://172.26.126.172:443/labor/air.png";
                                Glide.with(LaborCost.this).load(imgurl).into(img);
                                llImg.setVisibility(View.VISIBLE);
                                flag=1;
                            }
                        });
                        btnGene.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String imgurl = "http://172.26.126.172:443/labor/gene.png";
                                Glide.with(LaborCost.this).load(imgurl).into(img);
                                llImg.setVisibility(View.VISIBLE);
                                flag=1;
                            }
                        });
                        btnSelf.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String imgurl = "http://172.26.126.172:443/labor/self.png";
                                Glide.with(LaborCost.this).load(imgurl).into(img);
                                llImg.setVisibility(View.VISIBLE);
                                flag=1;
                            }
                        });
                        btnJoint.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String imgurl = "http://172.26.126.172:443/labor/joint.png";
                                Glide.with(LaborCost.this).load(imgurl).into(img);
                                llImg.setVisibility(View.VISIBLE);
                                flag=1;
                            }
                        });
                        btnBattery.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String imgurl = "http://172.26.126.172:443/labor/battery.png";
                                Glide.with(LaborCost.this).load(imgurl).into(img);
                                llImg.setVisibility(View.VISIBLE);
                                flag=1;
                            }
                        });
                        btnPower.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String imgurl = "http://172.26.126.172:443/labor/power.png";
                                Glide.with(LaborCost.this).load(imgurl).into(img);
                                llImg.setVisibility(View.VISIBLE);
                                flag=1;
                            }
                        });
    }

}
