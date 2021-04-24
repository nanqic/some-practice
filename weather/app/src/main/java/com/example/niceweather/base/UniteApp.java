package com.example.niceweather.base;

import android.app.Application;

import com.example.niceweather.db.DBManager;

import org.xutils.x;

public class UniteApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        DBManager.initDB(this);// 初始化数据库
    }
}

