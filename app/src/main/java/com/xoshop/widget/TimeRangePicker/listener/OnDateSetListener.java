package com.xoshop.widget.TimeRangePicker.listener;


import com.xoshop.widget.TimeRangePicker.DateScrollerDialog;

/**
 * 日期设置的监听器
 *
 * @author C.L. Wang
 */
public interface OnDateSetListener {
    void onDateSet(DateScrollerDialog timePickerView, long milliseconds, long milliFinishSeconds);
}
