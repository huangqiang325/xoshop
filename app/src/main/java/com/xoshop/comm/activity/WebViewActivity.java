package com.xoshop.comm.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xoshop.R;
import com.xoshop.mvp.base.BaseActivity;
import com.xoshop.utils.ActivityManagerUtils;
import com.xoshop.utils.StatusBarUtil;


/**
 * Created by xiaoqiang on 2017/12/31.
 */

public class WebViewActivity extends BaseActivity implements View.OnClickListener {
    private TextView textview_title;
    private int height_statusbar = 0;
    private LinearLayout linearlayout_status;
    private ImageButton imagebutton_back;
    private WebView webview ;
    private boolean ifExit = true ;
    private String TAG = "webview";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        ActivityManagerUtils.getInstance().addActivity(this);
    }


    public void setOnClick() {
        imagebutton_back.setOnClickListener(this);
    }


    @Override
    protected void initView() {
        textview_title = (TextView) findViewById(R.id.textview_title);
        imagebutton_back = (ImageButton) findViewById(R.id.imagebutton_back);
        webview = (WebView) findViewById(R.id.webview);
        Intent intent = getIntent();
//        String urlintent = intent.getStringExtra("url");
//        String title = intent.getStringExtra("title");
//        if(!TextUtils.isEmpty(title)){
//            textview_title.setText(title);
//        }
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if(webview.canGoBack()){
                  ifExit = false;
                }else{
                    ifExit = true;
                }
                Log.i("app1","onPageFinished"+webview.canGoBack());

            }
        });
        webview.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Log.i("app1",message);
                return super.onJsAlert(view, url, message, result);
            }
        });
        webview.loadUrl("http://aaa.sd3pv.cn");
        webview.addJavascriptInterface(new JsInteration(), "android");

    }

    @Override
    public int setContentViewId() {
        ifNeedStatus = true;
        return R.layout.__activity_webview;
    }

    @Override
    public void createPresenter() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imagebutton_back:
                if(ifExit){
                    this.finish();
                }else{
                    webview.goBack();
                }
                break;
            default:
                break;
        }
    }
    public class JsInteration {
        @JavascriptInterface
        public void back(String data) {
            Log.i(TAG,"DATA---->"+data);
            switch (data) {
                case "login":
                    break;
            }
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if(ifExit==true){
                this.finish();
            }else{
                webview.goBack();
            }
            return true;
        }else {
            return super.onKeyDown(keyCode, event);
        }

    }

}
