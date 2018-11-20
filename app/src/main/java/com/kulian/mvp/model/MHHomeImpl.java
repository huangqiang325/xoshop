package com.kulian.mvp.model;

import com.kulian.mvp.base.BaseModel;

import java.util.Map;

import com.kulian.utils.rxhelper.RxObservable;
import com.kulian.utils.rxhelper.RxTransformer;
import com.squareup.okhttp.Callback;

/**
 * Created by Liang_Lu on 2017/12/21.
 *
 * @author LuLiang
 * @github https://github.com/LiangLuDev
 */

public class MHHomeImpl extends BaseModel {
    public void mgetData(RxObservable rxObservable, Map<String,String> data) {
        apiService()
                .getHomeData(data)
                .compose(RxTransformer.switchSchedulers(this))
                .subscribe(rxObservable);
    }
    public void mgetBanner(RxObservable rxObservable, Map<String,String> data) {
        apiService()
                .getBanner(data)
                .compose(RxTransformer.switchSchedulers(this))
                .subscribe(rxObservable);
    }

}
