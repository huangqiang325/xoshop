package com.xoshop.mvp.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSONObject;
import com.xoshop.mvp.base.BasePresenter;
import com.xoshop.mvp.contract.CBook;
import com.xoshop.mvp.model.MBookImpl;

/**
 * Created by Liang_Lu on 2017/12/21.
 * P层 此类只用于处理业务逻辑 然后把最终的结果回调给V层
 */

public class PBookImpl extends BasePresenter<CBook.IVBook, MBookImpl> implements CBook.IPBook {

    private String TAG = "PBookImpl";
    public PBookImpl(Context mContext, CBook.IVBook mView) {
        super(mContext, mView, new MBookImpl());
    }


    @Override
    public void pBook() {
        mView.showLoading();

    }
    public Handler handler_getData_Success = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            JSONObject jsonObject = (JSONObject) msg.obj;
            mView.vBookSuccess(JSONObject.toJSONString(jsonObject));
        }
    };
    public Handler handler_getData_Failure = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mView.vBookError("请求失败");
        }
    };
}
