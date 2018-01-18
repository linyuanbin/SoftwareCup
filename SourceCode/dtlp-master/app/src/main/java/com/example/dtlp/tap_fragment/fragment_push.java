package com.example.dtlp.tap_fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.GridView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.dtlp.Date.User;
import com.example.dtlp.MainActivity;
import com.example.dtlp.R;
import com.example.dtlp.start.loginActivity;
import com.example.dtlp.tap_fragment.fragment_push_image.PhotosWallAdapter;
import com.example.dtlp.tap_fragment.fragment_push_image.utility.ImageSource;
import com.example.dtlp.user_main.MainActivity_2;
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
import java.util.List;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by cool on 2017-03-28.
 */
public class fragment_push extends Fragment {
    private View view;


    //用于展示照片墙的GridView
//    private GridView mPhotoWall = null;

    private MaterialRefreshLayout materialRefreshLayout;

    //GridView的适配器
//    private PhotoWallAdapter mAdapter = null;

    private Context mContext = null;

//    private int mImageThumbSize;
//    private int mImageThumbSpacing;

//    private String ima[] = new String[12];
    public static ArrayList<String> ImageUrl = new ArrayList<>();
//    private String ID[] = new String[12];
    private String UserID = "Fri May 26 19:33:14 CST 2017Jogvx";

    String MarkName = "";


    OkHttpClient okHttpClient = new OkHttpClient();


/////////////////////////////////////////////////////////////

    /** 用于展示照片墙的GridView */
    private GridView mPhotoWallView;
    /** GridView的适配器 */
    private PhotosWallAdapter mWallAdapter;

    private int mImageThumbSize;
    private int mImageThumbSpacing;

    private List<String> mImageUrlList = new ArrayList<>();
    private ImageSource imageSource;

    private boolean flag = true;
    private boolean flag1 = true;

    private ArrayList ima= new ArrayList();
    private ArrayList ID= new ArrayList();
    ///////////////////////////////////////////////////////


//    Handler handle = new Handler() {
//
//        public void handleMessage(Message msg) {
//            System.out.println("111");
////                    tup = (Bitmap[]) msg.obj;
////                    tupp = (ArrayList<Bitmap>) msg.obj;
//
//            String data = (String) msg.obj;
//            Type listType = new TypeToken<ArrayList<Picture>>() {
//            }.getType();
//            ArrayList<Picture> foos = new Gson().fromJson(data, listType);
//            for (int i = 0; i < foos.size(); i++) {
//                ima[i] = foos.get(i).getPAddress();
//                ID[i] = foos.get(i).getPID();
//                System.out.println("name [" + i + "] = " + foos.get(i).getPAddress());
//            }
//            for (int k = 0; k < ima.length; k++) {
//                Log.i("ccccc", "tupp =  " + ima[k]);
//            }
//
//            mAdapter = new PhotoWallAdapter(view.getContext(), 0, ima, mPhotoWall);
//            mPhotoWall.setAdapter(mAdapter);
//            mPhotoWall.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                @SuppressWarnings("deprecation")
//                @Override
//                public void onGlobalLayout() {
//                    final int numColumns = (int) Math.floor(mPhotoWall.getWidth() / (mImageThumbSize + mImageThumbSpacing));
//                    if (numColumns > 0) {
//                        int columnWidth = (mPhotoWall.getWidth() / numColumns) - mImageThumbSpacing;
//                        mAdapter.setItemHeight(columnWidth);
//                        mPhotoWall.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                    }
//                }
//            });
//
//
//            mPhotoWall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                    Intent intent = new Intent(view.getContext(), com.example.dtlp.tap_fragment.Image.class);
//                    intent.putExtra("image",ima[position]);
//                    intent.putExtra("ID",ID[position]);
//                    Log.i("BBBBBBB", " ima = " + ima[position]);
//                    Log.i("BBBBBBB", " ID = " + ID[position]);
//                    startActivityForResult(intent,0);
////                            startActivity(intent);
//                    Toast.makeText(mContext , position + "" , Toast.LENGTH_SHORT).show();
//                }
//            });
//
//        }
//    };

    Handler handle = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            String data = (String) msg.obj;
            if (data.indexOf("<html>") != -1) {

            } else {
                Type listType = new TypeToken<ArrayList<com.example.dtlp.Date.Picture>>() {
                }.getType();
                ArrayList<com.example.dtlp.Date.Picture> foos = new Gson().fromJson(data, listType);

                for (int i = 0; i < foos.size(); i++) {
                    ima.add(foos.get(i).getPAddress());
//                ID[i] = foos.get(i).getPID();
                    ID.add(foos.get(i).getPID());
                    System.out.println("name [" + i + "] = " + foos.get(i).getPAddress());
                }
//            mImageUrlList.addAll(ima);
                if (flag) {
                    ima.clear();
                    ID.clear();
                    flag = false;
                }
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
                        intent.putExtra("image", (String) ima.get(position));
                        intent.putExtra("ID", (String) ID.get(position));
                        Log.i("BBBBBBB", " ima = " + (String) ima.get(position));
                        Log.i("BBBBBBB", " ID = " + (String) ID.get(position));
                        startActivityForResult(intent, 0);
//                            startActivity(intent);
//                        Toast.makeText(mContext, position + "", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }
    };



    @Override
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup group, Bundle bundle){
        view =layoutInflater.inflate(R.layout.fragment_push,group,false);

        initView();

        materialRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.push_refresh);
        try {
            flash2();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        for (int j = 0; j < ima1.length; j++) {
//            Log.i("info", "ima =  " + ima1[j]);
//        }
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ID.clear();
                            ima.clear();
                            flash2();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
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


        mContext = view.getContext();

        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
         MarkName = data.getStringExtra("a");
        String PID = data.getStringExtra("b");
        String imageUrl = data.getStringExtra("c");
//        String TabID =UserID+PID;
        switch (requestCode) {
            case 0:
                Log.i("EEEEEE", "info = " + PID);
                Log.i("EEEEEE", "textMain = " + MarkName);
                Log.i("EEEEEE", "imageUrl = " + imageUrl);
                if (!(MarkName.equals("")||MarkName.equals("null")||MarkName.toString().equals("标签"))) {
//                    ImageUrl.add(imageUrl);
                    String name1 = "mark";
                    String key = "{\"state\":\""+name1+"\","+
                            "\"UserID\":\""+MainActivity.UserID+"\",\"PID\":\""+PID+"\",\"MarkName\":\""+MarkName+"\"}";
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
                    call1.enqueue(new Callback() {
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

        String key = "{\"state\":\""+name1+"\",\"UserID\":\""+MainActivity.UserID+"\"}";
        URLDecoder.decode(key, "utf-8");
        RequestBody requestBody1 = RequestBody
                .create(MediaType.parse("text/plain; charset=utf-8"), key);
        Request.Builder builder3 = new Request.Builder();
        Request request2 = builder3
                .url(MainActivity.URL)
                .post(requestBody1)
                .build();

        okhttp3.Call call1 = okHttpClient.newCall(request2);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.i("info", " Push_GET请求失败！！！");
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {

                final String res = response.body().string();
                Log.i("infoo", " Push_GET请求成功！！！");

                Log.i("infoo", "Push_res = " + res);
                if (res.equals("") || res.equals(null)) {

                } else {
                    Thread mThread = new Thread() {
                        @Override
                        public void run() {
                            Message msg = new Message();
                            msg.obj = res;
                            handle.sendMessage(msg); //新建线程加载图片信息，发送到消息队列中
                        }
                    };
                    mThread.start();
                }
            }
        });
    }



    public void initView() {
//        setContentView(R.layout.activity_main);
        mPhotoWallView = (GridView) view.findViewById(R.id.push_id_photo_wall);
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
            mWallAdapter = new PhotosWallAdapter(view.getContext(),ima, mPhotoWallView);
        }
        mPhotoWallView.setAdapter(mWallAdapter);
        imageSource = new ImageSource();
        mWallAdapter.notifyDataSetChanged();

        if (flag1) {
            flash2();
            flag1=false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
//        mWallAdapter.mImageLoader.flushCache();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 退出程序时结束所有的下载任务
//        mWallAdapter.mImageLoader.cancelAllTasks();
//        mWallAdapter.mImageLoader.deleteCache();
    }
}
