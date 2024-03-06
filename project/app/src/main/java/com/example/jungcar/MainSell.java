package com.example.jungcar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainSell extends AppCompatActivity {
    TextView tvSell,tvListed,tvQna,tvToComm,tvDealer;
    Button btnEnroll;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),MainPage.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.mainsell);
        tvSell = (TextView) findViewById(R.id.tvSell);
        tvListed = (TextView) findViewById(R.id.tvListed);
        tvQna  = (TextView) findViewById(R.id.tvQna);
        tvToComm = (TextView) findViewById(R.id.tvToComm);
        btnEnroll = (Button) findViewById(R.id.btnEnroll);
        tvDealer = (TextView)findViewById(R.id.tvDealer);

        tvDealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Dealer.class);
                startActivity(intent);
            }
        });

        tvToComm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Board.class);
                intent.putExtra("board_type","7");
                startActivity(intent);
            }
        });

        tvListed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyPageSell.class);
                startActivity(intent);

            }
        });

        tvQna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), QnA.class);
                startActivity(intent);
                overridePendingTransition(R.anim.sliding, 0);
            }
        });




        btnEnroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shared_preferences.get_user_email(MainSell.this).isEmpty()==true) {
                    new AlertDialog.Builder(MainSell.this)
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
                }else{
                   Intent intent = new Intent(getApplicationContext(), SetSellCar.class);
                    intent.putExtra("strBrand","");
                    intent.putExtra("strCountry","");
                    intent.putExtra("strMilesage","");
                    intent.putExtra("strGrade","");
                    intent.putExtra("strPrice","");
                    intent.putExtra("strModel","");
                    intent.putExtra("strOption","");

                    intent.putExtra("isChk","n");


                   startActivity(intent);
                }
            }
        });
    }
}
