package com.barry.designer.bean;

/**
 * Created by Barry on 2016/3/11.
 */
public class UserBean {
    private int id;
    private int level;
    private int sex;
    private int uuid;

    public UserBean() {
    }

    public UserBean(int id, int level, int sex, int uuid) {
        this.id = id;
        this.level = level;
        this.sex = sex;
        this.uuid = uuid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getUuid() {
        return uuid;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }
}
