package com.xoshop.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by xiaoqiang on 2018/6/20.
 */

public class ForbidRecycleViewScroll extends LinearLayoutManager {
    private boolean isScrollEnabled = true;

    public ForbidRecycleViewScroll(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }
}