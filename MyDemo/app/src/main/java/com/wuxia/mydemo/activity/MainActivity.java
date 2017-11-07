package com.wuxia.mydemo.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.wuxia.mydemo.R;
import com.wuxia.mydemo.adapter.MyRecylerViewAdapter;
import com.wuxia.mydemo.views.RecyclerViewDivider;

import java.util.ArrayList;
import java.util.List;

/**
 * App首页
 */
public class MainActivity extends BaseActivity {
    private RecyclerView recyclerViewId;
    private List<String> mDatas;
    private LinearLayoutManager layoutManager;
    private MyRecylerViewAdapter adapter;
    private SwipeRefreshLayout swipe_container;//google自带下拉刷新
    private String Tag = "MainActivity";


    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(View view) {
        recyclerViewId = (RecyclerView) findViewById(R.id.recyclerViewId);
        mDatas = new ArrayList<String>();
        adapter = new MyRecylerViewAdapter(this, mDatas);
        recyclerViewId.setAdapter(adapter);
//         给每个item添加分割线
        recyclerViewId.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.VERTICAL, R.drawable.bg_register));
        // 设置item增加和移除的动画
        recyclerViewId.setItemAnimator(new DefaultItemAnimator());
        layoutManager = new LinearLayoutManager(this);// 设置布局管理器
        recyclerViewId.setLayoutManager(layoutManager);
        swipe_container = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        //设置刷新时动画的颜色，可以设置4个
        swipe_container.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        //设置下拉进度条的背景颜色，默认白色。
        swipe_container.setProgressBackgroundColorSchemeResource(android.R.color.holo_orange_light);
        swipe_container.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //这里执行刷新数据操作
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        mDatas.clear();
                        mDatas.addAll(DataResource.getData());
                        adapter.notifyDataSetChanged();
                        showToast("刷新了10条数据");
                        if (swipe_container.isRefreshing()) {
                            swipe_container.setRefreshing(false);
                        }
                    }
                }, 6000);
            }
        });
        //加载更多
        recyclerViewId.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.e(Tag, Tag + "--ocnScrolled--->" + dy);
                if (dy > 0) {//向上滑动

                } else {//向下滑动

                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    @Override
    public void setListener() {

    }

    @Override
    public void widgetClicked(View v) {

    }

    @Override
    public void doBusiness(Context mContext) {
        //获取数据
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDatas.clear();
                mDatas.addAll(DataResource.getData());
                adapter.notifyDataSetChanged();
                // 加载完数据设置为不刷新状态，将下拉进度收起来
                if (swipe_container.isRefreshing()) {
                    swipe_container.setRefreshing(false);
                }
            }
        }, 2000);
    }

    /**
     * 数据源
     */
    public static class DataResource {
        private static List<String> datas = new ArrayList<>();
        private static int page = 0;

        public static List<String> getData() {
            page = 0;
            datas.clear();
            for (int i = 0; i < 15; i++) {
                datas.add("item " + i);
            }

            return datas;
        }

        public static List<String> getMoreData() {
            page = page + 1;
            for (int i = 20 * page; i < 20 * (page + 1); i++) {
                datas.add("item " + i);
            }

            return datas;
        }
    }
}
