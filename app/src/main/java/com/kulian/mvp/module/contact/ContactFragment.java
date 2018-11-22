package com.kulian.mvp.module.contact;

import com.kulian.R;
import com.kulian.mvp.base.BaseFragment;

import android.os.Bundle;

/**
 * Created by HuangQiang on 2017/12/21.
 *
 * @author HuangQiang
 * @github https://github.com/HuangQiang
 */

public class ContactFragment extends BaseFragment<PContactImpl> implements CContact.IVContact {


    public static ContactFragment newInstance() {
        ContactFragment fragment = new ContactFragment();
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
        return R.layout.fragment_contact;
    }

    @Override
    public void createPresenter() {
        mPresenter = new PContactImpl(mContext, this);
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

}
