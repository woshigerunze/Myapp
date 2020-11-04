package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.androidproject.collectlistview.CollectActivity;
import com.example.androidproject.collectlistview.CollectService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public Intent intent ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button AppName      =(Button)findViewById(R.id.button);
        Button Charger      =(Button)findViewById(R.id.button2);
        Button Time         =(Button)findViewById(R.id.button3);
        intent=new Intent(this, CollectService.class);
        startService(intent);
        AppName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AppNameActivity.class);
                startActivity(intent);
            }
        });
        Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, CollectActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        stopService(intent);
    }
}