package com.xoshop.mvp.module.contact;

import android.content.Context;

import com.xoshop.mvp.base.BasePresenter;


/**
 * Created by HuangQiang on 2017/12/21.
 *
 * @author HuangQiang
 * @github https://github.com/HuangQiang
 */

public class PContactImpl extends BasePresenter<CContact.IVContact, MContactImpl> implements CContact.IPContact {


    public PContactImpl(Context mContext, CContact.IVContact mView) {
        super(mContext, mView, new MContactImpl());
    }

}
