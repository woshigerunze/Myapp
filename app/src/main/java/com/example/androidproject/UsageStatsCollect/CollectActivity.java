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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.ToggleButton;

import com.example.androidproject.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CollectActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    public ListView listView;
    public List<CollectStatics> list_item                       =new ArrayList<>();
    public HashMap<String, Drawable> iconMap                    =new HashMap<>();
    public HashMap<String, String> packageInfoHashMap           =new HashMap<>();
    public HashMap<String,PackageInfo> SystemApp                =new HashMap<>();
    public HashMap<String,PackageInfo> UserApp                  =new HashMap<>();
    public HashMap<String, Integer> InstalledPackageMap         =new HashMap<>();
    public int Sort_selection                           =0;//默认0降序,1生序
    public int Display_mode                             =0;//默认0所有APP,1用户APP,2系统APP
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
                String packagename=packageInfo.packageName;
                Log.d("test",packagename);
                if(InstalledPackageMap.get(packagename)==null){
                    InstalledPackageMap.put(packagename,1);
                }
                else continue;
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
        Collections.sort(list_item,(CollectStatics o1,CollectStatics o2)->o2.getTotalTimeInForeground().compareTo(o1.getTotalTimeInForeground()));
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
                bundle.putString("timestamp",list_item.get(position).getFirstTimeStamp());
                Intent intent=new Intent(getApplicationContext(),ListViewItemActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        List<CollectStatics> list_allApp = new ArrayList<>();
        List<CollectStatics> list_userApp=new ArrayList<>();
        for(int i=0;i<list.size();i++) {
            if (UserApp.get(list.get(i).getPackageName()) != null)
                list_userApp.add(new CollectStatics(list.get(i), iconMap.get(list.get(i).getPackageName()), packageInfoHashMap.get(list.get(i).getPackageName())));
        }
        for (int i = 0; i < list.size(); i++) {
            if (SystemApp.get(list.get(i).getPackageName()) != null)
                list_allApp.add(new CollectStatics(list.get(i), iconMap.get(list.get(i).getPackageName()), packageInfoHashMap.get(list.get(i).getPackageName())));
        }
        Collections.sort(list_allApp,(CollectStatics o1,CollectStatics o2)->o2.getTotalTimeInForeground().compareTo(o1.getTotalTimeInForeground()));
        Collections.sort(list_userApp,(CollectStatics o1,CollectStatics o2)->o2.getTotalTimeInForeground().compareTo(o1.getTotalTimeInForeground()));
        ToggleButton toggleButton=(ToggleButton)findViewById(R.id.toggleButton);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
//                    List<CollectStatics> list_userApp=new ArrayList<>();
//                    List<CollectStatics> list_item=new ArrayList<>();
//                    long time =System.currentTimeMillis()-24*60*60*365*1000;
//                    List<UsageStats> list=usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, time, System.currentTimeMillis());
//                    for(int i=0;i<list.size();i++){
//                        if(UserApp.get(list.get(i).getPackageName())!=null)
//                            list_userApp.add(new CollectStatics(list.get(i),iconMap.get(list.get(i).getPackageName()),packageInfoHashMap.get(list.get(i).getPackageName())));
                    CollectListViewAdapater myAdapter = new CollectListViewAdapater(getApplicationContext(), R.layout.collect_listview, list_userApp);
                    listView = (ListView) findViewById(R.id.listView);
                    listView.setAdapter(myAdapter);
                    Display_mode=1;
                } else {
//                    List<CollectStatics> list_allApp = new ArrayList<>();
//                    long time = System.currentTimeMillis() - 24 * 60 * 60 * 365 * 1000;
//                    List<UsageStats> list = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, time, System.currentTimeMillis());
//                    for (int i = 0; i < list.size(); i++) {
//                        if (SystemApp.get(list.get(i).getPackageName()) != null)
//                            list_allApp.add(new CollectStatics(list.get(i), iconMap.get(list.get(i).getPackageName()), packageInfoHashMap.get(list.get(i).getPackageName())));
                    CollectListViewAdapater myAdapter = new CollectListViewAdapater(getApplicationContext(), R.layout.collect_listview, list_allApp);
                    listView = (ListView) findViewById(R.id.listView);
                    listView.setAdapter(myAdapter);
                    Display_mode=0;
                }
            }
        });
        ToggleButton toggleButton1=(ToggleButton)findViewById(R.id.toggleButton1);
        toggleButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(Sort_selection==0){
                    if(Display_mode==0){
                        Collections.sort(list_item,(CollectStatics o1,CollectStatics o2)->o1.getTotalTimeInForeground().compareTo(o2.getTotalTimeInForeground()));
                        CollectListViewAdapater myAdapter = new CollectListViewAdapater(getApplicationContext(), R.layout.collect_listview, list_item);
                        listView = (ListView) findViewById(R.id.listView);
                        listView.setAdapter(myAdapter);
                    }
                    else if(Display_mode==1){
                        Collections.sort(list_userApp,(CollectStatics o1,CollectStatics o2)->o1.getTotalTimeInForeground().compareTo(o2.getTotalTimeInForeground()));
                        CollectListViewAdapater myAdapter = new CollectListViewAdapater(getApplicationContext(), R.layout.collect_listview, list_userApp);
                        listView = (ListView) findViewById(R.id.listView);
                        listView.setAdapter(myAdapter);
                    }
                    else{
                        Collections.sort(list_allApp,(CollectStatics o1,CollectStatics o2)->o1.getTotalTimeInForeground().compareTo(o2.getTotalTimeInForeground()));
                        CollectListViewAdapater myAdapter = new CollectListViewAdapater(getApplicationContext(), R.layout.collect_listview, list_allApp);
                        listView = (ListView) findViewById(R.id.listView);
                        listView.setAdapter(myAdapter);
                    }
                    Sort_selection=1;
                }
                else if(Sort_selection==1){
                    if(Display_mode==0){
                        Collections.sort(list_item,(CollectStatics o1,CollectStatics o2)->o2.getTotalTimeInForeground().compareTo(o1.getTotalTimeInForeground()));
                        CollectListViewAdapater myAdapter = new CollectListViewAdapater(getApplicationContext(), R.layout.collect_listview, list_item);
                        listView = (ListView) findViewById(R.id.listView);
                        listView.setAdapter(myAdapter);
                    }
                    else if(Display_mode==1){
                        Collections.sort(list_userApp,(CollectStatics o1,CollectStatics o2)->o2.getTotalTimeInForeground().compareTo(o1.getTotalTimeInForeground()));
                        CollectListViewAdapater myAdapter = new CollectListViewAdapater(getApplicationContext(), R.layout.collect_listview, list_userApp);
                        listView = (ListView) findViewById(R.id.listView);
                        listView.setAdapter(myAdapter);
                    }
                    else{
                        Collections.sort(list_allApp,(CollectStatics o1,CollectStatics o2)->o2.getTotalTimeInForeground().compareTo(o1.getTotalTimeInForeground()));
                        CollectListViewAdapater myAdapter = new CollectListViewAdapater(getApplicationContext(), R.layout.collect_listview, list_allApp);
                        listView = (ListView) findViewById(R.id.listView);
                        listView.setAdapter(myAdapter);
                    }
                    Sort_selection=0;
                }
            }
        });
        SearchView searchView=(SearchView)findViewById(R.id.search_view);
        searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) this);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        List<CollectStatics> list_search=new ArrayList<>();
        for(int i=0;i<list_item.size();i++){
            if(list_item.get(i).getPackageName().contains(newText)){
                list_search.add(list_item.get(i));
            }
        }
        CollectListViewAdapater myAdapter = new CollectListViewAdapater(getApplicationContext(), R.layout.collect_listview, list_search);
        listView.setAdapter(myAdapter);
        return true;
    }
}