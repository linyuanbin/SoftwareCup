package com.ghgk.photomanage.tap_fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.ghgk.photomanage.Date.PhotosWallAdapter;
import com.ghgk.photomanage.MainActivity;
import com.ghgk.photomanage.R;
import com.ghgk.photomanage.javabean.HistoryMark;
import com.ghgk.photomanage.start.loginActivity;
import com.ghgk.photomanage.utility.ImageSource;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
public class fragment_classify extends Fragment {
    private View view;
    private Button search;
    private LinearLayout linearLayout;
    OkHttpClient okHttpClient = new OkHttpClient();
    // private String url1 = "http://192.168.0.18:8080/TotemDown/managerServer?username=linyuanbin&password=123456";
    // private String url1 = "http://114.115.210.8:8090/TotemDown/managerServer?username=linyuanbin&password=123456";
    private MaterialRefreshLayout materialRefreshLayout;
    private ArrayList ima= new ArrayList();
    private Context mContext = null;
    private Map<String,String> historyimage = new HashMap<>();
    private  List<String> fhistoryImageUrl=new ArrayList<>();
    private  List<String> fhistoryMark=new ArrayList<>();
    private List<Map<String,String>> historyimage1 = new ArrayList<>();
    private String UserID = "Fri May 26 19:33:14 CST 2017Jogvx";
    /** 用于展示照片墙的GridView */
    private GridView mPhotoWallView;
    /** GridView的适配器 */
    private PhotosWallAdapter mWallAdapter;
    final File finalFilephoto = new File(loginActivity.SDPATH +"dtlp/"+"photo"+".txt");
    private int mImageThumbSize;
    private int mImageThumbSpacing;

    private List<String> mImageUrlList = new ArrayList<>();
    private ImageSource imageSource;
    private boolean flag = false;
    private boolean flag1 = true;
    private boolean flag2 = false;//刷新从新获取适配器数据
    Handler handle = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            String da = (String) msg.obj;//html  判断
            Log.i("----",da);

            if (da.indexOf("<html>") != -1||da==""||da==null) {

            } else {

                Type listtype = new TypeToken<ArrayList<HistoryMark>>() {
                }.getType();
                ArrayList<HistoryMark> foos = new Gson().fromJson(da, listtype);

                if (foos.size()!=0){
                for (int i = 0; i < foos.size(); i++) {
                    String historyImageUrl = foos.get(i).getPAddress();
                    String historyMark = foos.get(i).getFinalMarkName();
                    historyimage.put(historyImageUrl, historyMark);
                    fhistoryImageUrl.add(i,historyImageUrl);
                    fhistoryMark.add(i,historyMark);
                    System.out.println("name [" + i + "] = " + foos.get(i).getPAddress());
//                        Map<String, String> history = new HashMap<>();
//                        String FinalMarkName = foos.get(i).getFinalMarkName();
//                        String historyImageUrl1 = foos.get(i).getPAddress();
//                        history.put(historyImageUrl1, FinalMarkName);
//                        historyimage1.add(history);

                }
                Iterator it = historyimage.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry en = (Map.Entry) it.next();
                    String key = (String) en.getKey();
                    String value = (String) en.getValue();
                    Log.i("Map", "key =  " + key + "Value = " + value);
                }

                if (flag) {
                    historyimage.clear();
                    flag = true;
                }
                initEvent();
                try {
                    initData();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            }

        }
    };
    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup group , Bundle bundle){


            view=inflater.inflate(R.layout.fragment_classify,group,false);

       // getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);

        initView();
        try {
            flash2();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        materialRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.finish_tag);
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final  MaterialRefreshLayout materialRefreshLayout) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            historyimage.clear();
                            flag2=true;
                            fhistoryImageUrl.clear();
                            fhistoryMark.clear();

                           // mPhotoWallView=null;
                       //     mWallAdapter=null;

                            flash2();


                        }
                        catch (UnsupportedEncodingException e){
                            e.printStackTrace();
                        }
                        materialRefreshLayout.finishRefresh();
                    }
                },3000);
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
       // getActivity().setContentView(R.layout.fragment_classify);
        mPhotoWallView = (GridView)view.findViewById(R.id.photo_wall);
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
            mWallAdapter = new PhotosWallAdapter(getActivity(),historyimage, mPhotoWallView);
        }

        if (flag2){
           mWallAdapter.SetData(fhistoryImageUrl,fhistoryMark);
            flag2=false;

        }
        mPhotoWallView.setAdapter(mWallAdapter);
        imageSource = new ImageSource();
        mWallAdapter.notifyDataSetChanged();
        if (flag1) {
            flash2();
            flag1=false;
        }
    }

    public void flash2() throws UnsupportedEncodingException {
        String name1 = "request";
        String key = "{\"state\":\"download\"}";
        URLDecoder.decode(key, "utf-8");
        RequestBody requestBody1 = RequestBody
                .create(MediaType.parse("text/plain; charset=utf-8"), key);
        Request.Builder builder3 = new Request.Builder();
        Request request2 = builder3
                .url(MainActivity.ur)
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
                Log.i("infoo", " GET请求成功！！！");
                Log.i("infoo", "res = " + res);

                //将服务器传回的用户信息写入到TLTTLI文件中
                BufferedWriter out1 = null;
                try {
                    out1 = new BufferedWriter(new FileWriter(finalFilephoto));
                    out1.write(res); // \r\n即为换行
                    out1.flush(); // 把缓存区内容压入文件
                    out1.close(); // 最后记得关闭文件
                } catch (IOException e) {
                    e.printStackTrace();
                }

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
        });
    }
//    @Override
//    public void onPause() {
//        super.onPause();
//    }
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mWallAdapter.mImageLoader.cancelAllTasks();
//        mWallAdapter.mImageLoader.deleteCache();
//    }
}
