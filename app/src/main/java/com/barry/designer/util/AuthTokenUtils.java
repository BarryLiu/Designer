package com.barry.designer.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;

import java.util.UUID;

/**
 * Created by aya4 on 2016/3/11.
 */
public class AuthTokenUtils {
    public static String USER_XML ="user_id";
    public static String getUUid(Context context){
        //获取设备id
         TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
         String DEVICE_ID = tm.getDeviceId();
        DEVICE_ID="";
        //如果设备的id 不可用就要得到 uuid代替
        if (DEVICE_ID.equals("")||DEVICE_ID.equals("000000")){
               SharedPreferences sp = context.getSharedPreferences(USER_XML, Context.MODE_PRIVATE);
               DEVICE_ID = sp.getString("uuid","");
               if(DEVICE_ID.equals("")){
                   DEVICE_ID = UUID.randomUUID().toString();
                   //将UUID进行保存
                   sp.edit().putString("uuid",DEVICE_ID).apply();
               }
        }
        return DEVICE_ID;
    }
}
