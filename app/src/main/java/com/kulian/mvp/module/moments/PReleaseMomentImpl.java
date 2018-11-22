package com.kulian.mvp.module.moments;

import android.content.Context;

import com.kulian.mvp.module.moments.CReleaseMoment;
import com.kulian.mvp.module.moments.MReleaseMomentImpl;
import com.kulian.mvp.base.BasePresenter;


/**
 * Created by HuangQiang on 2017/12/21.
 *
 * @author HuangQiang
 * @github https://github.com/HuangQiang
 */

public class PReleaseMomentImpl extends BasePresenter<CReleaseMoment.IVReleaseMoment, MReleaseMomentImpl> implements CReleaseMoment.IPReleaseMoment {


    public PReleaseMomentImpl(Context mContext, CReleaseMoment.IVReleaseMoment mView) {
        super(mContext, mView, new MReleaseMomentImpl());
    }

}
