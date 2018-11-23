package com.xoshop.mvp.model;

import com.xoshop.mvp.base.BaseModel;
import com.xoshop.utils.rxhelper.RxObservable;
import com.xoshop.utils.rxhelper.RxTransformer;

import java.util.Map;


/**
 * Created by Liang_Lu on 2017/12/21.
 *
 * @author LuLiang
 * @github https://github.com/LiangLuDev
 */

public class MHSheQuImpl extends BaseModel {
    public void mgetData(RxObservable rxObservable, Map<String,String> data) {
        apiService()
                .getFoundData(data)
                .compose(RxTransformer.switchSchedulers(this))
                .subscribe(rxObservable);
    }
}
