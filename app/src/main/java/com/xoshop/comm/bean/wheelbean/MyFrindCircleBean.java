package com.xoshop.comm.bean.wheelbean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/5/16.
 */

public class MyFrindCircleBean implements Serializable {

    /**
     * code : 1
     * message : 获取成功！
     * data : [{"friend_id":105,"user_id":2618,"content":"测试4","thumb":[],"create_time":"05-17 12:01","like_total":0,"comment_total":0},{"friend_id":106,"user_id":2618,"content":"测试3","thumb":[],"create_time":"05-17 12:01","like_total":0,"comment_total":0}]
     */

    private int code;
    private String message;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * friend_id : 105
         * user_id : 2618
         * content : 测试4
         * thumb : []
         * create_time : 05-17 12:01
         * like_total : 0
         * comment_total : 0
         */

        private int friend_id;
        private int user_id;
        private String content;
        private String create_time;
        private int like_total;
        private int comment_total;
        private List<?> thumb;

        public int getFriend_id() {
            return friend_id;
        }

        public void setFriend_id(int friend_id) {
            this.friend_id = friend_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getLike_total() {
            return like_total;
        }

        public void setLike_total(int like_total) {
            this.like_total = like_total;
        }

        public int getComment_total() {
            return comment_total;
        }

        public void setComment_total(int comment_total) {
            this.comment_total = comment_total;
        }

        public List<?> getThumb() {
            return thumb;
        }

        public void setThumb(List<?> thumb) {
            this.thumb = thumb;
        }
    }
}
