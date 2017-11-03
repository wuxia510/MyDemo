package com.wuxia.mydemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wuxia.mydemo.R;

import java.util.List;

/**
 * 作者： wuxia on 2017/10/31 0031 18:37
 * 邮箱：1137451819@qq.com
 * 说明：RecyclerView的适配器
 */
public class MyRecylerViewAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context mContext;
    private List<String> mDatas;

    public MyRecylerViewAdapter(Context context, List<String> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        viewHolder.textView.setText(mDatas.get(position));

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

}

class MyViewHolder extends RecyclerView.ViewHolder {
    // Item子布局上的一个元素
    TextView textView;

    public MyViewHolder(View itemView) {
        super(itemView);
        // 关联引动该元素 ，在item.xml中findView，注意不要忘写(itemview.)
        textView = (TextView) itemView.findViewById(R.id.item_textView);
    }
}