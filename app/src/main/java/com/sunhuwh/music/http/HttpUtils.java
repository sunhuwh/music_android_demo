package com.sunhuwh.music.http;

import android.util.Log;

import com.sunhuwh.music.util.AppContext;
import com.sunhuwh.music.util.Constants;
import com.sunhuwh.music.util.SHAUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by junxue.rao on 2016/2/29.
 * HTTP网络连接
 */
public class HttpUtils {

    private HttpURLConnection connection;

    /**
     * 服务器请求地址URL
     */
    //private static final String URL = "http://10.15.102.177:9090/";
    //public static final String URL = "http://mobile.renminyixue.com/";
    //public static final String URL = "http://192.168.81.24:8084/";

    public static final String URL = HttpConstants.API_URL;

    /**
     * GET请求
     */
    private static final String GET = "GET";

    /**
     * POST请求
     */
    private static final String POST = "POST";

    /**
     * 连接超时
     */
    private static final int CONNECTION_TIME_OUT = 15000;

    /**
     * 读超时
     */
    private static final int READ_TIME_OUT = 10000;

    /**
     * 编码
     */
    private static final String ENCODING = "UTF-8";

    /**
     * GET 请求
     * @param path  URL path地址
     * @return
     */
    public static String get(String path){
     return get(path,false);
    }

    /**
     * GET 请求
     * @param path  URL path地址
     * @return
     */
    public static String get(String path,boolean auth){
        HttpURLConnection connection = null;
        InputStream inputStream = null;

        String httpUrl =null;
        if(path.startsWith("http://")||path.startsWith("https://")){
            httpUrl = path;
        }else{
            httpUrl = URL + path;
        }
        Log.i("http",httpUrl);
        try {
            java.net.URL url = new URL(httpUrl);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod(GET);
            connection.setUseCaches(false);
            connection.setConnectTimeout(CONNECTION_TIME_OUT);
            connection.setReadTimeout(READ_TIME_OUT);
            connection.setDoInput(true);
            if(auth){
                AppContext appContext = AppContext.getInstance();
                String uid = appContext.getAttribute(Constants.UID);
                String token = appContext.getAttribute(Constants.TOKEN);
                String timestamp = String.valueOf(new Date().getTime());
                String encryptedMessage = SHAUtils.SHA1(uid+token+ timestamp);
                connection.setRequestProperty("X-Uid", uid);
                connection.setRequestProperty("X-Timestamp",timestamp);
                connection.setRequestProperty("X-EncryptedMessage",encryptedMessage);
            }
            int x = connection.getResponseCode();
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,ENCODING));
                StringBuffer response = new StringBuffer();
                String line = null;
                while((line = bufferedReader.readLine()) != null){
                    if(response.length() != 0) {
                        response.append("\r\n");
                    }
                    response.append(line);
                }
                Log.i("http get",response.toString());
                return response.toString();
            }else if(connection.getResponseCode() == HttpURLConnection.HTTP_UNAUTHORIZED){
                //AppContext.getInstance().goToLogin();
            }
        } catch (MalformedURLException e) {
            Log.e("http get",httpUrl,e);
        } catch (ProtocolException e) {
            Log.e("http get", httpUrl, e);
        } catch (IOException e) {
            Log.e("http get", httpUrl, e);
        } catch (Exception e) {
            Log.e("http get", httpUrl, e);
        }finally {
            if(connection != null){
                connection.disconnect();
                connection = null;
            }
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e("http get", httpUrl, e);
                }
            }
        }
        return null;
    }

    /**
     * POST 请求
     * @param path  URL path地址
     * @return
     */
    public static String post(String path,Map<String,String> params,boolean auth){
        if(params == null || params.isEmpty()){
            return get(path);
        }
        HttpURLConnection connection = null;
        String body = getParamsString(params);
        byte[] data = body.getBytes();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String httpUrl = URL + path;
        Log.i("http post",httpUrl);
        try {
            java.net.URL url = new URL(httpUrl);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod(POST);
            connection.setUseCaches(false);
            connection.setConnectTimeout(CONNECTION_TIME_OUT);
            connection.setReadTimeout(READ_TIME_OUT);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", String.valueOf(data.length));
            if(auth){
                AppContext appContext = AppContext.getInstance();
                String uid = appContext.getAttribute(Constants.UID);
                String token = appContext.getAttribute(Constants.TOKEN);
                String timestamp = String.valueOf(new Date().getTime());
                String encryptedMessage = SHAUtils.SHA1(uid+token+ timestamp);
                connection.setRequestProperty("X-Uid", uid);
                connection.setRequestProperty("X-Timestamp",timestamp);
                connection.setRequestProperty("X-EncryptedMessage",encryptedMessage);
            }
            outputStream = connection.getOutputStream();
            outputStream.write(data);
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,ENCODING));
                StringBuffer response = new StringBuffer();
                String line = null;
                while((line = bufferedReader.readLine()) != null){
                    response.append(line);
                }
                Log.i("http post",response.toString());
                return response.toString();
            }
        } catch (MalformedURLException e) {
            Log.e("http post",httpUrl,e);
        } catch (ProtocolException e) {
            Log.e("http post", httpUrl, e);
        } catch (IOException e) {
            Log.e("http post", httpUrl, e);
        }finally {
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    Log.e("http post", httpUrl, e);
                }
            }
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e("http post", httpUrl, e);
                }
            }
            if(connection != null){
                connection.disconnect();
                connection = null;
            }
        }
        return null;
    }

    /**
     * PUT请求
     * @param path
     * @param params
     * @param auth
     * @return
     */
    public static String put(String path,Map<String,String> params,boolean auth){
        params.put("_method", "put");
        return post(path, params, auth);
    }

    /**
     * 根据params拼出post的body部分
     * @param params
     * @return
     */
    private static String getParamsString(Map<String, String> params) {
        StringBuilder builder = new StringBuilder();
        Iterator<Map.Entry<String,String>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,String> iteratorItem = iterator.next();
            String key = iteratorItem.getKey();
            String value = iteratorItem.getValue();
            builder.append(key).append("=").append(value);
            if (iterator.hasNext()){
                builder.append("&");
            }
        }
        return builder.toString();
    }

}
