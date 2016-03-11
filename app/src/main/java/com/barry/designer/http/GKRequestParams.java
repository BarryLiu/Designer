package com.barry.designer.http;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Barry on 2016/3/11.
 */
public class GKRequestParams {

    String url;

    Map<String,String> bodyParams  = new HashMap<>();

    public Map<String, String> getBodyParams() {
        return bodyParams;
    }

    public void addParams(String key , String value){
        bodyParams.put(key,value);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
