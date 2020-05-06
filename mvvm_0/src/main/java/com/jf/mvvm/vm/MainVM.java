package com.jf.mvvm.vm;

import android.util.Log;
import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.jf.mvvm.BR;
import com.jf.mvvm.presenter.MainPresenter;

/**
 * @Discribe: com.jf.mvvm.vm
 * @Time: 2020/4/30
 * @Author: Yinhao
 * 继承BaseObservable 是为了方便动态通知databinding更新页面数据，
 * 当然第一次入手，这个不写也可以跟页面互动，只不过没办法通知页面更新
 */
public class MainVM extends BaseObservable {

    //通过MainPresenter可以跟数据库以及后台做通讯操作，用于更新UI数据
    private MainPresenter mainPresenter = new MainPresenter();
    // 布局文件可以直接读取该属性值
    @Bindable
    public String title2 = "这里的值也可以显示";

    @Bindable
    public String getTitle(){
        if(!"这里的值也可以显示".equals(title2)){
            return "我的值也一起变吧！";
        }
        return "这是textView显示的内容";
    }

    public void onTestClick(View view){
        title2 = "我的值改变了！";
        Log.d("mvvm","onTestClick >>> "+title2 + " >>> "+getTitle());
        notifyPropertyChanged(BR.title);
        notifyPropertyChanged(BR.title2);
    }

}
