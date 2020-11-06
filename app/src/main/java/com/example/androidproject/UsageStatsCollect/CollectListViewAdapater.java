package com.example.androidproject.UsageStatsCollect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidproject.R;

import java.util.List;

public class CollectListViewAdapater extends ArrayAdapter {
    public CollectListViewAdapater(Context context, int resource, List<CollectStatics> objects) {
        super(context, resource, objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CollectStatics linkMain = (CollectStatics) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.collect_listview, null);

        TextView packagename        = (TextView)view.findViewById(R.id.packagename);
        TextView time               = (TextView)view.findViewById(R.id.time);
        ImageView icon              = (ImageView)view.findViewById(R.id.icon);
        packagename.setText(linkMain.getPackageName());
        time.setText(linkMain.getTime());
        icon.setImageDrawable(linkMain.getIcon());
        return view;
    }


}

