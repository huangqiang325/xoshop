package com.xoshop.mvp.module.moments;

import com.xoshop.mvp.base.IBasePresenter;
import com.xoshop.mvp.base.IBaseView;
import com.xoshop.mvp.bean.ItemChannel;
import com.xoshop.mvp.bean.ItemIndustry;

import java.util.List;


/**
 * Created by HuangQiang on 2017/12/21.
 *
 * @author HuangQiang
 * @github https://github.com/LiangLuDev
 */

public interface CReleaseMoment {

    interface IPReleaseMoment extends IBasePresenter {
        void getIndustry();
        void getChannel();

    }

    interface IVReleaseMoment extends IBaseView {
        void callBackIndustry(List<ItemIndustry> itemIndustries);
        void callBackChannel(List<ItemChannel> itemChannels);
    }
}