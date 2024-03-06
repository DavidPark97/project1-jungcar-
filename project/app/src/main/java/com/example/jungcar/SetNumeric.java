package com.example.jungcar;

import android.app.Activity ;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SetNumeric extends Activity {

    int sub, pageflag;
    TextView tvColumn, smlCon, bigCon;
    String strBrand, strCountry, strModel, strGrade, minMilesage,maxMilesage, minPrice, maxPrice, isChk,strName,strTotal;
    Button btnCancel, btnModify;
    EditText edtMin, edtMax;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.setnumeric);
        tvColumn = (TextView) findViewById(R.id.tvColumn);
        smlCon = (TextView)findViewById(R.id.smlCon);
        bigCon = (TextView)findViewById(R.id.bigCon);
        edtMin = (EditText) findViewById(R.id.edtMin);
        edtMax = (EditText) findViewById(R.id.edtMax);
        btnCancel =(Button)findViewById(R.id.btnCancel);
        btnModify = (Button)findViewById(R.id.btnModify);
        Intent intent = getIntent();
        strBrand = intent.getExtras().getString("strBrand", "");
        strCountry = intent.getExtras().getString("strCountry", "");
        minMilesage = intent.getExtras().getString("minMilesage", "");
        maxMilesage = intent.getExtras().getString("maxMilesage", "");
        strModel = intent.getExtras().getString("strModel", "");
        strGrade = intent.getExtras().getString("strGrade", "");
        minPrice = intent.getExtras().getString("minPrice", "");
        maxPrice = intent.getExtras().getString("maxPrice", "");
        isChk = intent.getExtras().getString("isChk", "n");
        strName = intent.getExtras().getString("strName","");
        strTotal = intent.getExtras().getString("strTotal","");
        sub = intent.getExtras().getInt("sub");
        pageflag = intent.getExtras().getInt("pageflag");

        if(sub==2){
            tvColumn.setText("주행거리");
            smlCon.setText("KM");
            bigCon.setText("KM");
            if(!minMilesage.equals("")){
                edtMin.setText(minMilesage);
                edtMax.setText(maxMilesage);
            }
        }else if(sub==1){
            tvColumn.setText("가격");
            if(!minPrice.equals("")){
                edtMin.setText(minPrice);
                edtMax.setText(maxPrice);
            }

        }
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pageflag==1) {
                    Intent intent = new Intent(getApplicationContext(), BuyOption.class);
                    intent.putExtra("strName",strName);
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

                    finish();
                }else{
                    Intent intent = new Intent(getApplicationContext(), ShResult.class);
                    intent.putExtra("strName",strName);
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
            }
        });

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cl;
                if (edtMin.getText().toString().equals("") || edtMax.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "값을 입력하세요", Toast.LENGTH_SHORT).show();
                }else if(Integer.parseInt(edtMin.getText().toString())>Integer.parseInt(edtMax.getText().toString())){
                    Toast.makeText(getApplicationContext(), "최솟값이 더 큽니다", Toast.LENGTH_SHORT).show();
                }
                    else {
                    if (sub == 2) {
                        minMilesage = edtMin.getText().toString();
                        maxMilesage = edtMax.getText().toString();
                    } else if (sub == 1) {
                        minPrice = edtMin.getText().toString();
                        maxPrice = edtMax.getText().toString();
                    }

                    if (pageflag == 1) {
                        Intent intent = new Intent(getApplicationContext(), BuyOption.class);
                        intent.putExtra("strName", strName);
                        intent.putExtra("strBrand", strBrand);
                        intent.putExtra("strModel", strModel);
                        intent.putExtra("strGrade", strGrade);
                        intent.putExtra("minMilesage", minMilesage);
                        intent.putExtra("maxMilesage", maxMilesage);
                        intent.putExtra("minPrice", minPrice);
                        intent.putExtra("maxPrice", maxPrice);
                        intent.putExtra("strCountry", strCountry);
                        intent.putExtra("isChk", isChk);
                        intent.putExtra("strTotal", strTotal);

                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getApplicationContext(), ShResult.class);
                        intent.putExtra("strName", strName);
                        intent.putExtra("strBrand", strBrand);
                        intent.putExtra("strModel", strModel);
                        intent.putExtra("strGrade", strGrade);
                        intent.putExtra("minMilesage", minMilesage);
                        intent.putExtra("maxMilesage", maxMilesage);
                        intent.putExtra("minPrice", minPrice);
                        intent.putExtra("maxPrice", maxPrice);
                        intent.putExtra("strCountry", strCountry);
                        intent.putExtra("isChk", isChk);
                        intent.putExtra("strTotal", strTotal);
                        startActivity(intent);
                    }

                }
            }
        });
    }
}