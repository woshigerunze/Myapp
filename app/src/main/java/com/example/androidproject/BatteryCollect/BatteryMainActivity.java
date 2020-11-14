package com.example.androidproject.BatteryCollect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.BatteryManager;
import android.os.Bundle;
import com.example.androidproject.R;

public class BatteryMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_main);
        BatteryManager manager = (BatteryManager) getSystemService(BATTERY_SERVICE);
        manager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER);
        manager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE);
        manager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
        manager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);///当前电量百分比
    }
}