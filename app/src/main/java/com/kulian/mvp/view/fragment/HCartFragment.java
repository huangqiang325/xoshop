package com.kulian.mvp.view.fragment;

import com.kulian.R;
import com.kulian.mvp.base.BaseFragment;
import com.kulian.mvp.contract.CHCart;
import com.kulian.mvp.presenter.PHCartImpl;

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
