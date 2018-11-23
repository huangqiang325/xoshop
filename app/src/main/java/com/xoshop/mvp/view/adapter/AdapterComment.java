package com.xoshop.mvp.view.adapter;


import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xoshop.R;
import com.xoshop.mvp.bean.CommentListBean;
import com.xoshop.mvp.bean.ItemFoundData;


/**
 * Created by Administrator on 2018/5/20.
 */

public class AdapterComment extends BaseQuickAdapter<CommentListBean,BaseViewHolder> {
    private ItemFoundData foundBean;
    private Context context;
    public AdapterComment(ItemFoundData foundBean,Context context) {
        super(R.layout.item_comment);
        this.foundBean = foundBean;
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentListBean item) {
        helper.setText(R.id.comment_name,item.getUser_name()+":")
                .setText(R.id.comment_content,item.getInfo())
//                .setText(R.id.comment_time, SysUtils.formatDateTime_timeStamp(item.getCreate_time()))
        .setOnClickListener(R.id.item_comment_llayout, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String news_id = foundBean.getId();
//                String comment_num = foundBean.getComment_count();
//                Intent intent = new Intent(context,JCommentDetailActivity.class);
//                intent.putExtra("news_id",news_id);
//                intent.putExtra("comment_count",comment_num);
//                context.startActivity(intent);
            }
        });
    }
}
