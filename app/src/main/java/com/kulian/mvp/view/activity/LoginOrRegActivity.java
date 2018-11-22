package com.kulian.mvp.view.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.jakewharton.rxbinding2.view.RxView;
import com.kulian.R;
import com.kulian.api.Url;
import com.kulian.comm.activity.HomePageActivity;
import com.kulian.comm.bean.Constant;
import com.kulian.mvp.base.BaseActivity;
import com.kulian.mvp.contract.CLoginOrReg;
import com.kulian.mvp.presenter.PLoginOrRegImpl;
import com.kulian.utils.AuthPreferences;
import com.kulian.utils.GsonTool;
import com.kulian.utils.OkhttpUtil;
import com.kulian.utils.SysUtils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Liang_Lu on 2017/12/21.
 *
 * @author HuangQiang
 * @github https://github.com/HuangQiang
 */

public class LoginOrRegActivity extends BaseActivity<PLoginOrRegImpl> implements CLoginOrReg.IVLoginOrReg {


    @BindView(R.id.edit_tel)
    EditText editTel;
    @BindView(R.id.textview_yzm_reg)
    TextView textviewYzmReg;
    @BindView(R.id.edit_code)
    EditText editCode;
    @BindView(R.id.btn_login)
    Button btnLogin;


    @Override
    protected void initView() {
        super.initView();
        addDisposable(RxView.clicks(textviewYzmReg).throttleFirst(Constant.SECONDS_FORBID, TimeUnit.SECONDS)
                .subscribe(o -> {
                    String phone = editTel.getText().toString();
                    if (TextUtils.isEmpty(phone)) {
                        showToast("请输入手机号");
                        return;
                    }
                    mPresenter.getCode(phone);
                }));
        addDisposable(RxView.clicks(btnLogin).throttleFirst(Constant.SECONDS_FORBID, TimeUnit.SECONDS)
                .subscribe(o -> {
                    String phone = editTel.getText().toString();
                    String code = editCode.getText().toString();
                    if (TextUtils.isEmpty(phone)) {
                        showToast("请输入手机号");
                        return;
                    }
                    if (TextUtils.isEmpty(code)) {
                        showToast("请输入授权码");
                        return;
                    }
                    loadingView(true,"登录中");
                    mPresenter.login(phone, code);
                }));

    }

    @Override
    public int setContentViewId() {
        ifNeedStatus = true;
        return R.layout.activity_loginorreg;
    }

    @Override
    public void createPresenter() {
        mPresenter = new PLoginOrRegImpl(mContext, this);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @Override
    public void codeCallBack(JSONObject jsonObject) {
        int code = jsonObject.getInteger("code");
        if (code == 0) {
            timer_reg.start();
            textviewYzmReg.setEnabled(false);
        } else {
            showToast(jsonObject.getString("msg"));
        }
    }

    @Override
    public void loginCallBack(JSONObject jsonObject) {
        int code = jsonObject.getInteger("code");
        loadingView(false,"");
        if (code == 0) {
            //登录成功
            JSONObject userData = jsonObject.getJSONObject("data");
            String userString = JSONObject.toJSONString(userData);
            AuthPreferences.saveUserToken(userString);
            getUser();
        } else {
            showToast(jsonObject.getString("msg"));
        }

    }
    public void getUser() {
        String time = SysUtils.getTime();
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", SysUtils.getToken());
        map.put("time", time);
        map.put("sign", SysUtils.getSign(null, time));
        OkhttpUtil.onPost(Url.BASE_URL + Url.PER_INFO, map, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String jsonString = GsonTool.getGosnString(response.body().byteStream());
                Log.i(TAG, "获取用户数据为----》" + jsonString);
                try {
                    JSONObject jsonObject = JSON.parseObject(jsonString);
                    Message message = new Message();
                    message.obj = jsonObject;
                    handler_user.sendMessage(message);
                } catch (JSONException e) {

                }
            }
        });
    }
    public Handler handler_user  = new  Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            JSONObject jsonObject = (JSONObject) msg.obj;
            if(jsonObject.getInteger("code")==0){
                JSONObject jsonObjectUser = jsonObject.getJSONObject("data");
                String userString  = JSONObject.toJSONString(jsonObjectUser);
                AuthPreferences.saveUserInfo(userString);
                startActivity(HomePageActivity.class);
                LoginOrRegActivity.this.finish();

            }
        }
    };
    CountDownTimer timer_reg = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            textviewYzmReg.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            textviewYzmReg.setEnabled(true);
            textviewYzmReg.setText("发送验证码");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer_reg.cancel();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
