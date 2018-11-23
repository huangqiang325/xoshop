package com.xoshop.mvp.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.florent37.viewanimator.ViewAnimator;
import com.jakewharton.rxbinding2.view.RxView;
import com.xoshop.R;
import com.xoshop.mvp.base.BaseFragment;
import com.xoshop.mvp.contract.CHMine;
import com.xoshop.mvp.presenter.PHMineImpl;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Liang_Lu on 2017/12/21.
 *
 * @author LuLiang
 * @github https://github.com/LiangLuDev
 */

public class HMineFragment extends BaseFragment<PHMineImpl> implements CHMine.IVHMine {
    Unbinder unbinder;
    @BindView(R.id.btn_roll)
    Button btnRoll;
    boolean if9 = false;

    public static HMineFragment newInstance() {
        HMineFragment fragment = new HMineFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView() {
        addDisposable(RxView.clicks(btnRoll).throttleFirst(200, TimeUnit.MILLISECONDS)
                .subscribe(o -> {
                    ViewAnimator.animate(btnRoll, btnRoll)
                            .rotation(if9 == false ? 90f : -90)
                            .decelerate()
                            .duration(200)
                            .start();
                    if9 = !if9;
                }));
    }

    @Override
    public int getLayoutRes() {
        ifNeedStatus = false;
        return R.layout.fragment_hmine;
    }

    @Override
    public void createPresenter() {
        mPresenter = new PHMineImpl(mContext, this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
