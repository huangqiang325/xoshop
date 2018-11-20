package com.kulian.mvp.view.fragment;

import android.os.Bundle;

import com.kulian.R;
import com.kulian.mvp.base.BaseFragment;
import com.kulian.mvp.contract.CMusic;
import com.kulian.mvp.presenter.PMusicImpl;


/**
 * @author LuLiang
 * @github https://github.com/LiangLuDev
 */

public class MusicFragment extends BaseFragment<PMusicImpl> implements CMusic.IVMusic {


    public static MusicFragment newInstance() {
        MusicFragment fragment = new MusicFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView() {


    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_music;
    }

    @Override
    public void createPresenter() {
        mPresenter = new PMusicImpl(mContext, this);
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

}
