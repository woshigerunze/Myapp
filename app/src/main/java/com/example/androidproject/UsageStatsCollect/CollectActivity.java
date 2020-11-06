package com.example.androidproject.UsageStatsCollect;

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
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ToggleButton;

import com.example.androidproject.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CollectActivity extends AppCompatActivity {
    public ListView listView;
    public List<CollectStatics> list_item               =new ArrayList<>();
    public HashMap<String, Drawable> iconMap            =new HashMap<>();
    public HashMap<String, String> packageInfoHashMap   =new HashMap<>();
    public HashMap<String,PackageInfo> SystemApp        =new HashMap<>();
    public HashMap<String,PackageInfo> UserApp          =new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);

        Context context =getApplicationContext();
        PackageManager packageManager = context.getPackageManager();//获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        if(packageInfos!=null){
            for(int i=0;i<packageInfos.size();i++){
                PackageInfo packageInfo=packageInfos.get(i);
                if((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM)==0) {
                    iconMap.put(packageInfo.packageName, packageInfo.applicationInfo.loadIcon(getPackageManager()));
                    packageInfoHashMap.put(packageInfo.packageName, packageInfo.applicationInfo.loadLabel(getPackageManager()).toString());
                    UserApp.put(packageInfo.packageName,packageInfo);
                }
                else {
                    iconMap.put(packageInfo.packageName, packageInfo.applicationInfo.loadIcon(getPackageManager()));
                    SystemApp.put(packageInfo.packageName, packageInfo);
                    packageInfoHashMap.put(packageInfo.packageName, packageInfo.applicationInfo.loadLabel(getPackageManager()).toString());
                }
            }
        }
        UsageStatsManager usageStatsManager=(UsageStatsManager)getSystemService(USAGE_STATS_SERVICE);
        long time =System.currentTimeMillis()-24*60*60*365*1000;
        List<UsageStats> list=usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, time, System.currentTimeMillis());
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
        ToggleButton toggleButton=(ToggleButton)findViewById(R.id.toggleButton);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    List<CollectStatics> list_userApp=new ArrayList<>();
//                    List<CollectStatics> list_item=new ArrayList<>();
//                    long time =System.currentTimeMillis()-24*60*60*365*1000;
//                    List<UsageStats> list=usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, time, System.currentTimeMillis());
                    for(int i=0;i<list.size();i++){
                        if(UserApp.get(list.get(i).getPackageName())!=null)
                            list_userApp.add(new CollectStatics(list.get(i),iconMap.get(list.get(i).getPackageName()),packageInfoHashMap.get(list.get(i).getPackageName())));
                        CollectListViewAdapater myAdapter = new CollectListViewAdapater(getApplicationContext(),R.layout.collect_listview,list_userApp);
                        listView = (ListView)findViewById(R.id.listView);
                        listView.setAdapter(myAdapter);
                    }
                }
                else {
                    List<CollectStatics> list_allApp = new ArrayList<>();
//                    long time = System.currentTimeMillis() - 24 * 60 * 60 * 365 * 1000;
//                    List<UsageStats> list = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, time, System.currentTimeMillis());
                    for (int i = 0; i < list.size(); i++) {
                        if (SystemApp.get(list.get(i).getPackageName()) != null)
                            list_allApp.add(new CollectStatics(list.get(i), iconMap.get(list.get(i).getPackageName()), packageInfoHashMap.get(list.get(i).getPackageName())));
                        CollectListViewAdapater myAdapter = new CollectListViewAdapater(getApplicationContext(), R.layout.collect_listview, list_allApp);
                        listView = (ListView) findViewById(R.id.listView);
                        listView.setAdapter(myAdapter);
                    }
                }
            }
        });
    }
}