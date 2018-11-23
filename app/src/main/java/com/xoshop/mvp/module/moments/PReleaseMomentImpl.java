package com.xoshop.mvp.module.moments;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xoshop.mvp.bean.ItemChannel;
import com.xoshop.mvp.bean.ItemIndustry;
import com.xoshop.mvp.base.BasePresenter;
import com.xoshop.utils.SysUtils;
import com.xoshop.utils.rxhelper.RxObservable;

import java.util.List;


/**
 * Created by HuangQiang on 2017/12/21.
 *
 * @author HuangQiang
 * @github https://github.com/HuangQiang
 */

public class PReleaseMomentImpl extends BasePresenter<CReleaseMoment.IVReleaseMoment, MReleaseMomentImpl> implements CReleaseMoment.IPReleaseMoment {


    public PReleaseMomentImpl(Context mContext, CReleaseMoment.IVReleaseMoment mView) {
        super(mContext, mView, new MReleaseMomentImpl());
    }

    @Override
    public void getIndustry() {
        mModel.mgetIndustry(new RxObservable() {
            @Override
            public void onSuccess(Object o) {
                String jsonString = SysUtils.getJsonString(o);
                JSONObject jsonObject = JSONObject.parseObject(jsonString);
                Log.i(TAG, "获取行业--->" + jsonString);
                if(jsonObject.getInteger("code")==0){
                    JSONArray array_list = jsonObject.getJSONArray("data");
                    String itemHomeListsString = array_list.toJSONString(array_list);
                    List<ItemIndustry> itemIndustries = JSONObject.parseArray(itemHomeListsString, ItemIndustry.class);
                    mView.callBackIndustry(itemIndustries);
                }
            }

            @Override
            public void onFail(String reason) {

            }
        });
    }

    @Override
    public void getChannel() {
        mModel.mgetChannel(new RxObservable() {
            @Override
            public void onSuccess(Object o) {
                String jsonString = SysUtils.getJsonString(o);
                JSONObject jsonObject = JSONObject.parseObject(jsonString);
                Log.i(TAG, "获取频道--->" + jsonString);
                if(jsonObject.getInteger("code")==0){
                    JSONArray array_list = jsonObject.getJSONArray("data");
                    String itemHomeListsString = array_list.toJSONString(array_list);
                    List<ItemChannel> itemIndustries = JSONObject.parseArray(itemHomeListsString, ItemChannel.class);
                    mView.callBackChannel(itemIndustries);
                }
            }

            @Override
            public void onFail(String reason) {

            }
        });

    }
}
