package com.jf.mvvm_1.work.activity;

import com.jf.mvvm_1.R;
import com.jf.mvvm_1.databinding.ActivityMainBinding;
import com.jf.mvvm_1.mvvm.activity.BaseBDActivity;
import com.jf.mvvm_1.work.vm.MainVM;

public class MainActivity extends BaseBDActivity<ActivityMainBinding,MainVM> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

}
