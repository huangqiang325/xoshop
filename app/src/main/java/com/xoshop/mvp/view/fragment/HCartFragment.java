package com.xoshop.mvp.view.fragment;

import com.xoshop.R;
import com.xoshop.mvp.base.BaseFragment;
import com.xoshop.mvp.contract.CHCart;
import com.xoshop.mvp.presenter.PHCartImpl;

import android.os.Bundle;

/**
 * Created by Liang_Lu on 2017/12/21.
 *
 * @author LuLiang
 * @github https://github.com/LiangLuDev
 */

public class HCartFragment extends BaseFragment<PHCartImpl> implements CHCart.IVHCart {


    public static HCartFragment newInstance() {
        HCartFragment fragment = new HCartFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView() {


    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_hcart;
    }

    @Override
    public void createPresenter() {
        mPresenter = new PHCartImpl(mContext, this);
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

}
