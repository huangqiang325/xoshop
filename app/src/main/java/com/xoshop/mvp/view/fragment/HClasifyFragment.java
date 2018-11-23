package com.xoshop.mvp.view.fragment;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jakewharton.rxbinding2.view.RxView;
import com.xoshop.R;
import com.xoshop.comm.activity.WebViewActivity;
import com.xoshop.comm.bean.Constant;
import com.xoshop.mvp.base.BaseFragment;
import com.xoshop.mvp.bean.ItemClassify;
import com.xoshop.mvp.bean.ItemClassify_left;
import com.xoshop.mvp.contract.CHClasify;
import com.xoshop.mvp.presenter.PHClasifyImpl;
import com.xoshop.utils.SysUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Liang_Lu on 2017/12/21.
 *
 * @author LuLiang
 * @github https://github.com/LiangLuDev
 */

public class HClasifyFragment extends BaseFragment<PHClasifyImpl> implements CHClasify.IVHClasify {

    @BindView(R.id.imagebtn_search)
    ImageButton imagebtnSearch;
    @BindView(R.id.textview_search)
    TextView textviewSearch;
    @BindView(R.id.relative_search)
    RelativeLayout relativeSearch;
    @BindView(R.id.recyclerView_left)
    RecyclerView recyclerViewLeft;
    @BindView(R.id.recyclerView_comment)
    RecyclerView recyclerViewComment;
    @BindView(R.id.linearlayout_nodata)
    LinearLayout linearlayoutNodata;
    @BindView(R.id.xrefreshview)
    XRefreshView xRefreshView;
    private QuickAdapter_Left mAdapter_left;
    private QuickAdapter_Right mAdapter_right;
    Unbinder unbinder;
    private int pageOutter = 1;
    private String TAG = "HClasifyFragment";
    private int width_recycleview_left = 0;
    private String checkType = "";
    public static boolean ifRefresh = false;

    public static HClasifyFragment newInstance() {
        HClasifyFragment fragment = new HClasifyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView() {
        mAdapter_left = new QuickAdapter_Left();
        mAdapter_right = new QuickAdapter_Right();
        xRefreshView.setPinnedTime(1000);
        xRefreshView.setMoveForHorizontal(true);
        xRefreshView.setPullLoadEnable(true);
        xRefreshView.setAutoLoadMore(false);
        xRefreshView.enableReleaseToLoadMore(true);
        xRefreshView.enableRecyclerViewPullUp(true);
        xRefreshView.enablePullUpWhenLoadCompleted(true);
        recyclerViewComment.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerViewLeft.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewLeft.setAdapter(mAdapter_left);
        width_recycleview_left = recyclerViewLeft.getLayoutParams().width;
        recyclerViewComment.setAdapter(mAdapter_right);
        mPresenter.getDataLeft();
        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {

            @Override
            public void onRefresh(boolean isPullDown) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        ifRefresh = true ;
                        pageOutter = 1;
                        mPresenter.getDataRight(checkType, pageOutter);
                    }
                });
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                new Handler().post(new Runnable() {
                    public void run() {
                        pageOutter = pageOutter + 1;
                        mPresenter.getDataRight(checkType, pageOutter);
                    }
                });
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume--->DASDSA");
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_hclasify;
    }

    @Override
    public void createPresenter() {
        mPresenter = new PHClasifyImpl(mContext, this);
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
    public void renderDataLeft(List<ItemClassify_left> itemClassify_lefts) {
        if (itemClassify_lefts != null && itemClassify_lefts.size() > 0) {
            itemClassify_lefts.get(0).setIfCheck("1");
            checkType = itemClassify_lefts.get(0).getId();
            xRefreshView.startRefresh();
        }
        mAdapter_left.replaceData(itemClassify_lefts);
    }

    @Override
    public void renderDataRight(List<ItemClassify> itemClassifies, int page) {
        linearlayoutNodata.setVisibility(View.GONE);
        if (page == 1) {
            xRefreshView.stopRefresh();
            ifRefresh = false ;
            mAdapter_right.replaceData(itemClassifies);
            xRefreshView.setLoadComplete(false);
        } else {
            mAdapter_right.addData(itemClassifies);
            xRefreshView.stopLoadMore();
        }
    }

    @Override
    public void renderDataRightNoData(int page) {
        if (page == 1) {
            xRefreshView.stopRefresh();
            ifRefresh = false ;
            mAdapter_right.replaceData(new ArrayList<ItemClassify>());
            linearlayoutNodata.setVisibility(View.VISIBLE);
        } else {
            xRefreshView.setLoadComplete(true);
        }
    }

    public class QuickAdapter_Right extends BaseQuickAdapter<ItemClassify, BaseViewHolder> {
        public QuickAdapter_Right() {
            super(R.layout.item_classify);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, final ItemClassify item) {
            RelativeLayout relative_out = viewHolder.getView(R.id.relative_out);
            ViewGroup.LayoutParams lp = relative_out.getLayoutParams();
            int size = (SysUtils.getScreenWidth(getActivity()) - width_recycleview_left) / 3;
            lp.width = size;
            relative_out.setLayoutParams(lp);
            Glide.with(getActivity()).load(item.getFace_img()).bitmapTransform(new RoundedCornersTransformation(getActivity(), 15, 0,
                    RoundedCornersTransformation.CornerType.TOP)).into((ImageView) viewHolder.getView(R.id.imageview_top));
            ((TextView) viewHolder.getView(R.id.textview_form_price)).getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.setText(R.id.textview_price, "￥" + item.getPrice()).
                    setText(R.id.textview_form_price, item.getPrice()).
                    setText(R.id.textview_top, "奖CCM值 20.0").
                    setText(R.id.textview_name, item.getGoods_name());
            viewHolder.setOnClickListener(R.id.relative_out, new View.OnClickListener() {
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

    public class QuickAdapter_Left extends BaseQuickAdapter<ItemClassify_left, BaseViewHolder> {
        public QuickAdapter_Left() {
            super(R.layout.item_classify_left);
        }

        @Override
        protected void convert(final BaseViewHolder viewHolder, final ItemClassify_left item) {
            viewHolder.setText(R.id.textview_left, item.getCategory_name());
            if (TextUtils.isEmpty(item.getIfCheck()) || item.getIfCheck().equals("0")) {
                viewHolder.setVisible(R.id.imageview_left, false);
                viewHolder.setTextColor(R.id.textview_left, getResources().getColor(R.color.mainColor));
            } else {
                viewHolder.setVisible(R.id.imageview_left, true);
                viewHolder.setTextColor(R.id.textview_left, getResources().getColor(R.color.secondColor));
            }
            addDisposable(RxView.clicks(viewHolder.getView(R.id.relative_out)).throttleFirst(4, TimeUnit.SECONDS).subscribe(o -> {

                    if(ifRefresh){

                    }else{
                        int position = viewHolder.getPosition();
                        mAdapter_left.getData().get(position).setIfCheck("1");
                        checkClassify(position);
                        checkType = item.getId();
                        xRefreshView.startRefresh();
                    }

            }));
        }
    }

    public void checkClassify(int position) {
        List<ItemClassify_left> itemClassify_lefts = mAdapter_left.getData();
        for (int i = 0; i < itemClassify_lefts.size(); i++) {
            if (position == i) {
                itemClassify_lefts.get(i).setIfCheck("1");
            } else {
                itemClassify_lefts.get(i).setIfCheck("0");
            }
        }
        mAdapter_left.replaceData(itemClassify_lefts);
    }
}
