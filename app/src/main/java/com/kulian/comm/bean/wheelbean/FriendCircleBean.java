package com.kulian.comm.bean.wheelbean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/5/20.
 */

public class FriendCircleBean implements Serializable {

    /**
     * id : 66
     * user_id : 2614
     * type : 0
     * admin_id : 32
     * title : 测试测试
     * thumb : ["http://p7mhd0owm.bkt.clouddn.com/2018-05-17-15-04-19aspmnqisvt.jpg"]
     * content :
     * create_time : 1526540680
     * user_info : {"name":"lhx","avatar":"http://p7mhd0owm.bkt.clouddn.com/201805/62fc1201805141327201216.png"}
     * like : [{"fk_id":268,"fk_name":"Array","type":1},{"fk_id":2614,"fk_name":"lhx","type":2}]
     * comment : [{"fk_id":268,"fk_name":"Array","msg":"nubuck","id":37,"type":1},{"fk_id":268,"fk_name":"Array","msg":"哦啦啦","id":38,"type":1}]
     */

    private int id;
    private int user_id;
    private int type;
    private int admin_id;
    private String title;
    private String content;
    private int create_time;
    private UserInfoBean user_info;
    private List<String> thumb;
    private List<LikeBean> like;
    private List<CommentBean> comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public UserInfoBean getUser_info() {
        return user_info;
    }

    public void setUser_info(UserInfoBean user_info) {
        this.user_info = user_info;
    }

    public List<String> getThumb() {
        return thumb;
    }

    public void setThumb(List<String> thumb) {
        this.thumb = thumb;
    }

    public List<LikeBean> getLike() {
        return like;
    }

    public void setLike(List<LikeBean> like) {
        this.like = like;
    }

    public List<CommentBean> getComment() {
        return comment;
    }

    public void setComment(List<CommentBean> comment) {
        this.comment = comment;
    }

    public static class UserInfoBean {
        /**
         * name : lhx
         * avatar : http://p7mhd0owm.bkt.clouddn.com/201805/62fc1201805141327201216.png
         */

        private String name;
        private String avatar;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }

    public static class LikeBean {
        /**
         * fk_id : 268
         * fk_name : Array
         * type : 1
         */

        private int fk_id;
        private String fk_name;
        private int type;

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

    public static class CommentBean {
        /**
         * fk_id : 268
         * fk_name : Array
         * msg : nubuck
         * id : 37
         * type : 1
         */

        private int fk_id;
        private String fk_name;
        private String msg;
        private int id;
        private int type;

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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
