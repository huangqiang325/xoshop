package com.kulian.comm.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;

import com.kulian.R;
import com.kulian.utils.AuthPreferences;

/**
 * Created by xiaoqiang on 2017/12/25.
 */

public class SplashActivity extends Activity {
    private int height_statusbar = 0;
    private final int SPLASH_DISPLAY_LENGHT = 1000;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.__activity_splash);
        handler = new Handler();
        // 延迟SPLASH_DISPLAY_LENGHT时间然后跳转到MainActivity
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                String ifFirst = AuthPreferences.getFirst();
                Log.i("WelcomeGuideActivity","ifFirst---L"+ifFirst);
                if (TextUtils.isEmpty(ifFirst) || ifFirst.equals("1")) {
                    //下载后第一次打开app
                    startActivity(new Intent(SplashActivity.this, WelcomeGuideActivity.class));
                    overridePendingTransition(R.anim.ap2, R.anim.ap1);// 淡出淡入动画效果
                } else if(ifFirst.equals("0")){
                    //下载后不是第一次打开app
                    startActivity(new Intent(SplashActivity.this, HomePageActivity.class));
                    overridePendingTransition(R.anim.ap2, R.anim.ap1);// 淡出淡入动画效果
                }
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGHT);
    }

    //在SplashActivity中禁用返回键：
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
}