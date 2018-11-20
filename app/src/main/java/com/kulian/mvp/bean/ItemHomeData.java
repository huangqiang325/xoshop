package com.kulian.mvp.bean;

import java.io.Serializable;

/**
 * Created by xiaoqiang on 2018/8/31.
 */

public class ItemHomeData implements Serializable {

    private String goods_name;
    private String face_img;
    private String ccm;
    private String price;
    private String active_price;
    private String id;
    public ItemHomeData() {
    }

    public ItemHomeData(String goods_name, String face_img, String ccm, String price, String active_price, String id) {
        this.goods_name = goods_name;
        this.face_img = face_img;
        this.ccm = ccm;
        this.price = price;
        this.active_price = active_price;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
