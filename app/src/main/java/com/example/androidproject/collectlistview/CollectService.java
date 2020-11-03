package com.example.androidproject.collectlistview;

import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.BatteryManager;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.androidproject.SQLiteHelper;
import java.util.List;

public class CollectService extends Service {
    public static final String TAG = "myservice";
    public UsageStatsManager usageStatsManager=null;
    public long time =System.currentTimeMillis()-24*60*60*365*1000;
    public List<UsageStats> list=null;
    public SQLiteHelper db_helper=new SQLiteHelper(CollectService.this);
    public SQLiteDatabase db=null;
    @Override
    public void onCreate() {
        super.onCreate();
        usageStatsManager=(UsageStatsManager)getSystemService(USAGE_STATS_SERVICE);
        list=usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, time, System.currentTimeMillis());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        db=db_helper.getWritableDatabase();
        for(UsageStats cursor : list){

        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
