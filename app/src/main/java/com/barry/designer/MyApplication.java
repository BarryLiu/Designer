package com.barry.designer;

import android.app.Application;

import com.barry.designer.bean.UserBean;
import com.barry.designer.http.HttpUtils;
import com.barry.designer.util.AuthTokenUtils;

import org.xutils.x;

/**
 * Created by Barry on 2016/3/7.
 */
public class MyApplication extends Application {

    public static UserBean currUser;
    public static String uuid ;

    @Override
    public void onCreate() {
        super.onCreate();
        // 是否输出debug日志
        /*
        x.Ext.init(this);
        x.Ext.setDebug(true);
        */
        uuid = AuthTokenUtils.getUUid(this);
        //http
        HttpUtils.init(this);
    }
}
