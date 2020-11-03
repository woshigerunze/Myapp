package com.example.androidproject.collectlistview;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidproject.R;

import java.util.HashMap;
import java.util.Map;

public class ListViewItemActivity extends AppCompatActivity {
    String TAG="appusage";
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
    }
}