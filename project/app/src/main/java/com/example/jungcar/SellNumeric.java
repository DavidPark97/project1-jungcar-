package com.example.jungcar;

import android.app.Activity ;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SellNumeric extends Activity {

    int sub;
    TextView tvColumn, tvCond;
    String strBrand, strCountry, strModel, strGrade, strMilesage, strPrice, isChk,strOption,strPlnumber;
    Button btnCancel, btnModify;
    EditText edtCond;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sellnumeric);
        tvColumn = (TextView) findViewById(R.id.tvColumn);
        tvCond = (TextView)findViewById(R.id.tvCond);

        edtCond = (EditText) findViewById(R.id.edtCond);
        btnCancel =(Button)findViewById(R.id.btnCancel);
        btnModify = (Button)findViewById(R.id.btnModify);
        Intent intent = getIntent();
        strPlnumber = intent.getExtras().getString("strPlnumber","");
        strBrand = intent.getExtras().getString("strBrand","");
        strCountry= intent.getExtras().getString("strCountry","");
        strMilesage = intent.getExtras().getString("strMilesage","");
        strModel = intent.getExtras().getString("strModel","");
        strGrade = intent.getExtras().getString("strGrade","");
        strPrice = intent.getExtras().getString("strPrice","");
        isChk = intent.getExtras().getString("isChk","");
        strOption = intent.getExtras().getString("strOption","");
        sub = intent.getExtras().getInt("sub");


        if(sub==2){
            tvColumn.setText("주행거리");
            tvCond.setText("KM");
            if(!strMilesage.equals("")){
                edtCond.setText(strMilesage);
            }
        }else if(sub==1){
            tvColumn.setText("가격");
            if(!strPrice.equals("")){
                edtCond.setText(strPrice);
            }
        }
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getApplicationContext(), SetSellCar.class);
                intent.putExtra("strBrand", strBrand);
                intent.putExtra("strModel", strModel);
                intent.putExtra("strPlnumber",strPlnumber);
                intent.putExtra("strGrade", strGrade);
                intent.putExtra("strMilesage", strMilesage);
                intent.putExtra("strPrice", strPrice);
                intent.putExtra("strCountry", strCountry);
                intent.putExtra("strOption", strOption);
                intent.putExtra("isChk", isChk);
                startActivity(intent);


            }
        });

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(sub==2){
                    strMilesage = edtCond.getText().toString();

                }else if(sub==1){
                    strPrice = edtCond.getText().toString();

                }

                Intent intent = new Intent(getApplicationContext(), SetSellCar.class);
                intent.putExtra("strBrand", strBrand);
                intent.putExtra("strModel", strModel);
                intent.putExtra("strPlnumber",strPlnumber);
                intent.putExtra("strGrade", strGrade);
                intent.putExtra("strMilesage", strMilesage);
                intent.putExtra("strPrice", strPrice);
                intent.putExtra("strOption", strOption);
                intent.putExtra("strCountry", strCountry);
                intent.putExtra("isChk", isChk);
                startActivity(intent);

            }
        });
    }
}