package com.example.dtlp.side_pull_box;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtlp.Date.User;
import com.example.dtlp.Enter.ForgotPassword;
import com.example.dtlp.MainActivity;
import com.example.dtlp.R;
import com.example.dtlp.start.loginActivity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 阳瑞 on 2017/6/4.
 */
public class SetUp extends Activity {

    private LinearLayout ChangPassword,ChangTel;
    private EditText UserFeedback_;
    private TextView tel_;
    private Button sure;
    String UserTel = "";
    String Feedback = "";

    OkHttpClient okHttpClient = new OkHttpClient();

    private static final String MOBILE_PATTERN = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    private Pattern pattern = Pattern.compile(MOBILE_PATTERN);
    private Matcher matcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.side_pull_box_setup);
        initview();

        String userdata = "";
        File filename = new File(loginActivity.SDPATH + "dtlp/" + MainActivity.UserID + "/" + MainActivity.UserID + ".txt"); // 要读取以上路径的input。txt文件
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
//        Log.i("userdata", "userdata: = " + userdata);
        //解析得到的Json数据 得到用户名字
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(userdata);
            UserTel=jsonObject.getJSONObject("userData").getString("UserTel");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tel_.setText(UserTel);
    }

    private void initview() {
        ChangPassword = (LinearLayout) findViewById(R.id.ChangPassword);
        ChangTel = (LinearLayout) findViewById(R.id.ChangTel);
        UserFeedback_ = (EditText) findViewById(R.id.UserFeedback_);
        sure = (Button) findViewById(R.id.sure);
        tel_ = (TextView) findViewById(R.id.tel_);
    }
    public void doClick(View v) throws UnsupportedEncodingException {
        switch (v.getId())
        {
//            case R.id.ChangPassword:
//
//                Intent intent = new Intent(SetUp.this, ForgotPassword.class);
//                startActivity(intent);
//                break;
            case R.id.ChangPassword:
                RegisterPage registerPage=new RegisterPage();
                //注册回调事件
                registerPage.setRegisterCallback(new EventHandler(){
                    @Override
                    //事件完成后
                    public void afterEvent(int event, int result, Object data) {
                        //判断结果是否已经完成
                        if(result== SMSSDK.RESULT_COMPLETE){//解析完成
                            //获取数据data
                            HashMap<String,Object> maps= (HashMap<String, Object>) data;//数据强转
                            //国家
                            String country= (String) maps.get("country");
                            //手机号码
                            String phone= (String) maps.get("phone");
                            submitUserInfo(country,phone);
                        }
                        Intent intent = new Intent(SetUp.this, ForgotPassword.class);
                        startActivity(intent);
                    }
                });
                //显示注册界用下载的inde.xml文档中的show()方法
                registerPage.show(SetUp.this);
                break;
            case R.id.ChangTel:
                RegisterPage registerPage1=new RegisterPage();
                //注册回调事件
                registerPage1.setRegisterCallback(new EventHandler(){
                    @Override
                    //事件完成后
                    public void afterEvent(int event, int result, Object data) {
                        //判断结果是否已经完成
                        if(result== SMSSDK.RESULT_COMPLETE){//解析完成
                            //获取数据data
                            HashMap<String,Object> maps= (HashMap<String, Object>) data;//数据强转
                            //国家
                            String country= (String) maps.get("country");
                            //手机号码
                            String phone= (String) maps.get("phone");
                            submitUserInfo(country,phone);
                        }
                        setup_tel_Dialog();
                    }
                });
                //显示注册界用下载的inde.xml文档中的show()方法
                registerPage1.show(SetUp.this);
                break;
            case R.id.sure:
                 Feedback = UserFeedback_.getText().toString();
                flash2();
                UserFeedback_.setText("");
                Toast.makeText(SetUp.this, "反馈成功了哦！！！", Toast.LENGTH_SHORT).show();

                break;
        }
    }
    //    新建提交方法 提交用户信息到服务器在监听中返回结果
    public void submitUserInfo(String country,String phone){
        Random r=new Random();//获得一个随机数
        String uid=Math.abs(r.nextInt())+"";
        String nickName="MyApp";
        SMSSDK.submitUserInfo(uid,nickName,null,country,phone);
    }
    public void flash2() throws UnsupportedEncodingException {
        String name1 = "request";

        String key = "{\"state\":\"feedback\",\"UserID\":\""+ MainActivity.UserID+"\",\"UserTel\":\""+UserTel+"\"" +
                ",\"Content\":\""+Feedback+"\"}";
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
                Gson gson = new Gson();
                User user = gson.fromJson(res, User.class);
//                        user.getState();
                if (user.getState().equals("true")) {
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(SetUp.this, "反馈成功了哦！！！", Toast.LENGTH_SHORT).show();
//                        }
//                    });
                    Log.i("info", "反馈成功了哦！！！");
                }
            }
        });
    }

    public void setup_tel_Dialog()
    {
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.side_pull_box_setup_tel,
                (ViewGroup) findViewById(R.id.setup_tel));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(layout);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                EditText setup_tel_text = (EditText) layout.findViewById(R.id.setup_tel_text);
                if (!setup_tel_text.getText().toString().equals("")) {
                    String tel =setup_tel_text.getText().toString();
                    if (!validatenumber(tel))
                    {
                        Toast.makeText(SetUp.this, "亲 请输入正确的电话号码格式哦！！！", Toast.LENGTH_SHORT).show();
                    }else {
                        File filename = new File(loginActivity.SDPATH + "dtlp/" + MainActivity.UserID + "/" + MainActivity.UserID + ".txt");

//                        ema = personal_information_email_text.getText().toString();
                        //当用户修改了自己的信息时 同时也对保存在手机上的文件进行修改
                        try {
                            BufferedReader br = new BufferedReader(new FileReader(
                                    filename));// 读取原始json文件
                            StringBuilder user = new StringBuilder();
                            String s = "";
                            String ws = "";
                            while ((s = br.readLine()) != null) {
                                user.append(s);
                            }
                            try {
                                JSONObject dataJson = new JSONObject(user.toString());// 创建一个包含原始json串的json对象
                                JSONObject jsonObject1 = dataJson.getJSONObject("userData");
                                jsonObject1.put("UserTel",tel );
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

                        //像服务器发送请求修改密码
                        //将修改好的用户信息发送给服务器端
                        try {
                            BufferedReader br = new BufferedReader(new FileReader(
                                    filename));// 读取原始json文件
                            StringBuilder user = new StringBuilder();
                            String s = "";
                            while ((s = br.readLine()) != null) {
                                user.append(s);
                            }
                            try {
                                JSONObject dataJson = new JSONObject(user.toString());// 创建一个包含原始json串的json对象
                                JSONObject jsonObject1 = dataJson.getJSONObject("userData");
                                jsonObject1.put("state","update");

                                RequestBody requestBody2 = RequestBody
                                        .create(MediaType.parse("text/x-markdown; charset=utf-8"),jsonObject1.toString());
                                Request.Builder builder2 = new Request.Builder();
                                Request request2 = builder2
                                        .url(MainActivity.URL)
                                        .post(requestBody2)
                                        .build();
//                                CallHttp(request2);
                                okhttp3.Call call1 = okHttpClient.newCall(request2);
                                call1.enqueue(new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                        Log.i("info", " GET请求失败！！！");
                                        Log.i("info", " e  = "  + e .toString());
                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {

                                        final String res = response.body().string();
                                        Log.i("info", " GET请求成功！！！");
                                        Log.i("info", " res = "+ res);
                                    }
                                });
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            br.close();


                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        tel_.setText(tel);
                        Intent intent = new Intent(SetUp.this,MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
    public boolean validatenumber(String number)
    {
        matcher = pattern.matcher(number);
        return matcher.matches();
    }
}
