package com.kulian.mvp.contract;

import com.kulian.mvp.base.IBasePresenter;
import com.kulian.mvp.base.IBaseView;
import com.kulian.mvp.bean.ItemFoundData;
import com.kulian.mvp.bean.ItemHomeData;

import java.util.List;

/**
 * Created by Liang_Lu on 2017/12/21.
 *
 * @author LuLiang
 * @github https://github.com/LiangLuDev
 */

public interface CHSheQu {

    interface IPHSheQu extends IBasePresenter {
        void getData(int page);
    }

    interface IVHSheQu extends IBaseView {
        void showData(List<ItemFoundData> data , int page);
        void showNoData(int page);
    }
}