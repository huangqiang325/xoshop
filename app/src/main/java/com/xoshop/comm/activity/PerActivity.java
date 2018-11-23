package com.xoshop.comm.activity;

import android.Manifest;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Button;

import com.bigkoo.alertview.AlertView;
import com.jakewharton.rxbinding2.view.RxView;
import com.xoshop.R;
import com.xoshop.mvp.base.BaseActivity;
import com.xoshop.utils.AlertUtils;
import com.xoshop.utils.PermissionUtils;
import com.xoshop.utils.ToastUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import sskj.lee.appupdatelibrary.BaseUpdateDialogFragment;
import sskj.lee.appupdatelibrary.BaseVersion;
import sskj.lee.appupdatelibrary.SimpleUpdateFragment;
import sskj.lee.appupdatelibrary.VersionInfo;

/**
 * Created by xiaoqiang on 2018/11/9.
 */

public class PerActivity extends BaseActivity {


    @BindView(R.id.btn_opencamera)
    Button btnOpencamera;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    private AlertView photoPickerDialog;
    public boolean isGrantedAll = false;
    //申请权限
    private final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    // 打开相机定位请求Code，多个权限请求Code
    private final int REQUEST_CODE_PERMISSIONS = 2;

    @Override
    public int setContentViewId() {
        ifNeedStatus = false;
        return R.layout.__activity_permission;
    }

    @Override
    public void createPresenter() {

    }

    public void getPer() {
        // 自定义申请多个权限
        PermissionUtils.checkMorePermissions(this, PERMISSIONS, new PermissionUtils.PermissionCheckCallBack() {
            @Override
            public void onHasPermission() {

                getPic();
            }

            @Override
            public void onUserHasAlreadyTurnedDown(String... permission) {
                showExplainDialog(permission, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionUtils.requestMorePermissions(PerActivity.this, PERMISSIONS, REQUEST_CODE_PERMISSIONS);
                    }
                });
            }

            @Override
            public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
                PermissionUtils.requestMorePermissions(PerActivity.this, PERMISSIONS, REQUEST_CODE_PERMISSIONS);
            }
        });
    }

    /**
     * 解释权限的dialog
     */
    private void showExplainDialog(String[] permission, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(this)
                .setTitle("申请权限")
                .setMessage("我们需要摄像头的权限")
                .setPositiveButton("确定", onClickListener)
                .show();
    }

    /**
     * 显示前往应用设置Dialog
     */
    private void showToAppSettingDialog() {
        new AlertDialog.Builder(this)
                .setTitle("需要权限")
                .setMessage("我们需要相关权限，才能实现功能，点击前往，将转到应用的设置界面，请开启应用的相关权限。")
                .setPositiveButton("前往", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionUtils.toAppSetting(PerActivity.this);
                    }
                })
                .setNegativeButton("取消", null).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSIONS:
                PermissionUtils.onRequestMorePermissionsResult(PerActivity.this, PERMISSIONS, new PermissionUtils.PermissionCheckCallBack() {
                    @Override
                    public void onHasPermission() {
                        getPic();
                    }

                    @Override
                    public void onUserHasAlreadyTurnedDown(String... permission) {
                        ToastUtils.show("我们需要获取拍照等权限");
                    }

                    @Override
                    public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
                        ToastUtils.show("我们需要获取拍照等权限");
                        showToAppSettingDialog();
                    }
                });
        }
    }

    public void getPic() {
        if (photoPickerDialog == null) {
            photoPickerDialog = AlertUtils.photoPicker(PerActivity.this, GalleryFinal.copyGlobalFuncationConfig(),
                    new GalleryFinal.OnHanlderResultCallback() {
                        @Override
                        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                        }

                        @Override
                        public void onHanlderFailure(int requestCode, String errorMsg) {
                        }
                    }, false);
        }
        photoPickerDialog.show();
    }

    @Override
    protected void initView() {
        super.initView();
        addDisposable(RxView.clicks(btnOpencamera).throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(o -> {
                    Log.i(TAG, "DASDSADA");
                    getPer();
                }));
        addDisposable(RxView.clicks(btnUpdate).throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(o -> {
                    SimpleUpdateFragment updateFragment = new SimpleUpdateFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(BaseUpdateDialogFragment.INTENT_KEY, initData(BaseVersion.NOTIFYCATION_STYLE));
                    updateFragment.setArguments(bundle);
                    FragmentManager transition = getFragmentManager();
                    updateFragment.show(transition, "tag");
                }));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
    private VersionInfo initData(int dialogStyle) {
        VersionInfo versionInfo = new VersionInfo();
        versionInfo.setContent("版本更新内容\n1.aaaaaaaaaa\n2.bbbbbbbbb");
        versionInfo.setTitle("版本更新");
        versionInfo.setMustup(false);
        versionInfo.setUrl("http://192.168.1.122:8080/apk/app-debug.apk");
        versionInfo.setViewStyle(dialogStyle);
        return versionInfo;
    }
}
