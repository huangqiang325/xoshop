package com.xoshop.mvp.module.moments;

import com.xoshop.mvp.base.IBasePresenter;
import com.xoshop.mvp.base.IBaseView;
import com.xoshop.mvp.bean.ItemChannel;
import com.xoshop.mvp.bean.ItemIndustry;
import com.xoshop.mvp.bean.ItemMoment;

import java.util.List;

/**
 * Created by HuangQiang on 2017/12/21.
 *
 * @author HuangQiang
 * @github https://github.com/HuangQiang
 */

public interface CDongTai {

    interface IPDongTai extends IBasePresenter {
        void getIndustry();
        void getChannel();
        void getMoments(int page,String industryId,String channelId,String filter );

    }

    interface IVDongTai extends IBaseView {
        void callBackIndustry(List<ItemIndustry> itemIndustries);
        void callBackChannel(List<ItemChannel> itemChannels);
        void callBackMoment(List<ItemMoment> itemMoments);
    }
}