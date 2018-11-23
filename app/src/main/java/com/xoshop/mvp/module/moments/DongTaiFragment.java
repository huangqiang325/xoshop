package com.xoshop.mvp.module.moments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.acker.simplezxing.activity.CaptureActivity;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jakewharton.rxbinding2.view.RxView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xoshop.R;
import com.xoshop.comm.adapter.lunboadapter.CagegoryViewPagerAdapter;
import com.xoshop.comm.adapter.lunboadapter.EntranceAdapter;
import com.xoshop.comm.bean.Constant;
import com.xoshop.mvp.base.BaseFragment;
import com.xoshop.mvp.bean.ItemChannel;
import com.xoshop.mvp.bean.ItemIndustry;
import com.xoshop.mvp.bean.ItemMoment;
import com.xoshop.mvp.module.person.PersonInfoActivity;
import com.xoshop.utils.AuthPreferences;
import com.xoshop.utils.PermissionUtils;
import com.xoshop.utils.ScreenUtil;
import com.xoshop.widget.CircleImageView;
import com.xoshop.widget.DividerItemDecoration;
import com.xoshop.widget.ForbidRecycleViewScroll;
import com.xoshop.widget.IndicatorView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by HuangQiang on 2017/12/21.
 *
 * @author HuangQiang
 * @github https://github.com/HuangQiang
 */

public class DongTaiFragment extends BaseFragment<PDongTaiImpl> implements CDongTai.IVDongTai {

    public static final int HOME_ENTRANCE_PAGE_SIZE = 10;//首页菜单单页显示数量
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.image_btn_scan)
    ImageButton imageBtnScan;
    private final int REQUEST_CODE_PERMISSIONS = 55;
    private static String[] PERMISSIONS = {
            Manifest.permission.CAMERA};
    @BindView(R.id.image_btn_photo)
    ImageButton imageBtnPhoto;
    CircleImageView imageviewTouxiang;
    TextView textviewNickname;
    RecyclerView recyclerviewIndustry;
    ViewPager mainHomeEntranceVp;
    LinearLayout homeEntrance;
    IndicatorView mainHomeEntranceIndicator;
    @BindView(R.id.recyclerView_moment)
    RecyclerView recyclerViewMoment;
    private AlertView photoPickerDialog;
    private static String[] PERMISSIONS_PHOTO = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    public static DongTaiFragment newInstance() {
        DongTaiFragment fragment = new DongTaiFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private int page = 1;
    private String industryId = "";
    private String channelId = "";
    private String ifFilter = "";
    QuickAdapter_Industry quickAdapter_industry = new QuickAdapter_Industry();
    QuickAdapter_Moment quickAdapter_moment = new QuickAdapter_Moment();

    @Override
    protected void initView() {
        //初始化headerview
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.headerview_moment, null, false);
        homeEntrance = headerView.findViewById(R.id.home_entrance);
        mainHomeEntranceVp = headerView.findViewById(R.id.main_home_entrance_vp);
        mainHomeEntranceIndicator = headerView.findViewById(R.id.main_home_entrance_indicator);
        imageviewTouxiang = headerView.findViewById(R.id.imageview_touxiang);
        textviewNickname = headerView.findViewById(R.id.textview_nickname);
        recyclerviewIndustry = headerView.findViewById(R.id.recyclerview_industry);
        //初始化轮播菜单
        recyclerViewMoment.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        recyclerViewMoment.setItemAnimator(new DefaultItemAnimator());
        recyclerViewMoment.addItemDecoration(new DividerItemDecoration(this.getActivity()));
        recyclerViewMoment.setAdapter(quickAdapter_moment);
        quickAdapter_moment.addHeaderView(headerView);
        initRefresh();
        //设置横向的recycleview
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerviewIndustry.setLayoutManager(linearLayoutManager);
        recyclerviewIndustry.setAdapter(quickAdapter_industry);
        //扫一扫
        addDisposable(RxView.clicks(imageBtnScan).throttleFirst(Constant.SECONDS_FORBID, TimeUnit.SECONDS)
                .subscribe(o -> {
                    getPermions(PERMISSIONS);
                }));
        //个人中心
        addDisposable(RxView.clicks(imageviewTouxiang).throttleFirst(Constant.SECONDS_FORBID, TimeUnit.SECONDS)
                .subscribe(o -> {
                    startActivity(PersonInfoActivity.class);
                }));
        //发布朋友圈
        addDisposable(RxView.clicks(imageBtnPhoto).throttleFirst(Constant.SECONDS_FORBID, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                        showImage();
                    } else {
                        getPermions_PHOTO(PERMISSIONS_PHOTO);
                    }
                }));
        //获取数据
        mPresenter.getChannel();
        mPresenter.getIndustry();
        mPresenter.getMoments(page, industryId, channelId, ifFilter);
    }

    public void initRefresh() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mPresenter.getMoments(page, industryId, channelId, ifFilter);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page = page + 1;
                mPresenter.getMoments(page, industryId, channelId, ifFilter);
            }
        });
    }

    @Override
    public int getLayoutRes() {
        ifNeedStatus = false;
        return R.layout.fragment_dongtai;
    }

    @Override
    public void onResume() {
        super.onResume();
        String userString = AuthPreferences.getUserInfo();
        if (!TextUtils.isEmpty(userString)) {
            JSONObject jsonObject = JSONObject.parseObject(userString);
            String touxiang = jsonObject.getString("avatar");
            String name = jsonObject.getString("name");
            LoadImageUrl(imageviewTouxiang, touxiang);
            textviewNickname.setText(name);
        }
    }

    @Override
    public void createPresenter() {
        mPresenter = new PDongTaiImpl(mContext, this);
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

    /**
     * 上传图片
     */
    private void showImage() {
        new AlertView.Builder().setContext(getActivity())
                .setStyle(AlertView.Style.ActionSheet)
                .setMessage(null)
                .setCancelText("取消")
                .setDestructive("拍照", "从相册中选择")
                .setOthers(null)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        Intent intent = new Intent();
                        if (position == 0) {
                            intent.putExtra("way", "take");
                            intent.setClass(getActivity(), ReleaseMomentActivity.class);
                            startActivity(intent);
                        }
                        if (position == 1) {
                            intent.putExtra("way", "pick");
                            intent.setClass(getActivity(), ReleaseMomentActivity.class);
                            startActivity(intent);
                        }
                    }
                })
                .build().show();
    }

    //二维码
    private void startCaptureActivityForResult() {
        Intent intent = new Intent(getActivity(), CaptureActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(CaptureActivity.KEY_NEED_BEEP, CaptureActivity.VALUE_BEEP);
        bundle.putBoolean(CaptureActivity.KEY_NEED_VIBRATION, CaptureActivity.VALUE_VIBRATION);
        bundle.putBoolean(CaptureActivity.KEY_NEED_EXPOSURE, CaptureActivity.VALUE_NO_EXPOSURE);
        bundle.putByte(CaptureActivity.KEY_FLASHLIGHT_MODE, CaptureActivity.VALUE_FLASHLIGHT_OFF);
        bundle.putByte(CaptureActivity.KEY_ORIENTATION_MODE, CaptureActivity.VALUE_ORIENTATION_AUTO);
        bundle.putBoolean(CaptureActivity.KEY_SCAN_AREA_FULL_SCREEN, CaptureActivity.VALUE_SCAN_AREA_FULL_SCREEN);
        bundle.putBoolean(CaptureActivity.KEY_NEED_SCAN_HINT_TEXT, CaptureActivity.VALUE_SCAN_HINT_TEXT);
        intent.putExtra(CaptureActivity.EXTRA_SETTING_BUNDLE, bundle);
        startActivityForResult(intent, CaptureActivity.REQ_CODE);
    }

    //zxing扫码的权限
    public void getPermions(String[] PERMISSIONS) {
        PermissionUtils.checkMorePermissions(getActivity(), PERMISSIONS, new PermissionUtils.PermissionCheckCallBack() {
            @Override
            public void onHasPermission() {
                Log.i(TAG, "有权限");
                startCaptureActivityForResult();
            }

            @Override
            public void onUserHasAlreadyTurnedDown(String... permission) {
                Log.i(TAG, "onUserHasAlreadyTurnedDown");
                // showImage();
                showExplainDialog(permission, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionUtils.requestMorePermissions(getActivity(), PERMISSIONS, REQUEST_CODE_PERMISSIONS);
                    }
                });
            }

            @Override
            public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
                Log.i(TAG, "onUserHasAlreadyTurnedDownAndDontAsk");
                PermissionUtils.requestMorePermissions(getActivity(), PERMISSIONS, REQUEST_CODE_PERMISSIONS);
            }
        });

    }

    //朋友圈拍照的权限
    public void getPermions_PHOTO(String[] PERMISSIONS) {
        PermissionUtils.checkMorePermissions(getActivity(), PERMISSIONS, new PermissionUtils.PermissionCheckCallBack() {
            @Override
            public void onHasPermission() {
                Log.i(TAG, "有权限");
                showImage();
            }

            @Override
            public void onUserHasAlreadyTurnedDown(String... permission) {
                Log.i(TAG, "onUserHasAlreadyTurnedDown");
                // showImage();
                showExplainDialog(permission, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionUtils.requestMorePermissions(getActivity(), PERMISSIONS, REQUEST_CODE_PERMISSIONS);
                    }
                });
            }

            @Override
            public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
                Log.i(TAG, "onUserHasAlreadyTurnedDownAndDontAsk");
                PermissionUtils.requestMorePermissions(getActivity(), PERMISSIONS, REQUEST_CODE_PERMISSIONS);
            }
        });

    }

    private void showExplainDialog(String[] permission, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(getActivity())
                .setTitle("申请权限")
                .setMessage("我们需要摄像头的权限")
                .setPositiveButton("确定", onClickListener)
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CaptureActivity.REQ_CODE:
                switch (resultCode) {
                    case RESULT_OK:
                        String data_ = data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT);
                        break;
                    case RESULT_CANCELED:
                        if (data != null) {
                            // for some reason camera is not working correctly
                            String data_cancel = data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT);
                        }
                        break;
                }
                break;
        }
    }

    //回调行业
    @Override
    public void callBackIndustry(List<ItemIndustry> itemIndustries) {
        LinearLayout.LayoutParams layoutParams12 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) ((float) ScreenUtil.getScreenWidth() / 2.0f));
        //首页菜单分页
        //FrameLayout.LayoutParams entrancelayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, (int) ((float) ScreenUtil.getScreenWidth() / 2.0f + 70));
        //homeEntrance.setLayoutParams(entrancelayoutParams);
        mainHomeEntranceVp.setLayoutParams(layoutParams12);
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        //将RecyclerView放至ViewPager中：
        int pageSize = HOME_ENTRANCE_PAGE_SIZE;
        //一共的页数等于 总数/每页数量，并取整。
        int pageCount = (int) Math.ceil(itemIndustries.size() * 1.0 / pageSize);
        List<View> viewList = new ArrayList<View>();
        for (int index = 0; index < pageCount; index++) {
            //每个页面都是inflate出一个新实例
            RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.__item_home_menu_vp, mainHomeEntranceVp, false);
            recyclerView.setLayoutParams(layoutParams12);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 5));
            EntranceAdapter entranceAdapter = new EntranceAdapter(getActivity(), itemIndustries, index, HOME_ENTRANCE_PAGE_SIZE);
            recyclerView.setAdapter(entranceAdapter);
            viewList.add(recyclerView);
        }
        CagegoryViewPagerAdapter adapter = new CagegoryViewPagerAdapter(viewList);
        if (itemIndustries.size() <= 10) {
            mainHomeEntranceIndicator.setVisibility(View.GONE);
        } else {
            mainHomeEntranceIndicator.setVisibility(View.VISIBLE);
        }
        mainHomeEntranceVp.setAdapter(adapter);
        mainHomeEntranceIndicator.setIndicatorCount(mainHomeEntranceVp.getAdapter().getCount());
        mainHomeEntranceIndicator.setCurrentIndicator(mainHomeEntranceVp.getCurrentItem());
        mainHomeEntranceVp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mainHomeEntranceIndicator.setCurrentIndicator(position);
            }
        });

    }

    //回调频道
    @Override
    public void callBackChannel(List<ItemChannel> itemChannels) {
        if (itemChannels != null && itemChannels.size() > 0) {
            itemChannels.add(0, new ItemChannel("", "最新动态", "1"));
            quickAdapter_industry.replaceData(itemChannels);
        }

    }

    //回调朋友圈数据
    @Override
    public void callBackMoment(List<ItemMoment> itemMoments) {
        if (page == 1) {
            if (itemMoments == null || itemMoments.size() == 0) {
                showToast("没有数据");
                quickAdapter_moment.replaceData(new ArrayList<ItemMoment>());
                refreshLayout.finishRefresh();
            } else {
                quickAdapter_moment.replaceData(itemMoments);
                refreshLayout.finishRefresh();
            }
        } else if (page > 1) {
            if (itemMoments == null || itemMoments.size() == 0) {
                refreshLayout.finishLoadMore();
                showToast("没有更多的数据了");
                refreshLayout.setEnableLoadMore(false);
            } else {
                quickAdapter_moment.addData(itemMoments);
                refreshLayout.finishLoadMore();
            }
        }

    }

    public class QuickAdapter_Industry extends BaseQuickAdapter<ItemChannel, BaseViewHolder> {
        public QuickAdapter_Industry() {
            super(R.layout.item_industry);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, final ItemChannel item) {
            Button button = viewHolder.getView(R.id.btn_industry);
            button.setText(item.getName());
            if (item.getIfCheck().equals("0")) {
                button.setTextColor(getResources().getColor(R.color.indu_notcheck));
                button.setBackground(null);
            } else if (item.getIfCheck().equals("1")) {
                button.setTextColor(getResources().getColor(R.color.white));
                button.setBackground(getResources().getDrawable(R.drawable.bg_shape_industry));
            }
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<ItemChannel> itemIndustries = quickAdapter_industry.getData();
                    for (int i = 0; i < itemIndustries.size(); i++) {
                        if (viewHolder.getPosition() == i) {
                            itemIndustries.get(i).setIfCheck("1");
                        } else {
                            itemIndustries.get(i).setIfCheck("0");
                        }
                        quickAdapter_industry.replaceData(itemIndustries);
                    }
                }
            });

        }
    }

    public class QuickAdapter_Moment extends BaseQuickAdapter<ItemMoment, BaseViewHolder> {
        public QuickAdapter_Moment() {
            super(R.layout.item_moment);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, final ItemMoment item) {
            LoadImageUrl(viewHolder.getView(R.id.imageview_touxiang), item.getUser().getAvatar());


        }
    }
}
