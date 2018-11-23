package com.xoshop.mvp.bean;

import java.io.Serializable;

/**
 * Created by mac on 2018/11/22.
 */

public class ItemIndustry implements Serializable {
    private String industryId;
    private String name;
    private String cover;
    private String ifCheck = "0";

    public ItemIndustry() {
    }

    public ItemIndustry(String industryId, String name, String cover, String ifCheck) {
        this.industryId = industryId;
        this.name = name;
        this.cover = cover;
        this.ifCheck = ifCheck;
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getIfCheck() {
        return ifCheck;
    }

    public void setIfCheck(String ifCheck) {
        this.ifCheck = ifCheck;
    }
}
