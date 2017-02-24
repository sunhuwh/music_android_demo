package com.sunhuwh.music;

import com.sunhuwh.music.http.HttpRequester;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class CustomToolbarActivity extends BaseActivity implements HttpRequester.HttpRequestFailureHandler{


    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
    }

    protected String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }


    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

    }
}
