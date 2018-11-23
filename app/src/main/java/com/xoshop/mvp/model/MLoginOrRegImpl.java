package com.xoshop.mvp.model;

import com.xoshop.mvp.base.BaseModel;
import com.xoshop.utils.rxhelper.RxObservable;
import com.xoshop.utils.rxhelper.RxTransformer;

/**
 * Created by HuangQiang on 2017/12/21.
 *
 * @author HuangQiang
 * @github https://github.com/HuangQiang
 */

public class MLoginOrRegImpl extends BaseModel {
    public void mGetCode(RxObservable rxObservable, String phone) {
        apiService()
                .getCode(phone)
                .compose(RxTransformer.switchSchedulers(this))
                .subscribe(rxObservable);
    }

    public void mDoLogin(RxObservable rxObservable, String phone, String code) {
        apiService()
                .dologin(phone, code)
                .compose(RxTransformer.switchSchedulers(this))
                .subscribe(rxObservable);
    }


}
