package com.example.androidproject.Login;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.androidproject.R;

public class lifragment extends Fragment{
    ListView list;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.page_01,null);
        View view1=inflater.inflate(R.layout.bottom,null);
        view1.setVisibility(View.GONE);
        list=(ListView) view.findViewById(R.id.my_lv);

        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(),putData(),R.layout.listitem,
                new String[]{"name","pic"},new int[]{R.id.tv1,R.id.iv1});
        list.setAdapter(simpleAdapter);
        //list.setOnItemClickListener(this);//用来弹出一个悬浮条状图界面
        return view;

    }
    public List<Map<String,Object>> putData(){
        //这是主界面收集信息分类列表
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String,Object> map1 = new HashMap<String,Object>();
        map1.put("name", "已安装应用");
        map1.put("pic", R.drawable.timg);

        list.add(map1);

        return list;
    }
}
