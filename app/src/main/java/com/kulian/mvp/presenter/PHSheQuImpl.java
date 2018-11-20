package com.kulian.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kulian.mvp.bean.ItemFoundData;
import com.kulian.mvp.bean.ItemHomeData;
import com.kulian.mvp.contract.CHSheQu;
import com.kulian.mvp.model.MHSheQuImpl;
import com.kulian.mvp.base.BasePresenter;
import com.kulian.utils.SysUtils;
import com.kulian.utils.rxhelper.RxObservable;

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

public class PHSheQuImpl extends BasePresenter<CHSheQu.IVHSheQu, MHSheQuImpl> implements CHSheQu.IPHSheQu {
    private String TAG = "发现";
    private List<ItemFoundData> itemFoundData = new ArrayList<ItemFoundData>();

    public PHSheQuImpl(Context mContext, CHSheQu.IVHSheQu mView) {
        super(mContext, mView, new MHSheQuImpl());
    }

    @Override
    public void getData(int page) {
        Map<String, String> data = new HashMap<String, String>();
        data.put("page", page + "");
        data.put("page_num", 15 + "");
        data.put("token", "3280ace5020e03194ad3fa59d5b27dd5");
        mModel.mgetData(new RxObservable() {
            @Override
            public void onSuccess(Object o) {
                String jsonString = SysUtils.getJsonString(o);
                JSONObject jsonObject = JSONObject.parseObject(jsonString);
                String code = jsonObject.getString("code");
                Log.i(TAG, "发现朋友圈的数据--->" + jsonString);
                if (code.equals("1")) {
                    JSONArray array_list = jsonObject.getJSONArray("data");
                    String itemHomeListsString = array_list.toJSONString(array_list);
                    itemFoundData = JSONObject.parseArray(itemHomeListsString, ItemFoundData.class);
                    mView.showData(itemFoundData, page);
                } else {
                    mView.showNoData(page);
                }
            }

            @Override
            public void onFail(String reason) {

            }
        }, data);
    }
}
