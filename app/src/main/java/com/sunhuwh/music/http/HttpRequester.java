package com.sunhuwh.music.http;

import android.util.Log;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.sunhuwh.music.util.AppContext;
import com.sunhuwh.music.util.Constants;
import com.sunhuwh.music.util.SHAUtils;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by sunlin on 2016/8/26.
 */
public abstract class HttpRequester<T> {

    public interface HttpRequestFailureHandler {
        void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable);
    }

    public interface ModelListResponseHandler<T>{
        void onSuccess(List<T> list);
    }

    public interface TextResponseHandler{
        void onSuccess(String resp);
    }

    public interface ModelResponseHandler<T>{
        void onSuccess(T model);
    }

    public Class<T> getTClass()
    {
        Class<T> tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return tClass;
    }

    public void list(String url, final ModelListResponseHandler<T> handler, final HttpRequestFailureHandler failureHandler){
        get(url, new ModelListHttpResponseHandler(handler, failureHandler));
    }

    public void get(String url, final ModelResponseHandler<T> handler, final HttpRequestFailureHandler failureHandler){
        get(url, new ModelHttpResponseHandler(handler, failureHandler));
    }

    public void post(String url, RequestParams params, final ModelResponseHandler<T> handler, final HttpRequestFailureHandler failureHandler){
        post(url, params, new ModelHttpResponseHandler(handler, failureHandler));
    }

    /**
     * 处理模型列表
     */
    public class ModelListHttpResponseHandler extends TextHttpResponseHandler{

        private ModelListResponseHandler<T> successHandler;
        private HttpRequestFailureHandler failureHandler;
        public ModelListHttpResponseHandler(final ModelListResponseHandler<T> handler, final HttpRequestFailureHandler failureHandler){
            this.successHandler = handler;
            this.failureHandler = failureHandler;
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable
                throwable) {
            if(failureHandler != null){
                failureHandler.onFailure(statusCode, headers, responseString, throwable);
            }
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, String responseString) {
            Log.i("HTTP RESP", responseString);
            try {
                ObjectMapper mapper = new ObjectMapper();
                JavaType listType = mapper.getTypeFactory().constructParametrizedType(ArrayList.class, ArrayList.class, getTClass());
                List<T> list =  (List<T>)mapper.readValue(responseString, listType);
                if(successHandler != null){
                    successHandler.onSuccess(list);
                }
            } catch (IOException e) {
                if(failureHandler != null){
                    failureHandler.onFailure(statusCode, headers, responseString, e);
                }
            }
        }
    }

    /**
     * 处理模型实体返回
     */
    private class ModelHttpResponseHandler extends TextHttpResponseHandler{

        private ModelResponseHandler<T> successHandler;
        private HttpRequestFailureHandler failureHandler;
        public ModelHttpResponseHandler(final ModelResponseHandler<T> handler, final HttpRequestFailureHandler failureHandler){
            this.successHandler = handler;
            this.failureHandler = failureHandler;
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable
                throwable) {
            if(failureHandler != null){
                failureHandler.onFailure(statusCode, headers, responseString, throwable);
            }
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, String responseString) {
            Log.i("HTTP RESP", responseString);
            try {
                ObjectMapper mapper = new ObjectMapper();
                T model =  (T)mapper.readValue(responseString, getTClass());
                if(successHandler != null){
                    successHandler.onSuccess(model);
                }
            } catch (IOException e) {
                if(failureHandler != null){
                    failureHandler.onFailure(statusCode, headers, responseString, e);
                }
            }
        }
    }

    public class TextHttpResponseHandlerEx extends TextHttpResponseHandler{

        private TextResponseHandler successHandler;
        private HttpRequestFailureHandler failureHandler;

        public TextHttpResponseHandlerEx(TextResponseHandler handler, HttpRequestFailureHandler failureHandler){
            this.successHandler = handler;
            this.failureHandler = failureHandler;
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable
                throwable) {
            if(failureHandler != null){
                failureHandler.onFailure(statusCode, headers, responseString, throwable);
            }
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, String responseString) {
            Log.i("HTTP RESP", responseString);
            if(successHandler != null){
                successHandler.onSuccess(responseString);
            }
        }
    }

    /**
     * 增加验证信息，发送请求
     * @param url
     * @param responseHandler
     */
    public static void get(String url, AsyncHttpResponseHandler responseHandler){
        Log.i("HTTP GET", getUrl(url));
        //bindAuthData();
        HttpClientUtils.get(getUrl(url), responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler){
        Log.i("HTTP POST", getUrl(url));
        bindAuthData();
        HttpClientUtils.post(getUrl(url), params, responseHandler);
    }

    public static void put(String url, RequestParams params, AsyncHttpResponseHandler responseHandler){
        Log.i("HTTP PUT", getUrl(url));
        bindAuthData();
        HttpClientUtils.put(getUrl(url), params, responseHandler);
    }

    private static void bindAuthData(){
        AppContext appContext = AppContext.getInstance();
        String uid = appContext.getAttribute(Constants.UID);
        String token = appContext.getAttribute(Constants.TOKEN);
        String timestamp = String.valueOf(new Date().getTime());
        String encryptedMessage = SHAUtils.SHA1(uid+token+ timestamp);
        HttpClientUtils.getClient().addHeader("X-Uid", uid);
        HttpClientUtils.getClient().addHeader("X-Timestamp",timestamp);
        HttpClientUtils.getClient().addHeader("X-EncryptedMessage",encryptedMessage);
    }

    /**
     * 获取完全地址
     * @param path
     * @return
     */
    public static String getUrl(String path){
        String httpUrl =null;
        if(path.startsWith("http://")||path.startsWith("https://")){
            return path;
        }else{
            return HttpConstants.API_URL + path;
        }
    }

}
