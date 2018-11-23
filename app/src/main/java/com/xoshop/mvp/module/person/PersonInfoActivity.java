package com.xoshop.mvp.module.person;

import android.Manifest;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bigkoo.alertview.AlertView;
import com.jakewharton.rxbinding2.view.RxView;
import com.xoshop.R;
import com.xoshop.mvp.base.BaseActivity;
import com.xoshop.utils.AlertUtils;
import com.xoshop.utils.AuthPreferences;
import com.xoshop.widget.CircleImageView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by xiaoqiang on 2018/11/9.
 */

public class PersonInfoActivity extends BaseActivity {


    @BindView(R.id.imagebtn_back)
    ImageButton imagebtnBack;
    @BindView(R.id.iamgeview_touxiang)
    CircleImageView iamgeviewTouxiang;
    @BindView(R.id.textview_name)
    TextView textviewName;
    @BindView(R.id.textview_tel)
    TextView textviewTel;
    private AlertView photoPickerDialog;
    //申请权限
    private final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    // 打开相机定位请求Code，多个权限请求Code
    private final int REQUEST_CODE_PERMISSIONS = 2;

    @Override
    public int setContentViewId() {
        ifNeedStatus = true;
        return R.layout.activity_personinfo;
    }

    @Override
    public void createPresenter() {

    }


    public void getPic() {
        if (photoPickerDialog == null) {
            photoPickerDialog = AlertUtils.photoPicker(PersonInfoActivity.this, GalleryFinal.copyGlobalFuncationConfig(),
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
        addDisposable(RxView.clicks(imagebtnBack).throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(o -> {
                    Log.i(TAG, "DASDSADA");
                    this.finish();
                }));
        String userInfo = AuthPreferences.getUserInfo();
        if(!TextUtils.isEmpty(userInfo)){
            JSONObject jsonObject = JSONObject.parseObject(userInfo);
            String imageUrl = jsonObject.getString("avatar");
            String name =  jsonObject.getString("name");
            String phoneNum =  jsonObject.getString("phoneNum");
            textviewName.setText(name);
            textviewTel.setText(phoneNum);
            LoadImageUrl(iamgeviewTouxiang,imageUrl);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

}
