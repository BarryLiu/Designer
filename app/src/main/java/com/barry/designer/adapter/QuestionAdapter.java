package com.barry.designer.adapter;

import android.app.ActionBar;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.barry.designer.R;
import com.barry.designer.bean.QuestionBean;

import java.util.List;

/**
 * Created by Barry on 2016/3/9.
 */
public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ListViewHolder> {
    List<QuestionBean> mList;
    Context mContext;
    public QuestionAdapter(
            List<QuestionBean> list,
            Context context){
        mList = list;
        mContext = context;
    }
    public interface OnItemClickListener{
        public void onItemClick(View itemView, int position);
    }
    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 创建视图
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建子视图
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.rv_item_layout,null);

        //设置view的layout
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        itemView.setLayoutParams(layoutParams);
        //创建一个ViewHolder,将创建的视图放入到viewholder
        ListViewHolder viewHolder = new ListViewHolder(itemView);
        return viewHolder;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name;
        public ListViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        }

        public void setData(int position){
            QuestionBean qb = mList.get(position);
            tv_name.setText(qb.getName());
        }
    }


    /**
     * 填充数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final ListViewHolder holder, final int position) {
        holder.setData(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null){
                    onItemClickListener.onItemClick(holder.itemView,position);
                }
                  Toast.makeText(mContext,position+"问题",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 设置长度
     * @return
     */
    @Override
    public int getItemCount() {
        return mList.size();
    }
}
