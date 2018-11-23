package com.xoshop.mvp.module.moments;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xoshop.mvp.bean.ItemChannel;
import com.xoshop.mvp.bean.ItemIndustry;
import com.xoshop.mvp.base.BasePresenter;
import com.xoshop.mvp.bean.ItemMoment;
import com.xoshop.utils.SysUtils;
import com.xoshop.utils.rxhelper.RxObservable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by HuangQiang on 2017/12/21.
 *
 * @author HuangQiang
 * @github https://github.com/HuangQiang
 */

public class PDongTaiImpl extends BasePresenter<CDongTai.IVDongTai, MDongTaiImpl> implements CDongTai.IPDongTai {

     private int pageSize = 10;
    public PDongTaiImpl(Context mContext, CDongTai.IVDongTai mView) {
        super(mContext, mView, new MDongTaiImpl());
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

    @Override
    public void getMoments(int page,String industryId,String channelId,String filter) {
        Map<String,String> data = new LinkedHashMap<String,String>();
        data.put("channelId",channelId);
        data.put("filter",filter);
        data.put("industryId",industryId);
        data.put("page",page+"");
        data.put("pageLimit",pageSize+"");
        String token = SysUtils.getToken();
        String time = SysUtils.getTime();
        String sign = SysUtils.getSign(data,time);
        Log.i(TAG,"sign--->"+sign);
        mModel.mgetMoment(new RxObservable() {
            @Override
            public void onSuccess(Object o) {
                String jsonString = SysUtils.getJsonString(o);
                JSONObject jsonObject = JSONObject.parseObject(jsonString);
                Log.i(TAG, "获取朋友圈数据--->" + jsonString);
                int code = jsonObject.getInteger("code");
                if(code==0){
                    JSONArray array_list = jsonObject.getJSONObject("data").getJSONArray("list");
                    String itemHomeListsString = array_list.toJSONString(array_list);
                    List<ItemMoment> itemMoments = JSONObject.parseArray(itemHomeListsString, ItemMoment.class);
                    mView.callBackMoment(itemMoments);
                }

            }

            @Override
            public void onFail(String reason) {

            }
        },  token,  sign ,  time,  industryId,  channelId,  pageSize+"",  page+"",  filter);

    }
}
