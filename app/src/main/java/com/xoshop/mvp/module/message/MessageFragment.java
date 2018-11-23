package com.xoshop.mvp.module.message;

import com.xoshop.R;
import com.xoshop.mvp.base.BaseFragment;

import android.os.Bundle;

/**
 * Created by HuangQiang on 2017/12/21.
 *
 * @author HuangQiang
 * @github https://github.com/HuangQiang
 */

public class MessageFragment extends BaseFragment<PMessageImpl> implements CMessage.IVMessage {


    public static MessageFragment newInstance() {
        MessageFragment fragment = new MessageFragment();
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
        return R.layout.fragment_message;
    }

    @Override
    public void createPresenter() {
        mPresenter = new PMessageImpl(mContext, this);
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

}
