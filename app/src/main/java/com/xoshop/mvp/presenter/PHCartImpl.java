package com.xoshop.mvp.presenter;

import android.content.Context;

import com.xoshop.mvp.contract.CHCart;
import com.xoshop.mvp.model.MHCartImpl;
import com.xoshop.mvp.base.BasePresenter;


/**
 * Created by Liang_Lu on 2017/12/21.
 *
 * @author LuLiang
 * @github https://github.com/LiangLuDev
 */

public class PHCartImpl extends BasePresenter<CHCart.IVHCart, MHCartImpl> implements CHCart.IPHCart {


    public PHCartImpl(Context mContext, CHCart.IVHCart mView) {
        super(mContext, mView, new MHCartImpl());
    }

}
