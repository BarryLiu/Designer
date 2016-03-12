package com.barry.designer;

import android.app.Application;

import com.barry.designer.http.HttpUtils;

import org.xutils.x;

/**
 * Created by Barry on 2016/3/7.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 是否输出debug日志
        /*
        x.Ext.init(this);
        x.Ext.setDebug(true);
        */

        //http
        HttpUtils.init(this);
    }
}
