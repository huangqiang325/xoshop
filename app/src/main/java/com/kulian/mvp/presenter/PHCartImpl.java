package com.kulian.mvp.presenter;

import android.content.Context;

import com.kulian.mvp.contract.CHCart;
import com.kulian.mvp.model.MHCartImpl;
import com.kulian.mvp.base.BasePresenter;


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
