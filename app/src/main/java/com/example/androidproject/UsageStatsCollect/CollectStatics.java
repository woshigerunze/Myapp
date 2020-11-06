package com.example.androidproject.UsageStatsCollect;

import android.app.usage.UsageStats;
import android.graphics.drawable.Drawable;

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
//        Date date = new Date(this.usageStats.getTotalTimeInForeground());
//        SimpleDateFormat sd = new SimpleDateFormat("mm:ss");
//        String res=sd.format(date);
        long time=this.usageStats.getTotalTimeInForeground();
        long hour = time/(60*60*1000);
        long minute = (time - hour*60*60*1000)/(60*1000);
        long second = (time - hour*60*60*1000 - minute*60*1000)/1000;
        String res=new String();
        res+=String.valueOf(hour)+":"+String.valueOf(minute)+":"+String.valueOf(second);
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
