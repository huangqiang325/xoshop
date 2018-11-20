package com.kulian.comm.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.flyco.banner.anim.select.ZoomInEnter;
import com.kulian.R;
import com.kulian.utils.AuthPreferences;
import com.kulian.utils.ViewFindUtils;
import com.kulian.widget.SimpleGuideBanner;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.service.DataProvider;

/**
 * Created by xiaoqiang on 2017/12/25.
 */

public class WelcomeGuideActivity extends Activity {
    private Context context = this;
    private View decorView;
    private boolean isFromBannerHome;
    private Class<? extends ViewPager.PageTransformer> transformerClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.__activity_guide);
        decorView = getWindow().getDecorView();
        sgb();
    }

    private void sgb() {
        SimpleGuideBanner sgb = ViewFindUtils.find(decorView, R.id.sgb);

        sgb
                .setIndicatorWidth(6)
                .setIndicatorHeight(6)
                .setIndicatorGap(12)
                .setIndicatorCornerRadius(3.5f)
                .setSelectAnimClass(ZoomInEnter.class)
                .setTransformerClass(transformerClass)
                .barPadding(0, 10, 0, 10)
                .setSource(geUsertGuides())
                .startScroll();

        sgb.setOnJumpClickL(new SimpleGuideBanner.OnJumpClickL() {
            @Override
            public void onJumpClick() {
                Intent intent = new Intent(WelcomeGuideActivity.this,
                        HomePageActivity.class);
                Log.i("WelcomeGuideActivity","onJumpClick---");
                startActivity(intent);
                AuthPreferences.setFirst("0");
                finish(); // 销毁当前Activity
            }
        });
    }

    public static ArrayList<Integer> geUsertGuides() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(R.drawable.guide1);
        list.add(R.drawable.guide2);
        list.add(R.drawable.guide3);
        return list;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 如果切换到后台，就设置下次不进入功能引导页
        AuthPreferences.setFirst("0");
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}




