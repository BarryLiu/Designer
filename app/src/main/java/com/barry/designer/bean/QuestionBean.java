package com.barry.designer.bean;

/**
 * Created by Barry on 2016/3/9.
 */
public class QuestionBean {
    private String name;
    private String detail;

    public QuestionBean() {
    }

    public QuestionBean(String name, String detail) {

        this.name = name;
        this.detail = detail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "QuestionBean{" +
                "name='" + name + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
