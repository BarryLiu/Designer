package com.barry.designer.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Barry on 2016/3/13.
 */
public class DialogUtils {

    public static void showTips(Context content,String text){
        Toast.makeText(content, text, Toast.LENGTH_SHORT).show();
    }
}
