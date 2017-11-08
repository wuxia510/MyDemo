package com.wuxia.mydemo.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wuxia.mydemo.Content;
import com.wuxia.mydemo.R;
import com.wuxia.mydemo.abstracts.OnRecyclerViewScrollListener;
import com.wuxia.mydemo.adapter.MyAdapter;
import com.wuxia.mydemo.adapter.RecyclerViewAdapter;
import com.wuxia.mydemo.utils.StatusBarUtils;
import com.wuxia.mydemo.views.RecyclerViewDivider;

import java.util.ArrayList;
import java.util.List;

/**
 * App首页
 */
public class MainActivity extends BaseActivity {
    private RecyclerView recyclerViewId;
    private List<Content> mDatas;
    private LinearLayoutManager layoutManager;
    private RecyclerViewAdapter<Content> myAdapter;
    private SwipeRefreshLayout swipe_container;//google自带下拉刷新
    private String Tag = "MainActivity";
    private ArrayList<Content> arrayList;
    private RelativeLayout relFindSchoolId;
    private RelativeLayout relFindTeacherId;
    private TextView tv_tabLineTeacherId;
    private TextView tv_tabLineSchoolId;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<Content> list = (List<Content>) msg.obj;
            myAdapter.getList().addAll(list);
            myAdapter.notifyDataSetChanged();
            myAdapter.setFooterView(0);
        }
    };


    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        StatusBarUtils.setWindowStatusBarColor(this, R.color.main);//设置状态栏颜色
        return R.layout.activity_main;
    }

    @Override
    public void initView(View view) {
        recyclerViewId = (RecyclerView) findViewById(R.id.recyclerViewId);
        mDatas = new ArrayList<Content>();
        initData();
        myAdapter = new MyAdapter(mDatas);
        recyclerViewId.setAdapter(myAdapter);
        arrayList = new ArrayList<Content>(myAdapter.getList());
//         给每个item添加分割线
        recyclerViewId.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.VERTICAL));
        // 设置item增加和移除的动画
        recyclerViewId.setItemAnimator(new DefaultItemAnimator());
        layoutManager = new LinearLayoutManager(this);// 设置布局管理器
        recyclerViewId.setLayoutManager(layoutManager);
        swipe_container = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        //设置刷新时动画的颜色，可以设置4个
        swipe_container.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        //设置下拉进度条的背景颜色，默认白色。
//        swipe_container.setProgressBackgroundColorSchemeResource(android.R.color.holo_orange_light);
        swipe_container.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //这里执行刷新数据操作
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        mDatas.clear();
                        initData();
                        myAdapter.notifyDataSetChanged();
                        showToast("刷新了14条数据");
                        if (swipe_container.isRefreshing()) {
                            swipe_container.setRefreshing(false);
                        }
                    }
                }, 6000);
            }
        });
        //加载更多
        recyclerViewId.addOnScrollListener(new OnRecyclerViewScrollListener<Content>() {

            @Override
            public void onStart() {
                myAdapter.setFooterView(R.layout.view_footer);
                if (myAdapter.hasHeader()) {
                    recyclerViewId.smoothScrollToPosition(myAdapter.getItemCount() + 1);
                } else {
                    recyclerViewId.smoothScrollToPosition(myAdapter.getItemCount());
                }
            }

            @Override
            public void onLoadMore() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.e("TAG", "模拟网络请求数据");
                            Thread.sleep(5000);
                            //手动调用onFinish()
                            onFinish(arrayList);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            @Override
            public void onFinish(List<Content> contents) {
                Message message = Message.obtain();
                message.obj = contents;
                mHandler.sendMessage(message);
                setLoadingMore(false);
            }
        });
        relFindSchoolId = (RelativeLayout) findViewById(R.id.relFindSchoolId);
        relFindTeacherId = (RelativeLayout) findViewById(R.id.relFindTeacherId);
        tv_tabLineSchoolId = (TextView) findViewById(R.id.tv_tabLineSchoolId);
        tv_tabLineTeacherId = (TextView) findViewById(R.id.tv_tabLineTeacherId);
    }

    @Override
    public void setListener() {
        relFindSchoolId.setOnClickListener(this);
        relFindTeacherId.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void widgetClicked(View v) {
        switch (v.getId()) {
            case R.id.relFindTeacherId:
                tv_tabLineTeacherId.setBackgroundResource(R.color.barline);
                tv_tabLineSchoolId.setBackgroundResource(R.color.barlineUnselected);
                break;
            case R.id.relFindSchoolId:
                tv_tabLineSchoolId.setBackgroundResource(R.color.barline);
                tv_tabLineTeacherId.setBackgroundResource(R.color.barlineUnselected);
                break;
        }
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


    private void initData() {
        Content c = new Content();
        c.setIconUrl("http://p1.meituan.net/63.90/movie/7a29814fe6549b929df6e0ef9575ce699434172.jpg");
        c.setTitle("item1");
        c.setDesc("比基尼女郎，掀摇滚热浪。滨江区滨文路577号华润超市4楼。");
        mDatas.add(c);


        c = new Content();
        c.setIconUrl("http://p1.meituan.net/63.90/movie/bb8af131f11a6bc19e48081d6918e899579442.jpg");
        c.setTitle("item2");
        c.setDesc("说好悬疑片，能否严肃点。余杭区临平南苑街道藕花洲大街303号沃尔玛4楼。");
        mDatas.add(c);


        c = new Content();
        c.setIconUrl("http://p0.meituan.net/63.90/movie/8c5ee46ec0999de1fbc7a23e942dc102162274.jpg");
        c.setTitle("item3");
        c.setDesc("头七夜半时，亡妻回魂日。临安市锦城街道钱王街855号万华广场四楼。");
        mDatas.add(c);

        c = new Content();
        c.setIconUrl("http://p1.meituan.net/63.90/movie/bb8af131f11a6bc19e48081d6918e899579442.jpg");
        c.setTitle("item4");
        c.setDesc("说好悬疑片，能否严肃点。余杭区临平南苑街道藕花洲大街303号沃尔玛4楼。");
        mDatas.add(c);

        c = new Content();
        c.setIconUrl("http://p1.meituan.net/63.90/movie/bb8af131f11a6bc19e48081d6918e899579442.jpg");
        c.setTitle("item5");
        c.setDesc("说好悬疑片，能否严肃点。余杭区临平南苑街道藕花洲大街303号沃尔玛4楼。");
        mDatas.add(c);

        c = new Content();
        c.setIconUrl("http://p1.meituan.net/63.90/movie/2f369b627e44b0c9338ad562470665ca206314.jpg");
        c.setTitle("item6");
        c.setDesc("神探刘青云，破解连环杀。下城区龙游路38号。");
        mDatas.add(c);

        c = new Content();
        c.setIconUrl("http://p0.meituan.net/63.90/movie/f7df200378e3725a4bc825397f5c9956130126.jpg");
        c.setTitle("item7");
        c.setDesc("孑然火星巅，归途何其险。西湖区文一路298号物美超市6楼（近万塘路）。");
        mDatas.add(c);

        c = new Content();
        c.setIconUrl("http://p0.meituan.net/63.90/movie/b9ed75bcf21350ee25aaa0c5cec331e51029889.jpg");
        c.setTitle("item8");
        c.setDesc("分别再聚首，情动十年后。江干区金沙大道560号金沙天街商业中心5楼。");
        mDatas.add(c);

        c = new Content();
        c.setIconUrl("http://p1.meituan.net/63.90/movie/38810e3cc2613ec5dc939f3bd9b46fa5359031.jpg");
        c.setTitle("item9");
        c.setDesc("快递会武术，黑帮挡不住。西湖区紫金港路21号西溪天堂商业街地下一层（喜来登国际会议中心旁）。");
        mDatas.add(c);

        c = new Content();
        c.setIconUrl("http://p1.meituan.net/63.90/movie/3b698dbb8833ebf47ab4c5ba8e1a6d811495601.jpg");
        c.setTitle("item10");
        c.setDesc("雪域勇闯荡，终成一代王。萧山区市心中路123号旺角城新天地8号楼4-5层/市心中路268号银隆百货B座5楼。");
        mDatas.add(c);

        c = new Content();
        c.setIconUrl("http://p1.meituan.net/63.90/movie/3b698dbb8833ebf47ab4c5ba8e1a6d811495601.jpg");
        c.setTitle("item11");
        c.setDesc("雪域勇闯荡，终成一代王。桐庐县分水镇市达路239号。");
        mDatas.add(c);

        c = new Content();
        c.setIconUrl("http://p0.meituan.net/63.90/movie/7406438bf10d58f46fabe343fcb395c5338537.jpg");
        c.setTitle("item12");
        c.setDesc("难辨傻与痴，人生皆如此。上城区延安路98号西湖银泰城五层（银泰非营业时段可至红门局路乘直达电梯到达影院）。");
        mDatas.add(c);

        c = new Content();
        c.setIconUrl("http://p0.meituan.net/63.90/movie/973441b7c81486bc907fff28aa2950db907214.jpg");
        c.setTitle("item13");
        c.setDesc("红衣小男孩，猫脸老太太。江干区秋涛北路新澳门广场二楼。");
        mDatas.add(c);


        c = new Content();
        c.setIconUrl("http://p1.meituan.net/63.90/movie/38810e3cc2613ec5dc939f3bd9b46fa5359031.jpg");
        c.setTitle("item14");
        c.setDesc("快递会武术，黑帮挡不住。西湖区紫金港路21号西溪天堂商业街地下一层（喜来登国际会议中心旁）。");
        mDatas.add(c);
    }
}
