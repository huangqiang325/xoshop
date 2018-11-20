package com.kulian.mvp.contract;

import com.kulian.mvp.base.IBasePresenter;
import com.kulian.mvp.base.IBaseView;
import com.kulian.mvp.bean.ItemClassify;
import com.kulian.mvp.bean.ItemClassify_left;

import java.util.List;
import java.util.Map;

/**
 * Created by Liang_Lu on 2017/12/21.
 *
 * @author LuLiang
 * @github https://github.com/LiangLuDev
 */

public interface CHClasify {

    interface IPHClasify extends IBasePresenter {
        void getDataLeft();

        void getDataRight(String index,int page);
    }


    interface IVHClasify extends IBaseView {

        void renderDataLeft(List<ItemClassify_left> itemClassify_lefts);

        void renderDataRight(    List<ItemClassify> itemClassifies ,int page);

        void renderDataRightNoData(int page);
    }
}