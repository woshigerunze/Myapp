package com.example.androidproject.Login;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.androidproject.R;
public class SecondFragment extends Fragment {
    @Override
   public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                return inflater.inflate(R.layout.page_02, container, false);
           }
}
