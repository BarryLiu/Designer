package com.barry.designer.http.xutil;

import android.app.Application;
import android.util.Log;

import com.barry.designer.http.GKCallBack;
import com.barry.designer.http.GKRequestParams;
import com.barry.designer.http.IHttp;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Map;

/**
 * Created by Barry on 2016/3/11.
 */
public class XUtilImpl implements IHttp{

    public XUtilImpl(Application application){

        x.Ext.init(application);
        x.Ext.setDebug(true);

    }


    @Override
    public void post(GKRequestParams params, final GKCallBack callBack) {
        RequestParams xutilParams = new RequestParams(params.getUrl());

        //遍经body 进行添加
        for(Map.Entry<String,String> entrySet: params.getBodyParams().entrySet()){
            xutilParams.addParameter(entrySet.getKey(), entrySet.getValue());
            Log.d("XUtilImpl","entrySet.getKey()="+entrySet.getKey()+"  entrySet.getValue()="+entrySet.getValue());
        }

        x.http().post(xutilParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                callBack.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callBack.onError(ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
        

    }
}
