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
    private UsageStats usageStats;
    private Drawable   icon;
    public CollectStatics(UsageStats usageStats,Drawable icon) {
        this.usageStats=usageStats;
        this.icon      =icon;
    }
    public String getPackageName() {
        return this.usageStats.getPackageName();
    }

    public String getTime(){
        Date date = new Date(this.usageStats.getTotalTimeInForeground());
        SimpleDateFormat sd = new SimpleDateFormat("mm:ss");
        String res=sd.format(date);
        return res;
    }

    public Drawable getIcon(){
        return icon;
    }
}
