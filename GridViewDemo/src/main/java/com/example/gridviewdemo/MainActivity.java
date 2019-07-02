package com.example.gridviewdemo;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author DELL
 */
public class MainActivity extends Activity {
    private String ITEM_IAMGE = "item_image";
    private String ITEM_TEXT = "item_text";
    private GridView gvDemo;
    String[] from = {"img", "text"};
    List<Map<String, Object>> data;
    int[] arrImages = new int[]{
            R.mipmap.ic_home_one, R.mipmap.ic_home_two, R.mipmap.ic_home_three,
            R.mipmap.ic_home_four, R.mipmap.ic_home_five, R.mipmap.ic_home_seven,
            R.mipmap.ic_home_eight, R.mipmap.ic_home_nine, R.mipmap.ic_home_life
    };
    String[] arrText = new String[]{
            "账户充值", "订单查询", "集团理财", "差旅出行", "公务预约",
            "公共服务", "财务报销", "罚款缴纳", "生活缴费"
    };
    private TextView tvProgress;
    private double valueSize;
    private ProgressBar pb;
    private FileOutputStream fos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initID();
        initData();
    }

    private List<Map<String, Object>> getData() {
        data = new ArrayList<>();
        for (int i = 0; i < arrImages.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("img", arrImages[i]);
            map.put("text", arrText[i]);
            data.add(map);
        }
        return data;
    }

    private void initData() {
        gvDemo.setAdapter(new SimpleAdapter(this, getData(), R.layout.gv_item, from, new int[]{R.id.ItemImage, R.id.ItemText}));
    }

    private void initID() {
        gvDemo = findViewById(R.id.gv_demo);
        tvProgress = findViewById(R.id.tv_progress);
        pb = findViewById(R.id.pb);
        pb.setMax(100);
        gvDemo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                downSings("http://47.95.5.143:8043/group/api/download/export/100000001304454/1.4/member","真的爱你","郑源");
                new Thread() {
                    @Override
                    public void run() {
                        downloadFile1();
                    }
                }.start();
            }
        });
    }


    public void downSings(String sing_play_url, String singer_name, String sing_name) {
        //创建下载任务,downloadUrl就是下载链接
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(sing_play_url));
        //指定下载路径和下载文件名
        request.setDestinationInExternalPublicDir("/Music_download/", sing_name + "_" + singer_name + ".apk");
        //获取下载管理器
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        if (new File(Environment.getExternalStorageDirectory() + "/Music_download/", sing_name + "_" + singer_name + ".apk").exists()) {
            ShowSingExits("歌曲已存在" + Environment.getExternalStorageDirectory() + "/Music_download/文件夹中" + "无需下载");
            return;
        } else {
            ShowSingExits("歌曲正在下载中,请到" + Environment.getExternalStorageDirectory() + "/Music_download/文件夹中查看！");
            //将下载任务加入下载队列，否则不会进行下载
            downloadManager.enqueue(request);
        }
    }

    /**
     * 进行下载状态提示的方法
     *
     * @param str 提示的信息
     */
    private void ShowSingExits(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();
            }
        });
    }


    public void downloadFile1() {
        try {
            //下载路径，如果路径无效了，可换成你的下载路径
//            String url = "http://47.95.5.143:8043/group/api/download/export/100000001304454/1.4/member";
//            String url = "http://shouji.360tpcdn.com/181101/06a1b3f3b9c2bf39c81ec87a04f4a89a/com.wochacha_9580.apk";
            String url = "http://shouji.360tpcdn.com/180912/f5cebef6995f7c4841aa35bbfef99ce5/imoblife.toolbox.full_150208.apk";
            String path = Environment.getExternalStorageDirectory().getAbsolutePath();

            final long startTime = System.currentTimeMillis();
            Log.i("DOWNLOAD", "startTime=" + startTime);
            //下载函数
            String filename = url.substring(url.lastIndexOf("/") + 1);
            //获取文件名
            URL myURL = new URL(url);
            URLConnection conn = myURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            //根据响应获取文件大小
            int fileSize = conn.getContentLength();
            Log.e("文件大小", "" + fileSize);
            if (fileSize <= 0) {
                throw new RuntimeException("无法获知文件大小 ");
            }
            if (is == null) {
                throw new RuntimeException("stream is null");
            }
            File file1 = new File(path);
            if (!file1.exists()) {
                file1.mkdirs();
            }
            //把数据存入路径+文件名
            if (filename.endsWith(".apk")) {
                fos = new FileOutputStream(new File(path + "/" + filename));
            } else {
                fos = new FileOutputStream(new File(path + "/" + filename + ".apk"));
            }
            byte buf[] = new byte[1024];
            int downLoadFileSize = 0;
            do {
                //循环读取
                int numread = is.read(buf);
                if (numread == -1) {
                    break;
                }
                fos.write(buf, 0, numread);
                downLoadFileSize += numread;
                valueSize = ((double) downLoadFileSize / (double) fileSize);
                for (int i = 0; i < 100; i++) {
                    if (valueSize * 100 > i) {
                        Log.e("下载进度：", String.valueOf((int) (valueSize * 100)) + "%");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvProgress.setText("下载耗时：" + (System.currentTimeMillis() - startTime) / 1000 / 60 + "分钟"
                                        + (System.currentTimeMillis() - startTime) / 1000 % 60 + "秒" + "\n下载进度："
                                        + String.valueOf((int) (valueSize * 100)) + "%");
                                pb.setProgress((int) (valueSize * 100));
                            }
                        });
                    }

                }
                //更新进度条
            } while (true);

            Log.e("DOWNLOAD", "下载成功！");
            Log.e("DOWNLOAD", "totalTime=" + (System.currentTimeMillis() - startTime));
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "下载成功！耗时：" +
                            (System.currentTimeMillis() - startTime) / 1000 / 60 + "分钟" +
                            (System.currentTimeMillis() - startTime) / 1000 % 60 + "秒", Toast.LENGTH_LONG).show();
                }
            });
            is.close();
        } catch (Exception ex) {
            Log.e("DOWNLOAD", "error: " + ex.getMessage(), ex);
        }
    }
}
