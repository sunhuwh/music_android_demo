package com.sunhuwh.music.util;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

import com.sunhuwh.music.exception.CrashHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by junxue.rao on 2016/2/15.
 * 创建全局应用实例方便使用
 */
public class AppContext extends Application{

    /**
     * 全局单例的application对象
     */
    private static AppContext instance;

    /**
     * 登录sessionId
     */
    private String sessionId;


    /**
     * 全局的动态属性
     */
    private static final Map<String,Object> attributes = new ConcurrentHashMap<String,Object>();


    /**
     * sd卡根目录
     */
    private static final String appRootDir = "music_demo";

    /**
     * 缓存目录
     */
    private String cachePath;

    /**
     * 临时目录
     */
    private String tempPath;

    public static AppContext getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initSdFolder();
        instance = this;//初始化全局单例的application对象
        CrashHandler.register(this);
    }

    private void initSdFolder(){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            cachePath = this.getApplicationContext().getExternalFilesDir("cache").getAbsolutePath();
            tempPath = this.getApplicationContext().getExternalCacheDir().getAbsolutePath();
//            File rootPathFile = new File(cachePath);
//            if (!rootPathFile.exists()) {
//                rootPathFile.mkdirs();
//            }
        }
    }

    public String getCachePath(){
        return cachePath;
    }

    public String getTempPath(){
        return tempPath;
    }

    /**
     * 增加属性
     * @param key
     * @param value
     */
    public void putAttribute(Constants key,Object value){
        if(value != null){
            attributes.put(key.getCode(), value);
        }
    }

    /**
     * 获取属性
     * @param key
     * @param <T>
     * @return
     */
    public <T> T getAttribute(Constants key){
        if(attributes.containsKey(key.getCode())){
            return (T) attributes.get(key.getCode());
        }
        return null;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * 获取分销商id
     * @return
     */
    public String getDistributorId(){
        String distributorId = getAttribute(Constants.DISTRIBUTOR_ID);
        if(distributorId == null){
            return "1";
        }else{
            return distributorId;
        }
    }

    /**
     * 已登录
     * @return
     */
    public boolean isLogined(){
        return getAttribute(Constants.TOKEN) != null;
    }

    public void clear(){
        this.getAttributes().clear();
    }

    public boolean isWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }



}
