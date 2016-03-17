package com.barry.designer.http;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Barry on 2016/3/11.
 */
public class GKRequestParams {

    String url;

    Map<String,String> bodyParams  = new HashMap<>();
    Map<String,String> headParams  = new HashMap<>();

    public void setBodyParams(Map<String, String> bodyParams) {
        this.bodyParams = bodyParams;
    }

    public Map<String, String> getHeadParams() {
        return headParams;
    }

    public void setHeadParams(Map<String, String> headParams) {
        this.headParams = headParams;
    }

    public Map<String, String> getBodyParams() {
        return bodyParams;
    }

    public void addParams(String key , String value){
        bodyParams.put(key,value);
    }
    public void addHeader(String key , String value){
        headParams.put(key,value);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
