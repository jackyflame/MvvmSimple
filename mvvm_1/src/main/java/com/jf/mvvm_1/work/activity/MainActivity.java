package com.jf.mvvm_1.work.activity;

import android.os.Bundle;

import androidx.lifecycle.Observer;

import com.jf.mvvm_1.R;
import com.jf.mvvm_1.databinding.ActivityMainBinding;
import com.jf.mvvm_1.mvvm.activity.BaseBDActivity;
import com.jf.mvvm_1.work.vm.MainVM;

public class MainActivity extends BaseBDActivity<ActivityMainBinding,MainVM> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.title2.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.txvTitle.setText(viewModel.getTitle());
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

}
