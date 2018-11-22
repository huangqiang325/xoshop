package com.kulian.mvp.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kulian.R;
import com.kulian.utils.AlertUtils;
import com.kulian.utils.PermissionUtils;
import com.kulian.utils.StatusBarUtil;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * Created by Liang_Lu on 2017/9/29.
 * Fragment基类
 */

public abstract class BaseFragment<T extends BasePresenter> extends BaseTFragment {
    private AlertDialog loading;
    private int height_statusbar = 0;
    public String TAG =  this.getClass().getSimpleName();
    private LinearLayout linearlayout_status;
    protected View findViewById(int id) {
        return getView().findViewById(id);
    }
    public boolean ifNeedStatus = true;
    private Toast mToast;
    public CompositeDisposable mCompositeDisposable;
    protected Context mContext;
    public View mRootView;
    public T mPresenter;

    /**
     * 获得全局的，防止使用getActivity()为空
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {

        if (mRootView == null) {
            if (getLayoutRes() != 0) {
                mRootView = LayoutInflater.from(mContext)
                        .inflate(getLayoutRes(), container, false);
                ButterKnife.bind(this, mRootView);
                mCompositeDisposable = new CompositeDisposable();
                createPresenter();
                initView();
            } else {
                throw new RuntimeException("layoutResID==-1 have u create your layout?");
            }
        }

        return mRootView;
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
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StatusBarUtil.StatusBarLightMode(getActivity());
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
    }
    public void LoadImageUrl (ImageView id, String imageurl){
        Glide.with(getActivity()).load(imageurl).into(id);
    }
    protected void showToast(final String text) {
        if (mToast == null) {
            mToast = Toast.makeText(getActivity(), text, Toast.LENGTH_LONG);
        }
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mToast.setText(text);
                    mToast.show();
                }
            });
        } else {
            mToast.setText(text);
            mToast.show();
        }
    }
    /**
     * 显示前往应用设置Dialog
     */
    public void toSetting() {
        new AlertDialog.Builder(getActivity())
                .setTitle("需要权限")
                .setMessage("我们需要相关权限，才能实现功能，点击前往，将转到应用的设置界面，请开启应用的相关权限。")
                .setPositiveButton("前往", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionUtils.toAppSetting(getActivity());
                    }
                })
                .setNegativeButton("取消", null).show();
    }
    public void baseToLogin(){
        //startActivity(new Intent(getActivity(),LoginActivity.class));
    }
    public void loadingView(boolean isLoading,String content) {
        if (isLoading) {
            if (loading == null) {
                loading = AlertUtils.loadingDialog(getActivity(),content);
            }
            if (!loading.isShowing()) {
                loading.show();
                getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            }
        } else {
            if (loading != null && loading.isShowing()) {
                loading.dismiss();
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        }
    }
    /**
     * 创建presenter实例
     */
    public abstract void createPresenter();


    /**
     * 初始化
     */
    protected abstract void initView();



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

    /**
     * 传入布局文件
     *
     * @return
     */
    public abstract int getLayoutRes();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();//页面销毁网络请求也取消
        }
        ButterKnife.bind(getActivity()).unbind();
        clearDisposable();
    }
}
