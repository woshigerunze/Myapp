package com.example.androidproject.UsageStatsCollect;

import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.util.Log;

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
        PackageManager packageManager = getApplicationContext().getPackageManager();//获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        db=db_helper.getWritableDatabase();
            for (int i = 0; i < list.size(); i++) {
                String packagename = list.get(i).getPackageName();
                String timestamp = Long.toString(list.get(i).getLastTimeUsed());
                Cursor cursor = db.rawQuery("select timestamp from appuse where packagename=? and timestamp=?", new String[]{packagename, timestamp});
                if (cursor.getCount() <= 0) {
                    ContentValues values = new ContentValues();
                    values.put("id", (byte[]) null);
                    values.put("timestamp", timestamp);
                    values.put("packagename", packagename);
                    db.insert("appuse", null, values);
                }
                cursor.close();
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
