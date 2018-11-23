package com.xoshop.comm.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.xoshop.R;
import com.xoshop.api.Url;
import com.xoshop.mvp.base.BaseFragment;
import com.xoshop.mvp.module.contact.ContactFragment;
import com.xoshop.mvp.module.moments.DongTaiFragment;
import com.xoshop.mvp.module.message.MessageFragment;
import com.xoshop.mvp.module.person.PersonFragment;
import com.xoshop.mvp.view.activity.LoginOrRegActivity;
import com.xoshop.utils.ActivityManagerUtils;
import com.xoshop.utils.AuthPreferences;
import com.xoshop.utils.GsonTool;
import com.xoshop.utils.OkhttpUtil;
import com.xoshop.utils.PermissionUtils;
import com.xoshop.utils.StatusBarUtil;
import com.xoshop.utils.SysUtils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by xiaoqiang on 2017/12/25.
 */
public class HomePageActivity extends FragmentActivity implements View.OnClickListener {
    public static boolean isForeground = false;
    @BindView(R.id.main_dongtai)
    RadioButton mainDongtai;
    @BindView(R.id.main_xiaoxi)
    RadioButton mainXiaoxi;
    @BindView(R.id.main_tongxunlu)
    RadioButton mainTongxunlu;
    @BindView(R.id.main_geren)
    RadioButton mainGeren;
    @BindView(R.id.main_rgroupTabMenu)
    RadioGroup mainRgroupTabMenu;
    private static boolean isExit = false;
    private String TAG = "HomePageActivity";
    private final int REQUEST_CODE_PERMISSIONS = 55;
    private static String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    //装fragment的实例集合
    private ArrayList<BaseFragment> fragments;

    private int position = 0;

    //缓存Fragment或上次显示的Fragment
    private Fragment tempFragment;

    public void getPermions() {
        PermissionUtils.checkMorePermissions(this, PERMISSIONS, new PermissionUtils.PermissionCheckCallBack() {
            @Override
            public void onHasPermission() {
                Log.i(TAG, "有权限");
            }

            @Override
            public void onUserHasAlreadyTurnedDown(String... permission) {
                Log.i(TAG, "onUserHasAlreadyTurnedDown");
                showExplainDialog(permission, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionUtils.requestMorePermissions(HomePageActivity.this, PERMISSIONS, REQUEST_CODE_PERMISSIONS);
                    }
                });
            }

            @Override
            public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
                Log.i(TAG, "onUserHasAlreadyTurnedDownAndDontAsk");
                PermissionUtils.requestMorePermissions(HomePageActivity.this, PERMISSIONS, REQUEST_CODE_PERMISSIONS);
            }
        });

    }

    private void showExplainDialog(String[] permission, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(this)
                .setTitle("申请权限")
                .setMessage("我们需要摄像头的权限")
                .setPositiveButton("确定", onClickListener)
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sliding);
        StatusBarUtil.StatusBarLightMode(this);
        ButterKnife.bind(this);
        ActivityManagerUtils.getInstance().addActivity(this);
        initView();
        setOnClick();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getPermions();
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

           }else if(jsonObject.getInteger("code")==2003){
               //登录失效
               Toast.makeText(HomePageActivity.this,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
               AuthPreferences.saveUserInfo("");
               AuthPreferences.saveUserToken("");
               startActivity(new Intent(HomePageActivity.this, LoginOrRegActivity.class));
               HomePageActivity.this.finish();


           }
       }
   };
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_bottom_in, R.anim.silde_bottom_out);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * 添加的时候按照顺序
     */
    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new DongTaiFragment());
        fragments.add(new MessageFragment());
        fragments.add(new ContactFragment());
        fragments.add(new PersonFragment());
    }

    /**
     * 切换Fragment
     *
     * @param fragment
     * @param nextFragment
     */
    private void switchFragment(Fragment fragment, BaseFragment nextFragment) {
        if (tempFragment != nextFragment) {
            tempFragment = nextFragment;
            if (nextFragment != null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                //判断nextFragment是否添加成功
                if (!nextFragment.isAdded()) {
                    //隐藏当前的Fragment
                    if (fragment != null) {
                        transaction.hide(fragment);
                    }
                    //添加Fragment
                    transaction.add(R.id.home_container, nextFragment).commit();
                } else {
                    //隐藏当前Fragment
                    if (fragment != null) {
                        transaction.hide(fragment);
                    }
                    transaction.show(nextFragment).commit();
                }
            }
        }
    }

    /**
     * 根据位置得到对应的 Fragment
     *
     * @param position
     * @return
     */
    private BaseFragment getFragment(int position) {
        if (fragments != null && fragments.size() > 0) {
            BaseFragment baseFragment = fragments.get(position);
            return baseFragment;
        }
        return null;
    }

    public void initView() {
        initFragment();
        mainRgroupTabMenu.check(R.id.main_dongtai);
        switchFragment(tempFragment, getFragment(0));
        mainRgroupTabMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.main_dongtai: //动态
                        position = 0;
                        break;
                    case R.id.main_xiaoxi: //消息
                        position = 1;
                        break;
                    case R.id.main_tongxunlu: //通讯录
                        position = 2;
                        break;
                    case R.id.main_geren: //个人中心
                        position = 3;
                        break;
                    default:
                        position = 0;
                        break;

                }
                //根据位置得到相应的Fragment
                BaseFragment baseFragment = getFragment(position);
                /**
                 * 第一个参数: 上次显示的Fragment
                 * 第二个参数: 当前正要显示的Fragment
                 */
                switchFragment(tempFragment, baseFragment);
            }
        });
    }

    public void setOnClick() {
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
        }
    }

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
        getUser();
        Log.i(TAG, "onResume请求------");
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_bottom_in, R.anim.silde_bottom_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
