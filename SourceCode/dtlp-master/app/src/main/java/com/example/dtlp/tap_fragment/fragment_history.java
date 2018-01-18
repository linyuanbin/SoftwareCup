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
import com.example.dtlp.Date.HistoryMark;
import com.example.dtlp.Date.User;
import com.example.dtlp.MainActivity;
import com.example.dtlp.R;
import com.example.dtlp.tap_fragment.fragment_push_image.utility.ImageSource;
import com.example.dtlp.tap_fragment.frament_history_image.PhotosWallAdapterHistory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by cool on 2017-03-28.
 */
public class fragment_history extends Fragment {
    private View view;

    /////////////////////////////////////////////////////////////
    private Context mContext = null;

    private MaterialRefreshLayout materialRefreshLayout;

    OkHttpClient okHttpClient = new OkHttpClient();
    /** 用于展示照片墙的GridView */
    private GridView mPhotoWallView;
    /** GridView的适配器 */
    private PhotosWallAdapterHistory mWallAdapter;
//    private PhotosWallAdapterHistory mWallAdapter;
    private int mImageThumbSize;
    private int mImageThumbSpacing;

//    private Map<String,String> historyimage = new HashMap<>();
//    private Map<String,String> image = new HashMap<>();
    private List<Map<String,String>> ima_url_id = new ArrayList<>();
    private List<Map<String,String>> historyimage = new ArrayList<>();
//    private List<Map<String,String>> historyimage ;
    private List<String> ImageUrl = new ArrayList<>();
    private List<String> Imagemark = new ArrayList<>();
    private List<String> finalMark = new ArrayList<>();
    private int j=0;
    private ImageSource imageSource;

    private boolean flag = true;
    private boolean flag1 = true;
    private boolean flag2 = true;

//    private ArrayList ima= new ArrayList();

    private String UserID = "Fri May 26 19:33:14 CST 2017Jogvx";
    String MarkName = "";
    ///////////////////////////////////////////////////////


    Handler
            handle = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            String data = (String) msg.obj;
            if (data.indexOf("<html>") != -1) {

            } else {
                Type listType = new TypeToken<ArrayList<HistoryMark>>() {
                }.getType();
                ArrayList<HistoryMark> foos = new Gson().fromJson(data, listType);

                j = foos.size() + 1;
//            Log.i("history", );
//                if (historyimage==null) {
//                    historyimage = new ArrayList<>();
                    for (int i = 0; i < foos.size(); i++) {
//                ima[i] = foos.get(i).getPAddress();

                        finalMark.add(foos.get(i).getState());
                        Map<String, String> history = new HashMap<>();
                        String historyImageUrl = foos.get(i).getPAddress();
                        String historyMark = foos.get(i).getMarkName();
                        ImageUrl.add(historyImageUrl);
                        Imagemark.add(historyMark);
                        String historyPID = foos.get(i).getPID();
                        history.put(historyImageUrl, historyMark);
                        historyimage.add(history);
//                ima.add(foos.get(i).getPAddress());
                        Map<String, String> image = new HashMap<>();
                        image.put(historyImageUrl, historyPID);
                        ima_url_id.add(image);
//                ImageUrl.add(foos.get(i).getPAddress());
//                ImageId.add(foos.get(i).getPID());
                        System.out.println("name [" + i + "] = " + foos.get(i).getPAddress());
                    }

                for (int i = 0; i<ImageUrl.size();i++)
                {
                    Log.i("abcde", "ImageUrl =  " + ImageUrl.get(i));
                    Log.i("abcde", "ImageUrl =  " + Imagemark.get(i));
                }

//                }
//                else
//                {
//                    historyimage.clear();
//                }

//            ima_url_id.add(image);

//            ima_url_id.get(1).
//            for(Map.Entry<String, String> entry:ima_url_id.get(1).entrySet()){
//                System.out.println("The array is:" + entry.getKey() + "= " + entry.getValue());
//            }
////
//            Iterator it = historyimage.entrySet().iterator();
//            while (it.hasNext())
//            {
//                Map.Entry en = (Map.Entry) it.next();
//                String key = (String) en.getKey();
//                String value = (String) en.getValue();
//                Log.i("Map", "key =  " + key +"Value = " + value);
//            }
//            mImageUrlList.addAll(ima);
            if (flag)
            {
//                historyimage.clear();
                ima_url_id.clear();
                finalMark.clear();
                flag=false;
            }
//                initView();
                initEvent();
                try {
                    initData();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                mPhotoWallView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (finalMark.get(position).equals("false")) {
                            Map<String, String> map = ima_url_id.get(position);
                            Iterator it = map.entrySet().iterator();
                            while (it.hasNext()) {
                                Map.Entry en = (Map.Entry) it.next();
                                String key = (String) en.getKey();
                                String value = (String) en.getValue();
                                Log.i("Map", "key =  " + key + "Value = " + value);
                            }
                            String imageurl = null;
                            String imageid = null;
                            for (Map.Entry<String, String> entry : map.entrySet()) {
//                            startActivity(intent);
                                imageurl = entry.getKey();
                                imageid = entry.getValue();
                                System.out.println("The array is:" + entry.getKey() + "= " + entry.getValue());
                            }
                            Intent intent = new Intent(view.getContext(), image_history.class);
                            intent.putExtra("image", imageurl);
                            intent.putExtra("ID", imageid);
                            Log.i("BBBBBBB", " ima = " + imageurl);
                            Log.i("BBBBBBB", " ID = " + imageid);
//                            Toast.makeText(mContext, position + "", Toast.LENGTH_SHORT).show();
                            startActivityForResult(intent, 0);
//                            Toast.makeText(mContext, position + "", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, "标签也确定，不能修改了哦！！！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    };



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle bundle){
        view =inflater.inflate(R.layout.fragment_history,group,false);
        initView();
        try {
            ImageUrl.clear();
            Imagemark.clear();
            ima_url_id.clear();
            flash2();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        materialRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.history_refresh);
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            historyimage.clear();
                            ima_url_id.clear();
                            finalMark.clear();
                            ImageUrl.clear();
                            Imagemark.clear();
                            flash2();
//                            mWallAdapter=null;
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
//                        Toast.makeText(fragment_history.this, "21111", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(fragment_history.this, "111111", Toast.LENGTH_SHORT).show();
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
    public void initView() {
//        setContentView(R.layout.activity_main);
        mPhotoWallView = (GridView) view.findViewById(R.id.history_id_photo_wall);
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
                Log.i("numColumns", "numColumns =  " + numColumns);
                if (numColumns > 0) {
                    int columnWidth = (mPhotoWallView.getWidth() / numColumns) - mImageThumbSpacing;
                    Log.i("columnWidth", "columnWidth =  " + columnWidth);
                    mWallAdapter.setItemSize(columnWidth);
//                    mWallAdapter.setItemSize(k);
                    mPhotoWallView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
    }

    public void initData() throws UnsupportedEncodingException {
        if (mWallAdapter == null) {
            mWallAdapter = new PhotosWallAdapterHistory(view.getContext(),historyimage, mPhotoWallView);
        }
        if (flag2) {
            mWallAdapter.setData(ImageUrl, Imagemark);
            flag2 = false;
        }
        mPhotoWallView.setAdapter(mWallAdapter);
        imageSource = new ImageSource();
        mWallAdapter.notifyDataSetChanged();


        if (flag1) {

            flash2();
            ImageUrl.clear();
            Imagemark.clear();
            flag1=false;
        }
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
                    String name1 = "updatemark";
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
                            Gson gson = new Gson();
                            User user = gson.fromJson(res, User.class);
//                        user.getState();
                            if (!user.getState().equals("true")) {
                                Log.i("info", " Push_标签添加失败了哦！！！");
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

        String key = "{\"state\":\"history\",\"UserID\":\""+MainActivity.UserID+"\"}";
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
                Log.i("info", " GET请求失败！！！");
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {

                final String res = response.body().string();
                Log.i("infoo", " History_GET请求成功！！！");

                Log.i("infoo", "History_res = " + res);

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

}
