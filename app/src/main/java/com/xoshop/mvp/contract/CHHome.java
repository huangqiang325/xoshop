package com.xoshop.mvp.contract;

import com.xoshop.mvp.base.IBasePresenter;
import com.xoshop.mvp.base.IBaseView;
import com.xoshop.mvp.bean.ItemBanner;
import com.xoshop.mvp.bean.ItemHomeData;

import java.util.List;

/**
 * Created by Liang_Lu on 2017/12/21.
 *
 * @author LuLiang
 * @github https://github.com/LiangLuDev
 */

public interface CHHome {

    interface IPHHome extends IBasePresenter {
        void getData(  int page);
        void getBanner();
    }

    interface IVHHome extends IBaseView {
        void showData( List<ItemHomeData> data ,int page);
        void showNoData(int page);
        void showBanner (List<ItemBanner> itemBanners);
    }
}