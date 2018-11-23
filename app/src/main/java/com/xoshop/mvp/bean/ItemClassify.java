package com.xoshop.mvp.bean;

import java.io.Serializable;

/**
 * Created by xiaoqiang on 2018/8/31.
 */

public class ItemClassify implements Serializable {
    private String id;
    private String goods_name;
    private String face_img;
    private String ccm;
    private String category_id;
    private String price;
    private String active_price;

    public ItemClassify() {
    }

    public ItemClassify(String id, String goods_name, String face_img, String ccm, String category_id, String price, String active_price) {
        this.id = id;
        this.goods_name = goods_name;
        this.face_img = face_img;
        this.ccm = ccm;
        this.category_id = category_id;
        this.price = price;
        this.active_price = active_price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getFace_img() {
        return face_img;
    }

    public void setFace_img(String face_img) {
        this.face_img = face_img;
    }

    public String getCcm() {
        return ccm;
    }

    public void setCcm(String ccm) {
        this.ccm = ccm;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getActive_price() {
        return active_price;
    }

    public void setActive_price(String active_price) {
        this.active_price = active_price;
    }
}
