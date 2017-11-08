package com.wuxia.mydemo;

import com.wuxia.mydemo.adapter.RecyclerViewAdapter;

/**
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-11-28
 * Time: 19:55
 */
public class Content implements RecyclerViewAdapter.Item {
    private int TYPE = 2;
    private String title;
    private String desc;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIconUrl() {
        return url;
    }

    public void setIconUrl(String iconUrl) {
        this.url = iconUrl;
    }

    @Override
    public String toString() {
        return "Content{" +
                "title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", icon=" + url +
                '}';
    }

    @Override
    public int getType() {
        return TYPE;
    }
}
