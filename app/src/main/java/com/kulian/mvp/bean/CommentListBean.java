package com.kulian.mvp.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/9/19.
 */

public class CommentListBean implements Serializable {
    public CommentListBean(){
    }
    public CommentListBean(String user_name, String headimage, String info, String create_time){
        this.user_name = user_name;
        this.headimage = headimage;
        this.info = info;
        this.create_time = create_time;

    }
    /**
     * user_name : 还好
     * headimage : http://www.xiaotiaowa5.top/public/uploads/20180831/813794bae174a4a0aeaef5b17fc9f681.png
     * info : 全套多少钱呀！~
     * create_time : 373560
     */

    private String user_name;
    private String headimage;
    private String info;
    private String create_time;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getHeadimage() {
        return headimage;
    }

    public void setHeadimage(String headimage) {
        this.headimage = headimage;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
