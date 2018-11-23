package com.xoshop.mvp.model;

import com.xoshop.mvp.base.BaseModel;
import com.xoshop.utils.rxhelper.RxObservable;
import com.xoshop.utils.rxhelper.RxTransformer;

/**
 * Created by mac on 2018/11/21.
 */

public class MUserInfo extends BaseModel {
    public void mGetPerInfo(RxObservable rxObservable, String token, String sign, String time) {
        apiService()
                .getPerInfo(token, sign, time)
                .compose(RxTransformer.switchSchedulers(this))
                .subscribe(rxObservable);
    }
}
