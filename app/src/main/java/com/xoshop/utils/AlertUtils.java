package com.xoshop.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.xoshop.R;
import com.xoshop.comm.bean.Constant;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;


/**
 * Created by admin on 2017/8/4.
 * <p>
 * 弹框设置
 */
public class AlertUtils {
    public interface AlertViewControl {
        void onItemClickListener(int position);
    }

    public static TimePickerDialog timePicker(Context context, int before, int after, OnDateSetListener listener) {
        return new TimePickerDialog.Builder()
                .setCallBack(listener)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("选择时间")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setHourText("时")
                .setMinuteText("分")
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis() - 3600L * 1000 * 24 * before)
                .setMaxMillseconds(System.currentTimeMillis() + 3600L * 1000 * 24 * after)
                .setCurrentMillseconds(System.currentTimeMillis())
                .setThemeColor(context.getResources().getColor(R.color.timepicker_dialog_bg))
                .setType(Type.ALL)
                .setWheelItemTextNormalColor(context.getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(context.getResources().getColor(R.color.timepicker_toolbar_bg))
                .setWheelItemTextSize(12)
                .build();
    }

    public static TimePickerDialog timePicker2(Context context, OnDateSetListener listener) {
        return new TimePickerDialog.Builder()
                .setCallBack(listener)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("选择时间")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis())
                .setMaxMillseconds(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 5)
                .setCurrentMillseconds(System.currentTimeMillis())
                .setThemeColor(context.getResources().getColor(R.color.timepicker_dialog_bg))
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextNormalColor(context.getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(context.getResources().getColor(R.color.timepicker_toolbar_bg))
                .setWheelItemTextSize(12)
                .build();
    }

    public static AlertView photoPicker(final Context context, final FunctionConfig functionConfig,
                                        final GalleryFinal.OnHanlderResultCallback callback, boolean mutiSelect) {
        return new AlertView.Builder().setContext(context)
                .setStyle(AlertView.Style.ActionSheet)
                .setMessage(null)
                .setCancelText("取消")
                .setDestructive("拍照", "从相册中选择")
                .setOthers(null)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        String path = "";
                        switch (position) {
                            case 0:
                                GalleryFinal.openCamera(Constant.REQUEST_CODE_CAMERA, functionConfig, callback);
                                break;
                            case 1:
                                if (mutiSelect) {
                                    GalleryFinal.openGalleryMuti(Constant.REQUEST_CODE_GALLERY, functionConfig, callback);
                                } else {
                                    GalleryFinal.openGallerySingle(Constant.REQUEST_CODE_GALLERY, functionConfig, callback);
                                }
                                break;
//                            case 2:
//                                if (new File(path).exists()) {
//                                    GalleryFinal.openCrop(Constants.REQUEST_CODE_CROP, functionConfig, path, callback);
//                                } else {
//                                    Toast.makeText(context, "图片不存在", Toast.LENGTH_SHORT).show();
//                                }
//                                break;
//                            case 3:
//                                if (new File(path).exists()) {
//                                    GalleryFinal.openEdit(Constants.REQUEST_CODE_EDIT, functionConfig, path, callback);
//                                } else {
//                                    Toast.makeText(context, "图片不存在", Toast.LENGTH_SHORT).show();
//                                }
//                                break;
                            default:
                                break;
                        }
                    }
                })
                .build();
    }

    /**
     * 0-订单，1-会员卡
     *
     * @param context
     * @param control
     * @return
     */
    public static AlertView checkWhat(final Context context, final AlertViewControl control, String TitleInfo) {
        return new AlertView.Builder().setContext(context)
                .setStyle(AlertView.Style.ActionSheet)
                .setTitle("选择操作")
                .setMessage(null)
                .setCancelText("取消")
                .setDestructive(TitleInfo)
                .setOthers(null)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        control.onItemClickListener(position);
                    }
                })
                .build();
    }

    /**
     * 0-订单，1-会员卡
     *
     * @param context
     * @param control
     * @return
     */
    public static AlertView checkDoubleInfo(final Context context, final AlertViewControl control, String TitleInfo, String TitleInfo2) {
        return new AlertView.Builder().setContext(context)
                .setStyle(AlertView.Style.ActionSheet)
                .setTitle("选择操作")
                .setMessage(null)
                .setCancelText("取消")
                .setDestructive(TitleInfo, TitleInfo2)
                .setOthers(null)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        control.onItemClickListener(position);
                    }
                })
                .build();
    }

    /**
     * 加载框
     *
     * @param context
     * @return
     */
    public static AlertDialog loadingDialog(Context context,String content) {
        AlertDialog dialog = new AlertDialog.Builder(context, R.style.NoBackgroundDialog).create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        View view = LayoutInflater.from(context).inflate(R.layout.__dialog_loading,null);
        if(!TextUtils.isEmpty(content)){
            TextView textview_content = view.findViewById(R.id.textview_content);
            textview_content.setText(content);
        }
        dialog.setContentView(view);
        return dialog;
    }

//    public static AlertDialog updateDialog(final Context context, final AppVersion appVersion){
//        return new AlertDialog.Builder(context,android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar)
//                .setTitle("发现新版本")
//                .setMessage("发现可用的新版本,是否立即更新?")
//                .setNegativeButton("取消", null)
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        ToastUtils.shortToast("开始下载...");
//                        Intent intent=new Intent(context, DownloadService.class);
//                        intent.putExtra("url",appVersion.getDownloadUrl());
//                        context.startService(intent);
//                    }
//                })
//                .create();
//    }
}