package com.kulian.comm.activity;

import android.Manifest;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.flyco.tablayout.widget.MsgView;
import com.jkb.slidemenu.OnSlideChangedListener;
import com.jkb.slidemenu.SlideMenuLayout;
import com.kulian.R;
import com.kulian.comm.bean.Constant;
import com.kulian.comm.bean.TabEntity;
import com.kulian.mvp.view.fragment.HCartFragment;
import com.kulian.mvp.view.fragment.HClasifyFragment;
import com.kulian.mvp.view.fragment.HHomeFragment;
import com.kulian.mvp.view.fragment.HMineFragment;
import com.kulian.mvp.view.fragment.HSheQuFragment;
import com.kulian.utils.ActivityManagerUtils;
import com.kulian.utils.JpushUtil;
import com.kulian.utils.PermissionUtils;
import com.kulian.utils.StatusBarUtil;
import com.kulian.utils.ViewFindUtils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

import static com.jkb.slidemenu.SlideMenuAction.SLIDE_MODE_LEFT_RIGHT;
import static com.jkb.slidemenu.SlideMenuAction.SLIDE_MODE_NONE;


/**
 * Created by xiaoqiang on 2017/12/25.
 */
public class HomePageActivity extends FragmentActivity implements View.OnClickListener{
    public static boolean isForeground = false;
    private LinearLayout linearlayout_status;
    private Context mContext = this;
    //    private int height_statusbar = 0;
//    private LinearLayout linearlayout_status;
    private String[] mTitles = {"首页", "特惠专区", "发现", "购物车", "我的"};
    private int height_statusbar = 0;
    private int[] mIconSelectIds = {
            R.mipmap.shouye, R.mipmap.tehui, R.mipmap.faxian, R.mipmap.gouwuche, R.mipmap.wode
    };
    private int[] mIconUnselectIds = {
            R.mipmap.shouye1, R.mipmap.tehui1, R.mipmap.faxian1, R.mipmap.gouwuche1, R.mipmap.wode1
    };
    private ArrayList<Fragment> mFragments2 = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    public static CommonTabLayout mTabLayout_3;
    private View mDecorView;
    private static boolean isExit = false;
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    private String TAG = "HomePageActivity";
    private Intent websocketServiceIntent;
    public static int count = 0;
    private final int REQUEST_CODE_PERMISSIONS = 55;
    private SlideMenuLayout slideMenuLayout;
    private Button btn_left;
    private Button btn_right;
    private LinearLayout rl_top;
    private static String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA};

    public void getPermions() {
        PermissionUtils.checkMorePermissions(this, PERMISSIONS, new PermissionUtils.PermissionCheckCallBack() {
            @Override
            public void onHasPermission() {
                Log.i(TAG,"有权限");
               // showImage();
            }

            @Override
            public void onUserHasAlreadyTurnedDown(String... permission) {
                Log.i(TAG,"onUserHasAlreadyTurnedDown");
                // showImage();
                showExplainDialog(permission, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionUtils.requestMorePermissions(HomePageActivity.this, PERMISSIONS, REQUEST_CODE_PERMISSIONS);
                    }
                });
            }

            @Override
            public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
                Log.i(TAG,"onUserHasAlreadyTurnedDownAndDontAsk");
             PermissionUtils.requestMorePermissions(HomePageActivity.this, PERMISSIONS, REQUEST_CODE_PERMISSIONS);
            }
        });

    }
    private void showExplainDialog(String[] permission, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(this)
                .setTitle("申请权限")
                .setMessage("我们需要摄像头的权限")
                .setPositiveButton("确定", onClickListener)
                .show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sliding);
        //initStatusBar();
        StatusBarUtil.StatusBarLightMode(this);
        ActivityManagerUtils.getInstance().addActivity(this);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mFragments2.add(new HHomeFragment());
        mFragments2.add(new HClasifyFragment());
        mFragments2.add(new HSheQuFragment());
        mFragments2.add(new HCartFragment());
        mFragments2.add(new HMineFragment());
        mDecorView = getWindow().getDecorView();
        mTabLayout_3 = ViewFindUtils.find(mDecorView, R.id.tl_3);
        mTabLayout_3.setTabData(mTabEntities, this, R.id.fl_change, mFragments2);
        rl_top = findViewById(R.id.rl_top);
        mTabLayout_3.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switch (position) {
                    case 0:
                        //rl_top.setVisibility(View.VISIBLE);
                        //slideMenuLayout.setAllowTogging(true);
                        rl_top.setVisibility(View.GONE);
                        slideMenuLayout.setAllowTogging(false);
                        mTabLayout_3.setCurrentTab(0);
                        break;
                    case 1:
                        rl_top.setVisibility(View.GONE);
                        slideMenuLayout.setAllowTogging(false);
                        mTabLayout_3.setCurrentTab(1);
                        break;
                    case 2:
                        rl_top.setVisibility(View.GONE);
                        slideMenuLayout.setAllowTogging(false);
                        mTabLayout_3.setCurrentTab(2);
                        break;
                    case 3:
                        rl_top.setVisibility(View.GONE);
                        slideMenuLayout.setAllowTogging(false);
                        mTabLayout_3.setCurrentTab(3);
                        break;
                    case 4:
                        rl_top.setVisibility(View.GONE);
                        slideMenuLayout.setAllowTogging(false);
                        mTabLayout_3.setCurrentTab(4);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabReselect(int position) {


            }
        });
        mTabLayout_3.setCurrentTab(0);
        initView();
        setOnClick();
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            //getPermions();
        }
    }


    public static void setCurrentTab(int page) {
        mTabLayout_3.setCurrentTab(page);
    }


    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_bottom_in, R.anim.silde_bottom_out);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }



    public void initView() {
        btn_left = findViewById(R.id.btn_left);
        btn_right = findViewById(R.id.btn_right);
        //菜单侧滑
        slideMenuLayout = (SlideMenuLayout) findViewById(R.id.mainSlideMenu);
//        slideMenuLayout.setSlideMode(SLIDE_MODE_LEFT_RIGHT);
//        slideMenuLayout.setAllowTogging(true);
        slideMenuLayout.setSlideMode(SLIDE_MODE_NONE);
        slideMenuLayout.setAllowTogging(false);
        slideMenuLayout.addOnSlideChangedListener(new OnSlideChangedListener() {
            @Override
            public void onSlideChanged(SlideMenuLayout slideMenu, boolean isLeftSlideOpen, boolean isRightSlideOpen) {
                Log.d(TAG, "onSlideChanged:isLeftSlideOpen=" + isLeftSlideOpen + ":isRightSlideOpen=" + isRightSlideOpen);
            }
        });
        MsgView msgView = mTabLayout_3.getMsgView(1);
         msgView.setTextSize(8);
        if (msgView != null) {
            msgView.setBackgroundColor(Color.parseColor("#FA3232"));
        }
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height_statusbar = getResources().getDimensionPixelSize(resourceId);
        }
        linearlayout_status = (LinearLayout) findViewById(R.id.linearlayout_status);
        ViewGroup.LayoutParams paras = linearlayout_status.getLayoutParams();
        paras.height = height_statusbar;
        linearlayout_status.setLayoutParams(paras);
    }
    public void setOnClick() {
       btn_left.setOnClickListener(this);
       btn_right.setOnClickListener(this);
    }

    private void exit() {
        if (slideMenuLayout.isLeftSlideOpen() || slideMenuLayout.isRightSlideOpen()) {
            slideMenuLayout.closeLeftSlide();
            slideMenuLayout.closeRightSlide();
        } else {
            if (!isExit) {
                isExit = true;
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                // 利用handler延迟发送更改状态信息
                mHandler.sendEmptyMessageDelayed(0, 2000);
            } else {
                finish();
            }
        }
    }

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
        Log.i(TAG, "onResume请求------");
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_left:
                slideMenuLayout.toggleLeftSlide();
                break;
            case R.id.btn_right:
                slideMenuLayout.toggleRightSlide();
                break;
                default:
                    break;
        }

    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    Log.i(TAG, "extras-->" + extras + "messge-->" + messge);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!JpushUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                    setCostomMsg(showMsg.toString());
                }
            } catch (Exception e) {
            }
        }
    }
    /**
     * 初始化沉浸式状态栏
     */
    private void initStatusBar() {
        //设置是否沉浸式
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        int flag_translucent_status = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        //透明状态栏
        getWindow().setFlags(flag_translucent_status, flag_translucent_status);
    }
    private void setCostomMsg(String msg) {

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_bottom_in, R.anim.silde_bottom_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        count = 0;
        mTabLayout_3.hideMsg(1 );
    }


}
