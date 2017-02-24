package com.sunhuwh.music;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {

    protected void showNetworkErrorNotice(){
        showText("网络异常，数据加载失败！");
    }

    protected void showText(String text){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

}
