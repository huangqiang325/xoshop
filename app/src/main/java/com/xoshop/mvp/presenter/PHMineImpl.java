package com.xoshop.mvp.presenter;

import android.content.Context;

import com.xoshop.mvp.contract.CHMine;
import com.xoshop.mvp.model.MHMineImpl;
import com.xoshop.mvp.base.BasePresenter;


/**
 * Created by Liang_Lu on 2017/12/21.
 *
 * @author LuLiang
 * @github https://github.com/LiangLuDev
 */

public class PHMineImpl extends BasePresenter<CHMine.IVHMine, MHMineImpl> implements CHMine.IPHMine {


    public PHMineImpl(Context mContext, CHMine.IVHMine mView) {
        super(mContext, mView, new MHMineImpl());
    }

}
