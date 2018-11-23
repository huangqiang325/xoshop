package com.xoshop.mvp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/9/19.
 */

public class ItemFoundData implements Serializable {

    public ItemFoundData(){

    }
    public ItemFoundData(String id, String name, String headimage, String desc, String create_time, String like_count, String comment_count, String is_like,
                     List<String> image, List<CommentListBean> comment_list){
        this.id = id;
        this.name = name;
        this.headimage = headimage;
        this.desc = desc;
        this.create_time = create_time;
        this.like_count = like_count;
        this.comment_count = comment_count;
        this.is_like = is_like;
        this.image = image;
        this.comment_list = comment_list;
    }
    /**
     * name : 酷链商城
     * headimage : http://www.xiaotiaowa5.top/public/uploads/20180721/34b96a0c8d4d02c9622ac7346fe50db5.png
     * desc : 美女陪玩等你来哦！！！
     * create_time : 2018-08-31 13:14:24
     * image : ["http://www.xiaotiaowa5.top/public/uploads/20180831/c1fde7c5c75ce8cb35629eb3c14819dc.png","http://www.xiaotiaowa5.top/public/uploads/20180831/c1fde7c5c75ce8cb35629eb3c14819dc.png"]
     * like_count : 0
     * comment_count : 5
     * is_like : 0
     * comment_list : [{"user_name":"还好","headimage":"http://www.xiaotiaowa5.top/public/uploads/20180831/813794bae174a4a0aeaef5b17fc9f681.png","info":"全套多少钱呀！~","create_time":"373560"},{"user_name":"还好","headimage":"http://www.xiaotiaowa5.top/public/uploads/20180831/813794bae174a4a0aeaef5b17fc9f681.png","info":"卧槽！这是什么嘛！！","create_time":"278037"}]
     */
    private String id;
    private String name;
    private String headimage;
    private String desc;
    private String create_time;
    private String like_count;
    private String comment_count;
    private String is_like;
    private List<String> image;
    private List<CommentListBean> comment_list;

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadimage() {
        return headimage;
    }

    public void setHeadimage(String headimage) {
        this.headimage = headimage;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getLike_count() {
        return like_count;
    }

    public void setLike_count(String like_count) {
        this.like_count = like_count;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public String getIs_like() {
        return is_like;
    }

    public void setIs_like(String is_like) {
        this.is_like = is_like;
    }

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }

    public List<CommentListBean> getComment_list() {
        return comment_list;
    }

    public void setComment_list(List<CommentListBean> comment_list) {
        this.comment_list = comment_list;
    }
}
