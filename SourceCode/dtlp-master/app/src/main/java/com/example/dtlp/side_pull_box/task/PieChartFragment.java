package com.example.dtlp.side_pull_box.task;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dtlp.MainActivity;
import com.example.dtlp.R;
import com.example.dtlp.start.loginActivity;
import com.idtk.smallchart.chart.PieChart;
import com.idtk.smallchart.data.PieData;
import com.idtk.smallchart.interfaces.iData.IPieData;

import java.io.File;
import java.util.ArrayList;

import okhttp3.OkHttpClient;

/**
 * Created by Idtk on 2016/6/26.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 :
 */
public class PieChartFragment extends BaseFragment {

    private ArrayList<IPieData> mPieDataList = new ArrayList<>();

    private String[] sm = {"已完成","未完成"};
    File filename = new File(loginActivity.SDPATH + "dtlp/" + MainActivity.UserID + "/" + MainActivity.UserID + ".txt"); // 要读取以上路径的input。txt文件
    OkHttpClient okHttpClient = new OkHttpClient();

    public static int[] task = new int[2];

//    Handler handle = new Handler()
//    {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            String data = (String) msg.obj;
//            if (data.indexOf("<html>") != -1) {
//
//            } else {
//
//                Log.i("task", "data = : " + data);
//                try {
//                    JSONObject jsonObject = new JSONObject(data);
//                    task[0]=jsonObject.getJSONObject("userData").getInt("accomplish");
//                    task[1]=jsonObject.getJSONObject("userData").getInt("total");
//                    Log.i("task", "task:= " + task[0]);
//                    Log.i("task", "task:= " + task[1]);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                initData();
//            }
//        }
//    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.side_pull_box_task_piechart,container,false);
        initData();
//        finsh();
        PieChart pieChart = (PieChart) view.findViewById(R.id.pieChart);
        pieChart.setDataList(mPieDataList);
        pieChart.setAxisColor(Color.WHITE);
        pieChart.setAxisTextSize(pxTodp(20));

//        Toast.makeText(view.getContext(), "111111111111", Toast.LENGTH_SHORT).show();
//        Log.i("task", "task:= " + Task.task[0]);
//        Log.i("task", "task:= " + Task.task[1]);

        return view;
    }

    private void initData(){
        Log.i("task", "task1111111111:= " + task[0]);
        Log.i("task", "task111111111111:= " + task[1]);
        for (int i=0; i<2; i++){
            PieData pieData = new PieData();
            pieData.setName(sm[i]);
            pieData.setValue(task[i]);
            pieData.setColor(mColors[i]);
            mPieDataList.add(pieData);
        }
    }
//    public void finsh()
//    {
//        String userdata = "";//读出来的Json数据
//        String number = "";
//        String password = "";
//        Log.i("userdata", "userdata: = " + filename);
//        try {
//            FileReader read = new FileReader(filename);
//            StringBuffer sb = new StringBuffer();
//            char ch[] = new char[1024];
//            int d = read.read(ch);
//            while(d!=-1){
//                String str = new String(ch,0,d);
//                sb.append(str);
//                d = read.read(ch);
//            }
//            userdata= sb.toString();
//            System.out.print(sb.toString());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        JSONObject jsonObject = null;
//        try {
//            jsonObject = new JSONObject(userdata);
//            number = jsonObject.getJSONObject("userData").getString("UserTel");
//            password = jsonObject.getJSONObject("userData").getString("UserPassword");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String post ="{\"state\":\"login\",\"UserName\":\""+number+"\",\"UserPassword\":\""+password+"\"}";
//        RequestBody requestBody1 = RequestBody
//                .create(MediaType.parse("text/x-markdown; charset=utf-8"),post);
//        Request.Builder builder3 = new Request.Builder();
//        Request request2 = builder3
//                .url(MainActivity.URL)
//                .post(requestBody1)
//                .build();
//        CallHttp(request2);
//    }
//    public void CallHttp(Request request)
//    {
//        okhttp3.Call call1 = okHttpClient.newCall(request);
//        call1.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.i("info", " GET请求失败！！！");
//                Log.i("info", " e  = "  + e .toString());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//                final String res = response.body().string();
//
//                Log.i("task", "res = : " + res);
//                String accomplish="";
//
//                if (res.equals("") || res.equals(null)) {
//
//                } else {
//                    Thread mThread = new Thread() {
//                        @Override
//                        public void run() {
//                            Message msg = new Message();
//                            msg.obj = res;
//                            handle.sendMessage(msg); //新建线程加载图片信息，发送到消息队列中
//                        }
//                    };
//                    mThread.start();
//                }
//            }
//        });
//    }

}
