package com.kulian.mvp.module.person;

import android.content.Context;

import com.kulian.mvp.base.BasePresenter;


/**
 * Created by HuangQiang on 2017/12/21.
 *
 * @author HuangQiang
 * @github https://github.com/HuangQiang
 */

public class PPersonImpl extends BasePresenter<CPerson.IVPerson, MPersonImpl> implements CPerson.IPPerson {


    public PPersonImpl(Context mContext, CPerson.IVPerson mView) {
        super(mContext, mView, new MPersonImpl());
    }

}
