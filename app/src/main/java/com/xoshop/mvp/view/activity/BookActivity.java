package com.xoshop.mvp.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.xoshop.R;
import com.xoshop.mvp.base.BaseActivity;
import com.xoshop.mvp.contract.CBook;
import com.xoshop.mvp.presenter.PBookImpl;

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
