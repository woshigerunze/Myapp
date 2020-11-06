package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.androidproject.BatteryCollect.BatteryMainActivity;
import com.example.androidproject.UsageStatsCollect.CollectActivity;
import com.example.androidproject.UsageStatsCollect.CollectService;

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
        Charger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, BatteryMainActivity.class);
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