package com.barry.designer.http;

import android.app.Application;

import com.barry.designer.bean.ResponseBean;
import com.barry.designer.bean.UserBean;
import com.barry.designer.http.xutil.XUtilImpl;
import com.barry.designer.utils.JsonUtils;

/**
 * Created by Barry on 2016/3/11.
 */
public class HttpUtils {

    static IHttp httpClient;

    public static final int XUTIL =1;
    public static final int OKHTTP = 2;

    public static  int type = XUTIL;

    public static void init(Application app){
        switch (type){
            case XUTIL:
                httpClient = new XUtilImpl(app);
                break;
            case OKHTTP:
                httpClient = null;
                break;
        }
    }

    public static void resiger(UserBean ub , GKCallBack callBack){
        String jsonStr = JsonUtils.toJson(ub);
        GKRequestParams params = new GKRequestParams();
        params.addParams(HttpConfig.TAG_USER_JSON,jsonStr);

        params.setUrl(HttpConfig.RESIONGER_URL);

        httpClient.post(params,callBack);
    }

    public static void setType(int type) {
        HttpUtils.type = type;
    }

    public static ResponseBean paserResponse(String result) {
        return (ResponseBean) JsonUtils.fromJson(result,ResponseBean.class);
    }

    public static void loginer(UserBean ub, GKCallBack callBack) {
        String jsonStr = JsonUtils.toJson(ub);
        GKRequestParams params = new GKRequestParams();
        params.addParams(HttpConfig.TAG_USER_JSON,jsonStr);
        params.setUrl(HttpConfig.RESIONGER_URL);

        httpClient.post(params,callBack);
    }
}
