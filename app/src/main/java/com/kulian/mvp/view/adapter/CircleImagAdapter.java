package com.kulian.mvp.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kulian.R;
import com.kulian.comm.activity.ActivityLargPicture;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Administrator on 2018/7/24.
 */

public class CircleImagAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    private int position;
    private Context context;
    private List<String> list;
    public CircleImagAdapter(Context context, List<String> list) {
        super(R.layout.circle_imag_item);
        this.context = context;
        this.list = list;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final String item) {

        helper.setOnClickListener(R.id.inner_imag, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position =  helper.getPosition();
//                Fragmentfriends.ifCanRefresh = false;
                Intent intent = new Intent(context,ActivityLargPicture.class);
                intent.putExtra("position",String.valueOf(position));
                intent.putStringArrayListExtra("url", (ArrayList<String>) list);
                context.startActivity(intent);
            }
        });
        Glide.with(context).load(item).bitmapTransform(new RoundedCornersTransformation(context, 5, 0,
                RoundedCornersTransformation.CornerType.TOP)).into((ImageView) helper.getView(R.id.inner_imag));
    }
}
