package com.sunhuwh.music.service;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.sunhuwh.music.http.HttpClientUtils;
import com.sunhuwh.music.http.HttpRequester;
import com.sunhuwh.music.util.AppContext;
import com.sunhuwh.music.util.HttpUtilsJDK;

import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpResponse;

/**
 * Created by hu.sun on 2017/2/20.
 */

public class MusicService  extends HttpRequester<Map>{
    private static AsyncHttpClient client = new AsyncHttpClient();
    public void list(String word, final HttpRequester.ModelListResponseHandler handler,
                          final HttpRequestFailureHandler failureHandler){
        String url = "http://sug.music.baidu.com/info/suggestion?format=json&word="+word+"&version=2&from=0&third_type=0&client_type=0&_="+System.currentTimeMillis();
        System.out.print(url);
        //HttpClientUtils.get(getUrl(url), new ModelListHttpResponseHandler(handler, failureHandler));


        super.list(url, handler, failureHandler);
    }

    public void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }

    public List list(String word){
        Gson gson = new Gson();
        String url = "http://sug.music.baidu.com/info/suggestion?format=json&word="+word+"&version=2&from=0&third_type=0&client_type=0&_="+System.currentTimeMillis();
        System.out.print(url);
        String result = HttpUtilsJDK.performGetRequest(url);
        String jsonResult = gson.toJson(result);
        Map mapRes = gson.fromJson(jsonResult, Map.class);
        if(mapRes.get("errno")!=null){
            return null;
        }
        Map data = (Map)mapRes.get("data");
        if(data.get("song")==null||((List)data.get("song")).isEmpty()){
            return null;
        }
        List<Map> songs = (List)data.get("song");
        for(int i = songs.size()-1;i>=0;i--){
            Map song = songs.get(i);
            String id = String.valueOf(song.get("songid"));
            //String songurl = getUrlById(id);
            //song.put("downUrl", url);
        }
        return songs;

//        return songs.stream().filter(song->{
//            String id = String.valueOf(song.get("songid"));
//            String url = getUrlById(id);
//            song.put("downUrl", url);
//            return !Strings.isNullOrEmpty(url);
//        }).collect(Collectors.toList());


        //RequestHandle requestHandle = client.get(url, null);


        //HttpClientUtils.get(getUrl(url), new ModelListHttpResponseHandler(handler, failureHandler));


        //super.list(url, handler, failureHandler);
    }



    private List mock(){
        return null;
    }

//    private List findMusicByBaiDu(String word) {
//        Map result =restTemplate.getForObject("http://sug.music.baidu.com/info/suggestion?format=json&word={word}&version=2&from=0&third_type=0&client_type=0&_={time}", Map.class, word, System.currentTimeMillis());
//        if(result.get("errno")!=null){
//            return null;
//        }
//        Map data = (Map)result.get("data");
//        if(data.get("song")==null||((List)data.get("song")).isEmpty()){
//            return null;
//        }
//        List<Map> songs = (List)data.get("song");
//        return songs.stream().filter(song->{
//            String id = String.valueOf(song.get("songid"));
//            String url = getUrlById(id);
//            song.put("downUrl", url);
//            return !Strings.isNullOrEmpty(url);
//        }).collect(Collectors.toList());
//    }


}
