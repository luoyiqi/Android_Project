package com.cx.httpdemo;

import android.os.Environment;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by xiao on 2016-03-10.
 */
class MyThread extends Thread {
    private int beginIndex;
    private int endIndex;
    private String filePath;
    private String fileName;
    private int size;
    public MyThread(int beginIndex, int endIndex, String filePath,String fileName) {
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
        this.filePath = filePath;
        this.fileName =fileName;
    }
   public int getSize(){
	   return size;
   }
    @Override
    public void run() {
        try {
            URL url = new URL(filePath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(8000);
            conn.setReadTimeout(5000);
            conn.setRequestProperty("Range", "bytes=" + beginIndex + "-" + endIndex);
            if (conn.getResponseCode() == 206) {
                InputStream is = conn.getInputStream();
                File file = new File(Environment.getExternalStorageDirectory(),fileName);
                byte[] b = new byte[1024];
                int len = 0;
                RandomAccessFile raf = new RandomAccessFile(file, "rwd");
                raf.seek(beginIndex);
                while ((len = is.read(b)) != -1) {
                    raf.write(b, 0, len);
                    size+=len;
                }
                raf.close();
            }
        } catch (Exception e) {
        }

    }
}
