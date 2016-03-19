package com.barry.designer.http.xutil;

import android.app.Application;
import android.media.Image;
import android.util.Log;

import com.barry.designer.MyApplication;
import com.barry.designer.QuestionDetailActivity;
import com.barry.designer.http.GKCallBack;
import com.barry.designer.http.GKRequestParams;
import com.barry.designer.http.HttpConfig;
import com.barry.designer.http.IHttp;
import com.barry.designer.utils.DialogUtils;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;
import java.io.InputStream;
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
        //设置uuid 和账号 如果服务器没有登陆就登陆
        params.addHeader(HttpConfig.HAEDER_USER_NAME, MyApplication.currUser.getName());
        params.addHeader(HttpConfig.HAEDER_USER_ID,MyApplication.uuid);

        RequestParams xutilParams = new RequestParams(params.getUrl());
        //默认时间
        xutilParams.setConnectTimeout(10000);
        //遍经header 进行添加
        for(Map.Entry<String,String> entrySet: params.getHeadParams().entrySet()) {
            xutilParams.addHeader(entrySet.getKey(), entrySet.getValue());
            Log.d("XUtilImpl","HEAD: entrySet.getKey()="+entrySet.getKey()+"  entrySet.getValue()="+entrySet.getValue());
        }
        //遍经body 进行添加
        for(Map.Entry<String,String> entrySet: params.getBodyParams().entrySet()){
            xutilParams.addParameter(entrySet.getKey(), entrySet.getValue());
            Log.d("XUtilImpl","BODY: entrySet.getKey()="+entrySet.getKey()+"  entrySet.getValue()="+entrySet.getValue());
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

    @Override
    public void get(GKRequestParams params, final GKCallBack callBack) {
        //设置uuid 和账号 如果服务器没有登陆就登陆
        params.addHeader(HttpConfig.HAEDER_USER_NAME, MyApplication.currUser.getName());
        params.addHeader(HttpConfig.HAEDER_USER_ID,MyApplication.uuid);

        RequestParams xutilParams = new RequestParams(params.getUrl());
        //默认时间
        xutilParams.setConnectTimeout(10000);
        //遍经header 进行添加
        for(Map.Entry<String,String> entrySet: params.getHeadParams().entrySet()) {
            xutilParams.addHeader(entrySet.getKey(), entrySet.getValue());
            Log.d("XUtilImpl","HEAD: entrySet.getKey()="+entrySet.getKey()+"  entrySet.getValue()="+entrySet.getValue());
        }
        //遍经body 进行添加
        for(Map.Entry<String,String> entrySet: params.getBodyParams().entrySet()){
            xutilParams.addParameter(entrySet.getKey(), entrySet.getValue());
            Log.d("XUtilImpl","BODY: entrySet.getKey()="+entrySet.getKey()+"  entrySet.getValue()="+entrySet.getValue());
        }

        x.http().get(xutilParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                DialogUtils.showLog(result);
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

    @Override
    public void getPic(GKRequestParams params,  final GKCallBack callBack) {
        //设置uuid 和账号 如果服务器没有登陆就登陆
        params.addHeader(HttpConfig.HAEDER_USER_NAME, MyApplication.currUser.getName());
        params.addHeader(HttpConfig.HAEDER_USER_ID,MyApplication.uuid);

        RequestParams xutilParams = new RequestParams(params.getUrl());
        //默认时间
        xutilParams.setConnectTimeout(10000);
        //遍经header 进行添加
        for(Map.Entry<String,String> entrySet: params.getHeadParams().entrySet()) {
            xutilParams.addHeader(entrySet.getKey(), entrySet.getValue());
            Log.d("XUtilImpl","HEAD: entrySet.getKey()="+entrySet.getKey()+"  entrySet.getValue()="+entrySet.getValue());
        }
        //遍经body 进行添加
        for(Map.Entry<String,String> entrySet: params.getBodyParams().entrySet()){
            xutilParams.addParameter(entrySet.getKey(), entrySet.getValue());
            Log.d("XUtilImpl","BODY: entrySet.getKey()="+entrySet.getKey()+"  entrySet.getValue()="+entrySet.getValue());
        }
        ImageOptions options = new ImageOptions.Builder().build();

        DialogUtils.showLog("要加载的图片的路径: "+params.getUrl());
        x.image().loadFile(params.getUrl(), null, new Callback.CacheCallback<File>() {
            @Override
            public boolean onCache(File result) {
                return false;
            }

            @Override
            public void onSuccess(File result) {
                DialogUtils.showLog("得到的图片" + result.toString());
//                QuestionDetailActivity.file = result;
                callBack.onSuccess(result.getPath());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                DialogUtils.showLog("得到的图片失败");
                callBack.onError(ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                DialogUtils.showLog("得到的onCancelled");
            }

            @Override
            public void onFinished() {
                DialogUtils.showLog("得到的onFinished");
            }
        });

    }
}
