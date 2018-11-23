package com.xoshop.comm.bean.wheelbean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/21.
 */

public class ItemUserComment implements Serializable {
    /**
     * fk_id : 268
     * fk_name : Array
     * msg : nubuck
     * comment_id : 37
     * type : 1
     */

    private int fk_id;
    private String fk_name;
    private String msg;
    private int comment_id;
    private int type;

    public ItemUserComment() {
    }

    public ItemUserComment(int fk_id,String fk_name,String msg,int comment_id,int type) {
        this.fk_id = fk_id;
        this.fk_name = fk_name;
        this.msg = msg;
        this.comment_id = comment_id;
        this.type = type;
    }

    public int getFk_id() {
        return fk_id;
    }

    public void setFk_id(int fk_id) {
        this.fk_id = fk_id;
    }

    public String getFk_name() {
        return fk_name;
    }

    public void setFk_name(String fk_name) {
        this.fk_name = fk_name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int Comment_id) {
        this.comment_id = comment_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
