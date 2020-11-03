package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.database.*;
public class AppNameActivity extends AppCompatActivity {
    public SQLiteHelper database                =new SQLiteHelper(AppNameActivity.this);
    SQLiteDatabase      db                      ;
    HashMap<String, Integer> packageName_Map    =new HashMap<String, Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_name);

        db=database.getWritableDatabase();
        Cursor cursor       =db.rawQuery("select* from app",null);
        List<String> appName_Database       =new ArrayList<>();
        List<String> packageName_Database   =new ArrayList<>();
        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();
            for(int i=0;i<cursor.getCount();i++) {
                String appName_cursor = cursor.getString(cursor.getColumnIndex("appname"));
                String packageName_cursor = cursor.getString(cursor.getColumnIndex("packagename"));
                packageName_Map.put(packageName_cursor,1);
                appName_Database.add(appName_cursor);
                packageName_Database.add(packageName_cursor);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();

        Context context =getApplicationContext();
        PackageManager packageManager = context.getPackageManager();//获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);//用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<>();//从pinfo中将包名字逐一取出，压入pName list中
        List<String> appNames     = new ArrayList<>();
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                String appName  = packageInfos.get(i).applicationInfo.loadLabel(getPackageManager()).toString();
                packageNames.add(packName);
                appNames.add(appName);
            }
        }
        db=database.getWritableDatabase();
        for(int i=0;i<packageNames.size();i++)
        {
            if(!packageName_Map.containsKey(packageNames.get(i))) {
                ContentValues values = new ContentValues();
                values.put("appname", appNames.get(i));
                values.put("packagename", packageNames.get(i));
                db.insert("app", null, values);
                values.clear();
            }
        }
        db.close();
        ListView listView = (ListView) findViewById(R.id.listView);//得到ListView对象的引用
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, appName_Database));

    }
}