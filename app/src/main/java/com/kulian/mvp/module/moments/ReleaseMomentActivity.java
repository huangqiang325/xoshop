package com.kulian.mvp.module.moments;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.kulian.R;
import com.kulian.comm.bean.Constant;
import com.kulian.mvp.base.BaseActivity;
import com.kulian.mvp.module.moments.CReleaseMoment;
import com.kulian.mvp.module.moments.PReleaseMomentImpl;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by Liang_Lu on 2017/12/21.
 *
 * @author HuangQiang
 * @github https://github.com/HuangQiang
 */

public class ReleaseMomentActivity extends BaseActivity<PReleaseMomentImpl> implements CReleaseMoment.IVReleaseMoment {
    private List<String> images = new ArrayList<String>();

    @Override
    protected void initView() {
        super.initView();
        String way = getIntent().getStringExtra("way");
        if (way.equals("pick")) {
            //选取照片
            GalleryFinal.openGalleryMuti(Constant.REQUEST_CODE_GALLERY, GalleryFinal.copyGlobalFuncationConfig(), callback);
        } else if (way.equals("take")) {
            //拍照
            GalleryFinal.openCamera(Constant.REQUEST_CODE_CAMERA, GalleryFinal.copyGlobalFuncationConfig(), callback);
        }
    }

    public GalleryFinal.OnHanlderResultCallback callback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            for (PhotoInfo item : resultList) {
                images.add(item.getPhotoPath());
                Log.i(TAG,"刚进来获取的图片为-->"+ JSONObject.toJSONString(images));
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {

        }
    };

    @Override
    public int setContentViewId() {
        ifNeedStatus = true;
        return R.layout.activity_releasemoment;
    }

    @Override
    public void createPresenter() {
        mPresenter = new PReleaseMomentImpl(mContext, this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

}
