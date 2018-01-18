package com.example.dtlp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtlp.Date.DateOperation;
import com.example.dtlp.Date.User;
import com.example.dtlp.Enter.ForgotPassword;
import com.example.dtlp.Enter.Registered;
import com.example.dtlp.start.loginActivity;
import com.example.dtlp.tap_fragment.loading_animation;
import com.example.dtlp.user_main.MainActivity_2;
import com.example.dtlp.view.CircleImageView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public static String UserID = "";//用户ID
    //114.115.210.8:8090
//    public static String URL ="http://192.168.0.138:8080/TotemDown/LoginServe?username=linyuanbin&password=123456";
    public static String URL ="http://114.115.210.8:8090/TotemDown/LoginServe?username=linyuanbin&password=123456";

    private CircleImageView qq;
    private TextView registered,forgotpassword;
    private ImageView iv_head;
    private EditText account,password;
    private Button login;

    private String APPKEY ="1c3678b10a353";
    private String APPSECRETE="63787b577405c5d08b466e609661a843";
    private String KEY = "31704cfaa4b17e8f90c7dac59dbec4dd";


    public static MainActivity instance = null;

    public static String  PASSWORD;
    String length = "";
    public boolean FLAG = false;
    DateOperation dateOperation = new DateOperation();

    OkHttpClient okHttpClient = new OkHttpClient();
//    private String url1 = "http://192.168.0.116:8080/TotemDown/LoginServe?username=linyuanbin&password=123456";

    Handler handle = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            String data = (String) msg.obj;
            Gson gson = new Gson();
            User user = gson.fromJson(data, User.class);

            String localAddress = user.getlocalAddress();

            Bitmap bitMap = BitmapFactory.decodeFile(localAddress);
            iv_head.setImageBitmap(bitMap);

        }
    };


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

        initView();

        length = account.getText().toString().trim();
        if (length.length()==11)
        {
            try {
                getlocalAddress();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        else
        {
            iv_head.setImageResource(R.drawable.head_portrait);
        }
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
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i("beforeTextChanged", "1111111111");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i("beforeTextChanged", "222222222");
            }
        });

    }
    public void doClick(View view)
    {
        switch (view.getId())
        {
//            case R.id.registered:
//                Intent intent = new Intent(MainActivity.this,Registered.class);
//                startActivity(intent);
//                break;
            case R.id.Login:
                if (password.getText().toString().equals(""))
                {
                    Toast.makeText(MainActivity.this, "请输入密码！！", Toast.LENGTH_SHORT).show();
                    return;
                }else {
//                    login();
                    intent();
                }
                break;
            case R.id.ForgotPassword:
                Intent intent1 = new Intent(MainActivity.this,ForgotPassword.class);
                startActivity(intent1);
                break;
            case R.id.registered:
//                registered();
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
                        Intent intent = new Intent(MainActivity.this,Registered.class);
                        startActivity(intent);
                    }
                });
                //显示注册界用下载的inde.xml文档中的show()方法
                registerPage.show(MainActivity.this);
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
        iv_head = (ImageView) findViewById(R.id.iv_head);
    }


    private void getlocalAddress() throws UnsupportedEncodingException {
        String name1 = "getHead";

        String key = "{\"state\":\""+name1+"\",\"UserTel\":\""+length+"\"}";
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
                Gson gson = new Gson();
                User user = gson.fromJson(res, User.class);
                if (user.getState().equals("true")) {

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

    private void intent()
    {
        Intent intent = new Intent(MainActivity.this, loading_animation.class);
        intent.putExtra("id","1");
        intent.putExtra("account",account.getText().toString());
        intent.putExtra("password",password.getText().toString());
        setResult(00,intent);
//                finish();
        startActivity(intent);
        MainActivity.this.finish();
    }

    private void login()
    {
        String aco = account.getText().toString();
        String pass = password.getText().toString();
        String post ="{\"state\":\"login\",\"UserName\":\""+aco+"\",\"UserPassword\":\""+pass+"\"}";
        RequestBody requestBody1 = RequestBody
                .create(MediaType.parse("text/x-markdown; charset=utf-8"),post);
        Request.Builder builder3 = new Request.Builder();
        Request request2 = builder3
                .url(URL)
                .post(requestBody1)
                .build();
        CallHttp(request2);
        Log.i("info", "post = " + post);
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
                    UserID=jsonObject.getJSONObject("userData").getString("UserID");
                    state = jsonObject.getJSONObject("userData").getString("state");
                    bir = jsonObject.getJSONObject("userData").getString("UserBirthday");
                    Log.i("json", "UserID = " + UserID);
                    Log.i("json", "state = " + state);
                    Log.i("json", "bir = " + bir);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //在dtlp文件夹下载在创建一个由用户ID命名的文件夹 用来保存此用户的信息
                File file1 = null;
                if (!UserID.equals("")) {
                    File file = new File(loginActivity.SDPATH + "dtlp/" + UserID);
                    file.mkdir();
                    System.out.println("创建了文件夹");

                    //在由用户ID命名的文件夹下创建一个TXT格式的文件来保存数据
                    file1 = new File(loginActivity.SDPATH + "dtlp/" + UserID + "/" + UserID + ".txt");
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
                        Log.i("info", " UserID = " + UserID);
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
        });
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}

