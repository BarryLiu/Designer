package com.barry.designer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.barry.designer.R;
import com.barry.designer.bean.QuestionBean;

import java.util.List;

/**
 * Created by Barry on 2016/3/9.
 */
public class QuestionAdapter extends RecyclerView.Adapter {
    List<QuestionBean> mList ;
    Context mContext;

    public QuestionAdapter(List<QuestionBean> list, Context context) {
        this.mList = mList;
        this.mContext = mContext;
    }

    /**
     * 创建视图
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建子视图
        View itemview = LayoutInflater.from(mContext).inflate(R.layout.rv_item_layout,null);
        //创建一个ViewHolder,将创建的视图放入到viewholder
        RecyclerView.ViewHolder viewHolder = new RecyclerView.ViewHolder(itemview){
        };

        return viewHolder;
    }

    /**
     * 填充数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextView tv_name = (TextView) holder.itemView.findViewById(R.id.tv_name);
        QuestionBean qb = mList.get(position);
        tv_name.setText(qb.getName());
    }

    @Override
    public int getItemCount() {
        //设置长度
        return mList.size();
    }
}
