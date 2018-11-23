package com.xoshop.mvp.module.person;

import com.xoshop.R;
import com.xoshop.mvp.base.BaseFragment;

import android.os.Bundle;

/**
 * Created by HuangQiang on 2017/12/21.
 *
 * @author HuangQiang
 * @github https://github.com/HuangQiang
 */

public class PersonFragment extends BaseFragment<PPersonImpl> implements CPerson.IVPerson {


    public static PersonFragment newInstance() {
        PersonFragment fragment = new PersonFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView() {


    }

    @Override
    public int getLayoutRes() {
        ifNeedStatus = true;
        return R.layout.fragment_person;
    }

    @Override
    public void createPresenter() {
        mPresenter = new PPersonImpl(mContext, this);
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

}
