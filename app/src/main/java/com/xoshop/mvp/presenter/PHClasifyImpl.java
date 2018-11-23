package com.xoshop.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xoshop.mvp.bean.ItemClassify;
import com.xoshop.mvp.bean.ItemClassify_left;
import com.xoshop.mvp.contract.CHClasify;
import com.xoshop.mvp.model.MHClasifyImpl;
import com.xoshop.mvp.base.BasePresenter;
import com.xoshop.utils.SysUtils;
import com.xoshop.utils.rxhelper.RxObservable;


import java.util.List;


/**
 * Created by Liang_Lu on 2017/12/21.
 *
 * @author LuLiang
 * @github https://github.com/LiangLuDev
 */

public class PHClasifyImpl extends BasePresenter<CHClasify.IVHClasify, MHClasifyImpl> implements CHClasify.IPHClasify {

    public PHClasifyImpl(Context mContext, CHClasify.IVHClasify mView) {
        super(mContext, mView, new MHClasifyImpl());
    }

    @Override
    public void getDataLeft() {
        mModel.mgetClassifyLeft(new RxObservable() {
            @Override
            public void onSuccess(Object o) {
                String jsonString = SysUtils.getJsonString(o);
                JSONObject jsonObject = JSONObject.parseObject(jsonString);
                String code = jsonObject.getString("code");
                Log.i(TAG, "getDataLeft--->" + jsonString);
                if (code.equals("1")) {
                    JSONArray array_list = jsonObject.getJSONArray("data");
                    String itemHomeListsString = array_list.toJSONString(array_list);
                    List<ItemClassify_left> itemClassify_lefts = JSONObject.parseArray(itemHomeListsString, ItemClassify_left.class);
                    mView.renderDataLeft(itemClassify_lefts);
                }
            }

            @Override
            public void onFail(String reason) {

            }
        });

    }

    @Override
    public void getDataRight(String index, int page) {
        mModel.mgetClassifyRight(new RxObservable() {
            @Override
            public void onSuccess(Object o) {
                String jsonString = SysUtils.getJsonString(o);
                JSONObject jsonObject =  JSONObject.parseObject(jsonString);
                String code = jsonObject.getString("code");
                Log.i(TAG, "getDataRight--->" + jsonString);
                if (code.equals("1")) {
                    JSONArray array_list = jsonObject.getJSONArray("data");
                    String itemHomeListsString = array_list.toJSONString(array_list);
                    List<ItemClassify> itemClassifies = JSONObject.parseArray(itemHomeListsString, ItemClassify.class);
                    mView.renderDataRight(itemClassifies, page);
                } else {
                    mView.renderDataRightNoData(page);
                }
            }

            @Override
            public void onFail(String reason) {

            }
        }, index, page + "", 2 + "");
        Log.i(TAG, "发送的id为--->" + index);
    }
}
