package com.kulian.mvp.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding2.view.RxView;
import com.joker.pager.BannerPager;
import com.joker.pager.PagerOptions;
import com.joker.pager.holder.ViewHolder;
import com.joker.pager.holder.ViewHolderCreator;
import com.kulian.R;
import com.kulian.mvp.base.BaseFragment;
import com.kulian.mvp.bean.ItemFoundData;
import com.kulian.mvp.contract.CHSheQu;
import com.kulian.mvp.presenter.PHSheQuImpl;

import java.util.ArrayList;
import java.util.List;
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

public class HSheQuFragment extends BaseFragment<PHSheQuImpl> implements CHSheQu.IVHSheQu {
    @BindView(R.id.banner_pager2)
    BannerPager bannerPager2;
    Unbinder unbinder;

    public static HSheQuFragment newInstance() {
        HSheQuFragment fragment = new HSheQuFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView() {
        Log.i(TAG, "initView");
        final List<String> data = new ArrayList<>();
        data.add("http://7xi8d6.com1.z0.glb.clouddn.com/20180109085038_4A7atU_rakukoo_9_1_2018_8_50_25_276.jpeg");
        data.add("http://7xi8d6.com1.z0.glb.clouddn.com/20180102083655_3t4ytm_Screenshot.jpeg");
        data.add("http://7xi8d6.com1.z0.glb.clouddn.com/20171228085004_5yEHju_Screenshot.jpeg");
        final PagerOptions pagerOptions2 = new PagerOptions.Builder(getActivity())
                .setTurnDuration(2000)
                //.setIndicatorColor(Color.RED, Color.BLUE)
                .setIndicatorSize(10)
                .setPagePadding(16)
                .setPrePagerWidth(60)
                .setIndicatorAlign(RelativeLayout.CENTER_IN_PARENT)
                .setIndicatorMarginBottom(40)
                .build();
        bannerPager2
                .setPagerOptions(pagerOptions2)
                .setPages(data, new ViewHolderCreator<BannerPagerHolder>() {
                    @Override
                    public BannerPagerHolder createViewHolder() {
                        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.__item_image_banner, null);
                        return new BannerPagerHolder(view);
                    }
                });
    }

    @Override
    public int getLayoutRes() {
        ifNeedStatus = true;
        return R.layout.fragment_hshequ;
    }

    @Override
    public void createPresenter() {
        mPresenter = new PHSheQuImpl(mContext, this);
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        Log.i(TAG, "onCreateView");
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        bannerPager2.startTurning();
    }

    @Override
    public void onStop() {
        super.onStop();
        bannerPager2.stopTurning();
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
    public void showData(List<ItemFoundData> data, int page) {

    }

    @Override
    public void showNoData(int page) {

    }

    private class BannerPagerHolder extends ViewHolder<String> {

        private ImageView mImage;

        private BannerPagerHolder(View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.image);
        }

        @Override
        public void onBindView(View view, String data, int position) {

            addDisposable(RxView.clicks(mImage).throttleFirst(2, TimeUnit.SECONDS)
                    .subscribe(o -> {
                        Log.e(TAG, "clicks:点击了按钮：两秒内防抖position--->"+position);
                    }));
            Glide.with(mImage.getContext())
                    .load(data)
                    .into(mImage);
        }
    }

}
