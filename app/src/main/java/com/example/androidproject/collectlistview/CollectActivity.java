package com.example.androidproject.collectlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.androidproject.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class CollectActivity extends AppCompatActivity {
    public ListView listView;
    public List<CollectStatics> list_item=new ArrayList<>();
    public HashMap<String, Drawable> iconMap=new HashMap<>();
    public HashMap<String, String> packageInfoHashMap=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);

        Context context =getApplicationContext();
        PackageManager packageManager = context.getPackageManager();//获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        if(packageInfos!=null){
            for(int i=0;i<packageInfos.size();i++){
                iconMap.put(packageInfos.get(i).packageName,packageInfos.get(i).applicationInfo.loadIcon(getPackageManager()));
                packageInfoHashMap.put(packageInfos.get(i).packageName,packageInfos.get(i).applicationInfo.loadLabel(getPackageManager()).toString());
            }
        }
        UsageStatsManager usageStatsManager=(UsageStatsManager)getSystemService(USAGE_STATS_SERVICE);
        long time =System.currentTimeMillis()-24*60*60*365*1000;
        List<UsageStats> list=usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, time, System.currentTimeMillis());
        List<String> appName=new ArrayList<>();
        for(int i=0;i<list.size();i++) {
            list_item.add(new CollectStatics(list.get(i),iconMap.get(list.get(i).getPackageName()),packageInfoHashMap.get(list.get(i).getPackageName())));
        }
        Collections.sort(list_item,(CollectStatics o1,CollectStatics o2)->o2.getTimeStamp().compareTo(o1.getTimeStamp()));
        CollectListViewAdapater myAdapter = new CollectListViewAdapater(this,R.layout.collect_listview,list_item);
        listView = (ListView)this.findViewById(R.id.listView);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle=new Bundle();
                bundle.putString("appname",list_item.get(position).getAppname());
                bundle.putString("packagename",list_item.get(position).getPackageName());
                bundle.putString("time",list_item.get(position).getTime());
                Intent intent=new Intent(getApplicationContext(),ListViewItemActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}