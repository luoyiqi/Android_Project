package com.cx.httpdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by xiao on 2016-03-10.
 */
public class MainActivity extends Activity {
   private Handler handler=new Handler(){
       @Override
       public void handleMessage(Message msg) {
           Toast.makeText(MainActivity.this,"下载完成",Toast.LENGTH_SHORT).show();
       }
   };
  private TextView textView;
    private ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        String path = "http://bcscdn.baidu.com/netdisk/BaiduYun_7.11.7.apk";
        Download download = new Download(path);
        download.down();
        handler.sendEmptyMessage(1);

    }


}
