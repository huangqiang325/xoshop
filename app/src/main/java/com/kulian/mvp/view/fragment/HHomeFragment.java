package com.kulian.mvp.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jakewharton.rxbinding2.view.RxView;
import com.kulian.R;
import com.kulian.comm.activity.WebViewActivity;
import com.kulian.comm.bean.Constant;
import com.kulian.mvp.base.BaseFragment;
import com.kulian.mvp.bean.ItemBanner;
import com.kulian.mvp.bean.ItemHomeData;
import com.kulian.mvp.contract.CHHome;
import com.kulian.mvp.presenter.PHHomeImpl;
import com.kulian.utils.DynamicTimeFormat;
import com.kulian.utils.SysUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Liang_Lu on 2017/12/21.
 *
 * @author LuLiang
 * @github https://github.com/LiangLuDev
 */

public class HHomeFragment extends BaseFragment<PHHomeImpl> implements CHHome.IVHHome {


    @BindView(R.id.imagebtn_person)
    ImageButton imagebtnPerson;
    @BindView(R.id.imagebtn_search)
    ImageButton imagebtnSearch;
    @BindView(R.id.relative_search)
    RelativeLayout relativeSearch;
    @BindView(R.id.imagebtn_qiandao)
    ImageButton imagebtnQiandao;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.ll_latest)
    LinearLayout llLatest;
    @BindView(R.id.ll_hotest)
    LinearLayout llHotest;
    @BindView(R.id.ll_introduction)
    LinearLayout llIntroduction;
    @BindView(R.id.ll_join)
    LinearLayout llJoin;
    @BindView(R.id.recyclerView_comment)
    RecyclerView recyclerViewComment;
    @BindView(R.id.linearlayout_nodata)
    LinearLayout linearlayoutNodata;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    private int pageOutter = 1;
    private ClassicsHeader mClassicsHeader;
    private Drawable mDrawableProgress;
    private QuickAdapter mAdapter;
    private static String TAG = "HHomeFragment";
    private List<ItemBanner> itemBanners;

    public static HHomeFragment newInstance() {
        Log.i(TAG, "newInstance");
        HHomeFragment fragment = new HHomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume----》");
    }

    @Override
    protected void initView() {
        Log.i(TAG, "initView");
        refreshLayout.autoRefresh();
        mClassicsHeader = (ClassicsHeader) refreshLayout.getRefreshHeader();
        mClassicsHeader.setAccentColor(getResources().getColor(R.color.square));
//        mClassicsHeader.getTitleText().setTextColor(getResources().getColor(R.color.square));
//        mClassicsHeader.getLastUpdateText().setTextColor(getResources().getColor(R.color.square));
        mClassicsHeader.setLastUpdateTime(new Date(System.currentTimeMillis()));
        mClassicsHeader.setTimeFormat(new SimpleDateFormat("更新于 MM-dd HH:mm", Locale.CHINA));
        mClassicsHeader.setTimeFormat(new DynamicTimeFormat("更新于 %s"));
//        mDrawableProgress = mClassicsHeader.getProgressView().getDrawable();
//        if (mDrawableProgress instanceof LayerDrawable) {
//            mDrawableProgress = ((LayerDrawable) mDrawableProgress).getDrawable(0);
//        }
        mAdapter = new QuickAdapter();
        //mAdapter.setEmptyView(linearlayoutNodata);
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), VERTICAL));
        recyclerViewComment.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewComment.setAdapter(mAdapter);
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.header_homepage, recyclerViewComment, false);
        mAdapter.setHeaderView(headerView);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                refreshlayout.getLayout().post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("fenye", "onRefresh");
                        pageOutter = 1;
                        mPresenter.getData(pageOutter);
                    }
                });
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageOutter = pageOutter + 1;
                mPresenter.getData(pageOutter);
            }
        });
        mPresenter.getBanner();
        addDisposable(RxView.clicks(llJoin).throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(o -> {
                    Log.e(TAG, "clicks:点击了按钮：两秒内防抖");
                }));
    }

    @Override
    public int getLayoutRes() {
        Log.i(TAG, "getLayoutRes");
        ifNeedStatus = true;
        return R.layout.fragment_hhome;
    }

    @Override
    public void createPresenter() {
        mPresenter = new PHHomeImpl(mContext, this);
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
    }

    @Override
    public void showData(List<ItemHomeData> data, int page) {
        // linearlayoutNodata.setVisibility(View.GONE);
        if (page == 1) {
            refreshLayout.finishRefresh();
            mAdapter.replaceData(data);
        } else {
            mAdapter.addData(data);
            refreshLayout.finishLoadMore();
        }

    }

    @Override
    public void showNoData(int page) {
        if (page == 1) {
            refreshLayout.finishRefresh();
            mAdapter.replaceData(new ArrayList<ItemHomeData>());
            // linearlayoutNodata.setVisibility(View.VISIBLE);
        } else {
            refreshLayout.finishLoadMore();
        }
    }

    @Override
    public void showBanner(List<ItemBanner> itemBanners) {
        this.itemBanners = itemBanners;
        List<String> images = new ArrayList<>();
        for (ItemBanner itemBanner : itemBanners) {
            images.add(itemBanner.getImage());
        }
        banner.setImages(images);
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Picasso.with(context).load(path.toString()).into(imageView);
            }
        })
                .start();


    }

    public class QuickAdapter extends BaseQuickAdapter<ItemHomeData, BaseViewHolder> {
        public QuickAdapter() {
            super(R.layout.item_homepage);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, final ItemHomeData item) {
            Glide.with(getActivity()).load(item.getFace_img()).into((ImageView) viewHolder.getView(R.id.imageview_left));
            viewHolder.setText(R.id.textview_price, "￥" + item.getActive_price()).
                    setText(R.id.textview_reward, "奖CCM值 " + item.getCcm()).
                    setText(R.id.textview_title, item.getGoods_name());
            viewHolder.setOnClickListener(R.id.rl_out, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("title", "");
                    intent.setClass(getActivity(), WebViewActivity.class);
                    intent.putExtra("url", Constant.API_URL_GOODS + "id/" + item.getId() + (TextUtils.isEmpty(SysUtils.getToken()) ? "" : "/token/" + SysUtils.getToken()));
                    startActivity(intent);
                }
            });
            viewHolder.setOnClickListener(R.id.btn_qiang, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("title", "");
                    intent.setClass(getActivity(), WebViewActivity.class);
                    intent.putExtra("url", Constant.API_URL_GOODS + "id/" + item.getId() + (TextUtils.isEmpty(SysUtils.getToken()) ? "" : "/token/" + SysUtils.getToken()));
                    startActivity(intent);
                }
            });
        }
    }

}
