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
public class MyRecylerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<String> mDatas;
    private static final int TYPE_ITEM = 0;  //普通Item View
    private static final int TYPE_FOOTER = 1;  //底部FootView
    //上拉加载更多
    public static final int  PULLUP_LOAD_MORE=0;
    //正在加载中
    public static final int  LOADING_MORE=1;
    //上拉加载更多状态-默认为0
    private int load_more_status=0;



    public MyRecylerViewAdapter(Context context, List<String> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size() + 1;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof MyViewHolder) {
            MyViewHolder holder = ((MyViewHolder) viewHolder);
            holder.textView.setText(mDatas.get(position));
        } else if (viewHolder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) viewHolder;
            switch (load_more_status) {
                case PULLUP_LOAD_MORE:
                    footViewHolder.loadingTextId.setText("上拉加载更多...");
                    break;
                case LOADING_MORE:
                    footViewHolder.loadingTextId.setText("正在加载更多数据...");
                    break;
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //进行判断显示类型，来创建返回不同的View
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
            //这边可以做一些属性设置，甚至事件监听绑定
            //view.setBackgroundColor(Color.RED);
            MyViewHolder itemViewHolder = new MyViewHolder(view);
            return itemViewHolder;
        } else if (viewType == TYPE_FOOTER) {
            View foot_view = LayoutInflater.from(mContext).inflate(R.layout.view_footer, parent, false);
            //这边可以做一些属性设置，甚至事件监听绑定
            //view.setBackgroundColor(Color.RED);
            FootViewHolder footViewHolder = new FootViewHolder(foot_view);
            return footViewHolder;
        }
        return null;
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

class FootViewHolder extends RecyclerView.ViewHolder {
    TextView loadingTextId;

    public FootViewHolder(View itemView) {
        super(itemView);
        loadingTextId = (TextView) itemView.findViewById(R.id.loadingTextId);

    }
}