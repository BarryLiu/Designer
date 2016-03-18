package com.barry.designer.http;

import android.app.Application;
import android.util.Log;

import com.barry.designer.MyApplication;
import com.barry.designer.bean.QuestionBean;
import com.barry.designer.bean.ResponseBean;
import com.barry.designer.bean.UserBean;
import com.barry.designer.http.xutil.XUtilImpl;
import com.barry.designer.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Barry on 2016/3/11.
 */
public class HttpUtils {
    public static final String JSON_TAG_RESULT = "json_result";
    public static final String JSON_TAG_REASON = "json_reason";
    public static final String JSON_TAG_DATA = "json_data";

    static IHttp httpClient;

    public static final int XUTIL = 1;
    public static final int OKHTTP = 2;

    public static int type = XUTIL;

    public static void init(Application app) {
        switch (type) {
            case XUTIL:
                httpClient = new XUtilImpl(app);
                break;
            case OKHTTP:
                httpClient = null;
                break;
        }
    }

    public static void resiger(UserBean ub, GKCallBack callBack) {
        String jsonStr = JsonUtils.toJson(ub);
        GKRequestParams params = new GKRequestParams();
        params.addParams(HttpConfig.TAG_USER_JSON, jsonStr);

        String url = HttpConfig.HOST + HttpConfig.RESIONGER_URL;
        params.setUrl(url);

        httpClient.post(params, callBack);
    }

    public static void loginer(UserBean ub, GKCallBack callBack) {
        String jsonStr = JsonUtils.toJson(ub);
        GKRequestParams params = new GKRequestParams();
        params.addParams(HttpConfig.TAG_USER_JSON, jsonStr);
        String url = HttpConfig.HOST + HttpConfig.LOGINER_URL;

        params.setUrl(url);
        httpClient.post(params, callBack);
    }

    public static void setType(int type) {
        HttpUtils.type = type;
    }

    public static ResponseBean paserResponse(String result) {
        try {
            ResponseBean rbb = new ResponseBean();
            JSONObject root = new JSONObject(result);
            rbb.setJson_result(root.getInt(JSON_TAG_RESULT));
            rbb.setJson_reason(root.getString(JSON_TAG_REASON));
            JSONObject data = root.getJSONObject(JSON_TAG_DATA);
            if (data != null) {
                rbb.setJson_data(root.getJSONObject(JSON_TAG_DATA).toString());
            }
            return rbb;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("jsonparser", e.getMessage());
        }
        return null;
    }

    /**
     * 中 JSON_TAG_DATA 的值是jsonArray 格式的时候解析
     * @param result
     * @return
     */
    public static ResponseBean paserResponseDataArray(String result) {
        try {
            ResponseBean rbb = new ResponseBean();
            JSONObject root = new JSONObject(result);
            rbb.setJson_result(root.getInt(JSON_TAG_RESULT));
            rbb.setJson_reason(root.getString(JSON_TAG_REASON));
            JSONArray data = root.getJSONArray(JSON_TAG_DATA);
            if (data != null) {
                rbb.setJson_data(root.getJSONArray(JSON_TAG_DATA).toString());
            }
            return rbb;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("jsonparser", e.getMessage());
        }
        return null;
    }

    public static void updater(UserBean ub, GKCallBack callBack) {
        String jsonStr = JsonUtils.toJson(ub);
        GKRequestParams params = new GKRequestParams();
        params.addParams(HttpConfig.TAG_USER_JSON, jsonStr);
        String url = HttpConfig.HOST + HttpConfig.UPDATE_URL;

        params.setUrl(url);
        httpClient.post(params, callBack);
    }

    public static void makeQuestion(QuestionBean qb, GKCallBack callBack) {
        String url = HttpConfig.HOST + HttpConfig.MAKE_QUESTION_URL;
        String jsonStr = JsonUtils.toJson(qb);

        GKRequestParams params = new GKRequestParams();
        params.setUrl(url);
        params.addParams(HttpConfig.TAG_QUESTION_JSON, jsonStr);

        httpClient.post(params, callBack);
    }


    public static void getQuestions(Object o, GKCallBack callBack) {
        String url = HttpConfig.HOST+HttpConfig.GET_QUESTION_URL;
        GKRequestParams params = new GKRequestParams();
        params.setUrl(url);

        httpClient.get(params, callBack);
    }
}
