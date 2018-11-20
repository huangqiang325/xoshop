package com.kulian.mvp.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kulian.R;
import com.kulian.mvp.base.BaseActivity;
import com.kulian.mvp.contract.CBook;
import com.kulian.mvp.presenter.PBookImpl;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Liang_Lu on 2017/12/21.
 * V层 用于数据和页面UI展示（Fragment Dialog 同理）
 */

public class BookActivity extends BaseActivity<PBookImpl> implements CBook.IVBook {

    private Button mBtn;
    private String TAG = "BookActivity";
    int[] imgResId = {
            R.drawable.jackson,
            R.drawable.jordan,
            R.drawable.kobe,
            R.drawable.stephen,
            R.drawable.android
    };

    @Override
    protected void initView() {
        super.initView();

    }

    @Override
    public int setContentViewId() {
        ifNeedStatus = false;
        return R.layout.activity_book;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume--->");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void createPresenter() {
        mPresenter = new PBookImpl(mContext, this);
    }




    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void vBookSuccess(String data) {
    }

    @Override
    public void vBookError(String reason) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


}
