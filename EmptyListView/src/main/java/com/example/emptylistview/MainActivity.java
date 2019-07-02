package com.example.emptylistview;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author DELL
 */
public class MainActivity extends ListActivity {

    private ListView lvData;
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            String data = (String) msg.obj;
            try {
                JSONObject jo = new JSONObject(data);
                if (jo.getBoolean("usable")) {
                    lvData.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.listview_item, new String[]{"第一行", "第二行"}));
                } else {
                    Toast.makeText(getApplicationContext(), "没有数据", Toast.LENGTH_SHORT).show();
                    TextView emptyView = new TextView(getApplicationContext());
                    emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
                    emptyView.setText("没有数据1");
                    emptyView.setVisibility(View.GONE);
                    ((ViewGroup) lvData.getParent()).addView(emptyView);
                    emptyView.setTextColor(getResources().getColor(R.color.colorAccent));
                    lvData.setEmptyView(emptyView);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initID();
        initData();
    }

    private void initData() {
        takeNetworkData("http://www.wanandroid.com/tools/mockapi/3191/switch", "utf-8");
    }

    private void initID() {
        lvData = findViewById(android.R.id.list);
    }

    /**
     * 使用get方式与服务器通信
     *
     * @param newsUrl 网址
     * @param code    编码
     */
    public void takeNetworkData(final String newsUrl, final String code) {

        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(newsUrl);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setReadTimeout(20000);
                    urlConnection.setConnectTimeout(20000);
                    if (urlConnection.getResponseCode() == 200) {
                        //4.读流，获取源码内容
                        InputStream inputStream = urlConnection.getInputStream();
                        //创建一个内存管道流,方便转string
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        byte[] buff = new byte[1024 * 10];
                        int len = 0;
                        while ((len = inputStream.read(buff)) > -1) {
                            out.write(buff, 0, len);
                            out.flush();
                        }
                        byte[] lens = out.toByteArray();
                        //将lens编码设为gbk即可解决乱码问题
                        String result = new String(lens, code);
                        out.close();
                        inputStream.close();
                        Message obtain = Message.obtain();
                        obtain.obj = result;
                        Log.e("打印数据", result);
                        handler.sendMessage(obtain);
                    } else {
                        Toast.makeText(MainActivity.this, "服务器返回异常:" + urlConnection.getResponseCode(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }.start();
    }
}

