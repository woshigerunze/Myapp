package com.example.androidproject.collectlistview;

import android.app.usage.UsageStats;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CollectStatics {
    private UsageStats      usageStats;
    private Drawable        icon;
    private String          appName;
    public CollectStatics(UsageStats usageStats,Drawable icon,String appName) {
        this.usageStats     =usageStats;
        this.icon           =icon;
        this.appName        =appName;
    }
    public String getPackageName() {
        return this.usageStats.getPackageName();
    }

    public String getTime(){
        Date date = new Date(this.usageStats.getLastTimeUsed());
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String res=sd.format(date);
        if(res.equals("00:00"))return "还未使用过此APP";
        return "总使用时间："+res;
    }
    public Long getTimeStamp(){
        return this.usageStats.getTotalTimeInForeground();
    }
    public String getAppname()
    {
        return appName;
    }
    public Drawable getIcon(){
        return icon;
    }
}
