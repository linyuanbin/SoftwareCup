package com.example.dtlp.user_main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.dtlp.Date.Picture;
import com.example.dtlp.Date.User;
import com.example.dtlp.MainActivity;
import com.example.dtlp.R;
import com.example.dtlp.start.loginActivity;
import com.example.dtlp.tap_fragment.fragment_push_image.utility.ImageSource;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 阳瑞 on 2017/6/25.
 */
public class activity_main_search extends Activity {
    /////////////////////////////
    private MaterialRefreshLayout materialRefreshLayout;
    /**
     * 用于展示照片墙的GridView
     */
    private GridView mPhotoWallView;
    /**
     * GridView的适配器
     */
    private activity_main_search_PhotosWallAdapterSearch mWallAdapter;

    private int mImageThumbSize;
    private int mImageThumbSpacing;

    //    private List<String> mImageUrlList = new ArrayList<>();
//
    private ImageSource imageSource;

    private boolean flag = true;
    private boolean flag1 = true;
    private boolean isSearch = true;
    String search = "";

    public static ArrayList ima = new ArrayList();
    public static ArrayList ima1 = new ArrayList();
    private int key = 12;
    private int value = 0;
    private String key1 = "";
    private int k = 0;
    private int j = 0;
    public static ArrayList imaID = new ArrayList();
    public static ArrayList imaID1 = new ArrayList();
    OkHttpClient okHttpClient = new OkHttpClient();
    String MarkName = "";
    ////////////////////////////////


    Handler handle = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            String data = (String) msg.obj;
            if (data.indexOf("<html>") != -1) {

            } else {
                Log.i("search", "data: = " + data);
                Type listType = new TypeToken<ArrayList<Picture>>() {
                }.getType();
                ArrayList<Picture> foos = new Gson().fromJson(data, listType);

                for (int i = 0; i < foos.size(); i++) {
                    ima.add(foos.get(i).getPAddress());
                    imaID.add(foos.get(i).getPID());
                    System.out.println("name [" + i + "] = " + foos.get(i).getPAddress());
                    Log.i("BBBBBBB_getid", "imaID = : " +foos.get(i).getPID());
                }
                for (int i = 0; i< imaID.size();i++)
                {
                    Log.i("BBBBBBB_id", "imaID = : " + imaID.get(i));
                }
                value = ima.size() / 12;
                Log.i("valuevaluevalue", "value = : " + value);
                if (ima.size() < key) {
                    ima1.addAll(ima);
                    imaID1.addAll(imaID);
                } else {
                    for (int i = 0; i < key; i++) {
                        ima1.add(ima.get(i));
                        imaID1.add(imaID.get(i));
                    }
                }
//                        if (j!=2){
                if (flag) {
                    ima.clear();
                    ima1.clear();
                    imaID.clear();
                    imaID1.clear();
                    flag = false;
                }
//                        }
                initEvent();
                try {
                    initData();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                mPhotoWallView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent intent = new Intent(view.getContext(), com.example.dtlp.tap_fragment.Image.class);
                        intent.putExtra("image", (String) ima1.get(position));
                        intent.putExtra("ID", (String) imaID1.get(position));
                        Log.i("BBBBBBB", " ima = " + (String) ima1.get(position));
                        Log.i("BBBBBBB", " ID = " + (String) imaID1.get(position));
                        startActivityForResult(intent, 0);


//                            startActivity(intent);
//                                Toast.makeText(activity_main_search.this, position + "", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        key1 = getIntent().getStringExtra("id");
        Log.i("key1", "key1=  " + key1);
        j = Integer.parseInt(key1);
        Log.i("key1", "key12=  " + j);
        switch (j)
        {
            case 1:
                search = getIntent().getStringExtra("search");
                Log.i("search", "search:=  " + search);
                initView();
                try {
                    flash2();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                initView();
                ima.clear();
                ima1.clear();
                imaID.clear();
                imaID1.clear();
                search = getIntent().getStringExtra("search");
                Log.i("search", "search:=  " + search);
                Thread mThread = new Thread() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = search;
                        handle.sendMessage(msg); //新建线程加载图片信息，发送到消息队列中
                    }
                };
                mThread.start();
                break;
        }

        materialRefreshLayout = (MaterialRefreshLayout)findViewById(R.id.activity_search_refresh);
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        flash();
//                        Toast.makeText(MainActivity.this, "111111", Toast.LENGTH_SHORT).show();
                        materialRefreshLayout.finishRefresh();
                    }
                }, 3000);
            }

            @Override
            public void onRefreshLoadMore(final MaterialRefreshLayout materialRefreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(MainActivity.this, "222222222", Toast.LENGTH_SHORT).show();
                        materialRefreshLayout.finishRefreshLoadMore();
                    }
                }, 3000);
            }
        });

    }
    public void initView() {
//        setContentView(R.layout.activity_main);
        mPhotoWallView = (GridView) findViewById(R.id.activity_search_id_photo_wall);
    }

    public void initEvent() {
        mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
        mImageThumbSpacing = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_spacing);
        // 监听获取图片的宽高
        mPhotoWallView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                // 计算列数
                final int numColumns = (int) Math.floor(mPhotoWallView.getWidth() / (mImageThumbSize + mImageThumbSpacing));
                if (numColumns > 0) {
                    int columnWidth = (mPhotoWallView.getWidth() / numColumns) - mImageThumbSpacing;
                    mWallAdapter.setItemSize(columnWidth);
                    mPhotoWallView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
    }


    public void initData() throws UnsupportedEncodingException {
        if (mWallAdapter == null) {
//            mWallAdapter = new PhotosWallAdapter(this,mImageUrlList, mPhotoWallView);
            mWallAdapter = new activity_main_search_PhotosWallAdapterSearch(activity_main_search.this, ima1, mPhotoWallView);
        }
        mPhotoWallView.setAdapter(mWallAdapter);
        imageSource = new ImageSource();
        mWallAdapter.notifyDataSetChanged();

//        if (j!=2) {
        if (flag1) {
            if(j==2) {
                Thread mThread = new Thread() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = search;
                        handle.sendMessage(msg); //新建线程加载图片信息，发送到消息队列中
                    }
                };
                mThread.start();
                flag1 = false;
            }else {
                flash2();
                flag1 = false;
            }
        }
//        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        MarkName = data.getStringExtra("a");
        String PID = data.getStringExtra("b");
        String imageUrl = data.getStringExtra("c");
//        String TabID =UserID+PID;
        switch (requestCode) {
            case 0:
                Log.i("EEEEEE_search", "info = " + PID);
                Log.i("EEEEEE_search", "textMain = " + MarkName);
                Log.i("EEEEEE_search", "imageUrl = " + imageUrl);
                if (!(MarkName.equals("") || MarkName.equals("null") || MarkName.toString().equals("标签"))) {
                    String name1 = "mark";
                    String key = "{\"state\":\"" + name1 + "\"," +
                            "\"UserID\":\"" + MainActivity.UserID + "\",\"PID\":\"" + PID + "\",\"MarkName\":\"" + MarkName + "\"}";
                    try {
                        URLDecoder.decode(key, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    RequestBody requestBody1 = RequestBody
                            .create(MediaType.parse("text/plain; charset=utf-8"), key);
                    Request.Builder builder3 = new Request.Builder();
                    Request request2 = builder3
                            .url(MainActivity.URL)
                            .post(requestBody1)
                            .build();

                    okhttp3.Call call1 = okHttpClient.newCall(request2);
                    call1.enqueue(new okhttp3.Callback() {
                        @Override
                        public void onFailure(okhttp3.Call call, IOException e) {
                            Log.i("info", " GET请求失败！！！");
                        }

                        @Override
                        public void onResponse(okhttp3.Call call, Response response) throws IOException {
                            final String res = response.body().string();
                            Log.i("info", " Push_labelres" + res);
                            if (res.indexOf("<html>") != -1) {

                            } else {
                                Gson gson = new Gson();
                                User user = gson.fromJson(res, User.class);
//                        user.getState();
                                if (user.getState().equals("true")) {
                                    //将用户积分也保存到本地
                                    MainActivity_2.Integral = user.getUserIntegral();
                                    File filename = new File(loginActivity.SDPATH + "dtlp/" + MainActivity.UserID + "/" + MainActivity.UserID + ".txt");
//                                ema = personal_information_email_text.getText().toString();
//                                当用户修改了自己的信息时 同时也对保存在手机上的文件进行修改
                                    try {
                                        BufferedReader br = new BufferedReader(new FileReader(
                                                filename));// 读取原始json文件
                                        StringBuilder user1 = new StringBuilder();
                                        String s = "";
                                        String ws = "";
                                        while ((s = br.readLine()) != null) {
                                            user1.append(s);
                                        }
                                        try {
//                                            JSONObject dataJson = new JSONObject(user.toString());// 创建一个包含原始json串的json对象
                                            JSONObject dataJson = new JSONObject(user1.toString());
                                            JSONObject jsonObject1 = dataJson.getJSONObject("userData");
                                            jsonObject1.put("UserIntegral", user.getUserIntegral());
                                            ws = dataJson.toString();
                                        } catch (JSONException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                        BufferedWriter bw = new BufferedWriter(new FileWriter(
                                                filename));// 输出新的json文件
                                        bw.write(ws);
                                        bw.flush();
                                        br.close();
                                        bw.close();

                                    } catch (IOException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
//                                Toast.makeText(mContext, "成功打好标签", Toast.LENGTH_SHORT).show();
//                                Activity activity = (Activity) getActivity();
//                                TextView integral = (TextView) activity.findViewById(R.id.integral);
//                                integral.setText(MainActivity_2.Integral+"");
//                                MainActivity_2.integral.setText(MainActivity_2.Integral);
                                } else {
                                    Log.i("info", " Push_标签添加失败了哦！！！");
                                }
                            }
                        }
                    });
                }
                break;

            default:
                break;
        }
    }
    public void flash2() throws UnsupportedEncodingException {
        String name1 = "request";

        String key = "{\"state\":\"search\",\"UserID\":\"" + MainActivity.UserID + "\",\"content\":\"" + search + "\"}";
        URLDecoder.decode(key, "utf-8");
        RequestBody requestBody1 = RequestBody
                .create(MediaType.parse("text/plain; charset=utf-8"), key);
        Request.Builder builder3 = new Request.Builder();
        Request request2 = builder3
                .url(MainActivity.URL)
                .post(requestBody1)
                .build();

        okhttp3.Call call1 = okHttpClient.newCall(request2);
        call1.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.i("info", " GET请求失败！！！");
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {

                final String res = response.body().string();
                Log.i("infoo", " Classify_GET请求成功！！！");

                Log.i("infoo", "Classify_res = " + res);
                if (res.equals("") || res.equals(null)) {

//                    new Thread(new Runnable() {
//                        public void run() {
//                            Toast.makeText(fragment_classify.this, "请", Toast.LENGTH_SHORT).show();
//                        }
//                    }).start();
                } else {
                    Thread mThread = new Thread() {
                        @Override
                        public void run() {
                            Message msg = new Message();
                            msg.what = 1;
                            msg.obj = res;
                            handle.sendMessage(msg); //新建线程加载图片信息，发送到消息队列中
                        }
                    };
                    mThread.start();
                }
            }
        });
    }
    public void flash() {
        k++;
        if (k <= value) {
            switch (k) {
                case 1:
                    ima1.clear();
                    imaID1.clear();
                    for (int i = key; i < ima.size(); i++) {
                        ima1.add(ima.get(i));
                        imaID1.add(imaID.get(i));
                    }
                    initEvent();
                    try {
                        initData();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    ima1.clear();
                    imaID1.clear();
                    for (int i = key * 2; i < ima.size(); i++) {
                        ima1.add(ima.get(i));
                        imaID1.add(imaID.get(i));
                    }
                    initEvent();
                    try {
                        initData();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    ima1.clear();
                    imaID1.clear();
                    for (int i = key * 3; i < ima.size(); i++) {
                        ima1.add(ima.get(i));
                        imaID1.add(imaID.get(i));
                    }
                    initEvent();
                    try {
                        initData();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    ima1.clear();
                    imaID1.clear();
                    for (int i = key * 4; i < ima.size(); i++) {
                        ima1.add(ima.get(i));
                        imaID1.add(imaID.get(i));
                    }
                    initEvent();
                    try {
                        initData();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
                case 5:
                    ima1.clear();
                    imaID1.clear();
                    for (int i = key * 5; i < ima.size(); i++) {
                        ima1.add(ima.get(i));
                        imaID1.add(imaID.get(i));
                    }
                    initEvent();
                    try {
                        initData();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
                case 6:
                    ima1.clear();
                    imaID1.clear();
                    for (int i = key * 6; i < ima.size(); i++) {
                        ima1.add(ima.get(i));
                        imaID1.add(imaID.get(i));
                    }
                    initEvent();
                    try {
                        initData();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
                case 7:
                    ima1.clear();
                    imaID1.clear();
                    for (int i = key * 7; i < ima.size(); i++) {
                        ima1.add(ima.get(i));
                        imaID1.add(imaID.get(i));
                    }
                    initEvent();
                    try {
                        initData();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
                case 8:
                    ima1.clear();
                    imaID1.clear();
                    for (int i = key * 8; i < ima.size(); i++) {
                        ima1.add(ima.get(i));
                        imaID1.add(imaID.get(i));
                    }
                    initEvent();
                    try {
                        initData();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
                case 9:
                    ima1.clear();
                    imaID1.clear();
                    for (int i = key * 9; i < ima.size(); i++) {
                        ima1.add(ima.get(i));
                        imaID1.add(imaID.get(i));
                    }
                    initEvent();
                    try {
                        initData();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
                case 10:
                    ima1.clear();
                    imaID1.clear();
                    for (int i = key * 10; i < ima.size(); i++) {
                        ima1.add(ima.get(i));
                        imaID1.add(imaID.get(i));
                    }
                    initEvent();
                    try {
                        initData();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        } else {
            Toast.makeText(activity_main_search.this, "已经没有图片了哦！！", Toast.LENGTH_SHORT).show();
        }
    }
}
