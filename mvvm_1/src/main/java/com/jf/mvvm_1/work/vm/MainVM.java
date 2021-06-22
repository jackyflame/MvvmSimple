package com.jf.mvvm_1.work.vm;

import android.util.Log;
import android.view.View;

import androidx.databinding.Bindable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jf.mvvm_1.BR;
import com.jf.mvvm_1.mvvm.vm.BaseVM;
import com.jf.mvvm_1.work.model.MainViewModel;

/**
 * @Class: MainVM
 * @Description:
 * @author: github.com/jackyflame
 * @Date: 2021/6/22
 */
public class MainVM extends BaseVM {

    //通过MainPresenter可以跟数据库以及后台做通讯操作，用于更新UI数据
    private MainViewModel mainPresenter = new MainViewModel();
    // 布局文件可以直接读取该属性值
    public MutableLiveData<String> title2 = new MutableLiveData<>("这里的值也可以显示");

    public String getTitle(){
        if(!"这里的值也可以显示".equals(title2.getValue())){
            return "我的值也一起变吧！";
        }
        return "这是textView显示的内容";
    }

    public void onTestClick(View view){
        title2.setValue("我的值改变了！");
        Log.d("mvvm","onTestClick >>> "+title2 + " >>> "+getTitle());
    }

}
