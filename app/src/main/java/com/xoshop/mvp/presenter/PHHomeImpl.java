package com.xoshop.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xoshop.mvp.bean.ItemBanner;
import com.xoshop.mvp.bean.ItemHomeData;
import com.xoshop.mvp.contract.CHHome;
import com.xoshop.mvp.model.MHHomeImpl;
import com.xoshop.mvp.base.BasePresenter;
import com.xoshop.utils.SysUtils;
import com.xoshop.utils.rxhelper.RxObservable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Liang_Lu on 2017/12/21.
 *
 * @author LuLiang
 * @github https://github.com/LiangLuDev
 */

public class PHHomeImpl extends BasePresenter<CHHome.IVHHome, MHHomeImpl> implements CHHome.IPHHome {
    private String TAG = "首页";
    private List<ItemHomeData> itemHomeDatas = new ArrayList<ItemHomeData>();
    private List<ItemBanner> banners = new ArrayList<ItemBanner>();

    public PHHomeImpl(Context mContext, CHHome.IVHHome mView) {
        super(mContext, mView, new MHHomeImpl());
    }

    @Override
    public void getData(final int page) {
        int pageData = page;
        Map<String, String> data = new HashMap<String, String>();
        data.put("page", page + "");
        data.put("page_num", 15 + "");
        mModel.mgetData(new RxObservable() {
            @Override
            public void onSuccess(Object o) {
                String jsonString = SysUtils.getJsonString(o);
                Log.i(TAG,"getData-->"+jsonString);
                JSONObject jsonObject =  JSONObject.parseObject(jsonString);
                String code = jsonObject.getString("code");
                int page = pageData;
                if (code.equals("1")) {
                    JSONArray array_list = jsonObject.getJSONArray("data");
                    String itemHomeListsString = array_list.toJSONString(array_list);
                    itemHomeDatas = JSONObject.parseArray(itemHomeListsString, ItemHomeData.class);
                    mView.showData(itemHomeDatas, page);
                } else {
                    mView.showNoData(page);
                }
            }

            @Override
            public void onFail(String reason) {

            }
        }, data);

    }

    @Override
    public void getBanner() {
        Map<String, String> data = new HashMap<>();
        data.put("type", "0");
        mModel.mgetBanner(new RxObservable() {
            @Override
            public void onSuccess(Object o) {
                String jsonString = SysUtils.getJsonString(o);
                JSONObject jsonObject = JSONObject.parseObject(jsonString);
                String code = jsonObject.getString("code");
                if (code.equals("1")) {
                    JSONArray array_list = jsonObject.getJSONArray("data");
                    String itemHomeListsString = array_list.toJSONString(array_list);
                    banners = JSONObject.parseArray(itemHomeListsString, ItemBanner.class);
                    mView.showBanner(banners);
                }
            }

            @Override
            public void onFail(String reason) {

            }
        }, data);
    }
}
