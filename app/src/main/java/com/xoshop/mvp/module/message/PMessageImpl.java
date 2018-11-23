package com.xoshop.mvp.module.message;

import android.content.Context;

import com.xoshop.mvp.base.BasePresenter;


/**
 * Created by HuangQiang on 2017/12/21.
 *
 * @author HuangQiang
 * @github https://github.com/HuangQiang
 */

public class PMessageImpl extends BasePresenter<CMessage.IVMessage, MMessageImpl> implements CMessage.IPMessage {


    public PMessageImpl(Context mContext, CMessage.IVMessage mView) {
        super(mContext, mView, new MMessageImpl());
    }

}
