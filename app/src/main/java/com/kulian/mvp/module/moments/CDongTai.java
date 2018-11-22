package com.kulian.mvp.module.moments;

import com.kulian.mvp.base.IBasePresenter;
import com.kulian.mvp.base.IBaseView;
import com.kulian.mvp.bean.ItemChannel;
import com.kulian.mvp.bean.ItemIndustry;

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
    }
}