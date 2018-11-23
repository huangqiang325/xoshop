package com.xoshop.mvp.contract;

import com.xoshop.mvp.base.IBasePresenter;
import com.xoshop.mvp.base.IBaseView;
import com.xoshop.mvp.bean.ItemFoundData;

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