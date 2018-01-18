package com.ghgk.photomanage.user_main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ghgk.photomanage.Date.myadapter;
import com.ghgk.photomanage.MainActivity;
import com.ghgk.photomanage.R;
import com.ghgk.photomanage.feed_single_item;
import com.ghgk.photomanage.javabean.Feedback;
import com.ghgk.photomanage.single_feed_sovle;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class main_search extends Activity {
  OkHttpClient okHttpClient = new OkHttpClient();
     //private String url1 = "http://192.168.0.18:8080/TotemDown/managerServer?username=linyuanbin&password=123456";
     //private String url1 = "http://114.115.210.8:8090/TotemDown/managerServer?username=linyuanbin&password=123456";
    private  List<Feedback> userfeed=new ArrayList<>();
    private List<String> userfeed1 = new ArrayList<>();
    private List<Feedback>  usersovlefeed=new ArrayList<>();
    private CheckBox checkBox;
    private ImageButton img_button_sovle_feed;
    private ImageButton img_button_outstanding_feed;
    private TextView text_out;
    private  TextView text_solve;
    private String res;
    int item_number_solve=0;
    int item_number=0;
    ListView listView;
    ListView listView_isok;
    Boolean is_check=false;
    List<Map<String,Object>> list;
    List<Map<String,Object>> list_solve;
    private LinearLayout nosovle_LIN;
    private LinearLayout sovle_LIN;
    private  LinearLayout outstanding_feed_L;//未解决的按钮
    private LinearLayout  solve_feed_L;//已经解决的按钮
    private JSONArray jsonArray;

    private Button back;
    Handler handle = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0: {
                    String data = (String) msg.obj;
                    if (data.indexOf("<html>") != -1) {
                    } else {
                        Log.i("info", " data = " + data);
                        Type listType = new TypeToken<ArrayList<Feedback>>() {
                        }.getType();
                        if (data.equals("")||data.equals(null)) {
                            Toast.makeText(main_search.this, "异常已经全部解决", Toast.LENGTH_SHORT).show();}
                        else {
                            try {
                                JSONObject jsonObject = new JSONObject(res);
                                jsonArray = jsonObject.getJSONArray("userfeedbackdata");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            userfeed = new Gson().fromJson(String.valueOf(jsonArray), listType);
                            try {
                                for (int i = 0 ; i<jsonArray.length();i++)
                                {
                                    Log.i("Json", "jsonArray : " + jsonArray.get(i));
                                    userfeed1.add(jsonArray.get(i).toString());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            listView = (ListView) findViewById(R.id.mylist);
                            list = getData();
                            listView.setAdapter(new myadapter(main_search.this, list));
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    item_number = i;//获取第几个item的点击
                                    Intent intent = new Intent(main_search.this, feed_single_item.class);
                                    intent.putExtra("name", userfeed.get(i).getUserTel());
                                    intent.putExtra("feed", userfeed.get(i).getContent());
                                    intent.putExtra("ask", false);
                                    startActivityForResult(intent, 0);
                                }
                            });
                        }
                        break;
                    }
                }
                case 1:{
                    String data2=(String) msg.obj;
                    if (data2.indexOf("<html>") != -1) {//  html 判断
                    } else {
                        Type listType1 = new TypeToken<ArrayList<Feedback>>() {
                        }.getType();
                        if (data2.equals("")||data2.equals(null)){

                        }
                        else{
                            usersovlefeed= new Gson().fromJson(data2, listType1);
                            listView_isok =(ListView)findViewById(R.id.mylist_solve);
                            list_solve=getData_sovle();
                            listView_isok.setAdapter(new myadapter(main_search.this,list_solve));
                            Log.i("getData_sovle()",list_solve.toString());
                            listView_isok.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    item_number_solve = i;//获取第几个item的点击
                                    Intent intent = new Intent(main_search.this,single_feed_sovle.class);
                                    intent.putExtra("name", usersovlefeed.get(i).getUserTel());
                                    intent.putExtra("feed", usersovlefeed.get(i).getContent());
                                    intent.putExtra("ask", false);
                                    startActivityForResult(intent,00);
                                }
                            });
                            break;
                        }
                    }
            }
            }
            back=(Button) findViewById(R.id.main_search_back);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(main_search.this,MainActivity_2.class);
                    //userfeed=null;
                    startActivity(intent);
                    finish();
                }
            });

        }
    };
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_search);
        htt();
        //htt_SolvedFeedback();
        img_button_sovle_feed=(ImageButton)findViewById(R.id.img_button_sovle_feed);
        img_button_outstanding_feed=(ImageButton)findViewById(R.id.img_button_outstanding_feed);
        img_button_outstanding_feed.setImageResource(R.mipmap.outstading_feeding);//给点击图片的颜色
        text_out=(TextView)findViewById(R.id.text_out);
        text_out.setTextColor(Color.rgb(30,144,255));
        text_solve=(TextView)findViewById(R.id.text_sovle);
        nosovle_LIN=(LinearLayout)findViewById(R.id.nosolve_LIN);
        sovle_LIN=(LinearLayout)findViewById(R.id.solve_LIN);
        outstanding_feed_L=(LinearLayout)findViewById(R.id.outstanding_feed_L);
        solve_feed_L=(LinearLayout)findViewById(R.id.sovle_feed_L);
        outstanding_feed_L.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sovle_LIN.setVisibility(View.GONE);
                nosovle_LIN.setVisibility(View.VISIBLE);
                img_button_outstanding_feed.setImageResource(R.mipmap.outstading_feeding);
                img_button_sovle_feed.setImageResource(R.mipmap.sovle_feed);
                text_out.setTextColor(Color.rgb(0,191,255));
                text_solve.setTextColor(Color.rgb(112,128,144));
            }
        });
               solve_feed_L.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                htt_SolvedFeedback();
                nosovle_LIN.setVisibility(View.GONE);
                sovle_LIN.setVisibility(View.VISIBLE);
                img_button_outstanding_feed.setImageResource(R.mipmap.outstaning_feed);
                img_button_sovle_feed.setImageResource(R.mipmap.sovle_feeding);
                text_solve.setTextColor(Color.rgb(30,144,255));
                text_out.setTextColor(Color.rgb(112,128,144));
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent intent){
        if (requestCode==0&&resultCode==0){
            is_check=intent.getBooleanExtra("ischeck",is_check);
            if(is_check){
                list.remove(item_number);
                http_feed_del();
                userfeed.remove(item_number);
                listView.setAdapter(new myadapter(main_search.this,list));
            }
        }
        super.onActivityResult(requestCode,resultCode,intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
      getMenuInflater().inflate(R.menu.menu,menu);
        return  true;
    }
    public  List<Map<String,Object>>getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map1 = new HashMap<String, Object>();
        if (userfeed.size() == 0) {
            Toast.makeText(main_search.this, "异常已经全部解决了", Toast.LENGTH_SHORT);
           list=null;
        } else {
            for (int i = 0; i < userfeed.size(); i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("name", userfeed.get(i).getUserTel());
                map.put("feed", userfeed.get(i).getContent());
                map.put("ask", true);
                checkBox = (CheckBox) findViewById(R.id.ask);
                list.add(map);
            }
        }
            return list;
        }


    public  List<Map<String,Object>>getData_sovle() {
        List<Map<String, Object>> list_sovle = new ArrayList<Map<String, Object>>();
        Map<String, Object> map1 = new HashMap<String, Object>();
        if (usersovlefeed.size() == 0) {
            Toast.makeText(main_search.this, "异常已经全部解决了", Toast.LENGTH_SHORT);
            list_sovle=null;
        }
           else
          {
            for (int i = 0; i < usersovlefeed.size(); i++) {
                Map<String, Object> map11 = new HashMap<String, Object>();
                map11.put("name",usersovlefeed.get(i).getUserTel());
                map11.put("feed", usersovlefeed.get(i).getContent());
                map11.put("ask", true);
                checkBox = (CheckBox) findViewById(R.id.ask);
                list_sovle.add(map11);
            }
        }
        return  list_sovle;
    }
       public void htt(){
        String post = "{\"state\":\"getfeedback\"}";
        RequestBody requestBody1 = RequestBody
                .create(MediaType.parse("text/x-markdown; charset=utf-8"), post);
        Request.Builder builder3 = new Request.Builder();
        Request request2 = builder3
                .url(MainActivity.ur)
                .post(requestBody1)
                .build();
        CallHttp(request2);
        Log.i("search", "post = " + post);
    }
    public void http_feed_del(){
//        Gson gson = new Gson();
        String k = "";
//        Feedback fb=userfeed.get(item_number);//.setState("solveFeedback");

        try {
            JSONObject j = new JSONObject(userfeed1.get(item_number));
            j.put("state","solveFeedback");
             k = j.toString();
            Log.i("Json", "k= : " + k);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Log.i("userfeed","userfeed.get(item_number)");
//        fb.setState("solveFeedback");

//        String a = "{\"state\":\"solveFeedback\"}";
//        String feed_back=gson.toJson(fb);
//       Log.i("feed_back",feed_back);
        RequestBody requestBody1 = RequestBody
                .create(MediaType.parse("text/x-markdown; charset=utf-8"), k);
        Request.Builder builder3 = new Request.Builder();
        Request request2 = builder3
                .url(MainActivity.ur)
                .post(requestBody1)
                .build();
        CallHttp_feed(request2);

    }
    public void CallHttp_feed(Request request)///
    {
        okhttp3.Call call1 = okHttpClient.newCall(request);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("info", " GET请求失败！！！");
                Log.i("info", " e  = " + e.toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                 String eed_content_update = response.body().string();
                Log.i("info", " GET请求成功！！！");
            }
        });
    }
    public void CallHttp(final Request request) {
        okhttp3.Call call1 = okHttpClient.newCall(request);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("info", " GET请求失败！！！");
                Log.i("info", " e  = " + e.toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                res = response.body().string();
                Log.i("info", " GET请求成功！！！");
                Log.i("info", " res = " + res);

                Thread mThread = new Thread() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        msg.obj = res;
                        msg.what = 0;
                        handle.sendMessage(msg);
                    }
                };
                mThread.start();
            }
        });
    }
    public void htt_SolvedFeedback(){
        String post = "{\"state\":\"getSolvedFeedback\"}";
        RequestBody requestBody1 = RequestBody
                .create(MediaType.parse("text/x-markdown; charset=utf-8"), post);
        Request.Builder builder3 = new Request.Builder();
        Request request2 = builder3
                .url(MainActivity.ur)
                .post(requestBody1)
                .build();
        CallHttp1(request2);
        Log.i("search", "post = " + post);
    }

    public void CallHttp1(final Request request) {
        okhttp3.Call call1 = okHttpClient.newCall(request);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("infoget", " GET请求失败！！！");
                Log.i("info", " e  = " + e.toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                res = response.body().string();
                Log.i("infoget", " GET请求成功！！！");
                Log.i("info", " res = " + res);
                Thread mThread = new Thread() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        msg.obj = res;
                        msg.what = 1;
                        handle.sendMessage(msg);
                    }
                };
                mThread.start();
            }
        });
    }
}
