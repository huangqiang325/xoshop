package com.kulian.comm.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.alibaba.fastjson.JSONObject;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.jakewharton.rxbinding2.view.RxView;
import com.kulian.R;
import com.kulian.mvp.base.BaseActivity;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;


/**
 * Created by xiaoqiang on 2017/12/31.
 */

public class HuanXinActivity extends BaseActivity {

    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.btn_receive)
    Button btnReceive;
    private String TAG = "环信测试";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
        addDisposable(RxView.clicks(btnAdd).throttleFirst(200, TimeUnit.MILLISECONDS)
                .subscribe(o -> {
                    EMClient.getInstance().login("xiaoqiang", "123456", new EMCallBack() {//回调
                        @Override
                        public void onSuccess() {
                            EMClient.getInstance().groupManager().loadAllGroups();
                            EMClient.getInstance().chatManager().loadAllConversations();
                            Log.i(TAG, "登录聊天服务器成功！");
                        }

                        @Override
                        public void onProgress(int progress, String status) {

                        }

                        @Override
                        public void onError(int code, String message) {
                            Log.i("main", "登录聊天服务器失败！");
                        }
                    });
                }));
    }

    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            //收到消息
            Log.i(TAG,"messages-->"+ JSONObject.toJSONString(messages));
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
            //收到已送达回执
        }
        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
            //消息被撤回
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
        }
    };

    @Override
    public int setContentViewId() {
        ifNeedStatus = false;
        return R.layout._huanxin_activity;
    }

    @Override
    public void createPresenter() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // 记得在不需要的时候移除listener，如在activity的onDestroy()时
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
    }
}

