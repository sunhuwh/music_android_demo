package com.sunhuwh.music.exception;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by junxue.rao on 2016/2/20.
 * crash 处理器
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private Context context;

    private Thread.UncaughtExceptionHandler defaultHandler;

    /**
     * 全局唯一单例
     */
    private static CrashHandler instance;

    /**
     * 初始化CrashHandler
     * @param context
     */
    public void init(Context context){
        this.context = context;
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.i("crash", "wow,crash.", ex);
        Toast.makeText(context, "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_LONG).show();
        defaultHandler.uncaughtException(thread,ex);
    }

    /**
     * 注册该crash处理器
     * @param context
     * @return
     */
    public static void register(Context context){
        if(instance == null){
            instance = new CrashHandler();
            instance.init(context);
        }
    }
}
