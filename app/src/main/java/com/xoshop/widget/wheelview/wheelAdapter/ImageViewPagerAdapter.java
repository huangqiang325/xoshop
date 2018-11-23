package com.xoshop.widget.wheelview.wheelAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import com.xoshop.comm.adapter.ImageFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/16.
 */

public class ImageViewPagerAdapter extends FragmentStatePagerAdapter {
    private static final String IMAGE_URL = "image";

    ArrayList<String> mDatas;

    public ImageViewPagerAdapter(FragmentManager fm, ArrayList<String> data) {
        super(fm);
        mDatas = data;
    }

    @Override
    public Fragment getItem(int position) {
        String url = mDatas.get(position);
        Fragment fragment = ImageFragment.newInstance(url);
        return fragment;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }
}
