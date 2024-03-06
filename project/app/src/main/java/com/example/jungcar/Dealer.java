package com.example.jungcar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Dealer extends AppCompatActivity {
    TextView tvSell, tvMpSell, tvStart,tvEnd;
    EditText edtDist, edtPlNumber;
    Button btnSearch;
    String strStart,strEnd;
    Date format;
    int strMonth,strYear;SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMdd");
    SimpleDateFormat newDtFormat = new SimpleDateFormat("yyyyMM");
    Calendar cal = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener start = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            strStart = "" + i +"" +i1+"01";
            try {
                format = dtFormat.parse(strStart);
                strStart = newDtFormat.format(format);

                tvStart.setText(strStart);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    };

    DatePickerDialog.OnDateSetListener end = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            strEnd = "" + i +"" +i1+"01";
            tvEnd.setText(strEnd);
            try {
                format = dtFormat.parse(strEnd);
                strEnd = newDtFormat.format(format);
                tvEnd.setText(strEnd);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dealer);
        tvSell = (TextView)findViewById(R.id.tvSell);
        tvMpSell = (TextView)findViewById(R.id.tvMpSell);
        tvStart = (TextView)findViewById(R.id.tvStart);
        tvEnd = (TextView)findViewById(R.id.tvEnd);
        edtDist = (EditText)findViewById(R.id.edtDist);
        edtPlNumber = (EditText)findViewById(R.id.edtPlNumber);
        btnSearch = (Button)findViewById(R.id.btnSearch);
        strMonth = cal.get(Calendar.MONTH) + 1;
        strYear = cal.get(Calendar.YEAR);
        tvSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainSell.class);
                startActivity(intent);
            }
        });

        tvMpSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyPageSell.class);
                startActivity(intent);
            }
        });

        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monthPicker mp = new monthPicker();
                mp.setListener(start, strYear, strMonth);
                mp.show(getSupportFragmentManager(), "SelectMonth");
            }
        });

        tvEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monthPicker mp = new monthPicker();
                mp.setListener(end, strYear, strMonth);
                mp.show(getSupportFragmentManager(), "SelectMonth");
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(edtDist.length()==0||edtPlNumber.length()<7||tvStart.getText().toString().equals("")||tvEnd.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(),"정보를 모두 입력해주세요",Toast.LENGTH_SHORT).show();
            }else{
                if(tvStart.getText().toString().compareTo(tvEnd.getText().toString()) == 1){
                    Toast.makeText(getApplicationContext(),"기간 오류",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(getApplicationContext(), DealerPopup.class);
                    intent.putExtra("strStart",tvStart.getText().toString());
                    intent.putExtra("strEnd",tvEnd.getText().toString());
                    intent.putExtra("strPlnumber",edtPlNumber.getText().toString());
                    intent.putExtra("strDist",edtDist.getText().toString());
                    startActivity(intent);
                }
            }
            }
        });
    }

}