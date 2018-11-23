package com.xoshop.mvp.bean;

import java.io.Serializable;

/**
 * Created by mac on 2018/11/22.
 */

public class UserMoment implements Serializable {
    private String name;
    private String avatar;

    public UserMoment() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public UserMoment(String name, String avatar) {
        this.name = name;
        this.avatar = avatar;
    }
}
