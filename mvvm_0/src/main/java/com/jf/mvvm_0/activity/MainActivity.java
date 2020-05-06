package com.jf.mvvm_0.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.jf.mvvm_0.R;
import com.jf.mvvm_0.databinding.ActivityMainBinding;
import com.jf.mvvm_0.vm.MainVM;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.setViewModel(new MainVM());
    }
}
