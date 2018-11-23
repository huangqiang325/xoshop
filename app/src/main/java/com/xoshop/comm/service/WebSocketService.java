package com.xoshop.comm.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.xoshop.R;
import com.xoshop.comm.activity.HomePageActivity;
import com.xoshop.utils.ActivityManagerUtils;
import com.xoshop.utils.AuthPreferences;
import com.xoshop.utils.SysUtils;

import org.java_websocket.WebSocketImpl;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.X509Certificate;

/**
 * Created by wxs on 16/8/17.
 */
public class WebSocketService extends Service {
    private static final int PUSH_NOTIFICATION_ID = (0x001);
    private static final String PUSH_CHANNEL_ID = "PUSH_NOTIFY_ID";
    private static final String PUSH_CHANNEL_NAME = "PUSH_NOTIFY_NAME";
    private static final String TAG = "websocket1";

    public static final String WEBSOCKET_ACTION = "WEBSOCKET_ACTION";
    public static final String WEBSOCKET_ACTION_SELFSEND = "WEBSOCKET_ACTION_SELFSEND";
    public static final String WEBSOCKET_ACTION_RADAR = "WEBSOCKET_ACTION_RADAR";

    private static BroadcastReceiver connectionReceiver;
    private static boolean isClosed = true;
    //    private static WebSocketConnection webSocketConnection;
//    private static WebSocketOptions options = new WebSocketOptions();
    private static boolean isExitApp = false;
    private static String websocketHost = "wss://bbxjw.top:8080"; //websocket服务端的url,,,ws是协议,和http一样,我写的时候是用的我们公司的服务器所以这里不能贴出来
    private static WebSocketClient webSocketClient;
    private static Context context = null;
    private static String user_id = "";
    private static int secondsInterval = 5;
    private static boolean ifContinue = true;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        WebSocketImpl.DEBUG = true;
        context = getApplicationContext();
        Log.i(TAG, "调用服务---");
        if (connectionReceiver == null) {
            connectionReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                    if (networkInfo == null || !networkInfo.isAvailable()) {
                        Toast.makeText(getApplicationContext(), "网络已断开，请重新连接", Toast.LENGTH_SHORT).show();
                    } else {
                        if (webSocketClient != null) {
                            Toast.makeText(getApplicationContext(), "网络已连接", Toast.LENGTH_SHORT).show();
                            webSocketClient.close();
                            webSocketClient = null;
                        }
                        if (isClosed) {
                            webSocketConnect();
                        }
                    }

                }
            };
            Log.i(TAG, "注册服务");
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(connectionReceiver, intentFilter);

        }

        return super.onStartCommand(intent, flags, startId);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void closeWebsocket(boolean exitApp) {

        isExitApp = exitApp;
        if (webSocketClient != null) {
            webSocketClient.close();
            webSocketClient = null;
        }
    }

    //socket链接
    public static void webSocketConnect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (webSocketClient == null) {
                    try {
                        webSocketClient = new WebSocketClient(new URI(websocketHost)) {
                            @Override
                            public void onOpen(ServerHandshake handshakedata) {
                                Log.d(TAG, "开启socket");
                                // doInterval();
                                isClosed = false;
                            }

                            @Override
                            public void onMessage(String message) {
                                Log.i(TAG, "收到的消息为-->" + message);
                                JSONObject oject = JSONObject.parseObject(message);
                                if (oject.getString("type").equals("init")) {
                                    AuthPreferences.saveClientId(oject.getString("client_id"));
                                    Log.i(TAG, "getClientId" + AuthPreferences.getClientId());
                                    // bindClient_chat(oject.getString("client_id"));
                                    bindSocket(oject.getString("client_id"));
                                } else if (oject.getString("type").equals("sys")) {
                                    String sysType = oject.getJSONObject("message").getString("type");
                                    //sys聊天系统消息
                                    if (sysType.equals("chat")) {
                                        JSONObject messageObject = oject.getJSONObject("message").getJSONObject("message");
                                        if (String.valueOf(messageObject.getInteger("author")).equals(user_id)) {
                                            Log.i(TAG, "自己发的");
                                            Intent intent = new Intent();
                                            intent.setAction(WebSocketService.WEBSOCKET_ACTION_SELFSEND);
                                            intent.putExtra("chat", message);
                                            context.sendBroadcast(intent);
                                        } else {
                                            Log.i(TAG, "别人发的");
                                            if (SysUtils.isBackground(context)) {
                                                Log.i(TAG, "在后台");
                                                ActivityManagerUtils.getInstance().finishAllActivity();
                                                NotificationManager mNotifyMgr =
                                                        (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
                                                Intent mainIntent = new Intent(context, HomePageActivity.class);
                                                JSONObject intentData = new JSONObject();
                                                intentData.put("avatar", messageObject.getString("avatar"));
                                                intentData.put("name", messageObject.getString("name"));
                                                intentData.put("bz", messageObject.getString("bz"));
                                                intentData.put("fk_id", messageObject.getString("author"));
                                                mainIntent.putExtra("data", intentData.toJSONString());
                                                mainIntent.putExtra("lauchmode", "chat");
                                                PendingIntent mainPendingIntent = PendingIntent.getActivity(context, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                                                //创建 Notification.Builder 对象
                                                Uri sound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.message);
                                                long[] pattern = {0, 100, 1000};
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                    NotificationChannel channel = new NotificationChannel(PUSH_CHANNEL_ID, PUSH_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
                                                    if (mNotifyMgr != null) {
                                                        mNotifyMgr.createNotificationChannel(channel);
                                                    }
                                                }
                                                NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                                                        .setSmallIcon(R.mipmap.ic_launcher)
                                                        //点击通知后自动清除
                                                        .setAutoCancel(true)
                                                        .setSound(sound)
                                                        .setPriority(Notification.PRIORITY_HIGH)
                                                        .setDefaults(0)
                                                        .setTicker("悬浮通知")
                                                        .setChannelId(PUSH_CHANNEL_ID)
                                                        .setVibrate(pattern)
                                                        .setContentTitle(context.getResources().getString(R.string.app_name))
                                                        .setContentText((TextUtils.isEmpty(messageObject.getString("bz")) ? messageObject.getString("name") : messageObject.getString("bz")) + "给你发送了一条消息")
                                                        .setContentIntent(mainPendingIntent);
                                                        //.setFullScreenIntent(mainPendingIntent, true);
                                                Notification notification = builder.build();
                                                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                                                //发送通知
                                                //发送通知
                                                if (SysUtils.isNotificationEnabled(context)) {
                                                    Log.i(TAG, "有权限");
                                                    if (mNotifyMgr != null) {
                                                        mNotifyMgr.notify(PUSH_NOTIFICATION_ID, notification);
                                                    }
                                                } else {
                                                    // TODO Auto-generated method stub
                                                    // 6.0以上系统才可以判断权限
                                                    Toast.makeText(context, "未设置通知权限", Toast.LENGTH_SHORT).show();
                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE) {
                                                        // 进入设置系统应用权限界面
                                                        Intent intent = new Intent(Settings.ACTION_SETTINGS);
                                                        context.startActivity(intent);
                                                        return;
                                                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {// 运行系统在5.x环境使用
                                                        // 进入设置系统应用权限界面
                                                        Intent intent = new Intent(Settings.ACTION_SETTINGS);
                                                        context.startActivity(intent);
                                                        return;
                                                    }
                                                    return;
                                                }
                                                Intent intent = new Intent();
                                                intent.setAction(WebSocketService.WEBSOCKET_ACTION);
                                                Log.i(TAG, "不在后台");
                                                intent.putExtra("chat", message);
                                                context.sendBroadcast(intent);
                                            } else {
                                                Intent intent = new Intent();
                                                intent.setAction(WebSocketService.WEBSOCKET_ACTION);
                                                Log.i(TAG, "不在后台");
                                                intent.putExtra("chat", message);
                                                context.sendBroadcast(intent);
                                            }

                                        }
                                    }


                                } else if (oject.getString("type").equals("radar")) {
                                    JSONObject messageData = oject.getJSONObject("message");
                                    if (SysUtils.isBackground(context)) {
                                        Log.i(TAG, "在后台");
                                        ActivityManagerUtils.getInstance().finishAllActivity();
                                    }
                                    NotificationManager mNotifyMgr =
                                            (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
                                    Intent mainIntent = new Intent(context, HomePageActivity.class);
                                    mainIntent.putExtra("lauchmode", "radar");
                                    PendingIntent mainPendingIntent = PendingIntent.getActivity(context, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                                    //创建 Notification.Builder 对象
                                    Uri sound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.leida);
                                    long[] pattern = {0, 100, 1000};
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        NotificationChannel channel = new NotificationChannel(PUSH_CHANNEL_ID, PUSH_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
                                        if (mNotifyMgr != null) {
                                            mNotifyMgr.createNotificationChannel(channel);
                                        }
                                    }
                                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                                            .setSmallIcon(R.mipmap.ic_launcher)
                                            //点击通知后自动清除
                                            .setSound(sound)
                                            .setPriority(Notification.PRIORITY_HIGH)
                                            .setDefaults(0)
                                            .setTicker("悬浮通知")
                                            .setVibrate(pattern)
                                            .setAutoCancel(true)
                                            .setChannelId(PUSH_CHANNEL_ID)
                                            .setContentTitle(context.getResources().getString(R.string.app_name))
                                            .setContentText(messageData.getString("name") + messageData.getString("type") + messageData.getString("action"))
                                           .setContentIntent(mainPendingIntent);
                                            //.setFullScreenIntent(mainPendingIntent, true);
                                    //获取NotificationManager实例
                                    Notification notification = builder.build();
                                    notification.flags |= Notification.FLAG_AUTO_CANCEL;
                                    //发送通知
                                    //发送通知
                                    if (SysUtils.isNotificationEnabled(context)) {
                                        if (mNotifyMgr != null) {
                                            mNotifyMgr.notify(PUSH_NOTIFICATION_ID, notification);
                                        }
                                        Log.i(TAG, "有权限");
                                    } else {
                                        // TODO Auto-generated method stub
                                        // 6.0以上系统才可以判断权限
                                        Toast.makeText(context, "未设置通知权限", Toast.LENGTH_SHORT).show();
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE) {
                                            // 进入设置系统应用权限界面
                                            Intent intent = new Intent(Settings.ACTION_SETTINGS);
                                            context.startActivity(intent);
                                            return;
                                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {// 运行系统在5.x环境使用
                                            // 进入设置系统应用权限界面
                                            Intent intent = new Intent(Settings.ACTION_SETTINGS);
                                            context.startActivity(intent);
                                            return;
                                        }
                                        return;
                                    }
                                    Intent intent = new Intent();
                                    intent.setAction(WebSocketService.WEBSOCKET_ACTION_RADAR);
                                    intent.putExtra("radar", message);
                                    context.sendBroadcast(intent);
                                }
                            }

                            @Override
                            public void onClose(int code, String reason, boolean remote) {
                                isClosed = true;
                                Log.d(TAG, "关闭code = " + code + " reason = " + reason + " remote = " + remote);
                                switch (code) {
                                    //服务端关闭
                                    case 1006:
                                        Log.i(TAG, "服务器主动关闭");
                                        webSocketClient = null;
                                        webSocketConnect();
                                        break;
                                    case 1000:
                                        Log.i(TAG, "手动关闭");
                                        connectionReceiver = null;
                                        break;
                                    case 2:
                                        break;
                                    case 3:
                                        if (!isExitApp) {
                                            webSocketConnect();
                                        }
                                        break;
                                    case 4:
                                        break;
                                    /**
                                     * 由于我在这里已经对网络进行了判断,所以相关操作就不在这里做了
                                     */
                                    case 5://网络断开连接
//                            closeWebsocket(false);
//                            webSocketConnect();
                                        break;
                                }
                            }

                            @Override
                            public void onError(Exception ex) {

                            }
                        };
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
                SSLContext sslContext = null;
                try {
                    sslContext = SSLContext.getInstance("TLS");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                try {
                    sslContext.init(null, new TrustManager[]{
                            new X509TrustManager() {

                                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                                    System.out.println("checkClientTrusted1");
                                }

                                @Override
                                public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
                                        throws CertificateException {
                                    System.out.println("checkClientTrusted2");
                                }

                                public void checkServerTrusted(X509Certificate[] certs,
                                                               String authType) {
                                    System.out.println("checkServerTrusted1");
                                }

                                @Override
                                public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
                                        throws CertificateException {
                                    System.out.println("checkServerTrusted2");
                                }

                                @Override
                                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                    return null;
                                }
                            }
                    }, new SecureRandom());
                } catch (KeyManagementException e) {
                    e.printStackTrace();
                }
                SSLSocketFactory factory = sslContext.getSocketFactory();

                try {
                    if (webSocketClient.getSocket() == null) {
                        webSocketClient.setSocket(factory.createSocket());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    webSocketClient.connect();
                } catch (IllegalStateException e) {

                }
            }
        }).start();
    }


    public static void bindSocket(String client_id) {
        String tokenString = AuthPreferences.getUserToken();
        if (TextUtils.isEmpty(tokenString)) {
            Log.i(TAG, "没有存储用户信息");
            return;
        }
        JSONObject tokenObject = JSONObject.parseObject(tokenString);
        String token = tokenObject.getString("token");
        user_id = tokenObject.getString("user_id");
        String token_expiration_time = tokenObject.getString("token_expiration_time");
        Map<String, String> data = new HashMap<String, String>();
        //data.put("token", token);
        data.put("user_id", user_id);
        //data.put("token_expiration_time", token_expiration_time);
        data.put("client_id", client_id);
        data.put("fk_id", "");
        // data.put("http_source", "app");
//        OkhttpUtil.onPost(Constant.API_URL + "api_socket/bind", data, new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                String jsonString = GsonTool.getGosnString(response.body().byteStream());
//                Log.i(TAG, "绑定socket返回的数据为--->" + jsonString);
//                try {
//                    JSONObject jsonObject = JSON.parseObject(jsonString);
//                    Message message = new Message();
//                    message.obj = jsonObject;
//                    handler_bind.sendMessage(message);
//
//                } catch (JSONException E) {
//
//                }
//
//            }
//        });
    }

    public static Handler handler_bind = new Handler() {

    };

    public static void sendMsg(String s) {
        Log.d(TAG, "发送的内容为 = " + s);
        if (!TextUtils.isEmpty(s))
            if (webSocketClient != null) {
                webSocketClient.send(s);
            }
    }

    public static void doInterval() {
        while (ifContinue) {
            try {
                Thread.sleep(secondsInterval * 1000); //设置暂停的时间 5 秒
                sendMsg("{\"type\":\"ping\"}");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "service----<>onDestroy");
        if (connectionReceiver != null) {
            Log.i(TAG, "解除了服务");
            unregisterReceiver(connectionReceiver);
        }
    }
}
