package com.sunhuwh.music;

import android.os.Bundle;

import com.sunhuwh.music.xlistview.XListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public abstract class RefreshListViewActivity extends CustomToolbarActivity implements XListView.IXListViewListener{

    protected XListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //listView.setAutoLoadEnable(false);
        //listView.setXListViewListener(this);
        //listView.setRefreshTime(getTime());

    }

    protected String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }

//    @Override
//    public void onRefresh() {
//        reloadListData();
//    }

//    @Override
//    public void onLoadMore() {
//
//    }

    protected abstract void reloadListData();

}
