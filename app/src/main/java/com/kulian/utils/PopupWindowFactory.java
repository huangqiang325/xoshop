package com.kulian.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.kulian.R;

/**
 * 作者：Rance on 2016/11/29 10:47
 * 邮箱：rance935@163.com
 */
public class PopupWindowFactory {

    private Context mContext;

    private PopupWindow mPop;

    /**
     * @param mContext 上下文
     * @param view     PopupWindow显示的布局文件
     * @param width    PopupWindow的宽
     * @param heigth   PopupWindow的高
     */
    public PopupWindowFactory(Context mContext, View view, int width, int heigth, boolean ifCancel, int direction) {
        init(mContext, view, width, heigth, ifCancel, direction);
    }


    private void init(Context mContext, View view, int width, int heigth, final boolean ifCancle, int direction) {
        this.mContext = mContext;

        //下面这两个必须有！！
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        // PopupWindow(布局，宽度，高度)
        mPop = new PopupWindow(view, width, heigth, true);
        mPop.setTouchable(true);
        mPop.setOutsideTouchable(true);
        mPop.setBackgroundDrawable(new ColorDrawable());
        if (direction != 0) {
            mPop.setAnimationStyle(getAnimation(direction));
        }
        // 重写onKeyListener,按返回键消失
        if (ifCancle) {
            view.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        mPop.dismiss();
                        return true;
                    }
                    return false;
                }
            });
        } else {
            view.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    return false;
                }
            });
        }
        if (ifCancle) {
            //点击其他地方消失
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (mPop != null && mPop.isShowing()) {
                        mPop.dismiss();
                        return true;
                    }
                    return false;
                }
            });

        } else {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });
        }
        mPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // popupWindow隐藏时恢复屏幕正常透明度
                setBackgroundAlpha(1.0f);

            }
        });

    }

    public PopupWindow getPopupWindow() {
        return mPop;
    }


    /**
     * 以触发弹出窗的view为基准，出现在view的内部上面，弹出的pop_view左上角正对view的左上角
     *
     * @param parent  view
     * @param gravity 在view的什么位置 Gravity.CENTER、Gravity.TOP......
     * @param x       与控件的x坐标距离
     * @param y       与控件的y坐标距离
     */
    public void showAtLocation(View parent, int gravity, int x, int y) {
        if (mPop != null) {
            mPop.setFocusable(true);
        }
        if (mPop.isShowing()) {
            mPop.dismiss();
            return;
        } else {
            setBackgroundAlpha(0.5f);//设置屏幕透明度
            mPop.showAtLocation(parent, gravity, x, y);
        }

    }

    /**
     * /**
     * 以触发弹出窗的view为基准，出现在view的正下方，弹出的pop_view左上角正对view的左下角
     *
     * @param anchor view
     * @param xoff   与view的x坐标距离
     * @param yoff   与view的y坐标距离
     */
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        if (mPop != null) {
            mPop.setFocusable(true);
        }
        Log.i("PopupWindowFactory", "isShowing--->" + ifShow());
        if (ifShow()) {
            dismiss();
        } else {
            setBackgroundAlpha(0.5f);//设置屏幕透明度
            mPop.showAsDropDown(anchor, xoff, yoff);
        }
    }

    /**
     * 隐藏PopupWindow
     */
    public void dismiss() {
        if (mPop != null && ifShow()) {
            mPop.dismiss();
        }
    }

    public boolean ifShow() {
        return mPop.isShowing();
    }

    /**
     * 根据参数弹出的方向来决定弹出的时候的动画
     *
     * @param direction 弹出的方向 1从左往右 2 从上往下 3 从左往右 4从下往上
     * @return
     */
    public int getAnimation(int direction) {
        int animation = 0;
        switch (direction) {
            case 1:
                animation = R.style.PopupAnimationLeft;
                break;
            case 2:
                animation = R.style.PopupAnimationTop;
                break;
            case 3:
                animation = R.style.PopupAnimationRight;
                break;
            case 4:
                animation = R.style.PopupAnimationbottom;
                break;
            case 5:
                animation = R.style.PopupAnimationMiddle;
                break;

        }
        return animation;
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 屏幕透明度0.0-1.0 1表示完全不透明
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) mContext).getWindow().setAttributes(lp);
    }
}
