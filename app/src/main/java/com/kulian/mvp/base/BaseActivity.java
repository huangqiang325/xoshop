package com.kulian.mvp.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.kulian.R;
import com.kulian.utils.AlertUtils;
import com.kulian.utils.StatusBarUtil;
import com.kulian.widget.SlidingLayout;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Liang_Lu on 2017/12/21.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity   {
    public Context mContext;
    public T mPresenter;
    private AlertDialog loading;
    private static Handler handler;
    public String TAG =  this.getClass().getSimpleName();
    private int height_statusbar = 0;
    private LinearLayout linearlayout_status;
    public boolean ifNeedStatus = true;
    public CompositeDisposable mCompositeDisposable;
    public boolean ifSliding  =true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//子类的super.OnCreate必须在setContentView后调用
        StatusBarUtil.StatusBarLightMode(this);
        mContext = this;
        if (setContentViewId() != 0) {
            setContentView(setContentViewId());
        } else {
            throw new RuntimeException("layoutResID==-1 have u create your layout?");
        }
        SlidingLayout rootView = new SlidingLayout(this);
        if(ifSliding){
            rootView.bindActivity(this);
        }
        if(ifNeedStatus){
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                height_statusbar = getResources().getDimensionPixelSize(resourceId);
            }
            linearlayout_status = (LinearLayout) findViewById(R.id.linearlayout_status);
            ViewGroup.LayoutParams paras = linearlayout_status.getLayoutParams();
            paras.height = height_statusbar;
            linearlayout_status.setLayoutParams(paras);
        }
        createPresenter();
        ButterKnife.bind(this);
        mCompositeDisposable = new CompositeDisposable();
        initView();
    }
    /**
     * 添加订阅
     */
    public void addDisposable(Disposable mDisposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(mDisposable);
    }

    /**
     * 取消所有订阅
     */
    public void clearDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }
    /**
     * 初始化方法
     */
    protected void initView() {

    }
    private Toast mToast;
    public void showToast(final String text){
        if(mToast == null){
            mToast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        }
        if(Thread.currentThread() != Looper.getMainLooper().getThread()){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mToast.setText(text);
                    mToast.show();
                }
            });
        }else {
            mToast.setText(text);
            mToast.show();
        }
    }
    public void loadingView(boolean isLoading,String content) {
        if (isLoading) {
            if (loading == null) {
                loading = AlertUtils.loadingDialog(mContext,content);
            }
            if (!loading.isShowing()) {
                loading.show();
               getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        } else {
            if (loading != null && loading.isShowing()) {
                loading.dismiss();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        }
    }
    /**
     * 获取contentView 资源id
     */
    public abstract int setContentViewId();

    /**
     * 创建presenter实例
     */
    public abstract void createPresenter();
    /**
     * activity跳转（无参数）
     *
     * @param className
     */
    public void startActivity(Class<?> className) {
        Intent intent = new Intent(mContext, className);
        startActivity(intent);
    }

    /**
     * activity跳转（有参数）
     *
     * @param className
     */
    public void startActivity(Class<?> className, Bundle bundle) {
        Intent intent = new Intent(mContext, className);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();//页面销毁 网络请求同销毁
        }
        ButterKnife.bind(this).unbind();
        clearDisposable();
    }
    protected void showKeyboard(boolean isShow) {
        Activity activity = (Activity)mContext;
        if (mContext == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        if (isShow) {
            if (activity.getCurrentFocus() == null) {
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            } else {
                imm.showSoftInput(activity.getCurrentFocus(), 0);
            }
        } else {
            if (activity.getCurrentFocus() != null) {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }

        }
    }

    protected void hideKeyboard(View view) {
        if (mContext == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        imm.hideSoftInputFromWindow(
                view.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
