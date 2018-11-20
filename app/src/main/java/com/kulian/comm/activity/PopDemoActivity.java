package com.kulian.comm.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kulian.R;
import com.kulian.mvp.base.BaseActivity;
import com.kulian.utils.PopupWindowFactory;
import com.kulian.utils.StatusBarUtil;
import com.kulian.utils.SysUtils;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by xiaoqiang on 2017/12/31.
 */

public class PopDemoActivity extends BaseActivity {
    @BindView(R.id.imagebutton_back)
    ImageButton imagebuttonBack;
    @BindView(R.id.btn_top)
    Button btnTop;
    @BindView(R.id.btn_toRight)
    Button btnToRight;
    @BindView(R.id.btn_toLeft)
    Button btnToLeft;
    @BindView(R.id.btn_bottom)
    Button btnBottom;
    @BindView(R.id.btn_middle)
    Button btnMiddle;
    @BindView(R.id.btn_middle_dropdown)
    Button btnMiddleDropdown;
    private PopupWindowFactory mPopupWindow_left;
    private PopupWindowFactory mPopupWindow_top;
    private PopupWindowFactory mPopupWindow_right;
    private PopupWindowFactory mPopupWindow_bottom;
    private PopupWindowFactory mPopupWindow_center;
    private PopupWindowFactory mPopupWindow_dropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        View popupView = getLayoutInflater().inflate(R.layout.___popupwindow1_left, null);
        TextView textView = popupView.findViewById(R.id.textview_center);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"ASDSADSA");
            }
        });
        mPopupWindow_left = new PopupWindowFactory(this, popupView, SysUtils.getWidth(this) / 3, SysUtils.getHeight(this), true, 1);
        mPopupWindow_top = new PopupWindowFactory(this, popupView, SysUtils.getWidth(this), SysUtils.getHeight(this) / 3, true, 2);
        mPopupWindow_right = new PopupWindowFactory(this, popupView, SysUtils.getWidth(this) / 3, SysUtils.getHeight(this), true, 3);
        mPopupWindow_bottom = new PopupWindowFactory(this, popupView, SysUtils.getWidth(this), SysUtils.getHeight(this) / 3, true, 4);
        mPopupWindow_center = new PopupWindowFactory(this, popupView, SysUtils.getWidth(this) / 2, SysUtils.getHeight(this) / 2, true, 5);
        ViewGroup.LayoutParams layoutParams = btnMiddleDropdown.getLayoutParams();
        Log.i(TAG,"layoutParams.width"+layoutParams.width);
        Log.i(TAG,"btnMiddleDropdown.getWidth()"+btnMiddleDropdown.getWidth());
        mPopupWindow_dropdown = new PopupWindowFactory(this, popupView, SysUtils.getWidth(this) / 4, SysUtils.getHeight(this) / 3, true, 0);
    }


    public void setOnClick() {
    }


    @Override
    protected void initView() {
        StatusBarUtil.StatusBarLightMode(this);

    }

    @Override
    public int setContentViewId() {
        ifNeedStatus = false;
        return R.layout.__activity_popupdemo;
    }

    @Override
    public void createPresenter() {

    }

    @OnClick({R.id.imagebutton_back, R.id.btn_top, R.id.btn_toRight, R.id.btn_toLeft, R.id.btn_bottom, R.id.btn_middle, R.id.btn_middle_dropdown})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imagebutton_back:
                break;
            case R.id.btn_top:
                mPopupWindow_top.showAtLocation(btnTop, Gravity.TOP, 0, 0);
                break;
            case R.id.btn_toRight:
                mPopupWindow_left.showAtLocation(btnToRight, Gravity.LEFT, 0, 0);
                break;
            case R.id.btn_toLeft:
                mPopupWindow_right.showAtLocation(btnToLeft, Gravity.RIGHT, 0, 0);
                break;
            case R.id.btn_bottom:
                mPopupWindow_bottom.showAtLocation(btnBottom, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.btn_middle:
                mPopupWindow_center.showAtLocation(btnMiddle, Gravity.CENTER, 0, 0);
                break;
            case R.id.btn_middle_dropdown:
                mPopupWindow_dropdown.showAsDropDown(btnMiddleDropdown,  10, 0);
                break;
        }
    }
}

