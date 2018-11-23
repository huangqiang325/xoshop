package com.xoshop.comm.bean.wheelbean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/5/30.
 */

public class PaihangbangBean implements Serializable {

    /**
     * code : 1
     * message : 操作成功！
     * data : {"depart":[{"depart_name":"人事部","depart_id":"50"},{"depart_name":"销售部","depart_id":"47"},{"depart_name":"技术部","depart_id":"48"},{"depart_name":"客服部","depart_id":"49"}],"rank":[{"depart_id":"47","name":"小张","avatar":"https://qiuiu.bbxjw.top/201805/669e2201805050916573952.jpg","user_id":"2610","zan_total":"0","zf_total":"0","gj_total":"0"},{"depart_id":"47","name":"汤白菜","avatar":"https://qiuiu.bbxjw.top/201805/15346201805141327385412.jpg","user_id":"2611","zan_total":"0","zf_total":"0","gj_total":"0"},{"depart_id":"48","name":"565","avatar":"https://qiuiu.bbxjw.top/201805/1ef21201805141327315012.jpg","user_id":"2612","zan_total":"0","zf_total":"0","gj_total":"0"},{"depart_id":"48","name":"lhx","avatar":"https://qiuiu.bbxjw.top/201805/62fc1201805141327201216.png","user_id":"2614","zan_total":"0","zf_total":"0","gj_total":"0"},{"depart_id":"47","name":"jgd","avatar":"https://qiuiu.bbxjw.top/201805/0814620180514132637861.jpg","user_id":"2618","zan_total":"0","zf_total":"0","gj_total":"0"}]}
     */

    private int code;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<DepartBean> depart;
        private List<RankBean> rank;

        public List<DepartBean> getDepart() {
            return depart;
        }

        public void setDepart(List<DepartBean> depart) {
            this.depart = depart;
        }

        public List<RankBean> getRank() {
            return rank;
        }

        public void setRank(List<RankBean> rank) {
            this.rank = rank;
        }

        public static class DepartBean {
            /**
             * depart_name : 人事部
             * depart_id : 50
             */

            private String depart_name;
            private String depart_id;

            public String getDepart_name() {
                return depart_name;
            }

            public void setDepart_name(String depart_name) {
                this.depart_name = depart_name;
            }

            public String getDepart_id() {
                return depart_id;
            }

            public void setDepart_id(String depart_id) {
                this.depart_id = depart_id;
            }
        }

        public static class RankBean {
            /**
             * depart_id : 47
             * name : 小张
             * avatar : https://qiuiu.bbxjw.top/201805/669e2201805050916573952.jpg
             * user_id : 2610
             * zan_total : 0
             * zf_total : 0
             * gj_total : 0
             */

            private String depart_id;
            private String name;
            private String avatar;
            private String user_id;
            private String zan_total;
            private String zf_total;
            private String gj_total;

            public String getDepart_id() {
                return depart_id;
            }

            public void setDepart_id(String depart_id) {
                this.depart_id = depart_id;
            }

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

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getZan_total() {
                return zan_total;
            }

            public void setZan_total(String zan_total) {
                this.zan_total = zan_total;
            }

            public String getZf_total() {
                return zf_total;
            }

            public void setZf_total(String zf_total) {
                this.zf_total = zf_total;
            }

            public String getGj_total() {
                return gj_total;
            }

            public void setGj_total(String gj_total) {
                this.gj_total = gj_total;
            }
        }
    }
}
