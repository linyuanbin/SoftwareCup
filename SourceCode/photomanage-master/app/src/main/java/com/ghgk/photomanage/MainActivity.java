package com.ghgk.photomanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ghgk.photomanage.Date.DateOperation;
import com.ghgk.photomanage.Enter.ForgotPassword;
import com.ghgk.photomanage.Enter.Registered;
import com.ghgk.photomanage.start.loginActivity;
import com.ghgk.photomanage.user_main.MainActivity_2;
import com.ghgk.photomanage.view.CircleImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import cn.bmob.v3.Bmob;
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


public class MainActivity extends FragmentActivity {

    public static String mangerID;
    private CircleImageView qq;
    private TextView registered,forgotpassword;
    private EditText account,password;
    private Button login;
    private String APPKEY ="1c3678b10a353";
    private String APPSECRETE="63787b577405c5d08b466e609661a843";
    private String KEY = "31704cfaa4b17e8f90c7dac59dbec4dd";
    public static MainActivity instance = null;
    public static String  PASSWORD;
    public boolean FLAG = false;
    DateOperation dateOperation = new DateOperation();
    OkHttpClient okHttpClient = new OkHttpClient();
   // public static String ur = "http://192.168.0.104:8080/TotemDown/managerServer?username=linyuanbin&password=123456";//实验室服务器
    public static   String ur = "http://114.115.210.8:8090/TotemDown/managerServer?username=linyuanbin&password=123456";//华为服务器
  //  public static   String ur = "http://www.coolbhu.cn/TotemDown/managerServer?username=linyuanbin&password=123456";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        instance = this;
        Bmob.initialize(this,KEY);
        //1.初始化sdk
        SMSSDK.initSDK(this,APPKEY,APPSECRETE);
        //2.到清单文件中配置信息 （添加网络相关权限以及一个activity信息）
        //初始化控件信息
        initView();
        //当账户改变时  密码自动变空
        account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                password.setText("");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    public void doClick(View view)
    {
        switch (view.getId())
        {
            case R.id.Login:
                if (password.getText().toString().equals(""))
                {
                    Toast.makeText(MainActivity.this, "请输入密码！！", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    login();
                }
                break;
            case R.id.ForgotPassword:

                RegisterPage registerPage=new RegisterPage();
                //注册回调事件
                registerPage.setRegisterCallback(new EventHandler(){
                    @Override
                    //事件完成后
                    public void afterEvent(int event, int result, Object data) {
                        //判断结果是否已经完成
                        if(result==SMSSDK.RESULT_COMPLETE){//解析完成
                            //获取数据data
                            HashMap<String,Object> maps= (HashMap<String, Object>) data;//数据强转
                            //国家
                            String country= (String) maps.get("country");
                            //手机号码
                            String phone= (String) maps.get("phone");
                            submitUserInfo(country,phone);
                        }
                        Intent intent1 = new Intent(MainActivity.this,ForgotPassword.class);
                        startActivity(intent1);
                    }
                });
                //显示注册界用下载的inde.xml文档中的show()方法
                registerPage.show(MainActivity.this);
                break;
            case R.id.registered:
//                registered();
                RegisterPage registerPage1=new RegisterPage();
                //注册回调事件
                registerPage1.setRegisterCallback(new EventHandler(){
                    @Override
                    //事件完成后
                    public void afterEvent(int event, int result, Object data) {
                        //判断结果是否已经完成
                        if(result==SMSSDK.RESULT_COMPLETE){//解析完成
                            //获取数据data
                            HashMap<String,Object> maps= (HashMap<String, Object>) data;//数据强转
                            //国家
                            String country= (String) maps.get("country");
                            //手机号码
                            String phone= (String) maps.get("phone");
                            submitUserInfo(country,phone);
                        }
                        Intent intent = new Intent(MainActivity.this,Registered.class);
                        startActivity(intent);
                    }
                });
                //显示注册界用下载的inde.xml文档中的show()方法
                registerPage1.show(MainActivity.this);
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
    private void initView(){
        qq = (CircleImageView) findViewById(R.id.iv_bottom);
        registered = (TextView) findViewById(R.id.registered);
        forgotpassword = (TextView) findViewById(R.id.ForgotPassword);
        account = (EditText) findViewById(R.id.Account);
        password = (EditText) findViewById(R.id.Password_);
        login = (Button) findViewById(R.id.Login);
    }
    private void login()
    {
//        String Account = account.getText().toString();
//        BmobQuery<user> query = new BmobQuery<>();
//        query.addWhereEqualTo("number",Account);
//        query.findObjects(new FindListener<user>() {
//            @Override
//            public void done(List<user> list, BmobException e)
//            {
//                if (e==null)
//                {
//                    for (user user : list)
//                    {
//                        PASSWORD = user.getPassword();
//                        if (password.getText().toString().equals(PASSWORD))
//                        {
//                            Toast.makeText(MainActivity.this, "登录成功！！", Toast.LENGTH_SHORT).show();
//                            Intent intent1 = new Intent(MainActivity.this,MainActivity_2.class);
//                            startActivity(intent1);
//                            MainActivity.this.finish();
//                        }
//                        else
//                        {
//                            Toast.makeText(MainActivity.this, "密码输入错误！！", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//                else
//                {
//                    Toast.makeText(MainActivity.this, "密码或者账号不对哦！！", Toast.LENGTH_SHORT).show();
//                }
//                if (list.size() == 0)
//                {
//                    Toast.makeText(MainActivity.this, "密码或者账号不对哦！！", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

      //请求
        String aco = account.getText().toString();
        String pass = password.getText().toString();
        String post ="{\"state\":\"login\",\"mName\":\""+aco+"\",\"mPassword\":\""+pass+"\"}";
        RequestBody requestBody1 = RequestBody
                .create(MediaType.parse("text/x-markdown; charset=utf-8"),post);
        Request.Builder builder3 = new Request.Builder();
        Request request2 = builder3
                .url(ur)
                .post(requestBody1)
                .build();
        CallHttp(request2);
        Log.i("info", "post = " + post);

    }

    public void CallHttp(Request request)
       //login
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
                Log.i("info", " GET请求成功！！！");
                Log.i("info", " GET请求成功！！！"+res);

                if (res.indexOf("<html>") != -1) {
                    login();

                } else {//html 判断

                    String state = "";
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        mangerID=jsonObject.getJSONObject("mangerData").getString("mIdCard");
                        state = jsonObject.getJSONObject("mangerData").getString("state");
//                    bir = jsonObject.getJSONObject("userData").getString("UserBirthday");
                        Log.i("json", "mIdCard = " + mangerID);
                        Log.i("json", "state = " + state);
//                    Log.i("json", "bir = " + bir);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    //在dtlp文件夹下载在创建一个由用户ID命名的文件夹 用来保存此用户的信息
                    File file1 = null;
                    if (!mangerID.equals("")) {
                        File file = new File(loginActivity.SDPATH + "dtlp/" + mangerID);
                        file.mkdir();
                        System.out.println("创建了文件夹");

                        //在由用户ID命名的文件夹下创建一个TXT格式的文件来保存数据
                        file1 = new File(loginActivity.SDPATH + "dtlp/" + mangerID + "/" + mangerID + ".txt");
                        try {
                            file1.createNewFile();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        System.out.println("创建了文件");
                    }
                    final File finalFile1 = file1;
                    final File finalFile2 = new File(loginActivity.SDPATH +"dtlp/"+"TLTTLI_MANAGER.txt");
                    final String finalState = state;
//                final String finalBir = bir;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (finalState.equals("true"))
                            {
//                                Log.i("caonima", " 1111111");
//                                //将服务器传回的用户信息写入到文件中
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

                              //  Log.i("caonima", " 222222222");
//                            Log.i("info", "res  = " + res.toString());
                                Toast.makeText(MainActivity.this, "登录成功！！", Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent(MainActivity.this, MainActivity_2.class);
                                startActivity(intent1);
                                MainActivity.this.finish();
                            }//得到的res为用户ID  保存到本地
                            else
                                Toast.makeText(MainActivity.this, "登录失败了" +
                                        "哦！！！", Toast.LENGTH_SHORT).show();

                        }
                    });

                }
      }
        });
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}

