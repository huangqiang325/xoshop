package com.xoshop.mvp.contract;

import com.alibaba.fastjson.JSONObject;
import com.xoshop.mvp.base.IBasePresenter;
import com.xoshop.mvp.base.IBaseView;


/**
 * Created by HuangQiang on 2017/12/21.
 *
 * @author HuangQiang
 * @github https://github.com/LiangLuDev
 */

public interface CLoginOrReg {

    interface IPLoginOrReg extends IBasePresenter {
        void getCode(String phone);
        void login(String phone,String code);

    }

    interface IVLoginOrReg extends IBaseView {
        void codeCallBack(JSONObject jsonObject);
        void loginCallBack(JSONObject jsonObject);

    }
}