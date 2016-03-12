package com.cx.httpdemo;

import android.os.Environment;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by xiao on 2016-03-11.
 */
public class Download {
    private int filesize;
    private String filePath;
    public int getFileize(){
        return filesize;
    }
    public Download(String filePath) {
        this.filePath = filePath;
    }

    public void down() {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(filePath);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    conn.setReadTimeout(5000);
                    if (conn.getResponseCode() == 200) {
                        filesize  = conn.getContentLength();
                        URL u=conn.getURL();
                        String s=u.getFile();
                        s=s.substring(s.lastIndexOf('/')+1);
                        manyThreadDownload(filesize, getThreadNum(filesize),s);
                    }
                } catch (Exception e) {
                }
            }
        };
        t.start();
    }

    private void manyThreadDownload(int fileSize, int threadNum,String fileName) {
        int length = fileSize / threadNum;
        File file = new File(Environment.getExternalStorageDirectory(), fileName);
        try {
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.setLength(fileSize);
            raf.close();
            for (int i = 0; i < threadNum; i++) {
                int beginIndex = i * length;
                int endIndex = (i + 1) * length - 1;
                if (i == threadNum - 1) {
                    endIndex = fileSize - 1;
                }
                new MyThread(beginIndex, endIndex,filePath,fileName).start();
            }
        } catch (Exception e) {
        }
    }

    private int getThreadNum(int size) {
        size = size / (1024 * 1024);
        if (size < 1) return 1;
        if (size < 16) return size / 5;
        if (size < 32) return size / 8;
        if (size < 64) return size / 12;
        if (size < 128) return size / 20;
        if (size < 256) return size / 50;
        if (size < 512) return size / 80;
        if (size < 1024) return size / 100;
        return 10;
    }
}





