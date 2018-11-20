package com.kulian.mvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kulian.R;

/**
 * Created by xiaoqiang on 2018/9/12.
 */

public class FragmentDta extends Fragment {
    private String TAG = "FragmentDta";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =   LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_hshequ, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG,"ONrSUEP");
    }
}
