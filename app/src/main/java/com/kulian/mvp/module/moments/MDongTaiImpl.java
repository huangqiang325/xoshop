package com.kulian.mvp.module.moments;

import com.kulian.mvp.base.BaseModel;
import com.kulian.utils.rxhelper.RxObservable;
import com.kulian.utils.rxhelper.RxTransformer;


/**
 * Created by HuangQiang on 2017/12/21.
 *
 * @author HuangQiang
 * @github https://github.com/HuangQiang
 */

public class MDongTaiImpl extends BaseModel{
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
    public void mgetMoment(RxObservable rxObservable,String token,String sign ,String time,String industryId,String channelId,String pageLimit,String page,String filter){
            apiService().
                    getMoments(token,sign,time,industryId,channelId,pageLimit,page,filter)
                    .compose(RxTransformer.switchSchedulers(this))
                    .subscribe(rxObservable);
    }

}
