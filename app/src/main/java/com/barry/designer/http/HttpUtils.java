package com.barry.designer.http;

import com.barry.designer.bean.UserBean;

/**
 * Created by Barry on 2016/3/11.
 */
public class HttpUtils {

    static IHttp httpClient;

    public static final int XUTIL =1;
    public static final int OKHTTP = 2;

    public static  int type = XUTIL;

    public static void resiger(UserBean ub , GKCallBack callBack){

        switch (type){
            case XUTIL:
                httpClient = null;
                break;
            case OKHTTP:
                httpClient = null;
                break;
        }
    }

    public static void setType(int type) {
        HttpUtils.type = type;
    }
}
