package com.example.jungcar;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Smsreceiver extends BroadcastReceiver {
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SmsReceiver";

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void onReceive(Context context, Intent intent) {
        // SMS_RECEIVED에 대한 액션일때 실행
        if (intent.getAction().equals(SMS_RECEIVED)) {
            int flag1=0,flag2=0;
            String name="";
            String charge="";

            // Bundle을 이용해서 메세지 내용을 가져옴
            Bundle bundle = intent.getExtras();
            SmsMessage[] messages = parseSmsMessage(bundle);
            // 메세지가 있을 경우 내용을 로그로 출력해 봄
            if (messages.length > 0) {
                // 메세지의 내용을 가져옴
                String sender = messages[0].getOriginatingAddress();
                String contents = messages[0].getMessageBody().toString();
                Date receivedDate = new Date(messages[0].getTimestampMillis());

                String strcon[] = contents.split(" ");
                for(int i=0;i<strcon.length;i++){
                    if(strcon[i].indexOf('주')!=-1){
                        int k = strcon[i].indexOf('주');
                        if(strcon[i].charAt(k+1)=='유' && strcon[i].charAt(k+2)=='소'){
                            name = strcon[i];
                            flag1=1;
                            break;
                        }
                    }
                }

                for (int i=0;i<strcon.length;i++){
                    if(strcon[i].indexOf('원')!=-1||strcon[i].indexOf("출금")!=-1){
                        if(strcon[i].indexOf('원')!=-1) {
                            String str2[] = strcon[i].split("원");
                            charge = replace(str2[0]);
                        }else{
                            charge = replace(strcon[i]);
                        }
                        flag2=1;
                        break;
                    }
                }
                if(flag1==1&&flag2==1) {
                    sendToActivity(context, name, charge, receivedDate);
                }

                // 액티비티로 메세지의 내용을 전달해줌

            }
        }
    }

    public String replace(String rep){
        String comp="";
       rep= rep.replace('출',' ');
       rep= rep.replace('금',' ');
        rep=rep.replace('원',' ');
        rep=rep.replace(',',' ');
        String tmp[] = rep.split(" ");

        for(int j=0;j<tmp.length;j++){
            comp+=tmp[j];
        }


        return comp;

    }





    // 액티비티로 메세지의 내용을 전달해줌
    private void sendToActivity(Context context, String place, String charge, Date receivedDate){





        Intent intent = new Intent(context, AddOil.class);
        // Flag 설정
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // 메세지의 내용을 Extra에 넣어줌
        intent.putExtra("place",place);
        intent.putExtra("charge",charge);
        intent.putExtra("date", format.format(receivedDate));
        context.startActivity(intent);
    }

    private SmsMessage[] parseSmsMessage(Bundle bundle){
        Object[] objs = (Object[]) bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[objs.length];

        for(int i=0; i<objs.length; i++) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String format = bundle.getString("format");
                messages[i] = SmsMessage.createFromPdu((byte[]) objs[i], format);
            } else {
                messages[i] = SmsMessage.createFromPdu((byte[]) objs[i]);
            }
        }


        return messages;
    }



}