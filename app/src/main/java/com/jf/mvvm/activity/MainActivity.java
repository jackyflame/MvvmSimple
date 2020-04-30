package com.jf.mvvm.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.jf.mvvm.R;
import com.jf.mvvm.databinding.ActivityMainBinding;
import com.jf.mvvm.vm.MainVM;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.setViewModel(new MainVM());
    }
}
