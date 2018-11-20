package com.kulian.mvp.model;

import com.kulian.mvp.base.BaseModel;
import com.kulian.utils.rxhelper.RxObservable;
import com.kulian.utils.rxhelper.RxTransformer;

import java.util.Map;


/**
 * Created by Liang_Lu on 2017/12/21.
 *
 * @author LuLiang
 * @github https://github.com/LiangLuDev
 */

public class MHClasifyImpl extends BaseModel {
    public void mgetClassifyLeft(RxObservable rxObservable) {
        apiService()
                .getClassifyLeft()
                .compose(RxTransformer.switchSchedulers(this))
                .subscribe(rxObservable);
    }

    public void mgetClassifyRight(RxObservable rxObservable, String id ,String page ,String page_num) {
        apiService()
                .getClassifyRight(id,page,page_num)
                .compose(RxTransformer.switchSchedulers(this))
                .subscribe(rxObservable);
    }
}
