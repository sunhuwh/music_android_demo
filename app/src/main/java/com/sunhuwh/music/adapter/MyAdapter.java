package com.sunhuwh.music.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.io.Files;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.sunhuwh.music.R;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.Duration;

/**
 * Created by hu.sun on 2017/2/22.
 */

public class MyAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Map<String, Object>> datas;
    private Activity activity;

    public MyAdapter(Context context, List<Map<String, Object>> datas, Activity activity) {
        this.mInflater = LayoutInflater.from(context);
        this.datas = datas;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }
    //****************************************final方法
    //注意原本getView方法中的int position变量是非final的，现在改为final
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        MyListener myListener=null;
        if (convertView == null) {

            holder=new ViewHolder();

            //可以理解为从vlist获取view  之后把view返回给ListView
            myListener=new MyListener(position);

            convertView = mInflater.inflate(R.layout.music_list_item, null);
            holder.title = (TextView)convertView.findViewById(R.id.title);
            holder.artistname = (TextView)convertView.findViewById(R.id.artistname);
            holder.viewBtn = (Button)convertView.findViewById(R.id.view_btn);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.title.setText((String)datas.get(position).get("songname"));
        holder.artistname.setText((String)datas.get(position).get("artistname"));
        holder.viewBtn.setTag(position);
        //给Button添加单击事件  添加Button之后ListView将失去焦点  需要的直接把Button的焦点去掉
        holder.viewBtn.setOnClickListener(myListener);
        return convertView;
    }

    private class MyListener implements View.OnClickListener {
        int mPosition;
        public MyListener(int inPosition){
            mPosition= inPosition;
        }
        @Override
        public void onClick(View v) {
            String songName = String.valueOf(datas.get(mPosition).get("songname"));
            Log.i("songname", songName);
            downloadMusic(String.valueOf(datas.get(mPosition).get("downloadUrl")), songName);
        }
    }

    private BaseDownloadTask createDownloadTask(String url, String llsApkFilePath) {
        boolean isDir = false;
        String path;
        path = llsApkFilePath;

        return FileDownloader.getImpl().create(url)
                .setPath(path, isDir)
                .setCallbackProgressTimes(300)
                .setMinIntervalUpdateSpeed(400)
                .setListener(new FileDownloadSampleListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        //super.pending(task, soFarBytes, totalBytes);
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        //super.progress(task, soFarBytes, totalBytes);
                        Log.i("progress","progress");
                        Toast.makeText(activity, "downloading", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        //super.error(task, e);
                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                        super.connected(task, etag, isContinue, soFarBytes, totalBytes);
                        Log.i("conn","conn");
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        //super.paused(task, soFarBytes, totalBytes);
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        //super.completed(task);
                        Log.i("complete","true");
                        Toast.makeText(activity, "complete download", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        //super.warn(task);
                    }
                });

    }

    private void downloadMusic(String downloadUrl, String musicName) {
        String folderPath = activity.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        FileDownloadUtils.setDefaultSaveRootPath(folderPath);
        String llsApkFilePath =FileDownloadUtils.getDefaultSaveRootPath()+"/"+musicName+".flc";
        createDownloadTask(downloadUrl, llsApkFilePath).start();
    }

    //提取出来方便点
    public final class ViewHolder {
        public TextView title;
        public TextView artistname;
        public TextView info;
        public Button viewBtn;
    }

}
