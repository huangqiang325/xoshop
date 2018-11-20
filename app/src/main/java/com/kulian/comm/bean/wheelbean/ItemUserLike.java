package com.kulian.comm.bean.wheelbean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/21.
 */

public class ItemUserLike implements Serializable {
    /**
     * fk_id : 268
     * fk_name : Array
     * type : 1
     */

    private int fk_id;
    private String fk_name;
    private int type;

    public ItemUserLike() {
    }

    public ItemUserLike(int fk_id,String fk_name,int type) {
        this.fk_id = fk_id;
        this.fk_name = fk_name;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
