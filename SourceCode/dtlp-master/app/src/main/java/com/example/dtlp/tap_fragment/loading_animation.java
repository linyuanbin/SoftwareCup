package com.example.dtlp.tap_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.dtlp.MainActivity;
import com.example.dtlp.R;
import com.example.dtlp.start.loginActivity;
import com.example.dtlp.user_main.MainActivity_2;
import com.example.dtlp.user_main.activity_main_search;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 阳瑞 on 2017/8/21.
 */
public class loading_animation extends AppCompatActivity {


    private WhorlView mWhorlView3;
    String j = "";
    int key = 0;
    String image = "";
    String aco = "";
    String pass = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.loading_animation);
        mWhorlView3 = (WhorlView) this.findViewById(R.id.whorl3);
        mWhorlView3.start();

        j = getIntent().getStringExtra("id");
        key = Integer.parseInt(j);

        switch (key)
        {
            //登录
            case 1:
                aco = getIntent().getStringExtra("account");
                pass = getIntent().getStringExtra("password");
                login();
                break;
            //以图搜图
            case 2:
                image = getIntent().getStringExtra("image");
                finsh();
                break;
        }
    }


    //以图搜图
    public void finsh()
    {
        String post = "{\"state\":\"getSimplePicture\n\",\"PAddress\":\""+image+"\"}";
        RequestBody requestBody1 = RequestBody
                .create(MediaType.parse("text/x-markdown; charset=utf-8"),post);
        Request.Builder builder3 = new Request.Builder();
        Request request2 = builder3
                .url(MainActivity.URL)
                .post(requestBody1)
                .build();
//        CallHttp(request2);

//    }
//    public void CallHttp(Request request)
//
//    {
        OkHttpClient okHttpClient = new OkHttpClient();
        okhttp3.Call call1 = okHttpClient.newCall(request2);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("info", " GET请求失败！！！");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String search = response.body().string();

                Log.i("search", "search = : " + search);

                if (search.equals("")||search.equals(null)) {


                }else {
                    Intent intent = new Intent(loading_animation.this, activity_main_search.class);
                    intent.putExtra("id", "2");
                    intent.putExtra("search", search);
                    setResult(00, intent);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }



    private void login()
    {
//        aco = account.getText().toString();
//         pass = password.getText().toString();
        String post ="{\"state\":\"login\",\"UserName\":\""+aco+"\",\"UserPassword\":\""+pass+"\"}";
        RequestBody requestBody1 = RequestBody
                .create(MediaType.parse("text/x-markdown; charset=utf-8"),post);
        Request.Builder builder3 = new Request.Builder();
        Request request2 = builder3
                .url(MainActivity.URL)
                .post(requestBody1)
                .build();
//        CallHttp(request2);
//        Log.i("info", "post = " + post);
//    }
//    public void CallHttp(Request request)
//    {
        OkHttpClient okHttpClient = new OkHttpClient();
        okhttp3.Call call1 = okHttpClient.newCall(request2);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Intent intent = new Intent(loading_animation.this, MainActivity.class);
                startActivity(intent);
                loading_animation.this.finish();
//                runOnUiThread();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(loading_animation.this, "网络错误！！", Toast.LENGTH_SHORT).show();

                    }
                });
                Log.i("info", " GET请求失败！！！");
                Log.i("info", " e  = "  + e .toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String res = response.body().string();
                String state="";
                String bir = "";
//                final Date[ date = {new Date()};
                Log.i("info", " GET请求成功！！！");
                Log.i("info", " res = " + res);

//                Gson gson = new Gson();
//                final User user = gson.fromJson(res, User.class);
//                gson.
//                UserID = user.getUserID();
//                user.

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    MainActivity.UserID=jsonObject.getJSONObject("userData").getString("UserID");
                    state = jsonObject.getJSONObject("userData").getString("state");
                    bir = jsonObject.getJSONObject("userData").getString("UserBirthday");
                    Log.i("json", "UserID = " + MainActivity.UserID);
                    Log.i("json", "state = " + state);
                    Log.i("json", "bir = " + bir);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //在dtlp文件夹下载在创建一个由用户ID命名的文件夹 用来保存此用户的信息
                File file1 = null;
                if (!MainActivity.UserID.equals("")) {
                    File file = new File(loginActivity.SDPATH + "dtlp/" + MainActivity.UserID);
                    file.mkdir();
                    System.out.println("创建了文件夹");

                    //在由用户ID命名的文件夹下创建一个TXT格式的文件来保存数据
                    file1 = new File(loginActivity.SDPATH + "dtlp/" + MainActivity.UserID + "/" + MainActivity.UserID + ".txt");
                    try {
                        file1.createNewFile();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    System.out.println("创建了文件");
                }
                final File finalFile1 = file1;
                final File finalFile2 = new File(loginActivity.SDPATH +"dtlp/"+"TLTTLI.txt");
                final String finalState = state;
                final String finalBir = bir;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("info", " UserID = " + MainActivity.UserID);
//                        user.getState();
                        if (finalState.equals("true"))
                        {
                            //将服务器传回的用户信息写入到文件中
                            BufferedWriter out = null;
                            try {
                                out = new BufferedWriter(new FileWriter(finalFile1));
                                out.write(res); // \r\n即为换行
                                out.flush(); // 把缓存区内容压入文件
                                out.close(); // 最后记得关闭文件
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            //将服务器传回的用户信息写入到TLTTLI文件中
                            BufferedWriter out1 = null;
                            try {
                                out1 = new BufferedWriter(new FileWriter(finalFile2));
                                out1.write(res); // \r\n即为换行
                                out1.flush(); // 把缓存区内容压入文件
                                out1.close(); // 最后记得关闭文件
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                            SimpleDateFormat timeformat = new java.text.SimpleDateFormat(
                                    "yyyy-MM-dd");

                            if (finalBir.equals("")||finalBir.equals(null))
                            {

                            }else {

                                Date date = new Date(finalBir); //timeformat.parse(finalBir);
                                String time = new SimpleDateFormat("yyyy-MM-dd").format(date);//SimpleDateFormate("yyyy-MM-dd HH:mm:ss").format(new Date());


                                try {
                                    BufferedReader br = new BufferedReader(new FileReader(
                                            finalFile1));// 读取原始json文件
                                    StringBuilder user = new StringBuilder();
                                    String s = "";
                                    String ws = "";
                                    while ((s = br.readLine()) != null) {
                                        user.append(s);
                                    }
                                    try {
                                        JSONObject dataJson = new JSONObject(user.toString());// 创建一个包含原始json串的json对象
                                        JSONObject jsonObject1 = dataJson.getJSONObject("userData");
                                        jsonObject1.put("UserBirthday", time);
                                        ws = dataJson.toString();
                                    } catch (JSONException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                    BufferedWriter bw = new BufferedWriter(new FileWriter(
                                            finalFile1));// 输出新的json文件
                                    bw.write(ws);
                                    bw.flush();
                                    br.close();
                                    bw.close();

                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
//                            Log.i("info", "res  = " + res.toString());
                            Toast.makeText(loading_animation.this, "登录成功！！", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(loading_animation.this, MainActivity_2.class);
                            startActivity(intent1);
                            loading_animation.this.finish();
                        }
                        //得到的res为用户ID  保存到本地
                        else {
                            Toast.makeText(loading_animation.this, "登录失败了" +
                                    "哦！！！", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(loading_animation.this, MainActivity.class);
                            startActivity(intent);
                            loading_animation.this.finish();
                        }
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }
}
