package com.sunhuwh.music.http;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.loopj.android.http.SyncHttpClient;

import cz.msebera.android.httpclient.Header;

/**
 * Created by sunlin on 2016/8/27.
 */
public class HttpClientUtils {
    private static String sessionId = null;
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static PersistentCookieStore cookieStore ;


    SyncHttpClient s;
    static {
        //设置网络超时时间
        client.setTimeout(5000);
    }

    public static void get(String url, AsyncHttpResponseHandler responseHandler) {
        client.get(url, responseHandler);
    }

    public static void get(Context context, String url, ResponseHandlerInterface responseHandler) {
        client.get(context, url, responseHandler);

    }

    public static void get(String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        client.get(url, params, responseHandler);
    }

    public static void delete(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.delete(url,params,responseHandler);
    }

    public static void get(Context context, String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        client.get(context, url, params, responseHandler);
    }

    public static void get(Context context, String url, Header[] headers, RequestParams params, ResponseHandlerInterface responseHandler) {
        client.get(context, url, headers, params, responseHandler);

    }

    public static void post(String url,RequestParams params, ResponseHandlerInterface responseHandler){
        client.post(url, params, responseHandler);
    }

    public static void put(String url,RequestParams params, AsyncHttpResponseHandler responseHandler){
        client.put(url, params, responseHandler);
    }


    public static AsyncHttpClient getClient(){
        return client;
    }

    public static String getSessionId() {
        return sessionId;
    }

    public static void setSessionId(String sessionId) {
        HttpClientUtils.sessionId = sessionId;
    }

    public static PersistentCookieStore getCookieStore() {
        return cookieStore;
    }

    public static void setCookieStore(PersistentCookieStore cookieStore) {
        HttpClientUtils.cookieStore = cookieStore;
        client.setCookieStore(cookieStore);
    }


}
