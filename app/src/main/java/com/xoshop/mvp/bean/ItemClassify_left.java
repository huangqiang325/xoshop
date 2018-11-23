package com.xoshop.mvp.bean;

import java.io.Serializable;

/**
 * Created by xiaoqiang on 2018/8/31.
 */

public class ItemClassify_left implements Serializable {
    private  String category_name ;
    private String ifCheck ;
    private String id ;

    public ItemClassify_left(String category_name, String ifCheck, String id) {
        this.category_name = category_name;
        this.ifCheck = ifCheck;
        this.id = id;
    }

    public ItemClassify_left() {
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getIfCheck() {
        return ifCheck;
    }

    public void setIfCheck(String ifCheck) {
        this.ifCheck = ifCheck;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
