package com.example.androidproject.UsageStatsCollect;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.androidproject.R;
import com.example.androidproject.SQLiteHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListViewItemActivity extends AppCompatActivity {
    String TAG="appusage";
    public SQLiteHelper database=new SQLiteHelper(ListViewItemActivity.this);
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"test");
        setContentView(R.layout.activity_list_view_item);
        ImageView icon          =(ImageView)findViewById(R.id.icon);
        TextView  appname       =(TextView)findViewById(R.id.textView);
        TextView  packagename   =(TextView)findViewById(R.id.textView2);
        Bundle bundle=getIntent().getExtras();
        String appName          =bundle.getString("appname");
        String packageName      =bundle.getString("packagename");
        appname.setText(appName);
        packagename.setText(packageName);
        db=database.getWritableDatabase();
        List<String> timestamp=new ArrayList<>();
        Cursor cursor=db.rawQuery("select timestamp from appuse where packagename=?",new String[]{packageName});
        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();
            for(int i=0;i<cursor.getCount();i++) {
                String timestamp_cursor = cursor.getString(cursor.getColumnIndex("timestamp"));
                Date date = new Date(Long.valueOf(timestamp_cursor));
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String res=sd.format(date);
                timestamp.add(res);
                cursor.moveToNext();
            }
        }
        cursor.close();
        ListView listView = (ListView) findViewById(R.id.list_view);//得到ListView对象的引用
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, timestamp));
    }
}