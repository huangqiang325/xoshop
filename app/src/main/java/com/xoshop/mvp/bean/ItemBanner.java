package com.xoshop.mvp.bean;

import java.io.Serializable;

/**
 * Created by xiaoqiang on 2018/no_shoucang/3.
 */

public class ItemBanner implements Serializable {
    private String title;
    private String image;
    private String desc;
    private String href;

    public ItemBanner() {
    }

    public ItemBanner(String title, String image, String desc, String href) {
        this.title = title;
        this.image = image;
        this.desc = desc;
        this.href = href;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
