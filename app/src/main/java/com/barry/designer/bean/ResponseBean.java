package com.barry.designer.bean;

import org.json.JSONObject;

/**
 * Created by Barry on 2016/3/11.
 */
public class ResponseBean {
    private int json_result;
    private String json_reason;
    private String json_data;


    public ResponseBean() {
    }

    public ResponseBean(int json_result, String json_reason, String json_data) {
        this.json_result = json_result;
        this.json_reason = json_reason;
        this.json_data = json_data;
    }

    public int getJson_result() {
        return json_result;
    }

    public void setJson_result(int json_result) {
        this.json_result = json_result;
    }

    public String getJson_reason() {
        return json_reason;
    }

    public void setJson_reason(String json_reason) {
        this.json_reason = json_reason;
    }

    public String getJson_data() {
        return json_data;
    }

    public void setJson_data(String json_data) {
        this.json_data = json_data;
    }
}
