package com.example.androidproject.collectlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.androidproject.MainActivity;
import com.example.androidproject.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CollectActivity extends AppCompatActivity {
    public ListView listView;
    public List<CollectStatics> list_item=new ArrayList<>();
    public HashMap<String, Drawable> iconMap=new HashMap<>();
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
            }
        }
        UsageStatsManager usageStatsManager=(UsageStatsManager)getSystemService(USAGE_STATS_SERVICE);
        long time =System.currentTimeMillis()-24*60*60*365*1000;
        List<UsageStats> list=usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, time, System.currentTimeMillis());
        List<String> appName=new ArrayList<>();
        for(int i=0;i<list.size();i++) {
            list_item.add(new CollectStatics(list.get(i),iconMap.get(list.get(i).getPackageName())));
        }

        CollectListViewAdapater myAdapter = new CollectListViewAdapater(this,R.layout.collect_listview,list_item);
        listView = (ListView)this.findViewById(R.id.listView);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle=new Bundle();
                bundle.putString("packagename",list_item.get(position).getPackageName());
                bundle.putString("time",list_item.get(position).getTime());
                Intent intent=new Intent(getApplicationContext(),ListViewItemActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}