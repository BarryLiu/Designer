package com.barry.designer.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Barry on 2016/3/11.
 */
public class DialogUtils {
    public static void showTips(Context context,String text){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
    public static void showLog(String text){
        Log.d("Dialog", text);
    }

  }
