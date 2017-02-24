package com.sunhuwh.music;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sunhuwh.music.task.NetworkAsyncTask;

import java.io.File;


public class MainActivity  extends AppCompatActivity{

    private Button searchButton = null;
    private EditText editText = null;
    private String searchText = null;

    @Override
    public File getFilesDir() {
        return super.getFilesDir();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchButton = (Button)findViewById(R.id.searchMusic);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                editText = (EditText) findViewById(R.id.search_text);
                searchText = editText.getText().toString().trim();
                if (searchText != null && searchText.length() != 0) {
                    String path = "http://sug.music.baidu.com/info/suggestion?format=json&word="+searchText+"&version=2&from=0&third_type=0&client_type=0&_="+System.currentTimeMillis();
                    NetworkAsyncTask networkAsyncTask = new NetworkAsyncTask(MainActivity.this, MainActivity.this);
                    networkAsyncTask.execute("NETWORK_GET", path);
                }
                Toast.makeText(MainActivity.this, "searching...", Toast.LENGTH_LONG).show();
            }
        });
    }

}
