package com.barry.designer.bean;

import java.io.Serializable;

/**
 * Created by Barry on 2016/3/9.
 */
public class QuestionBean implements Serializable{
    private int id;
    private String name;
    private String words;
    private String detail;
    private int author;
    private String time;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "QuestionBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", words='" + words + '\'' +
                ", detail='" + detail + '\'' +
                ", author=" + author +
                ", time='" + time + '\'' +
                '}';
    }
}
