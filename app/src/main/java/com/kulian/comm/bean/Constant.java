package com.kulian.comm.bean;

/**
 * Created by xiaoqiang on 2017/12/6.
 */

public class Constant {
    /**
     * 拍照选取照片
     */

    public final static int REQUEST_CODE_CAMERA = 1000;
    public final static int REQUEST_CODE_GALLERY = 1001;




    //appid 微信分配的公众账号ID
    public static final String APPID = "1101152570";
    public static final String WX_APP_ID = "wx7342a1af562948a7";
    public static final String WX_APP_SECRET = "392baa80d215bd92d03a4a17976b069e ";
    //商户号 微信分配的公众账号ID
    public static final String WX_MCH_ID = "1432223602";
    //API密钥，在商户平台设置
    public static final String WX_API_KEY = "";
    public static final String API_URL = "http://www.xiaotiaowa5.top/api.php/";
    //public static final String API_URL = "http://116.62.17.179/json.php";
    public static final String API_BASE_URL = "http://www.xiaotiaowa5.top/";
    public static final String API_URL_ALI_PAY = API_BASE_URL+"/pay.php";
    public static final String API_URL_LOGIN = API_BASE_URL +"/login/";
    public static final String API_URL_GOODS = API_BASE_URL+"index.php/index/goods_detail/";
    public static final String API_URL_WULIU = API_BASE_URL+"index.php/index/logistics/";
    //商城介绍
    public static final String API_URL_INTRO = API_BASE_URL+"index.php/index/introduce";

    //广播
    public static final String BROADCAST_WEBVIEW = "BROADCAST_WEBVIEW";


}