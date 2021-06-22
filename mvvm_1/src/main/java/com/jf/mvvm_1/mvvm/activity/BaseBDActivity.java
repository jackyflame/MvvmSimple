package com.jf.mvvm_1.mvvm.activity;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jf.mvvm_1.BR;
import com.jf.mvvm_1.mvvm.base.BaseActivity;
import com.jf.mvvm_1.mvvm.vm.BaseVM;

import java.lang.reflect.ParameterizedType;

/**
 * @Class: BaseBDActivity
 * @Description:
 * @author: github.com/jackyflame
 * @Date: 2021/6/22
 */
public abstract class BaseBDActivity<T extends ViewDataBinding,X extends ViewModel> extends BaseActivity {

    protected T binding;
    protected X viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindLayout();
    }

    protected void bindLayout(){
        bindLayout(getViewModelClass());
    }

    protected void bindLayout(Class<X> clzVM){
        bindLayout(new ViewModelProvider(this).get(clzVM));
    }

    protected void bindLayout(X vm){
        viewModel = vm;
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        binding.setLifecycleOwner(this);
        binding.setVariable(BR.viewModel,vm);
    }

    public abstract int getLayoutId();

    //protected abstract Class<? extends ViewModel> getViewModelClass();

    public Class<X> getViewModelClass() {
        Class<X> xClass = (Class<X>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        return xClass;
    }

}