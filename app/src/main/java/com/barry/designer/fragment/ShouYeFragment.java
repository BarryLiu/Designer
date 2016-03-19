package com.barry.designer.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.barry.designer.GuiderActivity;
import com.barry.designer.QuestionDetailActivity;
import com.barry.designer.R;
import com.barry.designer.adapter.QuestionAdapter;
import com.barry.designer.bean.QuestionBean;
import com.barry.designer.bean.ResponseBean;
import com.barry.designer.http.GKCallBack;
import com.barry.designer.http.GKRequestParams;
import com.barry.designer.http.HttpUtils;
import com.barry.designer.utils.DialogUtils;
import com.barry.designer.utils.JsonUtils;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Barry on 2016/3/9.
 */
public class ShouYeFragment extends Fragment {
    List<QuestionBean> list;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_shouye, null);
        final RecyclerView rv_list = (RecyclerView) rootView.findViewById(R.id.rv_list);


        //获取数据
        list = new ArrayList<QuestionBean>();
       /* for (int i = 0; i < 15; i++) {
            QuestionBean qb = new QuestionBean();
            qb.setName("第" + (i + 1) + "个问题");
            qb.setDetail("这个问题是。。");

            list.add(qb);
        }
*/
        HttpUtils.getQuestions(null,new GKCallBack(){
            @Override
            public void onSuccess(String result) {

                ResponseBean rb = HttpUtils.paserResponseDataArray(result);

                DialogUtils.showLog(result);
                DialogUtils.showLog("dd:"+rb.getJson_reason());
                list = (List<QuestionBean>)JsonUtils.fromJson(rb.getJson_data(), new TypeToken<ArrayList<QuestionBean>>() {
                });
                //创建布局
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(inflater.getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                // linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);//设置横着显示
                GridLayoutManager gridLayoutManager = new GridLayoutManager(inflater.getContext(), 5);
                rv_list.setLayoutManager(linearLayoutManager);

                //3创建适配器
                QuestionAdapter adapter = new QuestionAdapter(list, inflater.getContext());
                //4绑定适配器
                rv_list.setAdapter(adapter);

                //5实现点击事件
                adapter.setOnItemClickListener(new QuestionAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(View itemView, int position) {

                        QuestionBean qb = list.get(position);
                        Intent intent = new Intent(getContext(), QuestionDetailActivity.class);

                        intent.putExtra("qb",qb);
                        startActivity(intent);
                    }

                });

            }
            @Override
            public void onError(String result) {
                DialogUtils.showTips(getContext(),result);
                DialogUtils.showLog("得到数据出错了");
            }
        });

         return rootView;
    }
}
