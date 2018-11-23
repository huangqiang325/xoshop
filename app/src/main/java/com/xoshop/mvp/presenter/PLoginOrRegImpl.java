package com.xoshop.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.xoshop.mvp.contract.CLoginOrReg;
import com.xoshop.mvp.model.MLoginOrRegImpl;
import com.xoshop.mvp.base.BasePresenter;
import com.xoshop.utils.SysUtils;
import com.xoshop.utils.rxhelper.RxObservable;


/**
 * Created by HuangQiang on 2017/12/21.
 *
 * @author HuangQiang
 * @github https://github.com/HuangQiang
 */

public class PLoginOrRegImpl extends BasePresenter<CLoginOrReg.IVLoginOrReg, MLoginOrRegImpl> implements CLoginOrReg.IPLoginOrReg {


    public PLoginOrRegImpl(Context mContext, CLoginOrReg.IVLoginOrReg mView) {
        super(mContext, mView, new MLoginOrRegImpl());
    }

    @Override
    public void getCode(String phone) {
        mView.showLoading();
        mModel.mGetCode(new RxObservable() {
            @Override
            public void onSuccess(Object o) {
                String jsonString = SysUtils.getJsonString(o);
                Log.i(TAG,"请求验证码获取的数据为--》"+jsonString);
                JSONObject jsonObject = JSONObject.parseObject(jsonString);
                mView.codeCallBack(jsonObject);
            }

            @Override
            public void onFail(String reason) {

            }
        },phone);


    }

    @Override
    public void login(String phone, String code) {
        mView.showLoading();
        mModel.mDoLogin(new RxObservable() {
            @Override
            public void onSuccess(Object o) {
                String jsonString = SysUtils.getJsonString(o);
                Log.i(TAG,"登录获取的数据--》"+jsonString);
                JSONObject jsonObject = JSONObject.parseObject(jsonString);
                mView.loginCallBack(jsonObject);
            }

            @Override
            public void onFail(String reason) {

            }
        },phone,code);
    }
}
