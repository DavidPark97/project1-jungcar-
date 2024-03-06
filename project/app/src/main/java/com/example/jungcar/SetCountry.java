package com.example.jungcar;

import android.app.Activity ;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.List;

public class SetCountry extends Activity {
    String strBrand, strCountry, strModel, strGrade, strMilesage, strPrice, isChk,strOption,strPlnumber;
    Button btnCancel, btnModify;
    Spinner spinProvince, spinCity;
    String tmp[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.setcountry);



        btnCancel =(Button)findViewById(R.id.btnCancel);
        btnModify = (Button)findViewById(R.id.btnModify);
        spinProvince = (Spinner)findViewById(R.id.spinProvince);
        spinCity = (Spinner)findViewById(R.id.spinCity);

        Intent intent = getIntent();
        strPlnumber = intent.getExtras().getString("strPlnumber","");
        strBrand = intent.getExtras().getString("strBrand","");
        strCountry= intent.getExtras().getString("strCountry","");
        strMilesage = intent.getExtras().getString("strMilesage","");
        strModel = intent.getExtras().getString("strModel","");
        strGrade = intent.getExtras().getString("strGrade","");
        strPrice = intent.getExtras().getString("strPrice","");
        strOption = intent.getExtras().getString("strOption","");

        isChk = intent.getExtras().getString("isChk","");

        List<String> pro = Arrays.asList(getResources().getStringArray(R.array.province));
        ArrayAdapter adp = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_dropdown_item,pro);
        spinProvince.setAdapter(adp);

        if(!strCountry.equals("")){
            tmp = strCountry.split(" ");
            for(int i=0;i<pro.size();i++){
                if(tmp[0].equals(pro.get(i))){
                    spinProvince.setSelection(i);
                    break;
                }
            }

        }

        spinProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0) {
                    List<String> ci = Arrays.asList(getResources().getStringArray(R.array.city1));
                    ArrayAdapter adp2 = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, ci);
                    spinCity.setAdapter(adp2);
                    if(!strCountry.equals(""))
                    for(int j=0;j<ci.size();j++){
                        if(tmp[1].equals(ci.get(j))){
                            spinCity.setSelection(j);
                            break;
                        }
                    }

                }else if(i==1){
                    List<String> ci = Arrays.asList(getResources().getStringArray(R.array.city2));
                    ArrayAdapter adp2 = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, ci);
                    spinCity.setAdapter(adp2);
                    if(!strCountry.equals(""))
                        for(int j=0;j<ci.size();j++){
                            if(tmp[1].equals(ci.get(j))){
                                spinCity.setSelection(j);
                                break;
                            }
                        }

                }else if(i==2){
                    List<String> ci = Arrays.asList(getResources().getStringArray(R.array.city3));
                    ArrayAdapter adp2 = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, ci);
                    spinCity.setAdapter(adp2);
                    if(!strCountry.equals(""))
                        for(int j=0;j<ci.size();j++){
                            if(tmp[1].equals(ci.get(j))){
                                spinCity.setSelection(j);
                                break;
                            }
                        }

                }else if(i==3){
                    List<String> ci = Arrays.asList(getResources().getStringArray(R.array.city4));
                    ArrayAdapter adp2 = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, ci);
                    spinCity.setAdapter(adp2);
                    if(!strCountry.equals(""))
                        for(int j=0;j<ci.size();j++){
                            if(tmp[1].equals(ci.get(j))){
                                spinCity.setSelection(j);
                                break;
                            }
                        }

                }else if(i==4){
                    List<String> ci = Arrays.asList(getResources().getStringArray(R.array.city5));
                    ArrayAdapter adp2 = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, ci);
                    spinCity.setAdapter(adp2);
                    if(!strCountry.equals(""))
                        for(int j=0;j<ci.size();j++){
                            if(tmp[1].equals(ci.get(j))){
                                spinCity.setSelection(j);
                                break;
                            }
                        }

                }else if(i==5){
                    List<String> ci = Arrays.asList(getResources().getStringArray(R.array.city6));
                    ArrayAdapter adp2 = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, ci);
                    spinCity.setAdapter(adp2);
                    if(!strCountry.equals(""))
                        for(int j=0;j<ci.size();j++){
                            if(tmp[1].equals(ci.get(j))){
                                spinCity.setSelection(j);
                                break;
                            }
                        }

                }else if(i==6){
                    List<String> ci = Arrays.asList(getResources().getStringArray(R.array.city7));
                    ArrayAdapter adp2 = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, ci);
                    spinCity.setAdapter(adp2);
                    if(!strCountry.equals(""))
                        for(int j=0;j<ci.size();j++){
                            if(tmp[1].equals(ci.get(j))){
                                spinCity.setSelection(j);
                                break;
                            }
                        }

                }else if(i==7){
                    List<String> ci = Arrays.asList(getResources().getStringArray(R.array.city8));
                    ArrayAdapter adp2 = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, ci);
                    spinCity.setAdapter(adp2);

                }else if(i==8){
                    List<String> ci = Arrays.asList(getResources().getStringArray(R.array.city9));
                    ArrayAdapter adp2 = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, ci);
                    spinCity.setAdapter(adp2);
                    if(!strCountry.equals(""))
                        for(int j=0;j<ci.size();j++){
                            if(tmp[1].equals(ci.get(j))){
                                spinCity.setSelection(j);
                                break;
                            }
                        }

                }else if(i==9){
                    List<String> ci = Arrays.asList(getResources().getStringArray(R.array.city10));
                    ArrayAdapter adp2 = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, ci);
                    spinCity.setAdapter(adp2);
                    if(!strCountry.equals(""))
                        for(int j=0;j<ci.size();j++){
                            if(tmp[1].equals(ci.get(j))){
                                spinCity.setSelection(j);
                                break;
                            }
                        }

                }else if(i==10){
                    List<String> ci = Arrays.asList(getResources().getStringArray(R.array.city11));
                    ArrayAdapter adp2 = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, ci);
                    spinCity.setAdapter(adp2);
                    if(!strCountry.equals(""))
                        for(int j=0;j<ci.size();j++){
                            if(tmp[1].equals(ci.get(j))){
                                spinCity.setSelection(j);
                                break;
                            }
                        }

                }else if(i==11){
                    List<String> ci = Arrays.asList(getResources().getStringArray(R.array.city12));
                    ArrayAdapter adp2 = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, ci);
                    spinCity.setAdapter(adp2);
                    if(!strCountry.equals(""))
                        for(int j=0;j<ci.size();j++){
                            if(tmp[1].equals(ci.get(j))){
                                spinCity.setSelection(j);
                                break;
                            }
                        }

                }else if(i==12){
                    List<String> ci = Arrays.asList(getResources().getStringArray(R.array.city13));
                    ArrayAdapter adp2 = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, ci);
                    spinCity.setAdapter(adp2);
                    if(!strCountry.equals(""))
                        for(int j=0;j<ci.size();j++){
                            if(tmp[1].equals(ci.get(j))){
                                spinCity.setSelection(j);
                                break;
                            }
                        }

                }else if(i==13){
                    List<String> ci = Arrays.asList(getResources().getStringArray(R.array.city14));
                    ArrayAdapter adp2 = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, ci);
                    spinCity.setAdapter(adp2);
                    if(!strCountry.equals(""))
                        for(int j=0;j<ci.size();j++){
                            if(tmp[1].equals(ci.get(j))){
                                spinCity.setSelection(j);
                                break;
                            }
                        }

                }else if(i==14){
                    List<String> ci = Arrays.asList(getResources().getStringArray(R.array.city15));
                    ArrayAdapter adp2 = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, ci);
                    spinCity.setAdapter(adp2);
                    if(!strCountry.equals(""))
                        for(int j=0;j<ci.size();j++){
                            if(tmp[1].equals(ci.get(j))){
                                spinCity.setSelection(j);
                                break;
                            }
                        }

                }else if(i==15){
                    List<String> ci = Arrays.asList(getResources().getStringArray(R.array.city16));
                    ArrayAdapter adp2 = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, ci);
                    spinCity.setAdapter(adp2);
                    if(!strCountry.equals(""))
                        for(int j=0;j<ci.size();j++){
                            if(tmp[1].equals(ci.get(j))){
                                spinCity.setSelection(j);
                                break;
                            }
                        }

                }else if(i==16){
                    List<String> ci = Arrays.asList(getResources().getStringArray(R.array.city17));
                    ArrayAdapter adp2 = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, ci);
                    spinCity.setAdapter(adp2);
                    if(!strCountry.equals(""))
                        for(int j=0;j<ci.size();j++){
                            if(tmp[1].equals(ci.get(j))){
                                spinCity.setSelection(j);
                                break;
                            }
                        }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getApplicationContext(), SetSellCar.class);
                intent.putExtra("strBrand", strBrand);
                intent.putExtra("strModel", strModel);
                intent.putExtra("strGrade", strGrade);
                intent.putExtra("strMilesage", strMilesage);
                intent.putExtra("strPrice", strPrice);
                intent.putExtra("strPlnumber",strPlnumber);
                intent.putExtra("strCountry", strCountry);
                intent.putExtra("strOption", strOption);
                intent.putExtra("isChk", isChk);
                startActivity(intent);

                startActivity(intent);
            }
        });

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cl;

                cl = spinProvince.getSelectedItem().toString()+ " " + spinCity.getSelectedItem().toString();

                Intent intent = new Intent(getApplicationContext(), SetSellCar.class);
                intent.putExtra("strBrand", strBrand);
                intent.putExtra("strModel", strModel);
                intent.putExtra("strGrade", strGrade);
                intent.putExtra("strMilesage", strMilesage);
                intent.putExtra("strPlnumber",strPlnumber);
                intent.putExtra("strPrice", strPrice);
                intent.putExtra("strOption", strOption);
                intent.putExtra("strCountry", cl);
                intent.putExtra("isChk", isChk);
                startActivity(intent);

            }
        });
    }
}