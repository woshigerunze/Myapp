package com.example.androidproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Toast.makeText(context,"boot complete",Toast.LENGTH_LONG).show();
        Log.d("wocaonima","wocaonima");
        throw new UnsupportedOperationException("Not yet implemented");
    }
}