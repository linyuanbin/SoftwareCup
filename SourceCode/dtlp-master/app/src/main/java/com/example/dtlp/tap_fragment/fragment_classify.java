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
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.dtlp.Date.User;
import com.example.dtlp.MainActivity;
import com.example.dtlp.R;
import com.example.dtlp.start.loginActivity;
import com.example.dtlp.tap_fragment.fragment_push_image.utility.ImageSource;
import com.example.dtlp.tap_fragment.frament_search_image.PhotosWallAdapterSearch;
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

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by cool on 2017-03-28.
 */
public class fragment_classify extends Fragment {

    private View view;
    private MaterialRefreshLayout materialRefreshLayout;

    //GridView的适配器
//    private PhotoWallAdapter mAdapter = null;

    private Context mContext = null;

//    private int mImageThumbSize;
//    private int mImageThumbSpacing;

    //    private String ima[] = new String[12];
    public static ArrayList<String> ImageUrl = new ArrayList<>();
    private String ID[] = new String[12];
    private String UserID = "Fri May 26 19:33:14 CST 2017Jogvx";

    String MarkName = "";


    OkHttpClient okHttpClient = new OkHttpClient();


/////////////////////////////////////////////////////////////

    /**
     * 用于展示照片墙的GridView
     */
    private GridView mPhotoWallView;
    /**
     * GridView的适配器
     */
    private PhotosWallAdapterSearch mWallAdapter;

    private int mImageThumbSize;
    private int mImageThumbSpacing;

    private List<String> mImageUrlList = new ArrayList<>();

    private ImageSource imageSource;

    private boolean flag = true;
    private boolean flag1 = true;
    private boolean isSearch = true;
    String search = "";

    public static ArrayList ima = new ArrayList();
    public static ArrayList ima1 = new ArrayList();
    private int key = 12;
    private int value = 0;
    private int k = 0;
    public static ArrayList imaID = new ArrayList();
    private ArrayList imaID1 = new ArrayList();

    private ArrayList ima_push = new ArrayList();
    private ArrayList ID_push = new ArrayList();

    ///////////////////////////////////////////////////////

    Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
//                    String data = (String) msg.obj;
//                    if (data.indexOf("<html>") != -1) {
//
//                    } else {
//                        Type listType = new TypeToken<ArrayList<Picture>>() {
//                        }.getType();
//                        ArrayList<Picture> foos = new Gson().fromJson(data, listType);
//
//                        for (int i = 0; i < foos.size(); i++) {
//                            ima.add(foos.get(i).getPAddress());
//                            imaID.add(foos.get(i).getPID());
//                            System.out.println("name [" + i + "] = " + foos.get(i).getPAddress());
//                        }
//                        value = ima.size() / 12;
//                        Log.i("valuevaluevalue", "value = : " + value);
//                        if (ima.size() < key) {
//                            ima1.addAll(ima);
//                            imaID1.addAll(imaID);
//                        } else {
//                            for (int i = 0; i < key; i++) {
//                                ima1.add(ima.get(i));
//                                imaID1.add(imaID.get(i));
//                            }
//                        }
//                        if (flag) {
//                            ima.clear();
//                            ima1.clear();
//                            flag = false;
//                        }
//                        initEvent();
//                        try {
//                            initData();
//                        } catch (UnsupportedEncodingException e) {
//                            e.printStackTrace();
//                        }

//                        mPhotoWallView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                                if (isSearch) {
//                                    Intent intent = new Intent(view.getContext(), com.example.dtlp.tap_fragment.Image.class);
//                                    intent.putExtra("image", (String) ima1.get(position));
//                                    intent.putExtra("ID", (String) imaID1.get(position));
//                                    Log.i("BBBBBBB", " ima = " + (String) ima1.get(position));
//                                    Log.i("BBBBBBB", " ID = " + (String) imaID1.get(position));
//                                    startActivityForResult(intent, 0);
//                                }
//                                else
//                                {
//                                    Intent intent = new Intent(view.getContext(), com.example.dtlp.tap_fragment.Image.class);
//                                    intent.putExtra("image", (String) ima_push.get(position));
//                                    intent.putExtra("ID", (String) ID_push.get(position));
//                                    Log.i("BBBBBBB", " ima = " + (String) ima_push.get(position));
//                                    Log.i("BBBBBBB", " ID = " + (String) ID_push.get(position));
//                                    startActivityForResult(intent, 0);
//                                }
////                            startActivity(intent);
//                                Toast.makeText(mContext, position + "", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
                    break;
                case 2:
                    String data1 = (String) msg.obj;
                    if (data1.indexOf("<html>") != -1) {

                    } else {
                        Type listType = new TypeToken<ArrayList<com.example.dtlp.Date.Picture>>() {
                        }.getType();
                        ArrayList<com.example.dtlp.Date.Picture> foos = new Gson().fromJson(data1, listType);

                        for (int i = 0; i < foos.size(); i++) {
                            ima_push.add(foos.get(i).getPAddress());
//                ID[i] = foos.get(i).getPID();
                            ID_push.add(foos.get(i).getPID());
                            System.out.println("name [" + i + "] = " + foos.get(i).getPAddress());
                        }
//            mImageUrlList.addAll(ima);
                        if (flag) {
                            ima_push.clear();
                            ID_push.clear();
                            flag = false;
                        }
                        initEvent();
                        try {
                            initData_push();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        mPhotoWallView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                    if (isSearch) {
//                        Intent intent = new Intent(view.getContext(), com.example.dtlp.tap_fragment.Image.class);
//                        intent.putExtra("image", (String) ima1.get(position));
//                        intent.putExtra("ID", (String) imaID1.get(position));
//                        Log.i("BBBBBBB", " ima = " + (String) ima1.get(position));
//                        Log.i("BBBBBBB", " ID = " + (String) imaID1.get(position));
//                        startActivityForResult(intent, 0);
//                    }
//                    else
//                    {
                                Intent intent = new Intent(view.getContext(), com.example.dtlp.tap_fragment.Image.class);
                                intent.putExtra("image", (String) ima_push.get(position));
                                intent.putExtra("ID", (String) ID_push.get(position));
                                Log.i("BBBBBBB", " ima = " + (String) ima_push.get(position));
                                Log.i("BBBBBBB", " ID = " + (String) ID_push.get(position));
                                startActivityForResult(intent, 0);
//                    }
//                            startActivity(intent);
//                                Toast.makeText(mContext, position + "", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    }
            }
//            mPhotoWallView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
////                    if (isSearch) {
////                        Intent intent = new Intent(view.getContext(), com.example.dtlp.tap_fragment.Image.class);
////                        intent.putExtra("image", (String) ima1.get(position));
////                        intent.putExtra("ID", (String) imaID1.get(position));
////                        Log.i("BBBBBBB", " ima = " + (String) ima1.get(position));
////                        Log.i("BBBBBBB", " ID = " + (String) imaID1.get(position));
////                        startActivityForResult(intent, 0);
////                    }
////                    else
////                    {
//                        Intent intent = new Intent(view.getContext(), com.example.dtlp.tap_fragment.Image.class);
//                        intent.putExtra("image", (String) ima_push.get(position));
//                        intent.putExtra("ID", (String) ID_push.get(position));
//                        Log.i("BBBBBBB", " ima = " + (String) ima_push.get(position));
//                        Log.i("BBBBBBB", " ID = " + (String) ID_push.get(position));
//                        startActivityForResult(intent, 0);
////                    }
////                            startActivity(intent);
//                    Toast.makeText(mContext, position + "", Toast.LENGTH_SHORT).show();
//                }
//            });
        }
    };




        @Nullable
        public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle bundle) {
            view = inflater.inflate(R.layout.fragment_classify, group, false);
            //得到搜索传过来的关键字
            search = getArguments().getString("search");
            Log.i("search", "search: =  " + search);

//            if (!(search.equals("") || search.equals(null))) {
//                try {
//                    flash2();
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//            } else {
//
//                try {
//                    flash_push();
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//            }

            try {
                flash_push();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            initView();

            materialRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.search_refresh);

            materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
                @Override
                public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

//                            if (isSearch == true) {
//                                flash();
//                            }
//                            else
//                            {
//                                try {
//                                    flash_push();
//                                } catch (UnsupportedEncodingException e) {
//                                    e.printStackTrace();
//                                }
//                            }
                            try {
                                ima_push.clear();
                                ID_push.clear();
                                flash_push();
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

        public void flash_push() throws UnsupportedEncodingException {
            isSearch=false;
            String name1 = "requestRand";

            String key = "{\"state\":\"" + name1 + "\",\"UserID\":\"" + MainActivity.UserID + "\"}";
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
                                msg.what = 2;
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
                Toast.makeText(mContext, "已经没有图片了哦！！", Toast.LENGTH_SHORT).show();
            }
        }


        public void initView() {
//        setContentView(R.layout.activity_main);
            mPhotoWallView = (GridView) view.findViewById(R.id.search_id_photo_wall);
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
                mWallAdapter = new PhotosWallAdapterSearch(view.getContext(), ima1, mPhotoWallView);
            }
            mPhotoWallView.setAdapter(mWallAdapter);
            imageSource = new ImageSource();
            mWallAdapter.notifyDataSetChanged();

            if (flag1) {
                flash2();
                flag1 = false;
            }
        }

        public void initData_push() throws UnsupportedEncodingException {
            if (mWallAdapter == null) {
//            mWallAdapter = new PhotosWallAdapter(this,mImageUrlList, mPhotoWallView);
                mWallAdapter = new PhotosWallAdapterSearch(view.getContext(), ima_push, mPhotoWallView);
            }
            mPhotoWallView.setAdapter(mWallAdapter);
            imageSource = new ImageSource();
            mWallAdapter.notifyDataSetChanged();

            if (flag1) {
                flash_push();
                flag1 = false;
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


