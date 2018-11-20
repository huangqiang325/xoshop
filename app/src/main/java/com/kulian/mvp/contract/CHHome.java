package com.kulian.mvp.contract;

import com.kulian.mvp.base.IBasePresenter;
import com.kulian.mvp.base.IBaseView;
import com.kulian.mvp.bean.ItemBanner;
import com.kulian.mvp.bean.ItemHomeData;

import java.util.List;
import java.util.Map;

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