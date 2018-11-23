package com.xoshop.mvp.module.moments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONObject;
import com.bigkoo.alertview.AlertView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jakewharton.rxbinding2.view.RxView;
import com.xoshop.R;
import com.xoshop.comm.bean.Constant;
import com.xoshop.mvp.base.BaseActivity;
import com.xoshop.mvp.bean.ItemChannel;
import com.xoshop.mvp.bean.ItemIndustry;
import com.xoshop.utils.AlertUtils;
import com.xoshop.utils.SysUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by Liang_Lu on 2017/12/21.
 *
 * @author HuangQiang
 * @github https://github.com/HuangQiang
 */

public class ReleaseMomentActivity extends BaseActivity<PReleaseMomentImpl> implements CReleaseMoment.IVReleaseMoment {
    @BindView(R.id.imagebtn_back)
    ImageButton imagebtnBack;
    @BindView(R.id.edit_content)
    EditText editContent;
    @BindView(R.id.recyclerView_image)
    RecyclerView recyclerViewImage;
    private List<String> images = new ArrayList<String>();
    private List<PhotoInfo> image_infos = new ArrayList<PhotoInfo>();
    private QuickAdapter quickAdapter;
    private FunctionConfig functionConfig = null;
    private AlertView photoPickerDialog;

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
        quickAdapter = new QuickAdapter();
        recyclerViewImage.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        recyclerViewImage.setItemAnimator(new DefaultItemAnimator());
        recyclerViewImage.setAdapter(quickAdapter);
        addDisposable(RxView.clicks(imagebtnBack).throttleFirst(Constant.SECONDS_FORBID, TimeUnit.SECONDS)
                .subscribe(o -> {
                    ReleaseMomentActivity.this.finish();
                }));
        mPresenter.getChannel();
        mPresenter.getIndustry();
    }


    /**
     * 点击加号上传图片
     */
    private void showImage() {
        photoPickerDialog = AlertUtils.photoPicker(this, functionConfig,
                new GalleryFinal.OnHanlderResultCallback() {
                    @Override
                    public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                        if(reqeustCode==Constant.REQUEST_CODE_CAMERA){
                            image_infos.addAll(resultList);
                            functionConfig = new FunctionConfig
                                    .Builder()
                                    .setMutiSelectMaxSize(9)
                                    .setSelected(image_infos)
                                    .build();
                        }else if(reqeustCode==Constant.REQUEST_CODE_GALLERY){
                            if(resultList.size()>0&&resultList!=null){
                                image_infos = resultList;
                                functionConfig = new FunctionConfig
                                        .Builder()
                                        .setMutiSelectMaxSize(9)
                                        .setSelected(image_infos)
                                        .build();
                            }
                        }
                        images = new ArrayList<String>();
                        for (PhotoInfo item:image_infos) {
                            images.add(item.getPhotoPath());
                        }
                        if(images.size()<9){
                            images.add("add");
                        }
                        quickAdapter.replaceData(images);
                        Log.i(TAG, "重新进入选择图片返回的数据为--->" + JSONObject.toJSONString(resultList));
                    }

                    @Override
                    public void onHanlderFailure(int requestCode, String errorMsg) {
                        Log.i(TAG, requestCode + errorMsg);
                    }
                }, true);
        photoPickerDialog.show();
    }

    //刚进来选择图片
    public GalleryFinal.OnHanlderResultCallback callback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if(resultList.size()>0&&resultList!=null){
                image_infos = resultList;
                functionConfig = new FunctionConfig
                        .Builder()
                        .setMutiSelectMaxSize(9)
                        .setSelected(image_infos)
                        .build();
                for (PhotoInfo item : resultList) {
                    images.add(item.getPhotoPath());
                    Log.i(TAG, "刚进来获取的图片为-->" + JSONObject.toJSONString(images));
                }
            }
            if (resultList.size() < 9) {
                images.add("add");
            }
            quickAdapter.replaceData(images);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void callBackIndustry(List<ItemIndustry> itemIndustries) {

    }

    @Override
    public void callBackChannel(List<ItemChannel> itemChannels) {

    }

    public class QuickAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        public QuickAdapter() {
            super(R.layout.item_imageview_release);
        }

        @Override
        protected void convert(final BaseViewHolder viewHolder, String item) {
            ImageView imageView = viewHolder.getView(R.id.imageview_moment);
            ViewGroup.LayoutParams lp = imageView.getLayoutParams();
            int size = SysUtils.getScreenWidth(ReleaseMomentActivity.this) / 4;
            lp.width = size;
            lp.height = size;
            imageView.setLayoutParams(lp);
            if (item.equals("add")) {
                imageView.setImageResource(R.drawable.image_add);
                addDisposable(RxView.clicks(imageView).throttleFirst(Constant.SECONDS_FORBID, TimeUnit.SECONDS)
                        .subscribe(o -> {
                            showImage();
                        }));
            } else {
                Bitmap bitMap = SysUtils.getSmallBitmap(item);
                imageView.setImageBitmap(bitMap);
            }


        }
    }
}
