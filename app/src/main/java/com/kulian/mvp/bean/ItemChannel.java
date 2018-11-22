package com.kulian.mvp.bean;

import java.io.Serializable;

/**
 * Created by mac on 2018/11/22.
 */

public class ItemChannel implements Serializable {
    private String channelId;
    private String name;
    private String ifCheck = "0";

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemChannel(String channelId, String name, String ifCheck) {
        this.channelId = channelId;
        this.name = name;
        this.ifCheck = ifCheck;
    }

    public String getIfCheck() {
        return ifCheck;
    }

    public void setIfCheck(String ifCheck) {
        this.ifCheck = ifCheck;
    }

    public ItemChannel() {
    }
}
