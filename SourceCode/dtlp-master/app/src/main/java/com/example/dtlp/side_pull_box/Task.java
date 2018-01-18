package com.example.dtlp.side_pull_box;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import com.example.dtlp.MainActivity;
import com.example.dtlp.R;
import com.example.dtlp.side_pull_box.task.PieChartFragment;
import com.example.dtlp.start.loginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 阳瑞 on 2017/6/23.
 */
public class Task extends AppCompatActivity{
//    public static int[] task = new int[2];
    private FrameLayout chartFragments;
    private TabLayout topTabs;
    private Fragment barChartFragment,lineChartFragment,curveChartFragment,combineChartFragment,
            radarChartFragment,pieChartFragment;
    private FragmentTransaction fragmentTransaction;
    OkHttpClient okHttpClient = new OkHttpClient();
    File filename = new File(loginActivity.SDPATH + "dtlp/" + MainActivity.UserID + "/" + MainActivity.UserID + ".txt"); // 要读取以上路径的input。txt文件


    Handler handle = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            String data = (String) msg.obj;
            if (data.indexOf("<html>") != -1) {

            } else {
                Log.i("task", "data = : " + data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    PieChartFragment.task[0]=jsonObject.getJSONObject("userData").getInt("accomplish");
                    PieChartFragment.task[1]=jsonObject.getJSONObject("userData").getInt("total");
                    Log.i("task", "task:= " + PieChartFragment.task[0]);
                    Log.i("task", "task:= " + PieChartFragment.task[1]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                 initView();
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.side_pull_box_task);
        finsh();
//        initView();
    }
    private void initView(){
        chartFragments = (FrameLayout) findViewById(R.id.chart_fragments);
        topTabs = (TabLayout) findViewById(R.id.top_tabs);
        topTabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        topTabs.setTabGravity(TabLayout.GRAVITY_FILL);

        topTabs.addTab(topTabs.newTab().setText("环形图"),0);
//        topTabs.addTab(topTabs.newTab().setText("曲线图"),0);
        topTabs.addTab(topTabs.newTab().setText(""),1);
//        topTabs.addTab(topTabs.newTab().setText("折线图"),2);
//        topTabs.addTab(topTabs.newTab().setText("组合图"),3);
//        topTabs.addTab(topTabs.newTab().setText("雷达图"),5);

        topTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                FragmentManager manager = getFragmentManager();
                FragmentManager manager=getSupportFragmentManager();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                hideFragment(fragmentTransaction);
                switch (tab.getPosition()){
//                    case 0:
//                        curveChartFragment = manager.findFragmentByTag("Tag0");
//                        if (curveChartFragment == null){
//                            curveChartFragment = new CurveChartFragment();
//                            fragmentTransaction.add(R.id.chart_fragments,curveChartFragment,"Tag0");
//                        }else {
//                            fragmentTransaction.show(curveChartFragment);
//                        }
//                        break;
                    case 0:
                        pieChartFragment = manager.findFragmentByTag("Tag0");
                        if (pieChartFragment == null){
                            pieChartFragment = new PieChartFragment();
                            fragmentTransaction.add(R.id.chart_fragments,pieChartFragment,"Tag0");
                        }else {
                            fragmentTransaction.show(pieChartFragment);
                        }
                        break;
                    case 1:
//                        barChartFragment = manager.findFragmentByTag("Tag1");
//                        if (barChartFragment == null){
//                            barChartFragment = new BarChartFragment();
//                            fragmentTransaction.add(R.id.chart_fragments, barChartFragment,"Tag1");
//                        }else {
//                            fragmentTransaction.show(barChartFragment);
//                        }
                        break;
//                    case 2:
//                        lineChartFragment = manager.findFragmentByTag("Tag2");
//                        if (lineChartFragment == null){
//                            lineChartFragment = new LineChartFragment();
//                            fragmentTransaction.add(R.id.chart_fragments,lineChartFragment,"Tag2");
//                        }else {
//                            fragmentTransaction.show(lineChartFragment);
//                        }
//                        break;
//                    case 3:
//                        combineChartFragment = manager.findFragmentByTag("Tag3");
//                        if (combineChartFragment == null){
//                            combineChartFragment = new CombineChartFragment();
//                            fragmentTransaction.add(R.id.chart_fragments,combineChartFragment,"Tag3");
//                        }else {
//                            fragmentTransaction.show(combineChartFragment);
//                        }
//                        break;
//                    case 5:
//                        radarChartFragment = manager.findFragmentByTag("Tag5");
//                        if (radarChartFragment == null){
//                            radarChartFragment = new RadarChartFragment();
//                            fragmentTransaction.add(R.id.chart_fragments,radarChartFragment,"Tag5");
//                        }else {
//                            fragmentTransaction.show(radarChartFragment);
//                        }
//                        break;
                }
                fragmentTransaction.commit();//提交事务
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                fragmentTransaction.show(pieChartFragment);
            }
        });

        topTabs.getTabAt(1).select();
        topTabs.getTabAt(0).select();

    }

    private void hideFragment(FragmentTransaction fragmentTransaction){

        if (barChartFragment != null){
            fragmentTransaction.hide(barChartFragment);
        }

        if (lineChartFragment != null){
            fragmentTransaction.hide(lineChartFragment);
        }

        if (curveChartFragment != null){
            fragmentTransaction.hide(curveChartFragment);
        }
        if (combineChartFragment != null){
            fragmentTransaction.hide(combineChartFragment);
        }

        if (radarChartFragment != null){
            fragmentTransaction.hide(radarChartFragment);
        }
        if (pieChartFragment != null){
            fragmentTransaction.hide(pieChartFragment);
        }
    }
    public void finsh()
    {
        String userdata = "";//读出来的Json数据
        String number = "";
        String password = "";
        Log.i("userdata", "userdata: = " + filename);
        try {
            FileReader read = new FileReader(filename);
            StringBuffer sb = new StringBuffer();
            char ch[] = new char[1024];
            int d = read.read(ch);
            while(d!=-1){
                String str = new String(ch,0,d);
                sb.append(str);
                d = read.read(ch);
            }
            userdata= sb.toString();
            System.out.print(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(userdata);
            number = jsonObject.getJSONObject("userData").getString("UserTel");
            password = jsonObject.getJSONObject("userData").getString("UserPassword");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String post ="{\"state\":\"login\",\"UserName\":\""+number+"\",\"UserPassword\":\""+password+"\"}";
        RequestBody requestBody1 = RequestBody
                .create(MediaType.parse("text/x-markdown; charset=utf-8"),post);
        Request.Builder builder3 = new Request.Builder();
        Request request2 = builder3
                .url(MainActivity.URL)
                .post(requestBody1)
                .build();
        CallHttp(request2);
    }
    public void CallHttp(Request request)
    {

        okhttp3.Call call1 = okHttpClient.newCall(request);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("info", " GET请求失败！！！");
                Log.i("info", " e  = "  + e .toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String res = response.body().string();

                String accomplish="";
                Log.i("task", "res = : " + res);
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    PieChartFragment.task[0]=jsonObject.getJSONObject("userData").getInt("accomplish");
                    PieChartFragment.task[1]=jsonObject.getJSONObject("userData").getInt("total");
                    Log.i("task", "task:= " + PieChartFragment.task[0]);
                    Log.i("task", "task:= " + PieChartFragment.task[1]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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


