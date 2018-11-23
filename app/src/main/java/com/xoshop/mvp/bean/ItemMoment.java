package com.xoshop.mvp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mac on 2018/11/22.
 */

public class ItemMoment implements Serializable {
    private String momentId;
    private String content;
    private List<String> pics;
    private String picTotal;
    private String commentTotal;
    private String loveTotal;
    private int hasLoved;
    private String userId;
    private String industryId;
    private String createTime;
    private String updateTime;
    private UserMoment user;

    public ItemMoment() {
    }

    public ItemMoment(String momentId, String content, List<String> pics, String picTotal, String commentTotal, String loveTotal, int hasLoved, String userId, String industryId, String createTime, String updateTime, UserMoment user) {
        this.momentId = momentId;
        this.content = content;
        this.pics = pics;
        this.picTotal = picTotal;
        this.commentTotal = commentTotal;
        this.loveTotal = loveTotal;
        this.hasLoved = hasLoved;
        this.userId = userId;
        this.industryId = industryId;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.user = user;
    }

    public String getMomentId() {
        return momentId;
    }

    public void setMomentId(String momentId) {
        this.momentId = momentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }

    public String getPicTotal() {
        return picTotal;
    }

    public void setPicTotal(String picTotal) {
        this.picTotal = picTotal;
    }

    public String getCommentTotal() {
        return commentTotal;
    }

    public void setCommentTotal(String commentTotal) {
        this.commentTotal = commentTotal;
    }

    public String getLoveTotal() {
        return loveTotal;
    }

    public void setLoveTotal(String loveTotal) {
        this.loveTotal = loveTotal;
    }

    public int getHasLoved() {
        return hasLoved;
    }

    public void setHasLoved(int hasLoved) {
        this.hasLoved = hasLoved;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public UserMoment getUser() {
        return user;
    }

    public void setUser(UserMoment user) {
        this.user = user;
    }
}
