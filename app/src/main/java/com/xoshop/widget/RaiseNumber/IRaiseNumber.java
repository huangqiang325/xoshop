package com.xoshop.widget.RaiseNumber;

/**
 * Created by xiaoqiang on 2018/4/19.
 */

public interface IRaiseNumber {

    void start();
    void setFloat(float fromNum, float toNum);
    void setInteger(int fromNum, int toNum);
    void setDuration(long duration);
    void setOnEndListener(RiseNumberTextView.EndListener callback);
}

