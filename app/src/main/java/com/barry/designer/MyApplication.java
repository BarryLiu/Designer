package com.barry.designer;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Barry on 2016/3/7.
 */
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true); // 是否输出debug日志
    }
}
