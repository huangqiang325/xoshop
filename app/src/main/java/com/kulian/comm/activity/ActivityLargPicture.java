package com.kulian.comm.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.kulian.R;
import com.kulian.mvp.base.BaseActivity;
import com.kulian.utils.ActivityManagerUtils;
import com.kulian.widget.HackyViewPager;
import com.kulian.widget.wheelview.wheelAdapter.ImageViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/16.
 */

public class ActivityLargPicture extends BaseActivity implements View.OnClickListener{

    private ImageViewPagerAdapter adapter;
    private HackyViewPager tupian_pager;
    private TextView tupian_page_total_tv;
    private List<String> thumb;
    private String position;
    private int totalPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManagerUtils.getInstance().addActivity(this);
        tupian_pager = (HackyViewPager) findViewById(R.id.tupian_pager);
        tupian_page_total_tv = (TextView) findViewById(R.id.tupian_page_total_tv);
        tupian_pager.setOnClickListener(this);

        position = getIntent().getStringExtra("position");
        ArrayList<String> item = getIntent().getStringArrayListExtra("url");
        String path = getIntent().getStringExtra("path");

        ArrayList<String> list = new ArrayList<>();
        if (TextUtils.isEmpty(listToString(item))){
            list.add(path);
            adapter = new ImageViewPagerAdapter(getSupportFragmentManager(), (ArrayList<String>) list);
            tupian_pager.setAdapter(adapter);
            tupian_page_total_tv.setText("1/1");
        }else if (TextUtils.isEmpty(path)){
            list  =  item;
            adapter = new ImageViewPagerAdapter(getSupportFragmentManager(), (ArrayList<String>) list);
            tupian_pager.setAdapter(adapter);
            tupian_pager.setCurrentItem(Integer.valueOf(position));
            totalPager = list.size();
            tupian_page_total_tv.setText(String.valueOf(Integer.valueOf(position)+1)+"/"+String.valueOf(totalPager));
        }
        tupian_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tupian_page_total_tv.setText(String.valueOf(tupian_pager.getCurrentItem()+1)+"/"+String.valueOf(totalPager));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }


    @Override
    protected void initView() {

    }

    @Override
    public int setContentViewId() {
        ifNeedStatus = false;
        return R.layout._activity_larg_picture;
    }

    @Override
    public void createPresenter() {

    }



    public static String listToString(List<String> list){
        if(list==null){
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean first = true;
        //第一个前面不拼接","
        for(String string :list) {
            if(first) {
                first=false;
            }else{
                result.append(",");
            }
            result.append(string);
        }
        return result.toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tupian_pager:

                break;
                default:
                    break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
