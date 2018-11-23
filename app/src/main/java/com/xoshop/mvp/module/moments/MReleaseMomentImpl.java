package com.xoshop.mvp.module.moments;

import com.xoshop.mvp.base.BaseModel;
import com.xoshop.utils.rxhelper.RxObservable;
import com.xoshop.utils.rxhelper.RxTransformer;

/**
 * Created by HuangQiang on 2017/12/21.
 *
 * @author HuangQiang
 * @github https://github.com/HuangQiang
 */

public class MReleaseMomentImpl extends BaseModel {
    public void mgetIndustry(RxObservable rxObservable) {
        apiService()
                .getIndustry()
                .compose(RxTransformer.switchSchedulers(this))
                .subscribe(rxObservable);
    }

    public void mgetChannel(RxObservable rxObservable) {
        apiService()
                .getChannel()
                .compose(RxTransformer.switchSchedulers(this))
                .subscribe(rxObservable);
    }

}
